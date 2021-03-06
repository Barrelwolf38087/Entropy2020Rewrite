package frc.robot.subsystems.shooter;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.Storage;

/** Singleton that represents the shooter mechanism. */
public class Shooter extends SubsystemBase {
  private final SpeedLookupTable mLookupTable = SpeedLookupTable.getInstance();

  // MIGRATED STATE BEGIN
  public enum State {
    IDLE, // Default state when State is not SHOOTING
    PREPARE_TO_SHOOT,
    SHOOT_BALL,
    SHOOT_BALL_COMPLETE,
    SHOOTING_COMPLETE
  }

  private State state = State.IDLE;
  private boolean isSpinningUp = false;
  private int currentCooldown = Constants.SHOOT_COOLDOWN;

  // Note: ALWAYS access this through getShootButtonWasPressed() so that it is always reset.
  private boolean shootButtonWasPressed = false;
  // MIGRATED STATE END

  private final double MAX_SPEED = 3600.0;
  // private static final double SPEED_DEADBAND = 20;
  private final double SPEED_DEADBAND = 5;
  private final double DROP_DEADBAND = 250;
  private final int SPEED_DEADBAND_DELAY = 15;
  private final double FEEDFORWARD = 1023d / MAX_SPEED;
  // private static final double P = (.3 * 1023) / 50;
  // private static final double I = 0.2;
  // private static final double D = 0.1;
  private final double P = .4;
  private final double I = 0;
  private final double D = 0;

  // a minimum acountdown
  private static final int MIN_SHOT_COUNTDOWN = 100;
  private int mShotCountdown = MIN_SHOT_COUNTDOWN;

  // TEMPORARY STUFF BEGINS HERE
  private final int ROLLER_PORT = Constants.SHOOTER_MASTER;
  private final int ROLLER_SLAVE_PORT = Constants.SHOOTER_SLAVE;

  private final int DEFAULT_ROLLER_SPEED = 2000; // Encoder ticks per 100ms, change this value
  private int mVelocityAdjustment = 0;
  private final int VELOCITY_ADJUSTMENT_BUMP = 100; // TODO: Use config system
//    Config.getInstance().getInt(Key.SHOOTER__VELOCITY_ADJUSTMENT);

  private boolean mHasHadCurrentDrop = false;

  // Aggregation
  private static Shooter instance;
  private final PIDRoller mRoller;
  private double mDistance = 0;
  private int mTimeSinceWeWereAtVelocity = SPEED_DEADBAND_DELAY;
  private final Storage storage = Storage.getInstance();

  private Shooter() {
    mRoller = new PIDRoller(ROLLER_PORT, ROLLER_SLAVE_PORT, P, I, D, FEEDFORWARD);
  }

  public static synchronized Shooter getInstance() {
    if (instance == null) instance = new Shooter();
    return instance;
  }

  /** Starts the roller. */
  public void start() {

    mRoller.setSpeed(getAdjustedVelocitySetpoint());
    SmartDashboard.putNumber("ShooterCurrent", mRoller.getCurrent());
  }

  /** Stops the roller. */
  public void stop() {
    mRoller.setSpeed(0);
  }

  public void updateDistance(double dist) {
    mDistance = dist;
  }

  public int getSpeed() {
    return mRoller.getVelocity();
  }

  private int getAdjustedVelocitySetpoint() {
    double distance = mDistance; // for now

    int speed = (int) Math.round(SpeedLookupTable.getInstance().getSpeedFromDistance(distance));

    return speed + mVelocityAdjustment;
  }

  public void increaseVelocity() {
    mVelocityAdjustment += VELOCITY_ADJUSTMENT_BUMP;
  }

  public void decreaseVelocity() {
    mVelocityAdjustment -= VELOCITY_ADJUSTMENT_BUMP;
  }

  public void resetVelocity() {
    mVelocityAdjustment = 0;
  }

  public int getVelocityAdjustment() {
    return mVelocityAdjustment;
  }

  /** Returns whether roller is at full speed. */
  // UPDATE:
  public boolean isAtVelocity() {

    // New Concept: Velocity FLOOR
    // our velocity setpoint will be slightly higher than it needs to be
    // allow velocity to be sliughtly lower, but operate as a floor
    boolean isAtVelocity = (mRoller.getVelocity() - (getAdjustedVelocitySetpoint() - 50) >= 0);

    SmartDashboard.putNumber("Velocity Countdown", mTimeSinceWeWereAtVelocity);
    SmartDashboard.putBoolean("Has Had Current Drop", mHasHadCurrentDrop);

    if (isAtVelocity) {
      // decrement
      mTimeSinceWeWereAtVelocity--;
    } else {
      // reset the time since we were at velocity
      mTimeSinceWeWereAtVelocity = SPEED_DEADBAND_DELAY;
    }
    // if the time is at least 0, we are "at velocity"
    boolean isAtVelocityDebounced = mTimeSinceWeWereAtVelocity <= 0;

    return isAtVelocityDebounced;

    // return false;
  }

  public boolean isBallFired() {
    boolean didDropVelocity =
      Math.abs(mRoller.getVelocity() - getAdjustedVelocitySetpoint()) >= (DROP_DEADBAND);
    boolean ballFired = didDropVelocity;
    if (ballFired) {
      System.out.println("BALL FIRED!");
    }
    return ballFired;
  }

  // Used in TEST mode only
  public void setOutput(double output) {
    mRoller.setPercentOutput(output);
  }

  // NEW STUFF BELOW THIS POINT

  // The state machine logic from the old code.
  @Override
  public void periodic() {
    if (Robot.getInstance().getGlobalState() == Robot.State.SHOOTING) {
      switch (state) {
        case IDLE:
//        checkTransitionToClimbing();
//        mRobotLogger.warn("Shooting state is idle");
          stop();
          break;
        case PREPARE_TO_SHOOT:
          stop();

          /* Starts roller */
          start();
          isSpinningUp = false;

          // make robot pass though cooldown timer
          // should help between shots and solve low velocity issue
          if (currentCooldown > 0) {
            currentCooldown--;
            return; // skip the rest of this loop
          }

          /* If rollers are spun up, changes to next state */
          if (isAtVelocity() /* TODO: && Target Acquired */) {
            state = State.SHOOT_BALL;
            // mFireTimer.start();

            // reset cooldown timer
            currentCooldown = Constants.SHOOT_COOLDOWN;
          }

          if (getShootButtonWasPressed()) {
            state = State.SHOOTING_COMPLETE;
            stop();
          }
          break;
        case SHOOT_BALL:
          storage.ejectBall();

          start();

          // turn off shooting
          if (getShootButtonWasPressed() || storage.isEmpty()) {
            state = State.SHOOTING_COMPLETE;
            storage.stop();
          }

          /* If finished shooting, changes to next state*/
          if (isBallFired()) {
            state = State.SHOOT_BALL_COMPLETE;
          }
          break;
        case SHOOT_BALL_COMPLETE:
          /* Decrements storage */
          storage.removeBall();

          // We are gonna continue shooting until
          // /* Goes to complete if storage is empty, otherwise fires again */
          // if (mStorage.isEmpty()) {
          //   mShootingState = ShootingState.SHOOTING_COMPLETE;
          // } else {
          //   mShootingState = ShootingState.PREPARE_TO_SHOOT;
          // }

          // shooting is a toggle
          state = State.PREPARE_TO_SHOOT;
          break;
        case SHOOTING_COMPLETE:

          /* Stops the roller and returns to intake state */
          stop();
          state = State.IDLE;

          Robot.getInstance().transitionToIntake();
          break;
        default:
//        mRobotLogger.error("Invalid shooting state");
          break;
      }
    }
  }

  public boolean getIsSpinningUp() {
    return isSpinningUp;
  }

  public void shoot() {
    if (Robot.getInstance().getGlobalState() != Robot.State.SHOOTING) {
      Robot.getInstance().transitionToShooting();
    }
    shootButtonWasPressed = true;
  }

  /** Sets the shooting state to preparing if it's not already */
  public void prepareToShoot() {
    if (state == State.IDLE)
      state = State.PREPARE_TO_SHOOT;
  }

  private boolean getShootButtonWasPressed() {
    if (shootButtonWasPressed) {
      shootButtonWasPressed = false;
      return true;
    } else {
      return false;
    }
  }
}


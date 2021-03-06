package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/** Add your docs here. */
public class Intake extends SubsystemBase {
  private int mOverCurrentCount = 0;

  // initial cooldown because our startup of the roller induces a countdown
  private int mOverCurrentCountdown = 30;

  private final int ROLLER_PORT = Constants.INTAKE_ROLLER;

  // TODO: Replace with config
  private final double ROLLER_SPEED = 1;

  private final WPI_TalonSRX mRoller;

  private static Intake sInstance;

  public static synchronized Intake getInstance() {
    if (sInstance == null) {
      sInstance = new Intake();
    }
    return sInstance;
  }

  private Intake() {
    mRoller = new WPI_TalonSRX(ROLLER_PORT);
    mRoller.configFactoryDefault();
  }

  public void barf() {
    mRoller.set(ControlMode.PercentOutput, -ROLLER_SPEED);
  }

  public void resetOvercurrentCooldown() {
    mOverCurrentCountdown = 30;
  }

  public void start() {
    mRoller.set(ControlMode.PercentOutput, ROLLER_SPEED);
  }

  /** Stops the roller. */
  public void stop() {
    // TODO: set local state to idle
    mRoller.set(ControlMode.PercentOutput, 0);
  }

  // This is for TEST mode only
  public void setOutput(double output) {
    mRoller.set(ControlMode.PercentOutput, output);
  }

  public void updateSmartDashboard() {
    SmartDashboard.putNumber("Intake Current Countdown", mOverCurrentCountdown);
    double current = mRoller.getStatorCurrent();
    SmartDashboard.putNumber("Intake Current", current);
    SmartDashboard.putBoolean("Intake Spinning Up", false);
    SmartDashboard.putBoolean("Intake Overcurrent", false);
    SmartDashboard.putBoolean("Intake Overcurrent Debounced", false);
  }
}

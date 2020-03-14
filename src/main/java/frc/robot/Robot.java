/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.OI.OperatorInterface;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Storage;
import frc.robot.subsystems.shooter.Shooter;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static Robot instance;
  public static Robot getInstance() {
    if (instance == null) {
      instance = new Robot();
    }

    return instance;
  }

  private final Drivetrain drivetrain;

  private final Shooter shooter;
  private final Intake intake;
  private final Storage storage;

  private Robot() {
    drivetrain = Drivetrain.getInstance();
    shooter = Shooter.getInstance();
    intake = Intake.getInstance();
    storage = Storage.getInstance();
  }

  private boolean auto = false;

  public enum State {
    IDLE, // Default state
    INTAKE,
    SHOOTING,
    CLIMBING
  }

  private State globalState = State.IDLE;

  public State getGlobalState() {
    return globalState;
  }

  public void transitionToShooting() {
//    mRobotLogger.log("Changing to shoot because our driver said so...");
    //noinspection SwitchStatementWithTooFewBranches
    switch (globalState) {

      /* Disables intake if transitioning from intake */
      case INTAKE:
        intake.stop();
        storage.stop();
        // TODO: Make sure this gets set once we migrate the intake state!
//        mIntakeState = IntakeState.IDLE;
        break;
      default:
        break;
    }
    globalState = State.SHOOTING;

    shooter.prepareToShoot();
  }

  public void transitionToIntake() {
    globalState = State.INTAKE;
  }


  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {
    OperatorInterface.mapTest();
    auto = false;
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void autonomousInit() {
    auto = true;
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    OperatorInterface.mapTeleop();

  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.TeleopDrive;

import java.util.stream.Stream;

public class Drivetrain extends SubsystemBase {
  private static Drivetrain instance;

  @SuppressWarnings("FieldCanBeLocal")
  private WPI_TalonSRX leftMaster, leftSlave, rightMaster, rightSlave;

  // WPILib drivetrain, which contains most of the driving logic.
  private DifferentialDrive drive;

  public static Drivetrain getInstance() {
    if (instance == null)
      instance = new Drivetrain();

    return instance;
  }

  private Drivetrain() {
    leftMaster = new WPI_TalonSRX(Constants.DRIVE_LEFT_MASTER);
    leftSlave = new WPI_TalonSRX(Constants.DRIVE_LEFT_SLAVE);
    rightMaster = new WPI_TalonSRX(Constants.DRIVE_RIGHT_MASTER);
    rightSlave = new WPI_TalonSRX(Constants.DRIVE_RIGHT_SLAVE);

    configTalons(leftMaster, rightMaster);
    leftSlave.follow(leftMaster);
    rightSlave.follow(rightMaster);

    drive = new DifferentialDrive(leftMaster, rightMaster);
    setDefaultCommand(new TeleopDrive());
  }

  private static void configTalons(WPI_TalonSRX... talons) {
    Stream.of(talons).forEach(Drivetrain::configTalon);
  }

  // TODO: Create TalonConfig class similar to PIDConfig
  private static void configTalon(WPI_TalonSRX talon) {
    talon.configFactoryDefault();
    talon.configNominalOutputForward(0., 0);
    talon.configNominalOutputReverse(0., 0);
    talon.configPeakOutputForward(1, 0);
    talon.configPeakOutputReverse(-1, 0);
    talon.configOpenloopRamp(0);
    talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
    talon.setSensorPhase(true);
    talon.setNeutralMode(NeutralMode.Brake);

    Constants.AUTO_DRIVE_PID_CONFIG.apply(talon);

    talon.configClosedLoopPeriod(0, 10);

    talon.configMotionCruiseVelocity(Constants.DEFAULT_CRUISE_VELOCITY);
    talon.configMotionAcceleration(Constants.DEFAULT_ACCEL);
  }

  public DifferentialDrive getDrive() {
    return drive;
  }
}

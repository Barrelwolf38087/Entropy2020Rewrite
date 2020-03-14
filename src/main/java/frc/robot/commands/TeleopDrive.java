package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI.OperatorInterface;
import frc.robot.OI.XboxController;
import frc.robot.subsystems.Drivetrain;
import frc.robot.util.MathUtil;

public class TeleopDrive extends CommandBase {
  private Drivetrain drivetrain;

  public TeleopDrive() {
    drivetrain = Drivetrain.getInstance();

    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {

    double throttle = OperatorInterface.getThrottle();
    double wheel = OperatorInterface.getWheel();
    boolean quickTurn = false;

    if (MathUtil.epsilonEquals(throttle, 0.0, 0.04)) {
      throttle = 0.0;
      quickTurn = true;
    }

    drivetrain.getDrive().curvatureDrive(throttle, wheel, quickTurn);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}

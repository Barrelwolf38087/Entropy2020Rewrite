package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.shooter.Shooter;

public class ShootCommand extends CommandBase {
  private Shooter shooter;
  private boolean done = false;

  public ShootCommand() {
    shooter = Shooter.getInstance();

    addRequirements(shooter);
  }

  @Override
  public void execute() {
    shooter.shoot();
    done = true;
  }

  @Override
  public boolean isFinished() {
    return done;
  }
}

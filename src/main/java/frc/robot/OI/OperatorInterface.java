package frc.robot.OI;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants;
import frc.robot.commands.ShootCommand;

public class OperatorInterface {

  public static final XboxController DRIVER_STICK = new XboxController(Constants.XBOX_CONTROLLER_PORT);
  public static final NykoController OPERATOR_STICK = new NykoController(Constants.NYKO_CONTROLLER_PORT);



  public static void mapTeleop() {
    OPERATOR_STICK.buttons.ONE_BUTTON.whenPressed(new ShootCommand());
  }

  public static void mapTest() {

  }

  public static void unregisterInputEvents() {

  }

  // TODO: D-Pad adjustment events
  // TODO: Feeder steer events

  public static double getThrottle() {
    return DRIVER_STICK.getRawAxis(XboxController.LEFT_Y_AXIS);
  }

  public static double getWheel() {
    return DRIVER_STICK.getRawAxis(XboxController.RIGHT_X_AXIS);
  }

  public static void setDriverRumble(boolean rumble) {
    DRIVER_STICK.setRumble(GenericHID.RumbleType.kLeftRumble, rumble ? 1 : 0);
    DRIVER_STICK.setRumble(GenericHID.RumbleType.kRightRumble, rumble ? 1 : 0);
  }
}

package frc.robot.OI;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class XboxController extends Joystick {

  public static class XboxButtons {

    // Button ids
    private static final int A = 1;
    private static final int B = 2;
    private static final int X = 3;
    private static final int Y = 4;
    private static final int LEFT_BUMPER = 5;
    private static final int RIGHT_BUMPER = 6;
    private static final int LEFT_STICK = 7;
    private static final int RIGHT_STICK = 8;
    private static final int MENU = 9;
    private static final int VIEW = 10;
    private static final int HOME = 11;
    private static final int DPAD_UP = 12;
    private static final int DPAD_DOWN = 13;
    private static final int DPAD_LEFT = 14;
    private static final int DPAD_RIGHT = 15;

    public final Button A_BUTTON;
    public final Button B_BUTTON;
    public final Button X_BUTTON;
    public final Button Y_BUTTON;
    public final Button LEFT_BUMPER_BUTTON;
    public final Button RIGHT_BUMPER_BUTTON;
    public final Button LEFT_STICK_BUTTON;
    public final Button RIGHT_STICK_BUTTON;
    public final Button MENU_BUTTON;
    public final Button VIEW_BUTTON;
    public final Button HOME_BUTTON;
    public final Button DPAD_UP_BUTTON;
    public final Button DPAD_DOWN_BUTTON;
    public final Button DPAD_LEFT_BUTTON;
    public final Button DPAD_RIGHT_BUTTON;

    public XboxButtons(XboxController controller) {
      A_BUTTON = new JoystickButton(controller, A);
      B_BUTTON = new JoystickButton(controller, B);
      X_BUTTON = new JoystickButton(controller, X);
      Y_BUTTON = new JoystickButton(controller, Y);
      LEFT_BUMPER_BUTTON = new JoystickButton(controller, LEFT_BUMPER);
      RIGHT_BUMPER_BUTTON = new JoystickButton(controller, RIGHT_BUMPER);
      LEFT_STICK_BUTTON = new JoystickButton(controller, LEFT_STICK);
      RIGHT_STICK_BUTTON = new JoystickButton(controller, RIGHT_STICK);
      MENU_BUTTON = new JoystickButton(controller, MENU);
      VIEW_BUTTON = new JoystickButton(controller, VIEW);
      HOME_BUTTON = new JoystickButton(controller, HOME);
      DPAD_UP_BUTTON = new JoystickButton(controller, DPAD_UP);
      DPAD_DOWN_BUTTON = new JoystickButton(controller, DPAD_DOWN);
      DPAD_LEFT_BUTTON = new JoystickButton(controller, DPAD_LEFT);
      DPAD_RIGHT_BUTTON = new JoystickButton(controller, DPAD_RIGHT);
    }
  }

  // Axes
  public static final int LEFT_X_AXIS = 0;
  public static final int LEFT_Y_AXIS = 1;
  public static final int LEFT_TRIGGER_AXIS = 2;
  public static final int RIGHT_TRIGGER_AXIS = 3;
  public static final int RIGHT_X_AXIS = 4;
  public static final int RIGHT_Y_AXIS = 5;

  public final XboxButtons buttons;

  /**
   * Construct an instance of an Xbox controller. The joystick index is the USB port on the drivers
   * station.
   *
   * @param port The port on the Driver Station that the controller is plugged into.
   */
  public XboxController(int port) {
    super(port);
    buttons = new XboxButtons(this);
  }
}
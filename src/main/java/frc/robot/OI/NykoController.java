package frc.robot.OI;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class NykoController extends Joystick {

  public static class NykoButtons {

    // Button ids
    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;
    public static final int FOUR = 4;
    public static final int LEFT_BUMPER = 5;
    public static final int RIGHT_BUMPER = 6;
    public static final int LEFT_TRIGGER = 7;
    public static final int RIGHT_TRIGGER = 8;
    public static final int NINE = 9;
    public static final int TEN = 10;
    public static final int ELEVEN = 11;
    public static final int LEFT_STICK = 12;
    public static final int RIGHT_STICK = 13;

    public final Button ONE_BUTTON;
    public final Button TWO_BUTTON;
    public final Button THREE_BUTTON;
    public final Button FOUR_BUTTON;
    public final Button LEFT_BUMPER_BUTTON;
    public final Button RIGHT_BUMPER_BUTTON;
    public final Button LEFT_TRIGGER_BUTTON;
    public final Button RIGHT_TRIGGER_BUTTON;
    public final Button NINE_BUTTON;
    public final Button TEN_BUTTON;
    public final Button ELEVEN_BUTTON;
    public final Button LEFT_STICK_BUTTON;
    public final Button RIGHT_STICK_BUTTON;

    public NykoButtons(NykoController controller) {
      ONE_BUTTON = new JoystickButton(controller, ONE);
      TWO_BUTTON = new JoystickButton(controller, TWO);
      THREE_BUTTON = new JoystickButton(controller, THREE);
      FOUR_BUTTON = new JoystickButton(controller, FOUR);
      LEFT_BUMPER_BUTTON = new JoystickButton(controller, LEFT_BUMPER);
      RIGHT_BUMPER_BUTTON = new JoystickButton(controller, RIGHT_BUMPER);
      LEFT_TRIGGER_BUTTON = new JoystickButton(controller, LEFT_TRIGGER);
      RIGHT_TRIGGER_BUTTON = new JoystickButton(controller, RIGHT_TRIGGER);
      NINE_BUTTON = new JoystickButton(controller, NINE);
      TEN_BUTTON = new JoystickButton(controller, TEN);
      ELEVEN_BUTTON = new JoystickButton(controller, ELEVEN);
      LEFT_STICK_BUTTON = new JoystickButton(controller, LEFT_STICK);
      RIGHT_STICK_BUTTON = new JoystickButton(controller, RIGHT_STICK);
    }
  }

  // Axes
  public static final int LEFT_X_AXIS = 0;        // X Axis on Driver Station
  public static final int LEFT_Y_AXIS = 1;        // Y Axis on Driver Station
  public static final int RIGHT_Y_AXIS = 2;    // Z Axis on Driver Station
  public static final int RIGHT_X_AXIS = 3;    // Rotate Axis on Driver Station

  public final NykoButtons buttons;

  /**
   * Construct an instance of a Nyko controller. The joystick index is the USB port on the drivers
   * station.
   *
   * @param port The port on the Driver Station that the controller is plugged into.
   */
  public NykoController(int port) {
    super(port);
    buttons = new NykoButtons(this);
  }
}
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.util.PIDConfig;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static final int TALON_CONFIG_TIMEOUT_MS = 250;

  // Controllers
  public static final int XBOX_CONTROLLER_PORT = 0;
  public static final int NYKO_CONTROLLER_PORT = 1;

  // Talon mappings
  public static final int DRIVE_LEFT_MASTER  = 1;
  public static final int DRIVE_LEFT_SLAVE   = 2;
  public static final int DRIVE_RIGHT_MASTER = 3;
  public static final int DRIVE_RIGHT_SLAVE  = 4;
  public static final int STORAGE_TOP        = 5;
  public static final int SHOOTER_MASTER     = 6;
  public static final int SHOOTER_SLAVE      = 7;
  public static final int STORAGE_BOTTOM     = 8;
  public static final int TURRET             = 9;
  public static final int INTAKE_ROLLER      = 10;


  // Various drivetrain stuff
  public static final int DEFAULT_CRUISE_VELOCITY = 900;
  public static final int DEFAULT_ACCEL           = 750;


  // PID gains
  // TODO: Move this into the config system
  public static final double AUTO_DRIVE_P          = 4;
  public static final double AUTO_DRIVE_I          = 0.0005;
  public static final double AUTO_DRIVE_D          = 0;
  public static final int AUTO_DRIVE_PID_TOLERANCE = 10; // Encoder ticks


  // PID configs (aggregations of gains)
  public static final PIDConfig AUTO_DRIVE_PID_CONFIG = new PIDConfig(
    AUTO_DRIVE_P,
    AUTO_DRIVE_I,
    AUTO_DRIVE_D
  );

  public static final double SHOOTER_P = 0.4;
  public static final PIDConfig SHOOTER_PID_CONFIG = new PIDConfig()
    .update(PIDConfig.Gain.P, SHOOTER_P);

  // Various auto stuff
  public static final int AUTO_DEBOUNCE_TICKS = 10; // ~0.2 seconds

  // Various shooter stuff
  public static final int SHOOT_COOLDOWN = 10;

  // Various storage stuff
  public static final int STORAGE_CURRENT_LIMIT = 7;


}

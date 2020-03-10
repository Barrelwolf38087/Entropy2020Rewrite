package frc.robot.util;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.robot.Constants;

import java.util.Arrays;

public class PIDConfig {
  public enum Gain {
    P(0), I(1), D(2), F(3);

    private int id;

    Gain(int id) {
      this.id = id;
    }

    public int id() {
      return id;
    }
  }

  // Stored as an array to avoid boilerplate. Without this, we'd need a long, redundant switch statement in get(Gain).
  private double[] gains = {0, 0, 0, 0};

  public PIDConfig(double p, double i, double d, double f) {
    gains[0] = p;
    gains[1] = i;
    gains[2] = d;
    gains[3] = f;
  }

  public PIDConfig(double p, double i, double d) {
    this(p, i, d, 0);
  }

  public PIDConfig() {
    this(0, 0, 0, 0);
  }

  public double get(Gain gain) {
    return gains[gain.id()];
  }

  public double[] getAll() {
    return Arrays.copyOf(gains, gains.length);
  }

  public PIDConfig update(Gain gain, double newValue) {
    gains[gain.id()] = newValue;

    return this;
  }

  public void apply(WPI_TalonSRX talon) {
    talon.config_kP(0, get(Gain.P), Constants.TALON_CONFIG_TIMEOUT_MS);
    talon.config_kI(0, get(Gain.I), Constants.TALON_CONFIG_TIMEOUT_MS);
    talon.config_kD(0, get(Gain.D), Constants.TALON_CONFIG_TIMEOUT_MS);
    talon.config_kF(0, get(Gain.F), Constants.TALON_CONFIG_TIMEOUT_MS);
  }
}

package org.firstinspires.ftc.teamcode.FlywheelAdvanced;

public class PIDController {
    private double kp;
    private double ki;
    private double kd;
    private double dt;
    private double out_min;
    private double out_max;
    private double integral;
    private double prev_error;

    public void init (double kp, double ki, double kd, double dt) {
        out_min = -100.0;
        out_max = 100.0;
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        this.dt = dt;
        this.integral = 0.0;
        this.prev_error = 0.0;
    }

    public double update(double setpoint, double measurement) {
        double error = setpoint - measurement;

        double p_term = this.kp * error;

        this.integral += error * this.dt;
        double i_term = this.ki * this.integral;

        double derivative = (error - this.prev_error) / this.dt;
        double d_term = this.kd * derivative;

        double output = Math.max(this.out_min, Math.min(this.out_max, p_term + i_term + d_term));
        this.prev_error = error;
        return output;
    }
}

package org.firstinspires.ftc.teamcode.FlywheelAdvanced;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class Flywheel {
    //Clicks per rotation on encoder
    private double encoderCPM = 28; //change depending on motor later
    private double gearRatio = 1; //change depending on actual gearratio for badwolf

    //velocity feed forward constant, static friction, and proportional constant

    public double kS = 0.066;
    public double kV = 0.0003615;
    public double kP = 0.00052;

    //initialize motors again
    public DcMotorEx flywheel_motor;
    public Servo gate;

    //Because we're building custom loop, we don't want encoder.
        //This allows us to still read the motor encoder, but no run at speed it wants


    //tell our motors how fast to go:
    public double currentVelocity;
    public double targetVelocity;

    public boolean gate_open;

    public void setMotorPower() {
        double power = (this.kS * Math.signum(this.targetVelocity))
                + (this.kV * this.targetVelocity)
                + (this.kP * (this.targetVelocity - this.currentVelocity));

        this.flywheel_motor.setPower(power);
    }

    public void openGate(Telemetry telemetry) {
        if (this.currentVelocity == this.targetVelocity) {
            this.gate_open = true;
            telemetry.addData("Current Velocity", this.currentVelocity);
            telemetry.addData("isthegateopen", this.gate_open);
            telemetry.update();
        }
        if (this.gate_open && this.targetVelocity != 0) {
            this.gate.setPosition(0);
        } else {
            this.gate.setPosition(0.5);
        }
    }
}

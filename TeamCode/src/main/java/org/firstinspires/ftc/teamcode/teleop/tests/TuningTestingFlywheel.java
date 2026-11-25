package org.firstinspires.ftc.teamcode.teleop.tests;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.FlywheelAdvanced.PIDController;

@TeleOp
public class TuningTestingFlywheel extends LinearOpMode {
    private DcMotorEx flywheel;
    private boolean isY = false;
    private boolean wasY = false;

    @Override
    public void runOpMode() {
        flywheel = hardwareMap.get(DcMotorEx.class, "flywheel_motor_nectar");
        flywheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        flywheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        double kS = 0.066;
        double kV = 0.0003615;
        double kP = 0.00052;

        double targetVelocity = 1500;
        telemetry.addLine("Flywheel PID Ready");
        telemetry.update();
        double power = 0;

        waitForStart();

        while (opModeIsActive()) {

            double currentVelocity = flywheel.getVelocity();
            telemetry.addData("Power", power);
            telemetry.addData("Velocity", currentVelocity);
            telemetry.addData("Velocity constant", kV);
            telemetry.update();

            if (currentVelocity == targetVelocity) {
                telemetry.addData("Velocity constant: speed achieved", kV);
                telemetry.update();
            }

            if (gamepad1.a) {
                targetVelocity = 1500;
            }
            if (gamepad1.b) {
                targetVelocity = 2000;
            }
            if (gamepad1.x) {
                targetVelocity = 2500;
            }
            // double power = controller.update(targetVelocity, currentVelocity);
            wasY = isY;
            isY = gamepad1.y;
            if (!isY && wasY) {
                kP = kP + 0.00001;
            }


            power = (kS * Math.signum(targetVelocity))
                    + (kV * targetVelocity)
                    + (kP * (targetVelocity - currentVelocity));

            flywheel.setPower(power);


        }
        // flywheel.setPower(0);

    }
}

//This imports all the ftc commands and stuff
package org.firstinspires.ftc.teamcode.teleop;

//This is to import the teleop and opMode stuff
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//hlo
import static org.firstinspires.ftc.teamcode.MyTeamUtil.drive;
import static org.firstinspires.ftc.teamcode.MyTeamUtil.intake;
import static org.firstinspires.ftc.teamcode.MyTeamUtil.move_servos;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.List;
import java.util.ArrayList;

//hello
@TeleOp (name = "TeleOpBadWolf")
public class TeleOpBadWolf extends LinearOpMode {
    private List<DcMotor> wheel_motors = new ArrayList<>();
    private List<Servo> flower_servos = new ArrayList<>();
    private DcMotor frontLeftMotor;
    private DcMotor backLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backRightMotor;
    private DcMotorEx intakeMotor;
    private Servo flowerServo1;
    //hello
    private Servo flowerServo2;
    private DcMotorEx nectarFlywheel;
    private DcMotorEx pollenFlywheel;
    private Servo nectarGate;
    private Servo pollenGate;
    private Servo nectarHood;
    private Servo pollenHood;
    private DcMotorEx transferMotor;


    private boolean currentAButton = false;
    private boolean lastAButton = false;

    @Override
    public void runOpMode() throws InterruptedException {
        //All the variables I set aside for motors and made empty I am now actually assigning the motors from the hardware map (this stores all the actual motors of the robot)
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeft");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeft");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRight");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRight");

        intakeMotor = hardwareMap.get(DcMotorEx.class, "intake");

        flowerServo1 = hardwareMap.get(Servo.class, "flower_servo_1");
        flowerServo2 = hardwareMap.get(Servo.class, "flower_servo_2");

        nectarGate = hardwareMap.get(Servo.class, "gate_nectar");
        pollenGate = hardwareMap.get(Servo.class, "gate_pollen");

        nectarHood = hardwareMap.get(Servo.class, "nectarHood");
        pollenHood = hardwareMap.get(Servo.class, "nectarHood");

        transferMotor = hardwareMap.get(DcMotorEx.class, "transferMotor");

        nectarFlywheel = hardwareMap.get(DcMotorEx.class, "flywheel_motor_nectar");
        pollenFlywheel = hardwareMap.get(DcMotorEx.class, "flywheel_motor_pollen");

        nectarGate = hardwareMap.get(Servo.class, "gate_nectar");
        pollenGate = hardwareMap.get(Servo.class, "gate_pollen");
        //Because we're building custom loop, we don't want encoder.
        //This allows us to still read the motor encoder, but no run at speed it wants
        nectarFlywheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        pollenFlywheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        pollenFlywheel.setDirection(DcMotorEx.Direction.REVERSE);
        nectarFlywheel.setDirection(DcMotorEx.Direction.REVERSE);

        //This is setting the directions for the spinning of the motors (the reverse is because of some weird meca wheel stuff)
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        wheel_motors.add(frontLeftMotor);
        wheel_motors.add(backLeftMotor);
        wheel_motors.add(frontRightMotor);
        wheel_motors.add(backRightMotor);

        flower_servos.add(flowerServo1);
        flower_servos.add(flowerServo2);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            double vertical_value = -gamepad1.left_stick_y;
            double horizontal_value = gamepad1.left_stick_x;
            double spin_amount = gamepad1.right_stick_x;

            drive(horizontal_value, vertical_value, spin_amount, wheel_motors);

            lastAButton = currentAButton;
            currentAButton = gamepad1.a;

            
            if (gamepad1.right_trigger_pressed && !gamepad1.left_trigger_pressed) {
                intake(intakeMotor, transferMotor, DcMotorSimple.Direction.REVERSE, DcMotorSimple.Direction.FORWARD, 1500, 1500);
            } else if (gamepad1.left_trigger_pressed && !gamepad1.right_trigger_pressed) {
                intake(intakeMotor, transferMotor, DcMotorSimple.Direction.FORWARD, DcMotorSimple.Direction.REVERSE, 2500, 1500);
            } else if (!gamepad1.right_trigger_pressed && !gamepad2.right_trigger_pressed) {
                intake(intakeMotor, transferMotor, DcMotorSimple.Direction.FORWARD, DcMotorSimple.Direction.REVERSE, 0 , 0);
            }


            if (!currentAButton && lastAButton) {
                move_servos(flower_servos);
            }
            if (gamepad2.x) {
                nectarFlywheel.setVelocity(1500);
                nectarGate.setPosition(0.5);
            } else if (!gamepad2.x) {
                nectarFlywheel.setVelocity(0);
                nectarGate.setPosition(1);
            }
            //h
            if (gamepad2.y) {
                pollenFlywheel.setVelocity(1500);
                pollenGate.setPosition(0.5);
            } else if (!gamepad2.y) {
                pollenFlywheel.setVelocity(0);
                pollenGate.setPosition(1);
            }
            if (gamepad2.left_stick_y > 0 && gamepad2.left_stick_y < 1) {
                pollenHood.setPosition(pollenHood.getPosition() + 0.05);
            } else if (gamepad2.left_stick_y < 0 && gamepad2.left_stick_y > 0.5) {
                pollenHood.setPosition(pollenHood.getPosition() - 0.005);
            }
            if (gamepad2.right_stick_y > 0 && gamepad2.right_stick_y < 1) {
                nectarHood.setPosition(nectarHood.getPosition() + 0.05);
            } else if (gamepad2.left_stick_y < 0 && gamepad2.left_stick_y > 0.5) {
                nectarHood.setPosition(nectarHood.getPosition() - 0.005);
            }

        }
    }
}

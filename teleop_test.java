package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.JavaUtil;

@TeleOp(name="teleop_test")

public class teleop_test extends LinearOpMode{
    public DcMotor rightFront = null;
    public DcMotor rightRear = null;
    public DcMotor leftFront = null;
    public DcMotor leftRear = null;
    // toate motoarele pt deplasare
    public DcMotor leftArm = null;
    public DcMotor rightArm = null;
    // motoare pt agatare
    public Servo OnOff = null;

    @Override
    public void runOpMode() throws InterruptedException {
        rightFront = hardwareMap.dcMotor.get("rightFront");
        rightRear = hardwareMap.dcMotor.get("rightRear");
        leftFront = hardwareMap.dcMotor.get("leftFront");
        leftRear = hardwareMap.dcMotor.get("leftRear");
        leftArm = hardwareMap.dcMotor.get("leftArm");
        rightArm = hardwareMap.dcMotor.get("rightArm");
        OnOff = hardwareMap.servo.get("OnOff");

//        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftRear.setDirection(DcMotorSimple.Direction.REVERSE); //experimental
//        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
//        rightRear.setDirection(DcMotorSimple.Direction.FORWARD);
//        rightArm.setDirection(DcMotorSimple.Direction.FORWARD);
//        leftArm.setDirection(DcMotorSimple.Direction.FORWARD);

        leftArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        waitForStart();
        while(opModeIsActive()) {
            double forward = -gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double turn = -gamepad1.right_stick_x;

            double denominator;
            denominator = JavaUtil.maxOfList(JavaUtil.createListWith(1, Math.abs(forward), Math.abs(strafe), Math.abs(turn)));
            leftFront.setPower((forward+(strafe+turn))/denominator);
            rightFront.setPower((forward-(strafe+turn))/denominator);
            leftRear.setPower((forward - (strafe - turn)) / denominator);
            rightRear.setPower((forward+(strafe-turn))/denominator);

            if(gamepad1.square) {
                OnOff.setPosition(0.5);//servo 0-1
            }
            if (gamepad1.triangle) {
                OnOff.setPosition(0);
            }
            if (gamepad1.right_bumper) {
                rightArm.setTargetPosition(2000);
                leftArm.setTargetPosition(2000);
                rightArm.setPower(1);
                leftArm.setPower(1);
                rightArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                leftArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            if (gamepad1.left_bumper) {
                rightArm.setTargetPosition(0);
                leftArm.setTargetPosition(0);
                rightArm.setPower(1);
                leftArm.setPower(1);
                rightArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                leftArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
        }
    }
}

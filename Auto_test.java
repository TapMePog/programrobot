package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(name="Auto_test")
public class Auto_test extends LinearOpMode {
    public DcMotor leftArm = null;
    public DcMotor rightArm = null;

    public Servo OnOff = null;

    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        //luam pozitia de start 7.5, 7.5, care e mijlocul robotului
        // trebuie sa fie la 48-7.5=40.5 inch departare
        Pose2d startPose = new Pose2d(7.5, 7.5, Math.toRadians(90));
        drive.setPoseEstimate(startPose);

        // trsq1 => sa puna specimenul pe bara, sa ia un sample din mijsloc si sa il duca la obs zone
        TrajectorySequence trsq1 = drive.trajectorySequenceBuilder(startPose)
                //se duce la bara
                .lineToConstantHeading(new Vector2d(7.5, 40.5))

                //pune clipperul
                .addDisplacementMarker(() -> {
                    rightArm.setTargetPosition(3000);
                    leftArm.setTargetPosition(3000);
                    OnOff.setPosition(0);
                })

                //aici pun sa selecteze din mijloc un sample de culoarea aliantei dar nu stiu cum, in mana stanga
                .lineToLinearHeading(new Pose2d(40.5, 8.5, Math.toRadians(270)))
                .addDisplacementMarker(() -> {
                    rightArm.setTargetPosition(0);
                    leftArm.setTargetPosition(0);
                    OnOff.setPosition(0);
                })
                .build();

        //trsq2 => sa ia cele primul sample pus special si il duce la obs zone
        TrajectorySequence trsq2 = drive.trajectorySequenceBuilder(trsq1.end())
                //un specimen are 3.5 inch, asa ca ne oprim la 3.5 inch inainte
                // pres ca mainile o sa fie la margine ca sa fie mai usor
                //primul => inchid si ma mut cu 1 inch in fata sample
                .lineToLinearHeading(new Pose2d(42, 36.5, Math.toRadians(90)))
                .addDisplacementMarker(() -> {
                     OnOff.setPosition(1);
                })
                //al doilea => il iau si inchid servoul
                .lineToLinearHeading(new Pose2d(42, 37.5, Math.toRadians(90)))
                .addDisplacementMarker(() -> {
                    OnOff.setPosition(0);
                })
                .lineToLinearHeading(new Pose2d(55.5, 17, Math.toRadians(270)))
                .addDisplacementMarker(() -> {
                    OnOff.setPosition(1);
                })
                .build();

        TrajectorySequence trsq3 = drive.trajectorySequenceBuilder(trsq2.end())
                .lineToLinearHeading(new Pose2d(54, 36.5, Math.toRadians(90)))
                .addDisplacementMarker(() -> {
                    OnOff.setPosition(1);
                })
                .lineToLinearHeading(new Pose2d(54, 37.5, Math.toRadians(90)))
                .addDisplacementMarker(() -> {
                    OnOff.setPosition(0);
                })
                .lineToLinearHeading(new Pose2d(60, 17, Math.toRadians(270)))
                .addDisplacementMarker(() -> {
                    OnOff.setPosition(1);
                })
                .build();

        TrajectorySequence trsq4 = drive.trajectorySequenceBuilder(trsq3.end())
                .lineToLinearHeading(new Pose2d(65, 36.5, Math.toRadians(90)))
                .addDisplacementMarker(() -> {
                    OnOff.setPosition(1);
                })

                .lineToLinearHeading(new Pose2d(65, 37.5, Math.toRadians(90)))
                .addDisplacementMarker(() -> {
                    OnOff.setPosition(0);
                })
                .lineToLinearHeading(new Pose2d(65, 17, Math.toRadians(270)))
                .addDisplacementMarker(() -> {
                    OnOff.setPosition(1);
                })
                .build();

        waitForStart();
        while (opModeIsActive()) {
            idle();
            drive.followTrajectorySequence(trsq1);
            sleep(100);
            drive.followTrajectorySequence(trsq2);
            sleep(100);
            drive.followTrajectorySequence(trsq3);
            sleep(100);
            drive.followTrajectorySequence(trsq4);
        }
    }
}

package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.BiobuzzRobot;
import org.firstinspires.ftc.teamcode.utils.DriveUtils;

@TeleOp(name = "Biobuzz")
public class Biobuzz extends LinearOpMode {

    BiobuzzRobot robot = new BiobuzzRobot();

    public void runOpMode() throws InterruptedException {

        robot.init(this.hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            DriveUtils.drive(robot, gamepad1, false);

            if (gamepad1.b) {
                robot.intake.setPower(1);
                robot.leftIntake.setPower(1);
                robot.rightIntake.setPower(1);
            } else if (gamepad1.a) {
                robot.intake.setPower(0);
                robot.leftIntake.setPower(0);
                robot.rightIntake.setPower(0);
            } else if (gamepad1.x) {
                robot.intake.setPower(-1);
                robot.leftIntake.setPower(-1);
                robot.rightIntake.setPower(-1);
            }
        }
    }


}
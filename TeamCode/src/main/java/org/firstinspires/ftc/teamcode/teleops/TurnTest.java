package org.firstinspires.ftc.teamcode.teleops;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.BiobuzzRobot;
import org.firstinspires.ftc.teamcode.utils.DriveUtils;

@TeleOp(name = "Turn Test")
@Disabled
public class TurnTest extends LinearOpMode {

    BiobuzzRobot robot = new BiobuzzRobot();

    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(this.hardwareMap, telemetry);

        waitForStart();

        while (opModeIsActive()) {

            DriveUtils.pidTurn(robot, 15, 2, 2);
            sleep(1000);
//            if (gamepad1.a) {
//                DriveUtils.driveTurn(robot, telemetry, true, 200);
//                sleep(1000);
//            } else if (gamepad1.b) {
//                DriveUtils.driveTurn(robot, telemetry, true, 400);
//                sleep(1000);
//            }

        }
    }

    private void setDriveMode(DcMotor.RunMode mode) {
        robot.frontLeftDrive.setMode(mode);
        robot.frontRightDrive.setMode(mode);
        robot.backLeftDrive.setMode(mode);
        robot.backRightDrive.setMode(mode);
    }

    private void setDrivePower(double power) {
        robot.frontLeftDrive.setPower(power);
        robot.frontRightDrive.setPower(power);
        robot.backLeftDrive.setPower(power);
        robot.backRightDrive.setPower(power);
    }

}

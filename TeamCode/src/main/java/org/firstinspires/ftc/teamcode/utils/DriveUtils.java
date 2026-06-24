package org.firstinspires.ftc.teamcode.utils;

import static java.lang.Math.abs;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.hardware.BiobuzzRobot;

public class DriveUtils {

    public static final double INTAKE_DRIVE_SPEED = 7.5;
    public static final double INTAKE_DRIVE_SPEED_FAST = 10.0;


    public static void drive(BiobuzzRobot robot, Gamepad gamepad, boolean turnDebounce) {
        if (!turnDebounce) {
            double y = -gamepad.right_stick_y; // Remember, Y stick value is reversed
            double x = gamepad.right_stick_x; // Counteract imperfect strafing
            double rx = gamepad.left_stick_x;

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(abs(y) + abs(x) + abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            robot.frontLeftDrive.setPower(frontLeftPower);
            robot.backLeftDrive.setPower(backLeftPower);
            robot.frontRightDrive.setPower(frontRightPower);
            robot.backRightDrive.setPower(backRightPower);
        }
    }

    public static void gyroTurn(BiobuzzRobot robot, double speed, double angle, double coeff) {
        if (angle < 0) {
            robot.frontLeftDrive.setDirection(DcMotor.Direction.FORWARD);
            robot.frontRightDrive.setDirection(DcMotor.Direction.REVERSE);
            robot.backLeftDrive.setDirection(DcMotor.Direction.FORWARD);
            robot.backRightDrive.setDirection(DcMotor.Direction.REVERSE);
        }

        if (angle < -10 || angle == 180) {
            robot.frontLeftDrive.setDirection(DcMotor.Direction.FORWARD);
            robot.frontRightDrive.setDirection(DcMotor.Direction.REVERSE);
            robot.backLeftDrive.setDirection(DcMotor.Direction.FORWARD);
            robot.backRightDrive.setDirection(DcMotor.Direction.REVERSE);
        }

        robot.frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.backLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.backRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        double error = 0;
        double steer = 0;
        double leftSpeed = 0;
        double rightSpeed = 0;
        boolean onTarget = false;
        error = getError(robot, angle);

        while (abs(error) > 1) {
            steer = Range.clip(coeff * error, -speed, speed);
            if (steer < 0.5) {
                if (abs(error) < 3) {
                    steer = 0.05;
                } else if (abs(error) < 6) {
                    steer = 0.1;
                } else {
                    steer = 0.5;
                }
            }
            rightSpeed = steer;
            leftSpeed = -rightSpeed;

            robot.frontLeftDrive.setVelocity(leftSpeed*2700);
            robot.frontRightDrive.setVelocity(rightSpeed*2700);
            robot.backLeftDrive.setVelocity(leftSpeed*2700);
            robot.backRightDrive.setVelocity(rightSpeed*2700);

            error = getError(robot, angle);
        }

        robot.frontLeftDrive.setVelocity(0);
        robot.frontRightDrive.setVelocity(0);
        robot.backLeftDrive.setVelocity(0);
        robot.backRightDrive.setVelocity(0);

        robot.frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        robot.frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        robot.backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        robot.backRightDrive.setDirection(DcMotor.Direction.FORWARD);
    }

    private static double getError(BiobuzzRobot robot, double targetAngle) {
        double angleError = 0;
        //Orientation orientation = imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
        Orientation orientation = robot.imu.getRobotOrientation(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
        angleError = targetAngle - orientation.thirdAngle;
        //angleError = targetAngle;

        if (angleError > 180) {
            angleError -= 360;
        }
        if (angleError <= -180) {
            angleError += 360;
        }
        return angleError;
    }

    public static void turnToAngle(BiobuzzRobot robot, Telemetry telemetry, double targetAngleDegrees, double turnPower) {

        robot.frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        robot.backRightDrive.setDirection(DcMotor.Direction.FORWARD);
        robot.frontRightDrive.setDirection(DcMotor.Direction.REVERSE);
        robot.backRightDrive.setDirection(DcMotor.Direction.REVERSE);

        double currentAngle = robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
        double angleDifference = targetAngleDegrees - currentAngle;

        // Normalize angleDifference to be between -180 and 180
        while (angleDifference > 180) angleDifference -= 360;
        while (angleDifference < -180) angleDifference += 360;

        // Adjust turn power based on angle difference
        // This simple example uses a constant power, but PID control is recommended for precision
        double rotationalPower = Math.signum(angleDifference) * turnPower;

        while (abs(angleDifference) > 5) { // Tolerance of 5 degrees
            currentAngle = robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
            angleDifference = targetAngleDegrees - currentAngle;

            while (angleDifference > 180) angleDifference -= 360;
            while (angleDifference < -180) angleDifference += 360;

            rotationalPower = Math.signum(angleDifference) * turnPower;

            // Apply power for rotation
            // Left wheels positive, right wheels negative for clockwise turn
            // Adjust signs based on your motor directions and desired turn direction
            robot.frontLeftDrive.setPower(rotationalPower);
            robot.backLeftDrive.setPower(rotationalPower);
            robot.frontRightDrive.setPower(-rotationalPower);
            robot.backRightDrive.setPower(-rotationalPower);

            telemetry.addData("Current Angle", currentAngle);
            telemetry.addData("Target Angle", targetAngleDegrees);
            telemetry.update();
        }
        setMotorPower(robot, 0, 0, 0, 0); // Stop motors after reaching target
    }

    private static void setMotorPower(BiobuzzRobot robot, double fl, double fr, double bl, double br) {
        robot.frontLeftDrive.setPower(fl);
        robot.frontRightDrive.setPower(fr);
        robot.backLeftDrive.setPower(bl);
        robot.backRightDrive.setPower(br);
    }

    public static void driveTurn(BiobuzzRobot robot, Telemetry t, boolean turnLeft, long timeoutMillis) {
        double yaw = turnLeft ? -0.5 : 0.5;
//        double yaw = turnLeft ? -0.25 : 0.25;
        driveWithParams(0.0, 0.0, yaw, timeoutMillis, robot, t);
    }

    private static void driveWithParams(double axial, double lateral, double yaw,
                                        long timeoutMillis, BiobuzzRobot robot, Telemetry t) {
        double leftFrontPower  = axial + lateral + yaw;
        double rightFrontPower = axial - lateral - yaw;
        double leftBackPower   = axial - lateral + yaw;
        double rightBackPower  = axial + lateral - yaw;

        // Normalize the values so no wheel power exceeds 100%
        // This ensures that the robot maintains the desired motion.
        double max = Math.max(abs(leftFrontPower), abs(rightFrontPower));
        max = Math.max(max, abs(leftBackPower));
        max = Math.max(max, abs(rightBackPower));

        if (max > 1.0) {
            leftFrontPower  /= max;
            rightFrontPower /= max;
            leftBackPower   /= max;
            rightBackPower  /= max;
        }

        // Send calculated power to wheels
        robot.frontLeftDrive.setPower(leftFrontPower);
        robot.frontRightDrive.setPower(rightFrontPower);
        robot.backLeftDrive.setPower(leftBackPower);
        robot.backRightDrive.setPower(rightBackPower);

        try {
            Thread.sleep(timeoutMillis);
        } catch (InterruptedException var4) {
            Thread.currentThread().interrupt();
        }

        // Stop all motion;
        robot.frontLeftDrive.setPower(0);
        robot.frontRightDrive.setPower(0);
        robot.backLeftDrive.setPower(0);
        robot.backRightDrive.setPower(0);
    }

    //threshold in degrees
    public static void pidTurn(BiobuzzRobot robot, double angle, double timeoutSec, double threshold) throws InterruptedException {
        robot.frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.backLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.backRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        double Kp = 0.04;
        double Ki = 0.0;
        double Kd = 0.09;
        double integral = 0;
        double lastError = 0;
        double error = 0;

        ElapsedTime timer = new ElapsedTime();

        timer.reset();
        while (timer.seconds() < timeoutSec) {
            double heading = robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
            error = AngleUnit.normalizeDegrees(angle - heading);

            integral += error;
            double derivative = error - lastError;

            double output = Kp * error + Ki * integral + Kd * derivative;

            // Clip output to motor power range
            output = Math.max(-1, Math.min(1, output));

            robot.frontLeftDrive.setPower(-output);
            robot.backLeftDrive.setPower(-output);
            robot.frontRightDrive.setPower(output);
            robot.backRightDrive.setPower(output);

            lastError = error;

            if (abs(error) < threshold) {
                break;
            }

        }

        // Stop the motors
        robot.frontLeftDrive.setPower(0);
        robot.backLeftDrive.setPower(0);
        robot.frontRightDrive.setPower(0);
        robot.backRightDrive.setPower(0);

    }

}

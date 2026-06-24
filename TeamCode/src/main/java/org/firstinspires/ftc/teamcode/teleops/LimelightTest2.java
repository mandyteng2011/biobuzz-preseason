package org.firstinspires.ftc.teamcode.teleops;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

@TeleOp(name = "Limelight Test 2")
@Disabled
public class LimelightTest2 extends OpMode {

    private Limelight3A limelight;
    private IMU imu;

    @Override
    public void init() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(1);
        imu = hardwareMap.get(IMU.class, "imu");
        RevHubOrientationOnRobot orientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.BACKWARD,
                RevHubOrientationOnRobot.UsbFacingDirection.UP
        );
        imu.initialize(new IMU.Parameters(orientation));
    }

    @Override
    public void start() {
        limelight.start();
    }

    @Override
    public void loop() {
        YawPitchRollAngles angles = imu.getRobotYawPitchRollAngles();
        limelight.updateRobotOrientation(angles.getYaw());
        LLResult llResult = limelight.getLatestResult();
        if (llResult != null && llResult.isValid()) {
            for (LLResultTypes.FiducialResult fiducial : llResult.getFiducialResults()) {
                int id = fiducial.getFiducialId();
                //Pose3D pose = llResult.getBotpose_MT2();
                Pose3D pose = fiducial.getRobotPoseTargetSpace();
                telemetry.addData("ID", id);
                telemetry.addData("Tx", pose.getPosition().x);
                telemetry.addData("Ty", pose.getPosition().y);
                telemetry.addData("Ta", pose.getPosition().z);
                telemetry.update();
            }

        }
    }
}

package org.firstinspires.ftc.teamcode.teleops;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Lift Test")
@Disabled
public class LiftTest extends LinearOpMode {

    public DcMotorEx leftLift = null;
    public DcMotorEx rightLift = null;

    @Override
    public void runOpMode(){

        leftLift = hardwareMap.get(DcMotorEx.class, "leftLift");
        rightLift = hardwareMap.get(DcMotorEx.class, "rightLift");

        leftLift.setDirection(DcMotorSimple.Direction.FORWARD);
        rightLift.setDirection(DcMotorSimple.Direction.FORWARD);

        waitForStart();

        while (opModeIsActive()){

            if (gamepad1.a) {
                setLiftPower(.5);
            } else if (gamepad1.b) {
                setLiftPower(-.5);
            } else if (gamepad1.x) {
                setLiftPower(0);
            }

        }
    }

    private void setLiftPower(double liftPower) {
        leftLift.setPower(liftPower);
        rightLift.setPower(liftPower);
    }
}

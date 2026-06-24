package org.firstinspires.ftc.teamcode.teleops;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Kicker Indexer Init Test")
@Disabled
public class KickerIndexerInitTest extends LinearOpMode {

    public Servo kicker = null;
    public Servo spindexer = null;

    @Override
    public void runOpMode(){

        kicker = hardwareMap.get(Servo.class, "kicker");
        spindexer = hardwareMap.get(Servo.class, "spindexer");

        waitForStart();

        while (opModeIsActive()){

            if (gamepad1.y) {
                kicker.setPosition(0.7);
            } else if (gamepad1.b) {
                kicker.setPosition(0.5);
            } else if (gamepad1.a) {
                kicker.setPosition(0.3);
            }

            // indexer 3 positions
            if (gamepad1.dpad_left) {
                spindexer.setPosition(0);
            } else if (gamepad1.dpad_down) {
                spindexer.setPosition(0.5);
            } else if (gamepad1.dpad_right) {
                spindexer.setPosition(1);
            }
        }
    }
}

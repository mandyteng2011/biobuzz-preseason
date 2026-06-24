package org.firstinspires.ftc.teamcode.teleops;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

@TeleOp(name = "Color Sensor Test")
@Disabled
public class ColorSensorTest extends LinearOpMode {

    public NormalizedColorSensor colorSensor = null;

    @Override
    public void runOpMode() {

        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "Ethernet Device");
        colorSensor.setGain(4);

        waitForStart();

        while (opModeIsActive()){
            // Determining the amount of red, green, and blue
            NormalizedRGBA colors = colorSensor.getNormalizedColors();
            telemetry.addData("Red", "%.3f", colors.red);
            telemetry.addData("Green", "%.3f", colors.green);
            telemetry.addData("Blue", "%.3f", colors.blue);
            telemetry.update();
        }
    }

}

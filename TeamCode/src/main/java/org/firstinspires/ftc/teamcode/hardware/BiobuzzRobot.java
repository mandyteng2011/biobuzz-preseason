package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class BiobuzzRobot {
//    static final double DRIVE_COUNTS_PER_MOTOR_REV = 537.7;
//    static final double DRIVE_DRIVE_GEAR_REDUCTION = 19.2;
//    static final double DRIVE_WHEEL_DIAMETER_INCHES = 4;
//    static final double DRIVE_TICKS_PER_INCH = (DRIVE_COUNTS_PER_MOTOR_REV * DRIVE_DRIVE_GEAR_REDUCTION) / (DRIVE_WHEEL_DIAMETER_INCHES*Math.PI);
//    public static String currentAlliance = "BLUE";
    private HardwareMap hwMap = null;
    public DcMotorEx frontLeftDrive = null;
    public DcMotorEx frontRightDrive = null;
    public DcMotorEx backLeftDrive = null;
    public DcMotorEx backRightDrive = null;

    public CRServo leftIntake = null;

    public CRServo rightIntake = null;

    public DcMotorEx intake = null;
//    public DcMotorEx leftLift = null;
//    public DcMotorEx rightLift = null;
//    public DcMotorEx shooter = null;
//    public DcMotorEx shooter2 = null;
//    public DcMotorEx intake = null;
//    public Servo spindexer = null;
//    public Servo kicker = null;
//    public Limelight3A limelight;
//    public NormalizedColorSensor intakeColorSensor;
//    public NormalizedColorSensor shooterColorSensor;
//    public NormalizedColorSensor humanColorSensor;
    public IMU imu = null;
//
//    public AprilTagDetector aprilTagDetector = null;
//    public MecanumDrive drive = null;
//    public Servo shooterLED = null;
    private Telemetry telemetry;
    ElapsedTime time = new ElapsedTime();
    public void init(HardwareMap ahwMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        init(ahwMap);
    }
    public void init(HardwareMap ahwMap){
        hwMap = ahwMap;

        // init drive motors
        frontLeftDrive = hwMap.get(DcMotorEx.class, "FL");
        frontRightDrive = hwMap.get(DcMotorEx.class, "FR");
        backLeftDrive = hwMap.get(DcMotorEx.class, "BL");
        backRightDrive = hwMap.get(DcMotorEx.class, "BR");
        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intake = hwMap.get(DcMotorEx.class, "intake");
        leftIntake = hwMap.get(CRServo.class, "intakeL");
        rightIntake = hwMap.get(CRServo.class, "intakeR");

        leftIntake.setDirection(DcMotorSimple.Direction.REVERSE);

        // init lifts
//        leftLift = hwMap.get(DcMotorEx.class, "LLift");
//        rightLift = hwMap.get(DcMotorEx.class, "RLift");

        // init color sensor
//        intakeColorSensor = hwMap.get(NormalizedColorSensor.class, "colorSensor");
//        intakeColorSensor.setGain(10);
//        shooterColorSensor = hwMap.get(NormalizedColorSensor.class, "colorSensorBack");
//        shooterColorSensor.setGain(4);
//        humanColorSensor = hwMap.get(NormalizedColorSensor.class, "humanColorSensor");
//        humanColorSensor.setGain(4);
//
//        // init limelight
//        limelight = hwMap.get(Limelight3A.class, "limelight");
//        limelight.pipelineSwitch(0);
//        limelight.start();
//        shooter2 = hwMap.get(DcMotorEx.class, "shooter2");
//        shooter2 = hwMap.get(DcMotorEx.class, "shooter2");
//        shooter2 = hwMap.get(DcMotorEx.class, "shooter2");

        // init other motors and servos
//        shooter = hwMap.get(DcMotorEx.class, "shooter");
//        shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        shooter.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, ShootingUtils.SHOOTER2_PIDF); //new PIDFCoefficients(45,0.0,5.0,2.0)
//        shooter2 = hwMap.get(DcMotorEx.class, "shooter2");
//        shooter2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        shooter2.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, ShootingUtils.SHOOTER2_PIDF); //new PIDFCoefficients(45,0.0,5.0,2.0)
//        shooter2.setDirection(DcMotor.Direction.REVERSE);
//        intake = hwMap.get(DcMotorEx.class, "intake");
//        spindexer = hwMap.get(Servo.class, "spindexer");
//        kicker = hwMap.get(Servo.class, "kicker");
//        shooterLED = hwMap.get(Servo.class, "shooterled");

        // init imu
//        imu = hwMap.get(IMU.class, "imu");
//        // Adjust the orientation parameters to match your robot
//        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
//                RevHubOrientationOnRobot.LogoFacingDirection.BACKWARD,
//                RevHubOrientationOnRobot.UsbFacingDirection.UP));
//        imu.initialize(parameters);
//
//        // init april tag detector
//        aprilTagDetector = new AprilTagDetector(telemetry, this, AllianceColor.EITHER_ALLIANCE);
////        aprilTagDetector = new AprilTagDetector(
//                telemetry,
//                hwMap.get(WebcamName.class, "Webcam 1"),
//                AllianceColor.EITHER_ALLIANCE);
//        aprilTagDetector.initAprilTag();
    }
//    public void init(HardwareMap ahwMap, Telemetry telemetry, Pose2d initialPose){
//        init(ahwMap,telemetry);
//        drive = new MecanumDrive(hwMap, initialPose);
////        currentPose = initialPose;
//    }
//    public double getDriveTicks(double inches){
//        return inches * DRIVE_TICKS_PER_INCH;
//    }
}


package org.usfirst.frc.team2875.robot;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.opencv.core.Mat;
import org.usfirst.frc.team2875.robot.commands.CalibrateGyro;
import org.usfirst.frc.team2875.robot.commands.CmdCameraVision;
import org.usfirst.frc.team2875.robot.commands.CmdDriveDistance;
import org.usfirst.frc.team2875.robot.subsystems.AutoAlign;
import org.usfirst.frc.team2875.robot.subsystems.BallGrabber;
import org.usfirst.frc.team2875.robot.subsystems.Camera;
import org.usfirst.frc.team2875.robot.subsystems.Clutch;
import org.usfirst.frc.team2875.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2875.robot.subsystems.Encoders;
import org.usfirst.frc.team2875.robot.subsystems.GearHolder;
import org.usfirst.frc.team2875.robot.subsystems.Gyroscope;

import auto.AutoLeft;
import auto.AutoLeft_RightBoiler;
import auto.AutoMid;
import auto.AutoRight;
import auto.AutoRight_RightBoiler;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	//Settings

	//Test Good
	
	public static final int[] ENCODERMAP = {1,2,3,4};
	//Objects
	//HeyThere!
	//Controls Systems enabled on Robot (testing purposes)
	public static boolean camEnabled = true;
	public static boolean driveEnabled = true;
	//public static CvSource outStream;
	//public static CvSink camStream;
	public static Dejitter driftWatcher;
	public static Encoders encoders;
	public static Drivetrain driveTrainSys;
	public static BallGrabber ballGrabber;
	//public static Camera cam;
	public static final IO input = new IO();
	public static Gyroscope gyroscope;
	public static double gyroStartAngle;
	public static int trackingThreshold = 80;
	public static GearHolder gear ;
	public static Clutch clutch;
	public static Preferences prefs;
	private double gyroDrift;
	private double gyroAngle= 0.0;
	private double cacheAngle = 0.0;
	public static Solenoid c;
	public static Solenoid gearHolder;
	//public static Solenoid lights;
	double meme = 0;
	boolean gyroCalibrating = false;
	double angleGoal = 0.0;
	public static Relay lights; 
	
	public static CamThread vis;
	public static AutoAlign auto = new AutoAlign();
	//SETTINGS
	public static final double GYRO_DEADZONE = .5;
	
	Command autonomousCommand;
	SendableChooser<Command> autoChooser = new SendableChooser<>();
	Mat imagery;
	public static Gearbox rightGearbox;

	public static  Gearbox leftGearbox;
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		prefs = Preferences.getInstance();
		//vis = new CamThread();
		lights = new Relay(3);
		input.init();
		encoders = new Encoders();
		rightGearbox = new Gearbox(0, 1, 2);
		leftGearbox = new Gearbox(9, 8, 7);
		
		System.out.println("startingup");
		
	
		ballGrabber = new BallGrabber();
		//Determine which systems are currently enabled via Smart Dash
		//camEnabled = true;
		
		driveEnabled = SmartDashboard.getBoolean("Driving Enabled", driveEnabled);
		//Determine tracking data via Smart Dash

		trackingThreshold = prefs.getInt("Tracking Threshold", trackingThreshold);
		SmartDashboard.putNumber("Tracking Thresh", trackingThreshold);
	    //Gyro init
		gyroscope = new Gyroscope();
		driftWatcher = new Dejitter(10);
		gyroscope.startThread();
		gyroStartAngle = gyroscope.get_heading();
		System.out.println("Setup");
		// Populate Smart Dash
		autoChooser.addDefault("AutoMid_LEFTBOILER", new AutoMid());
		autoChooser.addObject("Auto Left_LEFTBOILER --- Not Working", new AutoLeft());
		autoChooser.addObject("Auto Right_LEFTBOILER", new AutoRight());
		//autoChooser.addObject("AutoMid_RIGHTBOILER", new AutoMidRightBoiler());
		autoChooser.addObject("Auto Right_RIGHTBOILER --- Not Working", new AutoRight_RightBoiler());
		autoChooser.addObject("Auto Left_RIGHTBOILER", new AutoLeft_RightBoiler());
		SmartDashboard.putData("Auto mode", autoChooser);
		SmartDashboard.putNumber("Input Val", input.getForwardInput());
		SmartDashboard.putNumber("Ref Test", meme);
		SmartDashboard.putNumber("Est. Gyro Drift", gyroDrift);
		SmartDashboard.putString("Gyro Status", "Not Broken");
		SmartDashboard.putData("Calibrate Gyro", new CalibrateGyro());
		c = new Solenoid(0,0);
		gearHolder = new Solenoid(0,1);
		//lights = new Solenoid(0,2);
		clutch = new Clutch();
		gear =  new GearHolder();
		driveTrainSys =  new Drivetrain();
	}
	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
	
		/*if(c != null){
			c.free();
		}
		if(gearHolder != null){
			gearHolder.free();
		}*/
		//Save tracking data into prefs
		prefs.putInt("Tracking Threshold", (int)SmartDashboard.getNumber("Tracking Threshold", trackingThreshold));
	}
	@Override
	public void disabledPeriodic() {
		//Perform Gyro Checking while robot is disabled
		SmartDashboard.putNumber("Gyro", gyroscope.get_heading());
		//Scheduler.getInstance().run();
		gyroAngle = gyroscope.get_heading();
		gyroCalibrating = gyroscope.isCalibrating(); //this is a call into ADXRS453Gyro.isCalibrating();
		boolean drift = driftWatcher.update(Math.abs(gyroAngle - cacheAngle)  > (0.75 / 50.0));
		SmartDashboard.putNumber("Est. Gyro Drift", Math.abs(gyroAngle-cacheAngle));
		if (drift) {
			SmartDashboard.putString("Gyro Status", "Calibration Recommended!");
			
		}else{
			
		}
		
		cacheAngle = gyroAngle;
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	public static void straightDrive(double angleGoal, double speedL, double speedR){
		double angle = gyroscope.get_heading();
		if(Math.abs(angleGoal - angle) > 3){
			speedL += (angle - angleGoal);
		}
		//driveTrainSys.gearbox[0].setSpeed(speedL);
		//driveTrainSys.gearbox[1].setSpeed(speedR);
	}
	@Override
	public void autonomousInit() {
		//vis.run();
		
		Scheduler.getInstance().run();

		autonomousCommand = autoChooser.getSelected();
		autonomousCommand.start();

		System.out.println("sick dab bro");
		//(new AutoMid()).start();
		//Scheduler.getInstance().add(new CmdDriveDistance(1,.4));
		//Scheduler.getInstance().enable();
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}
	boolean drivingForward= false;
	Gearbox[] gearRef;
	double deltaTime;
	double lastTime = 0;
	double GyroAdjust = 0.3;
	

	@Override
	public void teleopInit() {
	
	if(vis != null){
		vis.stop();
	}
	
		//	driveTrainSys = new Drivetrain();
	    //driveTrainSys.getCurrentCommand().start();
		//drivingForward = false;
		lastTime = Timer.getFPGATimestamp();
		//gearRef = Robot.driveTrainSys.gearbox;
		
		//System.out.println("asdkjnaskdj");
		//System.out.println("dab");
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}
	/**
	 * This function is called periodically during operator control
	 */
	public static double getSign(double in){
		if(in == 0){
			return 0;
		}
		return (in / Math.abs(in));
	}
	boolean buttonDown = false;
	boolean buttonDownG = false;
	boolean solenOpen = false;
	boolean solenOpenG = false;
	@Override
	public void teleopPeriodic() {
		
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("Gyro", gyroscope.get_heading());
		//System.out.println(Robot.encoders.getRightSpeed());
	//System.out.println(encoders.getLeftSpeed());
		//System.out.println(encoders.getRightSpeed());
		/*
				if(Robot.input.getClutchSwitch()){
					if(!buttonDown){
						buttonDown = true;
						clutch.set(!solenOpen);
						solenOpen = !solenOpen;
						System.out.println("Switching");
					}
				}else if(buttonDown){
					buttonDown = false;
				}
				if(Robot.input.getGearSwitch()){
						
						if(!buttonDownG){
							
						buttonDownG = true;
						Robot.gearHolder.set(!solenOpenG);
						System.out.println("Backing Up");
						
						solenOpenG = !solenOpenG;
						System.out.println("Switching Gear");
					}
				}else if(buttonDownG){
					buttonDownG = false;
					
				}
				
				
				*/
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
		gearRef[0].setSpeed(1);
		gearRef[1].setSpeed(1);
	}
}


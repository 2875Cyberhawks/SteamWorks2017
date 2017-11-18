package org.usfirst.frc.team2875.robot;

import org.usfirst.frc.team2875.robot.commands.CmdClimbLock;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class IO {
	public static final double JOY_DEADZONE = 0.15;
	public static final double TRIGGER_DEADZONE = 0.02;
	//// CREATING BUTTONS
	public Joystick mainController;
	private Joystick subordanateController;
	
	public IO(){
		mainController = new Joystick(0);
		subordanateController = new Joystick(1);
		subordanateController.setRumble(GenericHID.RumbleType.kLeftRumble, 0);
		subordanateController.setRumble(GenericHID.RumbleType.kRightRumble,0);
	}
	public void init(){
		JoystickButton climblock = new JoystickButton(mainController, 8);
		//climblock.whenPressed(new CmdClimbLock());
	}
	public boolean getAutoAlign(){
		return mainController.getRawButton(1);
	}
	public boolean getEmergencyStop(){
		return false;
	
	}

	public boolean getClutchSwitch(){
		return mainController.getRawButton(3);
	}
	public boolean getAutoAlignCancel(){
		return mainController.getRawButton(5);
	}
	public boolean getGearSwitch(){
		return mainController.getRawButton(4);
	}
	//returns forward and backward movement input
	public double getForwardInput(){
		
		double in = mainController.getRawAxis(1);
		return (Math.abs(in) > JOY_DEADZONE) ? in : 0;
	}
	//returns yaw movement input
	public double getLeftInput(){
		double in = mainController.getRawAxis(2);
		return Math.abs(in)* 1 > TRIGGER_DEADZONE ? in : 0;
	}
	public double getRightInput(){
		double in = mainController.getRawAxis(3);
		return Math.abs(in)* 1 > TRIGGER_DEADZONE ? in : 0;
	}
	public double getStrafeInput(){
		double in = mainController.getRawAxis(4);
		return (Math.abs(in) > (JOY_DEADZONE)) ? in : 0;
	}
	public double getBallInput(){
		double in = subordanateController.getRawAxis(5);
		return (Math.abs(in) > (JOY_DEADZONE)) ? in : 0;
	}
	public boolean getClimbLock(){
		return mainController.getRawButton(8);
	}
	
	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
}

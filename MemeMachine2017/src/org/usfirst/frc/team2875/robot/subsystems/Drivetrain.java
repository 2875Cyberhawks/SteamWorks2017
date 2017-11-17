package org.usfirst.frc.team2875.robot.subsystems;

import org.usfirst.frc.team2875.robot.Debug;
import org.usfirst.frc.team2875.robot.Robot;
import org.usfirst.frc.team2875.robot.commands.Drive;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Drivetrain extends Subsystem{
	private double lastSpeed1 = 0;
	private double lastSpeed2 = 0;
	private double straight;
	public Drivetrain(){
		System.out.println("starting");
		straight = Robot.gyroscope.get_heading();
		//1/12 -- 0 is left, 1 is right TBU
	}
	public void setSpeed(double speed){
		Robot.leftGearbox.setSpeed(speed);
		Robot.rightGearbox.setSpeed(speed);
	}

	private double getSign(double in){
		if(in == 0){
			return 0;
		}
		return (in / Math.abs(in));
	}
	public void input(double forward, double left, double right){
		drive(-forward,left,right);
		
	}
	public void straightDrive(double iforward){
		double forward = -iforward;
		Double cur = Robot.gyroscope.get_heading() - straight;
		Debug.log("Straight", straight);
		int gyro_factor = 40;
		//System.out.println("Driving: Left is " + (((Math.abs(cur * forward)))/gyro_factor) + " Right is " + (((Math.abs(cur * forward)))/gyro_factor));
		Robot.leftGearbox.setSpeed(forward-((cur * forward)/gyro_factor));
		Robot.rightGearbox.setSpeed(forward+((cur * forward)/gyro_factor));
			
		
		
	}
	public void drive(double forward, double left, double right){
		if(Robot.isAligning) {
			return;
		}
		forward *= -1;
		double speed1, speed2, g1, g2;
		
		//System.out.println(Robot.vis.getAngle());
		 g1 = forward + ((right) - (left));
		 g2 = -forward + ((right) - (left));
		if(forward != 0 && (left == 0 && right == 0) || ( forward == 0)) {
		speed2 = getSign(g2) * Math.min(Math.abs(g2), Math.abs(lastSpeed1) + Math.abs(Robot.accelRate));//Math.max(0, Math.abs(g1)) * getSign(g1);
		speed1 = getSign(g1) * Math.min(Math.abs(g1), Math.abs(lastSpeed2) + Math.abs(Robot.accelRate));////Math.max(0, Math.abs(g2)) * getSign(g2);
		}else {
			 speed2 = g2;
			 speed1 = g1;
		}
		Robot.rightGearbox.setSpeed(-speed1);
	
		if (!Robot.clutch.isOpen) 
			Robot.leftGearbox.setSpeed(speed2 * .91);
			else
				Robot.leftGearbox.setSpeed(speed2 * .88);
		lastSpeed1 = speed1;
		lastSpeed2 = speed2;
	}
	protected void initDefaultCommand() {
		setDefaultCommand(new Drive());
	}
	public void stop() {
		
		Robot.rightGearbox.stop();
		Robot.leftGearbox.stop();
	}
	

}

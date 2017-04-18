package org.usfirst.frc.team2875.robot.commands;

import org.usfirst.frc.team2875.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CalibrateGyro extends Command{
	boolean isFinished = false;
	protected void initialize() {
		isFinished = false;
		if(!Robot.gyroscope.isCalibrating()){
		Robot.gyroscope.calibrate();
		System.out.println("Calibration");
		}else{
			isFinished = true;
		}
		SmartDashboard.putString("Gyro Status", "Calibrating");
		
	}
	protected void execute(){
		System.out.println(Robot.gyroscope.isCalibrating());
		if(!Robot.gyroscope.isCalibrating()){
			Robot.driftWatcher.reset();
			Robot.gyroStartAngle = Robot.gyroscope.get_heading();
			SmartDashboard.putString("Gyro Status", "Fine");
			isFinished = true;
		}
	}
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return isFinished;
	}

}

package org.usfirst.frc.team2875.robot.commands;

import org.usfirst.frc.team2875.robot.Robot;


import edu.wpi.first.wpilibj.command.Command;

public class CmdRotateAngle extends Command {
	public double theta;
	double speed;
	public int direction;
	//left is 0
	//right is 1
	double goal;
	public CmdRotateAngle(double theta, double ispeed, int direction)
	{
		//requires(Robot.drivetrain);
		speed = ispeed;
		this.theta=theta;
		this.direction=direction;
	}

	@Override
	protected void initialize() {
		if(direction==0){
			goal = Robot.gyroscope.get_heading() - theta;
		}else{
			goal = Robot.gyroscope.get_heading() + theta;
		}
		// TODO Auto-generated method stub

	}

	@Override
	protected void execute() {
		if(direction==1){
			Robot.rightGearbox.setSpeed(-speed);
			Robot.leftGearbox.setSpeed(speed);
		}else{
			Robot.rightGearbox.setSpeed(speed);
			Robot.leftGearbox.setSpeed(-speed);
		}
	}

	@Override
	protected boolean isFinished() {	
		if(direction==1&&Robot.gyroscope.get_heading()<goal)
			return false;
		else if(direction==0&&Robot.gyroscope.get_heading()>goal)
			return false;
		else{
			System.out.println("FINISHED");
			
			return true;
		}
	}

	@Override
	protected void end() {
		Robot.driveTrainSys.stop();

	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}

}

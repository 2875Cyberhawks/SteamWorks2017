package org.usfirst.frc.team2875.robot.commands;

import org.usfirst.frc.team2875.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class CmdClimbLock extends Command {
	private final double SPEED = .3;
	boolean isRunning = false;
	protected void initialize(){
		Robot.driveTrainSys.setSpeed(SPEED);
		requires(Robot.driveTrainSys);
	}
	
	@Override
	protected boolean isFinished() {
		if(!isRunning){
			isRunning = true;
			return false;
		}
		if(Robot.input.getClimbLock()){
			return true;
		}
		return false;
	}
	
}

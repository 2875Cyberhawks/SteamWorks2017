package org.usfirst.frc.team2875.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

public class TimeOut extends Command {
	
	
	
	public TimeOut(double seconds)
	{
		setTimeout(seconds);
	}

	@Override
	protected void initialize() {
		
	}

	@Override
	protected void execute() {
		
	}

	@Override
	protected boolean isFinished() {
		return isTimedOut();
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}

}

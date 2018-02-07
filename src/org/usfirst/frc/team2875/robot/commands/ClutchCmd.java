package org.usfirst.frc.team2875.robot.commands;

import org.usfirst.frc.team2875.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ClutchCmd extends Command {

	public ClutchCmd(){
		requires(Robot.clutch);
	}
	protected void execute(){
		
		Robot.clutch.input(Robot.input.getClutchSwitch());
	}
	
	
	@Override
	protected boolean isFinished() {
		
		return false;
	}

}

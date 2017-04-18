package org.usfirst.frc.team2875.robot.commands;

import org.usfirst.frc.team2875.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class GearAuto extends Command {
	boolean open;
	public GearAuto(boolean b){
		open = b;
	}
	
	protected void execute(){
		if (open) Robot.gear.open();
		if (!open) Robot.gear.close();
	}
	
	@Override
	protected boolean isFinished() {
		return true;
	}

}

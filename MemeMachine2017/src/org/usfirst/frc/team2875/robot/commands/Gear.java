package org.usfirst.frc.team2875.robot.commands;

import org.usfirst.frc.team2875.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Gear extends Command {
	boolean temp = false;
	public Gear(boolean b){
		if (b) Robot.gear.open();
		if (!b) Robot.gear.close();
		temp = true;
	}
	public Gear(){
		requires(Robot.gear);
	}
	protected void execute(){
		Robot.gear.input(Robot.input.getGearSwitch());
		//Robot.gear.input(Robot.input.getClutchSwitch() && Robot.gear.isOpen ? true : false);
	}
	
	
	@Override
	protected boolean isFinished() {
		if (temp) return true;
		return false;
	}

}

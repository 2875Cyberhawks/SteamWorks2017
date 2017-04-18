package org.usfirst.frc.team2875.robot.commands;

import org.usfirst.frc.team2875.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class AutoAlignCmd extends Command {
	boolean lastButton = false;
	boolean isMoving = false;
	CmdRotateAngle  rotation;
	public AutoAlignCmd(){
		requires(Robot.auto);
	}
	protected void execute(){
		if(Robot.input.getAutoAlign() && !lastButton && !isMoving){
			isMoving = true;
			//System.out.println("Doing it");
			//rotation = new CmdRotateAngle(Robot.vis.getAngle(), 0.2, 0);
			rotation.start();
			
		}
		if(rotation != null && rotation.isFinished() && isMoving){
			isMoving = false;
		}
		lastButton = Robot.input.getAutoAlign();
	}
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}

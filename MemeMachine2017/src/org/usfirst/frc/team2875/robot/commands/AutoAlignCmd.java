package org.usfirst.frc.team2875.robot.commands;

import org.usfirst.frc.team2875.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class AutoAlignCmd extends Command {
	boolean lastButton = false;
	boolean isMoving = false;
	boolean hasRunJump = false;
	int idleTime = 10;
	
	double startTimer = 0; 
	public AutoAlignCmd(){
		requires(Robot.auto);
	}
	protected void execute(){
		System.out.println(Robot.vis.targetLocation);
		if(Robot.input.getAutoAlign() && !isMoving){
			System.out.println("Got button");
			Robot.isAligning =true;
			hasRunJump = true;
			isMoving = true;
			idleTime = 10;
			startTimer = Timer.getFPGATimestamp();
		}
		idleTime--;
		System.out.println(Robot.input.getAutoAlignCancel());
		
		
		
		if(Robot.vis.hasTarget && isMoving) {
			if(hasRunJump) {
				Robot.leftGearbox.setSpeed(1);
				Robot.rightGearbox.setSpeed(-1);
			}
			if((Math.abs(Robot.vis.targetLocation) < Robot.prefs.getInt("Align Range", 5)) || Robot.input.getAutoAlignCancel() || (Timer.getFPGATimestamp() - startTimer >= 3)) {
				Robot.isAligning = false;
				isMoving = false;
			}
			double val =0;// Math.abs(Robot.vis.targetLocation * (.1)/(20));
			if(Robot.vis.targetLocation > 0) {
				Robot.leftGearbox.setSpeed(0.1 + (val));
				Robot.rightGearbox.setSpeed(-0.1 - val);
				if(hasRunJump) {
					Robot.leftGearbox.setSpeed(1);
					Robot.rightGearbox.setSpeed(-1);
					hasRunJump = false;
				}
			}else {
				Robot.leftGearbox.setSpeed(-0.1 - val);
				Robot.rightGearbox.setSpeed(0.1 + val);
				if(hasRunJump) {
					Robot.leftGearbox.setSpeed(-1);
					Robot.rightGearbox.setSpeed(1);
					hasRunJump = false;
				}
			}
			
		}else {
			Robot.isAligning = false;
			isMoving = false;
		}

	}
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}

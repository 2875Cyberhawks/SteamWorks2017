package org.usfirst.frc.team2875.robot.commands;

import org.usfirst.frc.team2875.robot.IO;
import org.usfirst.frc.team2875.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command allows xbox 360 controller to drive the robot. It is always running
 * except when interrupted by another command.
 */
//test
public class Drive extends Command {
	public Drive() {
		requires(Robot.driveTrainSys);
	}

	protected void initialize() {
	}

	protected void execute() {
		Robot.driveTrainSys.input(Robot.input.getForwardInput(), Robot.input.getTurnInput()- (Robot.gyroscope.getAngle()*IO.SD_MULT));
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		Robot.driveTrainSys.stop();
	}

	protected void interrupted() {
		end();
	}
}
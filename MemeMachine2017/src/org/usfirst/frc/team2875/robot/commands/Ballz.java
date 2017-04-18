package org.usfirst.frc.team2875.robot.commands;

import org.usfirst.frc.team2875.robot.Robot;
import org.usfirst.frc.team2875.robot.subsystems.BallGrabber;

import edu.wpi.first.wpilibj.command.Command;

public class Ballz extends Command {

	public Ballz() {
		requires(Robot.ballGrabber);
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
	protected void execute(){
		Robot.ballGrabber.spin(Robot.input.getBallInput());
	}

}

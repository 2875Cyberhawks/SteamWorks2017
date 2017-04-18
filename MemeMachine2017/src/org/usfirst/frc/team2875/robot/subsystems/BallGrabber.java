package org.usfirst.frc.team2875.robot.subsystems;

import org.usfirst.frc.team2875.robot.Gearbox;
import org.usfirst.frc.team2875.robot.IO;
import org.usfirst.frc.team2875.robot.commands.Ballz;
import org.usfirst.frc.team2875.robot.commands.Drive;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class BallGrabber extends Subsystem {
	SpeedController motor;
	public BallGrabber(){
motor = new Talon(3);
	}
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new Ballz());
	}
	public void spin(double speed){
		motor.set((Math.abs(speed/1.15)-IO.JOY_DEADZONE) * getSign(speed));
	}
	private double getSign(double in){
		if(in == 0){
			return 0;
		}
		return (in / Math.abs(in));
	}
}

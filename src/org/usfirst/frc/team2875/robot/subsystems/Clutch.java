package org.usfirst.frc.team2875.robot.subsystems;

import org.usfirst.frc.team2875.robot.Debug;
import org.usfirst.frc.team2875.robot.Robot;
import org.usfirst.frc.team2875.robot.commands.Ballz;
import org.usfirst.frc.team2875.robot.commands.ClutchCmd;
import org.usfirst.frc.team2875.robot.commands.Gear;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Clutch extends Subsystem {
	public boolean isOpen = false;
	boolean bLast = false;
	double CLUTCHRELEASE_ZONE = 1;
	double delta;
	double lastDelta = 0;
public Clutch(){
	
}
	@Override
	protected void initDefaultCommand() {
		isOpen = Robot.c.get();
		setDefaultCommand(new ClutchCmd());
		
	}
	public void input(boolean b){
		delta = Math.abs(Robot.input.getForwardInput());
		if (b && !bLast){
			if (isOpen) close();
			if (!isOpen) open();
			isOpen = !isOpen;
		}
		bLast = b;
		SmartDashboard.putBoolean("Clutch", isOpen);
	/*	if(!b){
			if(isOpen && (delta < CLUTCHRELEASE_ZONE) && (lastDelta > delta)){
				close();
				isOpen = false;
			}
		}*/
		lastDelta = Math.abs(Robot.input.getForwardInput());
	}
	public void open(){
			Robot.c.set(true);
			Robot.lights.set(Relay.Value.kOn);
			Robot.lights.set(Relay.Value.kForward);
			Robot.gear.close();
			
		}
	
	public void close(){
		Robot.c.set(false);
		Robot.lights.set(Relay.Value.kOff);
		Robot.gear.close();
	}

}

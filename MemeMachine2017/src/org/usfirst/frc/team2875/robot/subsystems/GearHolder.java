package org.usfirst.frc.team2875.robot.subsystems;

import org.usfirst.frc.team2875.robot.Robot;
import org.usfirst.frc.team2875.robot.commands.Ballz;
import org.usfirst.frc.team2875.robot.commands.Gear;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GearHolder extends Subsystem {
	public boolean isOpen = false;
	boolean bLast = false;
public GearHolder(){
	
}
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new Gear());
		
	}
	public void input(boolean b){
		if (b && !bLast){
			if (isOpen) close();
			if (!isOpen) open();
			isOpen = !isOpen;
		}
		bLast = b;
		if(Robot.c.get()){
			SmartDashboard.putString("Gate Value", "Open");
		}else{
			SmartDashboard.putString("Gate Value", "Closed");
		}
	}
	public void open(){
			Robot.gearHolder.set(true);
		}
	
	public void close(){
		Robot.gearHolder.set(false);
	}

}

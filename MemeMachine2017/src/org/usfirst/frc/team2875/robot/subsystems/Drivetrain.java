package org.usfirst.frc.team2875.robot.subsystems;

import org.usfirst.frc.team2875.robot.Debug;
import org.usfirst.frc.team2875.robot.Gearbox;
import org.usfirst.frc.team2875.robot.IO;
import org.usfirst.frc.team2875.robot.Robot;
import org.usfirst.frc.team2875.robot.commands.Drive;

import auto.AutoMid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.hal.HAL;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tInstances;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tResourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drivetrain extends Subsystem{
	
	private boolean drivingStraight;
	private double straight;
	private int count;
	public int straightdrive_delay = 4;
	public Drivetrain(){
		System.out.println("starting");
		
		//1/12 -- 0 is left, 1 is right TBU

	}
	public void setSpeed(double speed){
		Robot.leftGearbox.setSpeed(speed);
		Robot.rightGearbox.setSpeed(speed);
	}
	/*public void setSpeed(int num, double speed){
		gearbox[num].setSpeed(speed);
	}*/
	private double getSign(double in){
		if(in == 0){
			return 0;
		}
		return (in / Math.abs(in));
	}
	public void input(double outputMagnitude, double curve) {
	    final double leftOutput;
	    final double rightOutput;
	    if (curve < 0) {
	      double value = Math.log(-curve);
	      double ratio = (value - IO.DRIVE_SENSITIVITY) / (value + IO.DRIVE_SENSITIVITY);
	      if (ratio == 0) {
	        ratio = .0000000001;
	      }
	      leftOutput = outputMagnitude / ratio;
	      rightOutput = outputMagnitude;
	    } else if (curve > 0) {
	      double value = Math.log(curve);
	      double ratio = (value - IO.DRIVE_SENSITIVITY) / (value + IO.DRIVE_SENSITIVITY);
	      if (ratio == 0) {
	        ratio = .0000000001;
	      }
	      leftOutput = outputMagnitude;
	      rightOutput = outputMagnitude / ratio;
	    } else {
	      leftOutput = outputMagnitude;
	      rightOutput = outputMagnitude;
	    }
	    Robot.rightGearbox.setSpeed(-rightOutput);
		Robot.leftGearbox.setSpeed(leftOutput);
	  }
	
	protected void initDefaultCommand() {
		setDefaultCommand(new Drive());
	}
	public void stop() {
		
		Robot.rightGearbox.stop();
		Robot.leftGearbox.stop();
	}
	

}

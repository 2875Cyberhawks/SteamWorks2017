package org.usfirst.frc.team2875.robot.commands;

import org.usfirst.frc.team2875.robot.Debug;
import org.usfirst.frc.team2875.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class CmdDriveDistance extends Command{
	private double distance;
	private double speed;
	private double originDistL;
	private double originDistR;
	private double straight;
	public CmdDriveDistance(double dist,double ispeed){
		distance = dist;
		speed = ispeed;
	}
	@Override
	protected void initialize(){
		System.out.println("command starting");
		Robot.encoders.resetEncoders();
		originDistL = Robot.encoders.getLeftEncoder();
		originDistR = Robot.encoders.getRightEncoder();
		straight = Robot.gyroscope.get_heading();
		
	}
	@Override
	protected void execute(){
		
		double forward = speed;
		Double cur = Robot.gyroscope.get_heading() - straight;
		Debug.log("Straight", straight);
		
			if (cur> 0){
				Robot.leftGearbox.setSpeed((forward-Math.abs(cur)/90));
				Robot.rightGearbox.setSpeed((forward+Math.abs(cur)/90));
			}else if(cur <0 ){
				Robot.rightGearbox.setSpeed((forward-Math.abs(cur)/90));
				Robot.leftGearbox.setSpeed((forward+Math.abs(cur)/90));
			}else{
				Robot.rightGearbox.setSpeed(forward);
				Robot.leftGearbox.setSpeed(forward);
			}
		
		}
	
	@Override
	protected boolean isFinished() {
	//	double totalDelta = (originDistL - Robot.encoders.getLeftEncoder()) + (originDistR - Robot.encoders.getRightEncoder()) / 2;
		double deltaLeft = Math.abs(originDistL - Robot.encoders.getLeftEncoder());
		double deltaRight = Math.abs(originDistR - Robot.encoders.getRightEncoder());
		if(deltaLeft >= distance || deltaRight >= distance){
			//Robot.driveTrainSys.straightdrive_delay=4;
			Robot.driveTrainSys.stop();
			return true;
		}else{
			return false;
		}
	}
	
	
}

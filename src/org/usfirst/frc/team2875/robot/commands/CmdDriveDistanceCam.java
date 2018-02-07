package org.usfirst.frc.team2875.robot.commands;

import org.usfirst.frc.team2875.robot.Debug;
import org.usfirst.frc.team2875.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class CmdDriveDistanceCam extends Command{
	private double distance;
	private double speed;
	private double originDistL;
	private double originDistR;
	private double straight;
	public CmdDriveDistanceCam(double dist,double ispeed, double maxTime){
		distance = dist;
		speed = ispeed;
		setTimeout(maxTime);
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
		Double cur = 0.0;//Robot.vis.getAngle();
		
		Debug.log("Straight", straight);
		if (cur> 0){
				Robot.leftGearbox.setSpeed((speed+Math.abs(cur)/90));
				Debug.log("Gearbox Left", -speed);
				Robot.rightGearbox.setSpeed((speed-Math.abs(cur)/90));
		}else if(cur <0 ){
				Robot.rightGearbox.setSpeed((speed+Math.abs(cur)/90));
				Robot.leftGearbox.setSpeed((speed-Math.abs(cur)/90));
				Debug.log("Gearbox Left", -(speed-Math.abs(cur)/90));
			}else{
				Robot.rightGearbox.setSpeed(speed);
				Robot.leftGearbox.setSpeed(speed);
			}	
		}
	
	@Override
	protected boolean isFinished() {
	//	double totalDelta = (originDistL - Robot.encoders.getLeftEncoder()) + (originDistR - Robot.encoders.getRightEncoder()) / 2;
		double deltaLeft = Math.abs(originDistL - Robot.encoders.getLeftEncoder());
		double deltaRight = Math.abs(originDistR - Robot.encoders.getRightEncoder());
		//System.out.println(Robot.encoders.getLeftEncoder());
		//System.out.println(Robot.encoders.getRightEncoder());
		if(deltaLeft >= distance || deltaRight >= distance){
			//Robot.driveTrainSys.straightdrive_delay=4;
			System.out.println("bad meme");
			Robot.driveTrainSys.stop();
			return true;
		}else{
			return false;
		}
	}
	
	
}

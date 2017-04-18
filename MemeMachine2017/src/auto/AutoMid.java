package auto;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team2875.robot.commands.*;
public class AutoMid extends CommandGroup {

	public AutoMid(){
		System.out.println("Runing");
		//addSequential(new CmdRotateAngle(10, .3));
	//addSequential(new GearAuto(false));
		//addSequential(new TimeOut(2));
		
		addSequential(new CmdDriveDistanceCam(50,.4, 3));
		addSequential(new CmdDriveDistance(20 ,.4));
		//addSequential(new GearAuto(true));
		//addSequential(new TimeOut(1));
		//addSequential(new CmdDriveDistance(.2, -.8));
		//addSequential(new GearAuto(false));
	
	}
	
	
	
}

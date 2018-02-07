package auto;

import org.usfirst.frc.team2875.robot.commands.CmdDriveDistance;
import org.usfirst.frc.team2875.robot.commands.CmdDriveDistanceCam;
import org.usfirst.frc.team2875.robot.commands.CmdRotateAngle;
import org.usfirst.frc.team2875.robot.commands.GearAuto;
import org.usfirst.frc.team2875.robot.commands.TimeOut;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoLeft extends CommandGroup{
	public AutoLeft(){
		addSequential(new CmdDriveDistance(86 ,.6));
		addSequential(new CmdRotateAngle(40, .4, 1));
		addSequential(new CmdDriveDistanceCam(14, .4, 3));
		addSequential(new CmdDriveDistance(16 ,.6));
		
		/*addSequential(new CmdDriveDistance(45 ,.6));
		addSequential(new CmdRotateAngle(49, .4, 0));
		addSequential(new CmdDriveDistance(47 ,.6));
		//addSequential(new CmdRotateAngle(358, .8, 1));//xd
		
		addSequential(new CmdRotateAngle(90, .4, 1));
		addSequential(new CmdDriveDistanceCam(20, .4, 3));
		addSequential(new CmdDriveDistance(20, .4));*/
		//addSequential(new CmdDriveDistanceCam(8, .5, 2.5));//xd
		//addSequential(new GearAuto(true));
	    //addSequential(new TimeOut(.2));
		//addSequential(new CmdDriveDistance(5, -.8));
		
		 	//addSequential(new TimeOut(1));
		 
		//addSequential(new GearAuto(false));
		/*
		addSequential(new CmdDriveDistanceCam(8, -.5, 2.5));
		addSequential(new CmdDriveDistanceCam(33, -.5, 2.5));
		addSequential(new CmdRotateAngle(358, .8, 0));
		addSequential(new CmdRotateAngle(90, .5, 0));
		addSequential(new CmdDriveDistance(35 ,-.7));
		addSequential(new CmdRotateAngle(45, .5, 1));
		addSequential(new CmdDriveDistance(48 ,-.7));
		*/
}
}

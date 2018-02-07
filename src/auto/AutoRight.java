package auto;

import org.usfirst.frc.team2875.robot.commands.CmdDriveDistance;
import org.usfirst.frc.team2875.robot.commands.CmdDriveDistanceCam;
import org.usfirst.frc.team2875.robot.commands.CmdRotateAngle;
import org.usfirst.frc.team2875.robot.commands.GearAuto;
import org.usfirst.frc.team2875.robot.commands.TimeOut;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoRight extends CommandGroup{
	public AutoRight(){
		
		addSequential(new CmdDriveDistance(75, .5));
		addSequential(new CmdRotateAngle(40, .5, 0));
		addSequential(new CmdDriveDistanceCam(31, .5, 3));
		addSequential(new CmdDriveDistance(16, .5));
		//addSequential(new GearAuto(true));
		//addSequential(new TimeOut(.3));
		//addSequential(new CmdDriveDistance(5, -.8));
	}
}

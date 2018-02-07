package org.usfirst.frc.team2875.robot.subsystems;

import org.usfirst.frc.team2875.robot.commands.AutoAlignCmd;

import edu.wpi.first.wpilibj.command.Subsystem;

public class AutoAlign extends Subsystem{

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new AutoAlignCmd());
		
	}

}

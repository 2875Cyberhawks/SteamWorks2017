package org.usfirst.frc.team2875.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Debug {
	public static void log(String name, double num){
		SmartDashboard.putNumber(name, num);
	}
	public static void log(String name, boolean bool){
		SmartDashboard.putBoolean(name, bool);
	}
}

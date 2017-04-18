package org.usfirst.frc.team2875.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;

import org.usfirst.frc.team2875.robot.commands.ExampleCommand;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Encoders extends Subsystem {

	private Encoder left_encoder;
	private Encoder right_encoder;
	
	
	public Encoders() //You're Ugly
	{
		left_encoder = new Encoder(6,7, true, EncodingType.k1X);       		
		left_encoder.setDistancePerPulse(.148);
		//0.0027786
		right_encoder= new Encoder(8,9,true, EncodingType.k1X);
		right_encoder.setDistancePerPulse(.148);
	}
	public double getLeftSpeed(){
		return left_encoder.getRate();
	}
	public double getRightSpeed(){
		return right_encoder.getRate();
	}
	public void resetEncoders()
	{
		left_encoder.reset();
		right_encoder.reset();
	}
	
	public Double getLeftEncoder()
	{
		return left_encoder.getDistance();
	}
	
	public Double getRightEncoder()
	{
		return right_encoder.getDistance();
	}

	@Override
	protected void initDefaultCommand() {		
		setDefaultCommand(null);
	}
}
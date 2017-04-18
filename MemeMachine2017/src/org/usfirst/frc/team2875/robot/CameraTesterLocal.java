package org.usfirst.frc.team2875.robot;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

public class CameraTesterLocal {
	/*public static void main(String[] args){
		new Thread(()->{
			System.out.println("Camera Init");
			UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
	        camera.setExposureManual(20);
	        camera.setResolution(640, 480);
	        camera.setFPS(15);
	        CvSink cvSink = CameraServer.getInstance().getVideo();
	        CvSource outputStream = CameraServer.getInstance().putVideo("Processed", 640, 480);
	        Mat source = new Mat();
	        List<Mat> rgbChannels = new ArrayList<Mat>();
	        Mat greenMat;
	        //List<MatOfPoint> matPoints = new ArrayList<MatOfPoint>();
	        System.out.println("Camera Init Over");
	        while(!Thread.interrupted()) {
	        	cvSink.grabFrame(source);
	        	Core.split(source, rgbChannels);
	        	greenMat = rgbChannels.get(1);
	            outputStream.putFrame(greenMat);
	        		
	        }
		});
	}*/
}

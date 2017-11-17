package org.usfirst.frc.team2875.robot;

import java.util.ArrayList;
import java.util.Vector;

import org.opencv.core.Core;
import org.opencv.core.CvType;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CameraServerJNI;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CamThread  {
	private UsbCamera camera2;
	private UsbCamera camera;
	private Mat green;
	static int trackingThreshold;
    static int blue,gren,red;
    static boolean isOpen;
   // private CvSource outputStream2;
    private  CvSource outputStream;
    private int i = 0;
    private double dist;
    private double angle;
    public boolean canSee= false;
    public boolean running = true;
    Mat first;
   // private CameraServerJNI cs;
	

    public CamThread() {
    	SmartDashboard.putNumber("Peg Cam Threshold", 145);
    	SmartDashboard.putNumber("Peg Cam Threshold Upper", 240);
    	camera = CameraServer.getInstance().startAutomaticCapture();
    	camera.setResolution(320, 240);
	   camera.setExposureManual(1);
		camera.setBrightness(1);
	    camera.setFPS(10);
	    camera.setPixelFormat(PixelFormat.kMJPEG);
		outputStream = CameraServer.getInstance().putVideo("Processed", 320, 240);
		camera2 = CameraServer.getInstance().startAutomaticCapture(1);
    	camera2.setFPS(10);
    	camera2.setResolution(400, 300);
    	camera2.setPixelFormat(PixelFormat.kMJPEG);
		System.out.println("Camera Init");
		run();
    }
    public void transition(){
    	
    }
    Thread thread;
public  void processing(){
	 thread = new Thread(()->{
		while(!Thread.interrupted()){
		if(!running){
			outputStream.free();
			//CameraServer.getInstance().removeCamera("USB Camera 0");
			
			break;
		}
		
		SmartDashboard.putNumber("iterations", i);
		i++;
		Mat mat = new Mat();
		CameraServer.getInstance().getVideo().grabFrame(mat);
		//Mat matbrightness = Mat.zeros(mat.size(), mat.type());
		//Core.add(mat, Scalar.all(130), matbrightness);
		//outputStream2.putFrame(matbrightness);
		//matbrightness.release();
		ArrayList<Mat> channels = new ArrayList<Mat>();
		//ArrayList<Mat> invChannels = new ArrayList<Mat>();
		Core.split(mat, channels);
		green = mat;
		Mat dab = new Mat();
		Core.multiply(mat, new Scalar(2, 2,  3), dab);
		Mat gut = new Mat();
	    Imgproc.cvtColor(dab, gut, Imgproc.COLOR_BGR2GRAY);
		Mat bridge = new Mat();
		Core.subtract(gut, channels.get(2), bridge);
		Mat bridge2 = new Mat();
		Core.subtract(gut, channels.get(1), bridge2);
		Mat out = new Mat();
		Core.add(bridge2, bridge, out);
		//System.out.println("This is a frame");
		 
		green = channels.get(0);
		channels.get(1).release();
		channels.get(2).release();
		//Imgproc.GaussianBlur(green, green, new Size(3,3), 2);
        Core.inRange(green, new Scalar(SmartDashboard.getNumber("Peg Cam Threshold", 145)), new Scalar(SmartDashboard.getNumber("Peg Cam Threshold Upper", 240)), green);
		Imgproc.threshold(green, green, SmartDashboard.getNumber("Peg Cam Threshold", Robot.prefs.getInt("Peg Cam Threshold", 145)), 255, Imgproc.THRESH_BINARY);
		for(int i = 0; i < 6; i++)
		Imgproc.erode(green, green,  Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2,2),new Point(-1,-1)));
		for(int i = 0; i < 6; i++)
		Imgproc.dilate(green, green,  Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2,2),new Point(-1,-1)));
        Imgproc.Canny(green, green, 100, 255);	
        //Imgproc.HoughLinesP(green, green, 1, Math.PI/180,50, 50, 10);
//out.release();
		//int range = 20;
		//double[] colors = mat.get((int)(mat.size().height / 2), ((int)mat.size().width /2));
		//Core.inRange(green, new Scalar(blue -range, gren -range, red - range), new Scalar(blue + range, gren + range, red + range), green);
		//Imgproc.GaussianBlur(green, green, new Size(3,3), 2);
		//Mat green2 = green;
		//Imgproc.threshold(green, green, SmartDashboard.getNumber("Peg Cam Threshold Upper", 255), 255, Imgproc.THRESH_BINARY_INV);
		//Imgproc.threshold(green, green, SmartDashboard.getNumber("Peg Cam Threshold", Robot.prefs.getInt("Peg Cam Threshold", 70)), 255, Imgproc.THRESH_BINARY);
		//Core.compare(green, green2, green, Core.CMP_EQ);
		
		//Robot.prefs.putInt("Peg Cam Threshold", 70);
		//Imgproc.Canny(green, green, 100, 255);
		
		Mat finalized = new Mat();
		ArrayList<MatOfPoint> matPoints = new ArrayList<MatOfPoint>();
		Imgproc.findContours(green, matPoints, finalized, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
		//Mat hi = Mat.zeros(green.size(), green.type());
		//Vector<Vector<Point>> polys = new Vector<Vector<Point>>(matPoints.size());
		Vector<Rect> rects = new Vector<Rect>(matPoints.size());
		for(int i = 0; i < matPoints.size(); i++){
			MatOfPoint2f nice = new MatOfPoint2f();
			matPoints.get(i).convertTo(nice, CvType.CV_32FC2);
			Imgproc.approxPolyDP(nice, nice, 2, true);
			nice.convertTo(matPoints.get(i), CvType.CV_32S);
		}
		
		for(int i = 0; i < matPoints.size(); i++ )
	    { 

			Rect rect = Imgproc.boundingRect(matPoints.get(i));
			rects.add(rect);   
	    }
		int j = -1;
		int h = -1;
		//System.out.println("Height: " + rects.get(0).height + " Width: " + rects.get(0).width);
		//System.out.println(rects.get(0).height / rects.get(0).width);
		
		//Imgproc.rsectangle(green, rects.get(1).tl(), rects.get(1).br(), Scalar.all(255));
		//System.out.println(rects.get(1).height / rects.get(1).width);
		for( int i = 0; i< matPoints.size(); i++ )
		{ 
			//System.out.println(rects.get(i).height / rects.get(i).width);
			if (rects.get(i).height > 100){
						break;
			}
				
			if(0 < rects.get(i).height && rects.get(i).height < 350 && Math.abs((rects.get(i).height / rects.get(i).width) - (2.5)) < 1){			
				j = i;
				break;
			}
	    }
		if(j != -1){
		for( int i = 0; i< matPoints.size(); i++ )
		{ 
			if (rects.get(i).height > 100){
				break;
	}
			if(0 < rects.get(i).height && rects.get(i).height < 350 && Math.abs((rects.get(i).height / rects.get(i).width) - (2.5)) < 1 && i != j && Math.abs(rects.get(i).tl().x - rects.get(j).tl().x) > 10){
				h = i;
				break;
			}
			
		
	    }
		}
		for(int i = 0;i < matPoints.size(); i++){
			matPoints.get(i).release();
		}

		int xavg =green.width() /2;
		int yavg = green.height()/ 2;
		canSee = j != -1 && h != -1;
		if(j != -1 && h != -1){
			
			if(rects.get(j).x > rects.get(h).x){
				xavg = (int)((rects.get(j).br().x + rects.get(h).tl().x) / 2);
				yavg = (int)((rects.get(j).br().y + rects.get(h).tl().y) / 2);
			}else{
				xavg = (int)((rects.get(j).tl().x + rects.get(h).br().x) / 2);
				yavg = (int)((rects.get(j).tl().y + rects.get(h).br().y) / 2);
			}
			
			//System.out.println("X1: " + rects.get(j).tl().x + " X2:" + rects.get(h).tl().x);
		}
		Imgproc.line(green, new Point(xavg, 0), new Point(xavg, green.height()), new Scalar(255,0,0));
		Imgproc.line(green, new Point(0, yavg), new Point(green.width(), yavg), new Scalar(255,0,0));
		angle = (xavg - ( green.width() / 2 )) / 10.6666666;
		//System.out.println(angle);
		outputStream.putFrame(green);
		finalized.release();
		green.release();
		 }
	 });
	 thread.start();
	}
private Scalar Scalar(int j) {
	// TODO Auto-generated method stub
	return null;
}
public void start(){
	//thread.start();
}
public void stop(){
	running = false;
}
public double getDistance(){
	return dist;
}
public double getAngle(){
	return angle;
}
public void run() {
	processing();
	
}
}

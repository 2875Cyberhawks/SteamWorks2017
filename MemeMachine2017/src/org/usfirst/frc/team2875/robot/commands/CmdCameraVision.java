package org.usfirst.frc.team2875.robot.commands;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;

import org.opencv.core.Core;
//import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import edu.wpi.first.wpilibj.command.Command;
//import edu.wpi.first.wpilibj.command.Command;
public class CmdCameraVision extends Command{
	//CvSink cvSink;
   // Mat original;
   // Mat output;
    static int trackingThreshold;
    static int blue,gren,red;
   //static  Mat green;
    static boolean isOpen;
    public void initialize(){
    	/*Robot.camera = CameraServer.getInstance().startAutomaticCapture();
		Robot.camera.setResolution(640, 480);
		Robot.camera.setExposureManual(10);
		Robot.camera.setBrightness(10);
		Robot.camera.setFPS(30);
        //cvSink = CameraServer.getInstance().getVideo();
		//new CvSource("Processed",VideoMode.getPixelFormatFromInt(2), 640, 480, 5 );//
		Robot.outputStream = CameraServer.getInstance().putVideo("Processed", 640, 480);
		//Robot.outputStream.setFPS(5);
			System.out.println("Camera Init");
		    imagery = new Mat();
		    System.out.println("Camera Init Over");
		    new Thread(()->{
		    	System.out.println("one loop");
		  		CameraServer.getInstance().getVideo().grabFrame(imagery);
		  		Robot.outputStream.putFrame(processing(imagery));
		    	});*/
    }
    protected void execute(){
    	
    }
	public CmdCameraVision() {
		
		   
		   
	}
	static JFrame frame0;
	Mat imagery;
	//CvSource outputStream;
	public  Mat processing(Mat mat){
		
		ArrayList<Mat> channels = new ArrayList<Mat>();
		ArrayList<Mat> invChannels = new ArrayList<Mat>();
		Core.split(mat, channels);
		Mat green = mat;
		Mat dab = new Mat();
		Core.multiply(mat, new Scalar(2, 1,  2), dab);
		Mat gut = new Mat();
	    Imgproc.cvtColor(dab, gut, Imgproc.COLOR_BGR2GRAY);
		Mat bridge = new Mat();
		Core.subtract(gut, channels.get(2), bridge);
		Mat bridge2 = new Mat();
		Core.subtract(gut, channels.get(0), bridge2);
		Mat out = new Mat();
		Core.add(bridge2, bridge, out);
		System.out.println("This is a frame");
		green = mat;
		int range = 20;
		double[] colors = mat.get((int)(mat.size().height / 2), ((int)mat.size().width /2));
		//Core.inRange(green, new Scalar(blue -range, gren -range, red - range), new Scalar(blue + range, gren + range, red + range), green);
		Imgproc.GaussianBlur(green, green, new Size(5,5), 4);
		Imgproc.threshold(green, green, 150, 255, Imgproc.THRESH_BINARY);
		Imgproc.Canny(green, green, 80, 255);
		Mat finalized = new Mat();
		ArrayList<MatOfPoint> matPoints = new ArrayList<MatOfPoint>();
		Imgproc.findContours(green, matPoints, finalized, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
		Mat hi = Mat.zeros(green.size(), green.type());
		Vector<Vector<Point>> polys = new Vector<Vector<Point>>(matPoints.size());
		Vector<Rect> rects = new Vector<Rect>(matPoints.size());
		//Imgproc.polylines(green, matPoints, false, Scalar.all(255), 3);
		//int j = -1;
		double area = 0; 
		for(int i = 0; i < matPoints.size(); i++){
			MatOfPoint2f nice = new MatOfPoint2f();
			matPoints.get(i).convertTo(nice, CvType.CV_32FC2);
			Imgproc.approxPolyDP(nice, nice, 20, true);
			nice.convertTo(matPoints.get(i), CvType.CV_32S);
			
			//matPoints2f.add(nice);
		}
		
		for(int i = 0; i < matPoints.size(); i++ )
	    { 

			Rect rect = Imgproc.boundingRect(matPoints.get(i));
			//System.out.println(matPoints.get(i).total());
			if(rect.area() > area){
				//j = i;
				area = rect.area();
			}
			rects.add(Imgproc.boundingRect( (matPoints.get(i))));   
	    }
		double xval =0;
		double yval =0;
		double totalArea = 0;
		double area2 = 0;
		int j = -1;
		for( int i = 0; i< matPoints.size(); i++ )
		{ 
			if(Imgproc.contourArea(matPoints.get(i)) > area2){
				area2 = Imgproc.contourArea(matPoints.get(i));
				j = i;
			}
			Imgproc.rectangle(mat, rects.get(i).tl(), rects.get(i).br(), Scalar.all(255));
            xval += Imgproc.contourArea(matPoints.get(i)) * (rects.get(i).tl().x + rects.get(i).br().x) / 2;
            yval += Imgproc.contourArea(matPoints.get(i)) * (rects.get(i).tl().y + rects.get(i).br().y) / 2;
            totalArea += Imgproc.contourArea(matPoints.get(i));
	    }
		double xd = 0;
		double yd = 0;
		if(j != -1){
			xd = (rects.get(j).tl().x + rects.get(j).br().x) / 2;
			yd = (rects.get(j).tl().y + rects.get(j).br().y) / 2;
			Imgproc.rectangle(green, rects.get(j).tl(), rects.get(j).br(), Scalar.all(255));
		}
		xval /= matPoints.size();
		yval /= matPoints.size();
		xval /= totalArea;
		yval /= totalArea;
		//System.out.println(xval + "   " + yval);

		Imgproc.line(green, new Point(xd, 0), new Point(xd, green.height()), new Scalar(255,0,0));
		Imgproc.line(green, new Point(0, yd), new Point(green.width(), yd), new Scalar(255,0,0));
		//mat.release();
		
		return green;
		
	}
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
}

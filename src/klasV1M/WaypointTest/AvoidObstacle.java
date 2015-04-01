package klasV1M.WaypointTest;

import java.util.Iterator;
import java.util.LinkedList;

import klasV1M.TI.sensoren.MyUltraSonicSensor;
import klasV1M.TI.sensoren.SensorHandler;
import klasV1M.TI.sensoren.SensorListener;
import klasV1M.TI.sensoren.UpdatingSensor;
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.comm.RConsole;

public class AvoidObstacle implements SensorListener{
	public static void main(String[] args) {
		//RConsole.open();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AvoidObstacle aob = new AvoidObstacle();
		//aob.start();
		Button.waitForAnyPress();
	}

	private boolean objectDetected;
	public static MyUltraSonicSensor MUS = new MyUltraSonicSensor(SensorPort.S4);
	public static NXTRegulatedMotor mRight = Motor.A;
	/**
	 * The middle {@link NXTRegulatedMotor}
	 */
	public static NXTRegulatedMotor mMiddle = Motor.B;
	/**
	 * The left {@link NXTRegulatedMotor}
	 */
	public static NXTRegulatedMotor mLeft = Motor.C;
	public static LinkedList<Float[]> angleAndCM = new LinkedList<Float[]>();
	private boolean left, right, middle = false;
	
	public AvoidObstacle() {
		SensorHandler.PERIOD = 200;
		mMiddle.resetTachoCount();
		MUS.addListener(this);
		mLeft.setAcceleration(180);
		mRight.setAcceleration(180);
	    mLeft.setSpeed(180);
		mRight.setSpeed(180);
		mLeft.forward();
		mRight.forward();
	}
	
	//@Override
	//public void run() {
		/*
		 * Globals.mLeft.setSpeed(360); Globals.mRight.setSpeed(360);
		 * Globals.mLeft.forward(); Globals.mRight.forward();
		 */
	//	mMiddle.setSpeed(90);
	//	while (true) {
			//scanEnvironment();
		//	try {
		//		Thread.sleep(500);
		//	} catch (InterruptedException e) {

		//	}
	//	}
	//}
	
	public void calculateRoute() {
		float hoek = angleAndCM.get(angleAndCM.size() - 1)[1];
		float distance = angleAndCM.get(angleAndCM.size() - 1)[0];
		
		if(-5 < hoek && hoek < 5 && distance < 60){
			//object in front 
			System.out.println("Obstacle in front of us");
			//RConsole.println("front");
			middle = true;
		}
		else if(hoek > 5 && distance < 60){
			System.out.println("Obstacle at the right side");
		//	RConsole.println("right");
			right = true;
			//object at the right side
			
		}
		else if(hoek < -5 && distance < 60){
			System.out.println("Obstacle at the left side");
			left = true;
		//	RConsole.println("left");
			//object at the right side
			
		}
		if(right && middle){ // determine if you want to drive left or right the obstacle.
			//left = false;
		    right = false;
		    middle = false;
			mLeft.setSpeed(140);
			mRight.setSpeed(220);
			System.out.println("driving via left side");
		}
		else if(left && middle){ // determine if you want to drive left or right the obstacle.
			left = false;
		    middle = false;
		   // middle = false;
			mLeft.setSpeed(140);
			mRight.setSpeed(220);
			System.out.println("driving via right side");
		}
		else if(middle){ //obstacle still in middle
			mLeft.setSpeed(90);
			mRight.setSpeed(220);
		}
		
		
		/*
		if (angleAndCM.get(angleAndCM.size() - 1)[1] <= 40) {
			Iterator<Float[]> theIterator = angleAndCM.iterator();

			while (theIterator.hasNext()) {
				Float[] input = theIterator.next();
				Float distance = input[0];
				Float angle = input[1];
				System.out.println("distance: " + distance + " angle: " + angle);
			}
		}*/
	}
	
	public void scanEnvironment() {
		// Globals.mMiddle.setAcceleration(20);
		// Globals.mMiddle

		mMiddle.rotateTo(-60); //links
		mMiddle.rotateTo(+60);// rechts

	}

	@Override
	public void stateChanged(UpdatingSensor s, float oldVal, float newVal) {
		if (s.equals(MUS)) {
			if (newVal < 50) {
				mLeft.setSpeed(20);
				mRight.setSpeed(20);
				angleAndCM.add(new Float[] { newVal,
						(float) mMiddle.getTachoCount() });
				
				// Object detected
			//	System.out.println("Obstacle cm: " + newVal);
			//	System.out.println("Angle: " + mMiddle.getTachoCount());
				//mMiddle.setSpeed(50);
				//mMiddle.rotateTo(-60);
				//mMiddle.rotateTo(+60);
				mLeft.setSpeed(90);
				mRight.setSpeed(220);
				// angleAndCM
				//calculateRoute();
				objectDetected = true;
				
				//mLeft.setSpeed(220);
				//mRight.setSpeed(140);
				// TODO: Scan area now or do so while driving?

			} else {
				if(objectDetected){
					mRight.setSpeed(90);
					mLeft.setSpeed(220);
				}
				else{
					mLeft.setSpeed(180);
					mRight.setSpeed(180);
					objectDetected = false;					
				}

			}
		}
		
		
	}
	
	
}

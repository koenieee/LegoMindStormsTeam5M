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

public class AvoidObstacle  implements SensorListener, Runnable{
	
	public static void main(String[] args) {
		RConsole.open();
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

	private Thread t;
	private boolean beginScan;
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
	private boolean doneScanning = false;
	private boolean startOmzeilen;
	
	public AvoidObstacle() {
		t = null;
		SensorHandler.PERIOD = 200;
		mMiddle.resetTachoCount();
		MUS.addListener(this);
	/*	mLeft.setAcceleration(180);
		mRight.setAcceleration(180);
	    mLeft.setSpeed(180);
		mRight.setSpeed(180);
		mLeft.forward();
		mRight.forward();*/
	}
	
	public void start(){
		if(t == null){
			t = new Thread(this);
			t.start();
		}
	}
	
	public void stop(){
		if(t != null){
			t.interrupt();
			t = null;
		}
	}
	
	//@Override
	public void run() {
		/*
		 * Globals.mLeft.setSpeed(360); Globals.mRight.setSpeed(360);
		 * Globals.mLeft.forward(); Globals.mRight.forward();
		 */
	//	mMiddle.setSpeed(90);
		mMiddle.setSpeed(30);
		while (!t.interrupted()) {
			mMiddle.rotateTo(-70); //links
			mMiddle.rotateTo(+70);// rechts
			mMiddle.rotateTo(0);
		}
	}
	
	public void calculateRoute(float newVal, int angle) {
		System.out.println("Centi: " + newVal);
		System.out.println("Angle: " + mMiddle.getTachoCount());
			if(-5 < angle && angle < 5 && newVal < 30){
				//object in front 
				System.out.println("front is blocked");
				//RConsole.println("front");
				//middle = true;
			}
			else if(angle > 5 && newVal < 30){
				System.out.println("right is blocked");
			//	RConsole.println("right");
				//right = true;
				//object at the right side
				
			}
			else if(angle < -5 && newVal < 30){
				System.out.println("left is blocked");
				//left = true;
			//	RConsole.println("left");
				//object at the right side
				
			}
		}
		//doneScanning = false;
		
/*
		if(-5 < hoek && hoek < 5 && distance < 30){
			//object in front 
			System.out.println("Obstacle in front of us");
			//RConsole.println("front");
			//middle = true;
		}
		else if(hoek > 5 && distance < 30){
			System.out.println("Obstacle at the right side");
		//	RConsole.println("right");
			//right = true;
			//object at the right side
			
		}
		else if(hoek < -5 && distance < 30){
			System.out.println("Obstacle at the left side");
			//left = true;
		//	RConsole.println("left");
			//object at the right side
			
		}
	/*	if(right && middle){ // determine if you want to drive left or right the obstacle.
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
		*/
		
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
	//}
	
	@Override
	public void stateChanged(UpdatingSensor s, float oldVal, float newVal) {
		
		if (s.equals(MUS)) {
			
			if (startOmzeilen){
				if(right && left){ //zowel links als rechts is niet vrij, wat nu??
					
				}
				if(right){ //rechts is geblokkeerd, dus links er om heen
					stop();
					mMiddle.setSpeed(50);
					mMiddle.rotateTo(+60);
					System.out.println("Rechts omzeilen");
				}
				else if(left){ //links is geblokkeerd, dus rechts er om heen.
					stop();
					mMiddle.setSpeed(50);
					mMiddle.rotateTo(-60);
					System.out.println("Links omzeilen");
					
				}
			}
			else if (!startOmzeilen && newVal < 30 && mMiddle.getTachoCount() == 0) { //object detected in front of us, scan right and left to find new path.
			//	mLeft.setSpeed(20);
			//	mRight.setSpeed(20);
			//	angleAndCM.add(new Float[] { newVal,
			//			(float) mMiddle.getTachoCount() });
				
				
				
				
				
				start();
				//System.out.println("");
				// Object detected
			//	System.out.println("Centi: " + newVal);
			//	System.out.println("Angle: " + mMiddle.getTachoCount());
				//mMiddle.setSpeed(50);
				//mMiddle.rotateTo(-60);
				//mMiddle.rotateTo(+60);
				//mLeft.setSpeed(90);
				//mRight.setSpeed(220);
				// angleAndCM
				//calculateRoute(newVal, mMiddle.getTachoCount() );
				beginScan = true;
				
				//mLeft.setSpeed(220);
				//mRight.setSpeed(140);
				// TODO: Scan area now or do so while driving?

			} else if(beginScan && !startOmzeilen){ //linksom of rechtsom?
				int angle = mMiddle.getTachoCount();
				
				if(angle > 35 && newVal < 30){
					System.out.println("right is blocked");
				//	RConsole.println("right");
					right = true;
					//object at the right side
					
				}
				else if(angle < -35 && newVal < 30){
					System.out.println("left is blocked");
					left = true;
				//	RConsole.println("left");
					//object at the right side
					
				}
				if(left || right){
					startOmzeilen = true;
					beginScan = false;
					stop();
					mMiddle.rotateTo(0); 
					
				}
				
				
				//System.out.println("calcu route");
				//start();
				//calculateRoute(newVal, mMiddle.getTachoCount() );
				
				//objectDetected = false;
			}
			
			//else if(!objectDetected && mMiddle.getTachoCount() == 0) {
			//	this.interrupt();
			//	mMiddle.rotateTo(0); 
			//}
			else {
			    stop();
			    
			}
			/*	if(objectDetected){
					mRight.setSpeed(90);
					mLeft.setSpeed(220);
				}
				else{
					mLeft.setSpeed(180);
					mRight.setSpeed(180);*/
				//	objectDetected = false;					
				//}

			}
		}
	

	@Override
	public void stateNotification(UpdatingSensor s, float value) {
		// TODO Auto-generated method stub
		
	}
	
	
}

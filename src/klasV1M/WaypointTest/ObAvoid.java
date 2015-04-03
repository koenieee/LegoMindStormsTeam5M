package klasV1M.WaypointTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import klasV1M.TI.sensoren.MyUltraSonicSensor;
import klasV1M.TI.sensoren.SensorHandler;
import klasV1M.TI.sensoren.SensorListener;
import klasV1M.TI.sensoren.UpdatingSensor;
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.comm.RConsole;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Waypoint;

public class ObAvoid implements SensorListener, Runnable {

	public static void main(String[] args) {
		RConsole.open();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ObAvoid aob = new ObAvoid();
		// aob.start();
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
	public static ArrayList<Integer> angles = new ArrayList<Integer>();
	public static ArrayList<Float> distances = new ArrayList<Float>();
	public int[] obRechts = new int[]{};
	public int[] obLinks = new int[]{};
	private boolean left, right, middle = false;
	private boolean doneScanning = false;
	private boolean startOmzeilen;
	private boolean drivingViaLeft;
	private int aantalKeer = 0;

	public ObAvoid() {
		t = null;
		SensorHandler.PERIOD = 100;
		mMiddle.resetTachoCount();
		MUS.addListener(this);
	/*	mLeft.setAcceleration(180);
	     mRight.setAcceleration(180);
		 mLeft.setSpeed(180);
		 mRight.setSpeed(180);
		 mLeft.forward();
		 mRight.forward();*/
	}

	public void start() {
		if (t == null) {
			t = new Thread(this);
			t.start();
		}
	}

	// @Override
	public void run() {
		/*
		 * Globals.mLeft.setSpeed(360); Globals.mRight.setSpeed(360);
		 * Globals.mLeft.forward(); Globals.mRight.forward();
		 */
		// mMiddle.setSpeed(90);
		mMiddle.setSpeed(30);
		while (aantalKeer < 1) {
			mMiddle.rotateTo(+70, false);// rechts
			
			// try {
			// Thread.sleep(400);
			// } catch (InterruptedException e) {
			// }
			mMiddle.rotateTo(-70, false); //links
			mMiddle.rotateTo(0, false); //links
			aantalKeer++;

		}
		//calculateObstacleWidth();

		// driveAroundObstacle();

	}

	/*
	 * public void calculateRoute(float newVal, int angle) {
	 * System.out.println("Centi: " + newVal); System.out.println("Angle: " +
	 * mMiddle.getTachoCount()); if(-5 < angle && angle < 5 && newVal < 30){
	 * //object in front System.out.println("front is blocked");
	 * //RConsole.println("front"); //middle = true; } else if(angle > 5 &&
	 * newVal < 30){ System.out.println("right is blocked"); //
	 * RConsole.println("right"); //right = true; //object at the right side
	 * 
	 * } else if(angle < -5 && newVal < 30){
	 * System.out.println("left is blocked"); //left = true; //
	 * RConsole.println("left"); //object at the right side
	 * 
	 * } }
	 */
	// doneScanning = false;

	/*
	 * if(-5 < hoek && hoek < 5 && distance < 30){ //object in front
	 * System.out.println("Obstacle in front of us");
	 * //RConsole.println("front"); //middle = true; } else if(hoek > 5 &&
	 * distance < 30){ System.out.println("Obstacle at the right side"); //
	 * RConsole.println("right"); //right = true; //object at the right side
	 * 
	 * } else if(hoek < -5 && distance < 30){
	 * System.out.println("Obstacle at the left side"); //left = true; //
	 * RConsole.println("left"); //object at the right side
	 * 
	 * } /* if(right && middle){ // determine if you want to drive left or right
	 * the obstacle. //left = false; right = false; middle = false;
	 * mLeft.setSpeed(140); mRight.setSpeed(220);
	 * System.out.println("driving via left side"); } else if(left && middle){
	 * // determine if you want to drive left or right the obstacle. left =
	 * false; middle = false; // middle = false; mLeft.setSpeed(140);
	 * mRight.setSpeed(220); System.out.println("driving via right side"); }
	 * else if(middle){ //obstacle still in middle mLeft.setSpeed(90);
	 * mRight.setSpeed(220); }
	 */

	/*
	 * if (angleAndCM.get(angleAndCM.size() - 1)[1] <= 40) { Iterator<Float[]>
	 * theIterator = angleAndCM.iterator();
	 * 
	 * while (theIterator.hasNext()) { Float[] input = theIterator.next(); Float
	 * distance = input[0]; Float angle = input[1];
	 * System.out.println("distance: " + distance + " angle: " + angle); } }
	 */
	// }

	public void calculateObstacleWidth() {
		System.out.println("Obstacle width is called");
		ArrayList<Number> anglas = returnFreeAngles();
		ArrayList<Number> distances = lowDistances();
		//int angleWhenObjectIsGone = (int)averageOfList(anglas);
		System.out.println("Angles: " + anglas);
		System.out.println("Distances: " + distances);
		
		//System.out.println("");
		//double cmWhenAngleIsZero = averageOfList(lowDistances());
		/*
		double widthObject = (Math.sin(Math.toRadians(angleWhenObjectIsGone))) * 10;
		
		System.out.println("The half of the object is: " + widthObject);
		
		DifferentialPilot pilot = new DifferentialPilot(3.4, 13.5, mLeft,
				mRight, false);
		  pilot.setTravelSpeed(7);
		pilot.travelArc(-(widthObject + 30), ((widthObject + 30)/ 4 ) * Math.PI);
		pilot.travelArc((widthObject + 30), ((widthObject + 30)/ 2 ) * Math.PI);
		pilot.travelArc(-(widthObject + 30), ((widthObject + 30)/ 4 ) * Math.PI);
		*/
		//pilot.setTravelSpeed(9);
        //pilot.setRotateSpeed(15);
       // pilot.rotate(-angleWhenObjectIsGone, false);
      
       // pilot.travel(widthObject + 30);
       // pilot.rotate(angleWhenObjectIsGone, false);
       // pilot.travel(widthObject + 30);
        
		
		
		/*
		for (int flan : angles) {
			itera++;
			RConsole.println("Iterator: " + itera);
			if (flan == 0) {
				System.out.println("Lengte bij 0 graden: "	+ distances.get(itera));
				//System.out.println("Hoek bij hoogste afstand: "	+ angles.get(indexKeydistance));
			}
		}*/
	}
	
	
	
	public double averageOfList(ArrayList<Number> NUMBERS){
		   double total = 0;
		    for (Number element : NUMBERS) {
		    		total += element.floatValue();
		    }
		    
		    double average = total / NUMBERS.size();
	  return average;
	}
	
	public ArrayList<Number> returnFreeAngles(){
		ArrayList<Integer> allTheHighs = getMaxes(distances);
		ArrayList<Number> tempAngles = new ArrayList<Number>();
		if(allTheHighs.size() != 0){
			for(int index : allTheHighs){
				tempAngles.add(angles.get(index-1)+1);
			}
		}
		return tempAngles;
		
	}
	
	public ArrayList<Number> lowDistances(){
		ArrayList<Integer> ind = getMaxes(distances);
		ArrayList<Number> tempDista = new ArrayList<Number>();
		if(ind.size() != 0){
			for(int index : ind){
				tempDista.add(distances.get(index-3));
			}
			
		}
		return tempDista;
	}
	
	public ArrayList<Integer> getMaxes(ArrayList<Float> list) {
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		int i = 0;
		for (float dista : list) {
			
			if (100 < dista  && dista <= 255 ) {
				RConsole.println("Float: " + dista);
				indexes.add(i);
			}
			i++;
		}
		return indexes;
	}
/*
	public void driveAroundObstacle() {
		beginScan = false;
		if (right && left) { // zowel links als rechts is niet vrij, wat nu??
			System.out.println("Rechts en Links is bezet");
		} else if (right && !left) { // rechts is geblokkeerd, dus links er om
										// heen
			mMiddle.setSpeed(50);
			mMiddle.rotateTo(+60);
			System.out.println("Rechts omzeilen");
		} else if (left && !right) { // links is geblokkeerd, dus rechts er om
										// heen.
			mMiddle.setSpeed(50);
			mMiddle.rotateTo(-60);
			DifferentialPilot pilot = new DifferentialPilot(3.4, 13, mLeft,
					mRight, false);
			Navigator navigator = new Navigator(pilot);

			navigator.addWaypoint(new Waypoint(50, 50));
			navigator.addWaypoint(new Waypoint(-50, -50));

			navigator.followPath();

			System.out.println("Links omzeilen");

		}
	}
*/
	
	
	@Override
	public void stateChanged(UpdatingSensor s, float oldVal, float newVal) {

		if (s.equals(MUS)) {
			if (beginScan) { // linksom of rechtsom?
				int angle = mMiddle.getTachoCount();
				if(100 < newVal  && newVal <= 255 && angle > 0){ //rechts
					obRechts[0] = (int) oldVal;
					obRechts[1] = (int) angle;
					
					
					RConsole.println("Rechts Centi: " + oldVal);
					RConsole.println("Rechts Angle: " + angle);
					mMiddle.stop(true);
				}
				else if(100 < newVal  && newVal <= 255 && angle < 0){ //rechts
					obLinks[0] = (int) oldVal;
					obLinks[1] = (int) angle;
					RConsole.println("Links Centi: " + oldVal);
					RConsole.println("Links Angle: " + angle);
					mMiddle.stop(true);
				}
				
				
				//RConsole.println("Centi: " + newVal);
				//RConsole.println("Angle: " + angle);
				//synchronized (this) {
				//	angles.add(angle);
				//	distances.add(newVal);
				//}

				// angleAndCM.add(new Float[]{angle, newVal} );
				//RConsole.println("Centi: " + newVal);
				//RConsole.println("Angle: " + angle);
			}
			else if (newVal < 26  && !beginScan) { // object detected in front
												// of us, scan right and
												// left to find new path.
				// mLeft.setSpeed(0); //stop driving
				// mRight.setSpeed(0); //stop driving
				 aantalKeer = 0;
				// doneScanning = false;
				start(); // start scanning
				
				beginScan = true;

			} 
			else {
				//doneScanning = true;
				//synchronized (this) {
					//aantalKeer = 0;
				//	angles.clear();
				//	distances.clear();
				//}
			}
		}
	}

	@Override
	public void stateNotification(UpdatingSensor s, float value, float RawValue) {
		// TODO Auto-generated method stub

	}

}

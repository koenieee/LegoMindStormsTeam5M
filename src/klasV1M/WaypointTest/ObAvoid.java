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
		// RConsole.open();
		// try {
		// Thread.sleep(000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
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

	public int[] obRechts = new int[2];
	public int[] obLinks = new int[2];
	private boolean left, right = false;
	DifferentialPilot pilot; 
	private int aantalKeer = 0;
	private int avoidingObstacle = 0;

	public ObAvoid() {
		pilot = new DifferentialPilot(3.4, 13.5, mLeft,
				mRight, false);
		t = null;
		SensorHandler.PERIOD = 100;
		mMiddle.resetTachoCount();
		MUS.addListener(this);
		mLeft.setAcceleration(180);
		mRight.setAcceleration(180);
		mLeft.setSpeed(180);
		mRight.setSpeed(180);
		mLeft.forward();
		mRight.forward();
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
			mMiddle.rotateTo(+90, false);// rechts

			// try {
			// Thread.sleep(400);
			// } catch (InterruptedException e) {
			// }
			mMiddle.rotateTo(-90, false); // links
			mMiddle.rotateTo(0, false); // links
			aantalKeer++;

		}
		mMiddle.stop();
		calculateObstacleWidth();

		// driveAroundObstacle();

	}

	public void calculateObstacleWidth() {
		System.out.println("Obstacle width is called");
		// ArrayList<Number> anglas = returnFreeAngles();
		// ArrayList<Number> distances = lowDistances();
		// int angleWhenObjectIsGone = (int)averageOfList(anglas);
		// if(obLinks[0] != 0){

		int sidea = obLinks[0];// links
		int sideb = obRechts[0];// rechts
		int theAngle = Math.abs(obRechts[1]) + Math.abs(obLinks[1]);
		double theWidth = Math.sqrt((Math.pow(sidea, 2) + Math.pow(sideb, 2))
				- (2 * sidea * sideb * Math.cos(Math.toRadians(theAngle))));
		System.out.println("Width: " + theWidth);
		System.out.println("Angle: " + theAngle);
		System.out.println("Left: " + sidea);
		System.out.println("Right : " + sideb);

		// beginScan = false;

		
		pilot.setTravelSpeed(5);
		pilot.setRotateSpeed(15);

		if (sidea < sideb) { // via links
			
			mMiddle.rotateTo(+90, false); // links
			pilot.rotate(150, false);
			avoidingObstacle = 1;
			//
			//pilot.travel(90);

			//pilot.rotate(-(theAngle + 30), false);
			//pilot.travel(theWidth);
		} else // via rechts
			{
			
			mMiddle.rotateTo(-90, false); // links
			pilot.rotate(-150, false);
			avoidingObstacle = 1;
			//pilot.rotate(-90, false);
			//pilot.travel(theWidth);

			//pilot.rotate((theAngle + 30), false);
			//pilot.travel(theWidth);
		}

		// pilot.setTravelSpeed(7);
		// pilot.travelArc(-(theWidth + 30), ((theWidth + 30)/ 4 ) * Math.PI);
		// pilot.travelArc((theWidth + 30), ((theWidth + 30)/ 2 ) * Math.PI);
		// pilot.travelArc(-(theWidth + 30), ((theWidth + 30)/ 4 ) * Math.PI);

		// }

		// System.out.println("");
		// double cmWhenAngleIsZero = averageOfList(lowDistances());
		/*
		 * double widthObject =
		 * (Math.sin(Math.toRadians(angleWhenObjectIsGone))) * 10;
		 * 
		 * System.out.println("The half of the object is: " + widthObject);
		 * 
		 * DifferentialPilot pilot = new DifferentialPilot(3.4, 13.5, mLeft,
		 * mRight, false); pilot.setTravelSpeed(7);
		 * pilot.travelArc(-(widthObject + 30), ((widthObject + 30)/ 4 ) *
		 * Math.PI); pilot.travelArc((widthObject + 30), ((widthObject + 30)/ 2
		 * ) * Math.PI); pilot.travelArc(-(widthObject + 30), ((widthObject +
		 * 30)/ 4 ) * Math.PI);
		 */
		// pilot.setTravelSpeed(9);
		// pilot.setRotateSpeed(15);
		// pilot.rotate(-angleWhenObjectIsGone, false);

		// pilot.travel(widthObject + 30);
		// pilot.rotate(angleWhenObjectIsGone, false);
		// pilot.travel(widthObject + 30);

		/*
		 * for (int flan : angles) { itera++; RConsole.println("Iterator: " +
		 * itera); if (flan == 0) { System.out.println("Lengte bij 0 graden: " +
		 * distances.get(itera));
		 * //System.out.println("Hoek bij hoogste afstand: " +
		 * angles.get(indexKeydistance)); } }
		 */
	}

	@Override
	public void stateChanged(UpdatingSensor s, float oldVal, float newVal) {
		// RConsole.println("nw " + newVal);
		// RConsole.println("ov " + oldVal);
		if (s.equals(MUS)) {
			if(avoidingObstacle == 1){
				if((100 < newVal && newVal <= 255)){
					pilot.travelArc(oldVal, 70, false);
				}
				else{
					pilot.travel(15, false);
				}
			}
			if (beginScan) { // linksom of rechtsom?
				int angle = mMiddle.getTachoCount();

				if ((100 < newVal && newVal <= 255) && angle > 0 && !right) { // rechts

					obRechts[0] = (int) oldVal;
					obRechts[1] = (int) angle;
					right = true;

					RConsole.println("Rechts Centi: " + oldVal);
					RConsole.println("Rechts Angle: " + angle);
					mMiddle.stop(true);
				} else if ((100 < newVal && newVal <= 255) && angle < 0
						&& !left) { // rechts
					obLinks[0] = (int) oldVal;
					obLinks[1] = (int) angle;
					left = true;
					RConsole.println("Links Centi: " + oldVal);
					RConsole.println("Links Angle: " + angle);
					mMiddle.stop(true);
				}
				else{
					//todo
				}

				// RConsole.println("Centi: " + newVal);
				// RConsole.println("Angle: " + angle);
				// synchronized (this) {
				// angles.add(angle);
				// distances.add(newVal);
				// }

				// angleAndCM.add(new Float[]{angle, newVal} );
				// RConsole.println("Centi: " + newVal);
				// RConsole.println("Angle: " + angle);
			} else if (newVal < 26 && !beginScan) { // object detected in front
													// of us, scan right and
													// left to find new path.
				mLeft.stop(true); // stop driving
				mRight.stop(); // stop driving
				aantalKeer = 0;
				// doneScanning = false;
				start(); // start scanning

				beginScan = true;

			} else {
				// doneScanning = true;
				// synchronized (this) {
				// aantalKeer = 0;
				// angles.clear();
				// distances.clear();
				// }
			}
		}
	}

	@Override
	public void stateNotification(UpdatingSensor s, float value, float RawValue) {
		// TODO Auto-generated method stub

	}

}

package klasV1M.TI.controllers;

import java.util.Iterator;

import klasV1M.TI.Globals;
import klasV1M.TI.sensoren.SensorHandler;
import klasV1M.TI.sensoren.SensorListener;
import klasV1M.TI.sensoren.UpdatingSensor;
import lejos.nxt.comm.RConsole;

public class ObstacleController implements Runnable, SensorListener, SensorPairListener {

	private Thread t;
	private boolean objectDetected;
	private boolean lineLost;
	private float heading;
	
	private long prevTime;
	private long curTime;

	public ObstacleController() {
		t = null;
		//SensorHandler.PERIOD = period;
		// Light sensor are being handled by sensorpair
		// Globals.MLS.addListener(this);
		// Globals.MCS.addListener(this);
		Globals.diffPilot.setRotateSpeed(1);//Globals.LowSpeed);
		
		Globals.MUS.addListener(this);
		

		//Globals.mLeft.setAcceleration(180);
		//Globals.mRight.setAcceleration(180);
		//Globals.mLeft.setSpeed(180);
		//Globals.mRight.setSpeed(180);
		//Globals.mLeft.forward();
		//Globals.mRight.forward();
		prevTime = curTime = System.currentTimeMillis();
	}

	public void calculateRoute() {
		if (Globals.angleAndCM.get(Globals.angleAndCM.size() - 1)[0] <= 40) {
			Iterator<Float[]> theIterator = Globals.angleAndCM.iterator();

			while (theIterator.hasNext()) {
				Float[] input = theIterator.next();
				Float distance = input[0];
				Float angle = input[1];
				System.out.println("distance: " + distance + " angle: " + angle);
			}
		}
	}

	public void pollStatus() {

	}

	@Override
	public void run() {
		/*
		 * Globals.mLeft.setSpeed(360); Globals.mRight.setSpeed(360);
		 * Globals.mLeft.forward(); Globals.mRight.forward();
		 */
		Globals.mMiddle.setSpeed(50);
		while (true) {
			scanEnvironment();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {

			}
		}
	}

	public void start() {
		if (t == null) {
			t = new Thread(this);
			t.start();
		}
	}

	public void stop() {
		if (t != null) {
			t.interrupt();
			t = null;
			t.interrupt();
		}
	}

	// TODO:
	// Remove as it is deemed unnecessary
	public void scanEnvironment() {
		// Globals.mMiddle.setAcceleration(20);
		// Globals.mMiddle

		Globals.mMiddle.rotateTo(-45);
		Globals.mMiddle.rotateTo(+45);

	}

	@Override
	public void stateChanged(UpdatingSensor s, float oldVal, float newVal) {
		// Ultrasonic Sensor
		if (s.equals(Globals.MUS)) {
			if (newVal < 255) {
				Globals.angleAndCM.add(new Float[] { newVal,
						(float) Globals.mMiddle.getTachoCount() });

				// Object detected
				System.out.println("Obstacle cm: " + newVal);
				System.out.println("Angle: " + Globals.mMiddle.getTachoCount());

				// angleAndCM
				objectDetected = true;
				// TODO: Scan area now or do so while driving?

			} else {
				objectDetected = false;
			}
		}
		
		/*int diff = 0;
		if (newVal < 20) {
			diff = (int) ((20 - newVal) * (36 + 18));
		}*/
		
		//System.out.println("Diff: " + diff);
		
		// LightSensor
		if (s.equals(Globals.MLS)) {
			// Test
			//Globals.mLeft.setSpeed(newVal < 50 ? 360 : 180);//180 + diff);//3.6f * (100 - newVal) + 180);
		}
		// ColorSensor
		if (s.equals(Globals.MCS)) {
			// Test
			//Globals.mRight.setSpeed(newVal < 50 ? 360 : 180);//180 + diff);//3.6f * (100 - newVal) + 180);
		}
	}

	@Override
	public void stateNotification(UpdatingSensor s, float value, float rawValue) {
		// TODO Auto-generated method stub
		// Ignore
	}

	@Override
	public void lineFound() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void lineLost() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stateChanged(int oldState, int newState) {
		// TODO Auto-generated method stub
		/*if (SensorPair.lineFound()) {
			if (oldState == SensorPair.LINE_MIDDLE) {
				if (newState == SensorPair.LINE_LEFT) {
					adjustToLeft();
				} else if (newState == SensorPair.LINE_RIGHT) {
					adjustToRight();
				} else {
					goSlow();
				}
			} else if (oldState == SensorPair.LINE_LEFT) {
				if (newState == SensorPair.LINE_MIDDLE) {
					goAhead();
				} else if (newState == SensorPair.LINE_RIGHT) {
					
				} else if (newState == SensorPair.LINE_UNKNOWN) {
					
				}
			}
		}*/
		//Globals.diffPilot.steer(turnRate, angle, immediateReturn);
		prevTime = curTime;
		curTime = System.currentTimeMillis();
		long mod = (curTime - prevTime);// / 2;
		RConsole.println("TimeDiff: " + mod);
		if (mod <= Globals.TimeThreshold) {
			mod = Globals.LowSpeed;
		} else {
			mod = Globals.NormalSpeed;
		}
		
		if (newState == SensorPair.LINE_LEFT) {
			//adjustToLeft(mod);
			//Globals.diffPilot.setTravelSpeed(Globals.LowSpeed);
			Globals.diffPilot.steer(25);//arcForward(30);//steer(25, 5, true);//steer to the right, incrementing the angle slightly
		} else if (newState == SensorPair.LINE_RIGHT) {
			//adjustToRight(mod);
			//Globals.diffPilot.setTravelSpeed(Globals.LowSpeed);
			Globals.diffPilot.steer(-25);//;arcForward(30);//steer(-25, -5, true);//steer to the right, incrementing the angle slightly
		} else if (newState == SensorPair.LINE_MIDDLE) {
			//goAhead();
			//Globals.diffPilot.setTravelSpeed(Globals.NormalSpeed);
			Globals.diffPilot.steer(0);//forward();
		} else {
			//Globals.diffPilot.setTravelSpeed(Globals.LowSpeed);
			//Globals.diffPilot.forward();
			//goSlow();
		}
	}
	
	private void adjustToRight(long l) {
		float max = Math.min(Globals.mLeft.getMaxSpeed(), Globals.MaxSpeed);
		if (max < l) {
			l = (long) max - Globals.NormalSpeed;
		}
		Globals.mLeft.setSpeed(Globals.NormalSpeed + l);
		Globals.mRight.setSpeed(Globals.NormalSpeed);
	}
	
	private void adjustToLeft(long l) {
		float max = Math.min(Globals.mRight.getMaxSpeed(), Globals.MaxSpeed);
		if (max < l) {
			l = (long) max - Globals.NormalSpeed;
		}
		Globals.mLeft.setSpeed(Globals.NormalSpeed);
		Globals.mRight.setSpeed(Globals.NormalSpeed + l);
	}
	
	private void goAhead() {
		Globals.mLeft.setSpeed(Globals.NormalSpeed);
		Globals.mRight.setSpeed(Globals.NormalSpeed);
	}
	
	private void goSlow() {
		Globals.mLeft.setSpeed(Globals.LowSpeed);
		Globals.mRight.setSpeed(Globals.LowSpeed);
	}
}

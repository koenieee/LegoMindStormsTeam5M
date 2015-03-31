package klasV1M.TI.controllers;

import java.util.HashMap;

import klasV1M.TI.Globals;
import klasV1M.TI.sensoren.SensorHandler;
import klasV1M.TI.sensoren.SensorListener;
import klasV1M.TI.sensoren.UpdatingSensor;

public class ObstacleController implements Runnable, SensorListener {

	private Thread t;
	private boolean objectDetected;
	private boolean lineLost;
	private float heading;

	
	public ObstacleController(int period) {
		t  = null;
		SensorHandler.PERIOD = period;
	//	Globals.MLS.addListener(this);
	//	Globals.MCS.addListener(this);
		Globals.MUS.addListener(this);
		
	}

	
	public void calculateRoute() {
		
	}
	
	public void pollStatus() {
		
	}
	
	@Override
	public void run() {
		/*Globals.mLeft.setSpeed(360);
		Globals.mRight.setSpeed(360);
		Globals.mLeft.forward();
		Globals.mRight.forward();*/
		Globals.mMiddle.setSpeed(50);
		while(true){
			System.out.println("kip");
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
	
	public void scanEnvironment(){
		//Globals.mMiddle.setAcceleration(20);
		//Globals.mMiddle

		Globals.mMiddle.rotateTo(-45);
		Globals.mMiddle.rotateTo(+45);
		
	}

	@Override
	public void stateChanged(UpdatingSensor s, float oldVal, float newVal) {
		// Ultrasonic Sensor
		if (s.equals(Globals.MUS)) {
			if (newVal < 255) {
				Globals.angleAndCM.put(newVal, Globals.mMiddle.getTachoCount());
				// Object detected
				System.out.println("Obstacle cm: " + newVal);
				System.out.println("Angle: " + Globals.mMiddle.getTachoCount());
				
				
				
				//angleAndCM
				objectDetected = true;
				// TODO: Scan area now or do so while driving?
				
			} else {
				objectDetected = false;
			}
		}
		// LightSensor
		if (s.equals(Globals.MLS)) {
			// Test
			Globals.mLeft.setSpeed(3.6f * (100 - newVal) + 180);
		}
		// ColorSensor
		if (s.equals(Globals.MCS)) {
			// Test
			Globals.mRight.setSpeed(3.6f * (100 - newVal) + 180);
		}
	}
}

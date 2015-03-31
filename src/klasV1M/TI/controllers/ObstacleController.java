package klasV1M.TI.controllers;

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
		SensorHandler.PERIOD = period;
		Globals.MLS.addListener(this);
		Globals.MCS.addListener(this);
		Globals.MUS.addListener(this);
		
		Globals.mLeft.setAcceleration(180);
		Globals.mRight.setAcceleration(180);
		Globals.mLeft.setSpeed(180);
		Globals.mRight.setSpeed(180);
		Globals.mLeft.forward();
		Globals.mRight.forward();
	}
	
	public void calculateRoute() {
		
	}
	
	public void pollStatus() {
		
	}
	
	@Override
	public void run() {
		Globals.mLeft.forward();
		Globals.mRight.forward();
		
		while(!Thread.interrupted()) {
			//Thread.yield();
			try {
				Thread.sleep(Globals.StandardDelay);
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

	@Override
	public void stateChanged(UpdatingSensor s, float oldVal, float newVal) {
		// Ultrasonic Sensor
		if (s.equals(Globals.MUS)) {
			if (newVal < 255) {
				// Object detected
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
			Globals.mLeft.setSpeed(newVal < 50 ? 360 : 180);//180 + diff);//3.6f * (100 - newVal) + 180);
		}
		// ColorSensor
		if (s.equals(Globals.MCS)) {
			// Test
			Globals.mRight.setSpeed(newVal < 50 ? 360 : 180);//180 + diff);//3.6f * (100 - newVal) + 180);
		}
	}
}

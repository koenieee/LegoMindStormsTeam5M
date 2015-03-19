package klasV1M.TI.controllers;

import klasV1M.TI.sensoren.MyColorSensor;
import klasV1M.TI.sensoren.MyLightSensor;
import klasV1M.TI.sensoren.MyUltraSonicSensor;
import klasV1M.TI.sensoren.SensorListener;
import klasV1M.TI.sensoren.UpdatingSensor;

public class SensorPair implements Runnable, SensorListener {

	private Thread t;
	private MyLightSensor lightSensor;
	private MyColorSensor colorSensor;
	private MyUltraSonicSensor ultraSonicSensor;
	
	public SensorPair(MyLightSensor ls, MyColorSensor cs, MyUltraSonicSensor uss) {
		lightSensor = ls;
		colorSensor = cs;
		ultraSonicSensor = uss;
		lightSensor.addListener(this);
		colorSensor.addListener(this);
		ultraSonicSensor.addListener(this);
	}

	@Override
	public void run() {

	}

	public void start() {
		if (t == null) {
			t = new Thread(this);
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
		System.out.println("old: " + oldVal + " | new: " + newVal);
		
		if (s.equals(lightSensor)) {
			
		}
		
		if (s.equals(colorSensor)) {
			
		}
		
		if (s.equals(ultraSonicSensor)) {
			
		}
	}
}
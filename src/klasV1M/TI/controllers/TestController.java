package klasV1M.TI.controllers;

import klasV1M.TI.Globals;
import klasV1M.TI.sensoren.SensorHandler;
import klasV1M.TI.sensoren.SensorListener;
import klasV1M.TI.sensoren.UpdatingSensor;
import lejos.nxt.LCD;

/**
 * Example TestController to test the working and implementation of Sensors and
 * Sensor Listener Pattern
 * 
 * @author koen
 *
 */
public class TestController implements SensorListener {
	SensorHandler scc;

	/**
	 * TestController can be called with an integer to know how many times the
	 * SensorHandler must be called
	 * 
	 * @param period
	 *            how much second the SensorHandler can call the sensor for
	 *            updates
	 */
	public TestController(int period) {
		SensorHandler.PERIOD = period;
		Globals.MLS.addListener(this);
		Globals.MCS.addListener(this);
	}

	/**
	 * StateChanged is used for call this method from the Sensor if there was
	 * sensor reading changed.
	 * 
	 * @param s
	 *            UpdatingSensor the Sensor that has called stateChanged
	 * @param oldVal
	 *            The old value from the Sensor
	 * @param newVal
	 *            The new value that the Sensor has detected
	 */
	@Override
	public void stateChanged(UpdatingSensor s, float oldVal, float newVal) {

		if (s.equals(Globals.MLS)) {
			// LCD.clear();
			LCD.drawString(("Light oldval: " + oldVal + "\nnewVal: " + newVal),
					0, 0);
		}
		if (s.equals(Globals.MCS)) {
			LCD.drawString(("Color oldval: " + oldVal + "\nnewVal: " + newVal),
					0, 20);
		}

	}

}
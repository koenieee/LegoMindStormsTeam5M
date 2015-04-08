package klasV1M.TI.sensoren;

import java.util.ArrayList;
import java.util.List;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;

/**
 * Overrides class LightSensor to implement SensorListener Pattern
 * 
 * @author Remco, Koen, & Medzo
 * @version 1.0.0.0
 */
public class MyLightSensor extends LightSensor implements UpdatingSensor {
	private List<SensorListener> listeners;
	private float oldVal, newVal;
	private static MyLightSensor sensor = null;

	private MyLightSensor() {
		super(SensorPort.S2);
		listeners = new ArrayList<SensorListener>();
	}
	
	public static MyLightSensor getInstance() {
		if (sensor == null) {
			sensor = new MyLightSensor();
		}
		return sensor;
	}
	
	@Override
	public int getLightValue() {
		int val = super.getLightValue();
		if (val < 0) {
			return 0;
		} else if (val > 100) {
			return 100;
		} else {
			return val;
		}
	}

	/**
	 * Updates calls the method that implements the SensorListener with the new
	 * and old values
	 * 
	 */
	public void updateState() {
		oldVal = newVal;
		newVal = getLightValue();

		if (oldVal != newVal) {
			for (SensorListener s : listeners) {
				s.stateChanged(this, oldVal, newVal);
			}
		}
	}

	/**
	 * This method adds the SensorListener to this object, and this object is
	 * added to the SensorHandler. It also starts the Thread of SensorHandler to
	 * keep track of new sensor values.
	 * 
	 * @param senin
	 *            SensorListener
	 */
	public void addListener(SensorListener senin) {
		// Does not allow multiple of the same SensorListener
		// HashMap and HashSet are deprecated and as of yet unoptimized, so that can't be used at the moment
		if (hasListener(senin)) {
			return;
		}
		if (listeners.size() == 0) {
			SensorHandler.getInstance().addSensor(this);
		}
		listeners.add(senin);
	}
	
	public void removeListener(SensorListener senin) {
		if (hasListener(senin)) {
			listeners.remove(senin);
			if (listeners.size() == 0) {
				SensorHandler.getInstance().removeSensor(this);
			}
		}
	}

	public boolean hasListener(SensorListener sensor) {
		return listeners.contains(sensor);
	}
}
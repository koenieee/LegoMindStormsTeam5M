package klasV1M.TI.sensoren;

import java.util.ArrayList;
import java.util.List;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;

/**
 * Overrides the {@link LightSensor} to implement the listener-pattern
 * 
 * @author Remco, Koen, & Medzo
 * @version 1.0.0.0
 */
public class MyLightSensor extends LightSensor implements UpdatingSensor {
	/**
	 * Internal list of listeners
	 */
	private List<SensorListener> listeners;
	private float oldVal, newVal;
	private static MyLightSensor sensor = null;

	private MyLightSensor() {
		super(SensorPort.S2); // Super call to instantiate the UltraSonicSensor
		listeners = new ArrayList<SensorListener>();
	}

	/**
	 * The {@link MyLightSensor} mounted on the front.<br>
	 * Creates a single instance of {@link MyLightSensor} if it does not yet exist.
	 */
	public static MyLightSensor getInstance() {
		// lazy initialization
		if (sensor == null) {
			sensor = new MyLightSensor();
		}
		return sensor;
	}

	public int getLightValue() {
		int val = super.getLightValue();
		// correct out of bounds values
		if (val < 0) {
			return 0;
		} else if (val > 100) {
			return 100;
		} else {
			return val;
		}
	}

	public void updateState() {
		oldVal = newVal;
		newVal = getLightValue();

		if (oldVal != newVal) {
			// notify listeners about a change
			for (SensorListener s : listeners) {
				s.stateChanged(this, oldVal, newVal);
			}
		}
	}

	public void addListener(SensorListener listener) {
		// Does not allow multiple of the same SensorListener
		// HashMap and HashSet are deprecated and as of yet unoptimized, so that
		// can't be used at the moment
		if (hasListener(listener)) {
			return;
		}
		if (listeners.size() == 0) {
			SensorHandler.getInstance().addSensor(this);
		}
		listeners.add(listener);
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
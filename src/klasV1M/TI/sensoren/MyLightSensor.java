package klasV1M.TI.sensoren;

import java.util.ArrayList;
import java.util.List;

import lejos.nxt.ADSensorPort;
import lejos.nxt.LightSensor;

/**
 * Can provide a connection to the LightSensor with a few more functions
 * Overrides the {@link LightSensor} to implement the listener-pattern
 * 
 * @author Remco, Koen, & Medzo
 * @version 1.0.0.0
 */
public class MyLightSensor extends LightSensor implements UpdatingSensor {

	/**
	 * Internal list of sensor listeners
	 */
	private List<SensorListener> listeners;

	/**
	 * The old and new light value from the LightSensor
	 */
	private float oldVal, newVal;

	/**
	 * Creates and instantiates a new instance of {@link MyLightSensor}.
	 * 
	 * @param port
	 *            The {@link ADSensorPort} this object will be attached to.
	 */
	public MyLightSensor(ADSensorPort port) {
		super(port);
		listeners = new ArrayList<SensorListener>();
	}

	/**
	 * This function returns the current light value of the LightSensor
	 * 
	 * @return current light value of LightSensor
	 */
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
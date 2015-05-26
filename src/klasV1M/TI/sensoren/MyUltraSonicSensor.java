package klasV1M.TI.sensoren;

import java.util.ArrayList;
import java.util.List;

import lejos.nxt.I2CPort;
import lejos.nxt.UltrasonicSensor;

/**
 * Can provide a connection to the UltrasonicSensor with a few more functions
 * Overrides the {@link UltrasonicSensor} to implement the listener-pattern
 * 
 * @author Remco, Koen, & Medzo
 * @version 1.0.0.0
 */
public class MyUltraSonicSensor extends UltrasonicSensor implements
		UpdatingSensor {

	/**
	 * The old and new distance value from the UltraSonicSensor
	 */
	private float oldVal, newVal;
	/**
	 * Internal list of sensor listeners
	 */
	private List<SensorListener> listeners;

	/**
	 * Creates and instantiates a new instance of {@link MyUltraSonicSensor}.
	 * 
	 * @param port
	 *            The {@link I2CPort} this object will be attached to.
	 */
	public MyUltraSonicSensor(I2CPort port) {
		super(port);
		listeners = new ArrayList<SensorListener>();
	}

	public void updateState() {
		oldVal = newVal;
		newVal = super.getRange();

		if (oldVal != newVal) {
			// notify listeners about a change
			for (SensorListener s : listeners) {
				s.stateChanged(this, oldVal, newVal);
			}
		}
	}

	public void addListener(SensorListener senin) {
		// Does not allow multiple of the same SensorListener
		// HashMap and HashSet are deprecated and as of yet unoptimized, so that
		// can't be used at the moment
		if (hasListener(senin)) {
			return;
		}

		if (listeners.size() == 0) {
			SensorHandler.getInstance().addSensor(this);
		}
		listeners.add(senin);
	}

	public void removeListener(SensorListener sensor) {
		if (hasListener(sensor)) {
			listeners.remove(sensor);
			if (listeners.size() == 0) {
				SensorHandler.getInstance().removeSensor(this);
			}
		}
	}

	public boolean hasListener(SensorListener sensor) {
		return listeners.contains(sensor);
	}
}
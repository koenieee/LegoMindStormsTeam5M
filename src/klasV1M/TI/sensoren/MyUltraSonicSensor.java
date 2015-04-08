package klasV1M.TI.sensoren;

import java.util.ArrayList;
import java.util.List;

import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

/**
 * Overrides the {@link UltrasonicSensor} to implement the listener-pattern
 * 
 * @author Remco, Koen, & Medzo
 * @version 1.0.0.0
 */
public class MyUltraSonicSensor extends UltrasonicSensor implements
		UpdatingSensor {
	private float oldVal, newVal;
	private List<SensorListener> listeners;
	private static MyUltraSonicSensor sensor = null;

	private MyUltraSonicSensor() {
		super(SensorPort.S4);
		listeners = new ArrayList<SensorListener>();
	}

	/**
	 * The {@link MyUltraSonicSensor} mounted on the front
	 */
	public static MyUltraSonicSensor getInstance() {
		if (sensor == null) {
			sensor = new MyUltraSonicSensor();
		}
		return sensor;
	}

	/**
	 * Updates calls the method that implements the SensorListener with the new
	 * and old values
	 * 
	 */
	@Override
	public void updateState() {
		oldVal = newVal;
		newVal = super.getRange();
		if (oldVal != newVal) {
			for (SensorListener s : listeners) {
				s.stateChanged(this, oldVal, newVal);
			}
		}
	}

	/**
	 * This method adds the {@link SensorListener} to this object, and this
	 * object is added to the {@link SensorHandler}. It also starts the
	 * {@link Thread} of {@link SensorHandler} to keep track of new sensor
	 * values.
	 * 
	 * @param senin
	 *            SensorListener
	 */
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
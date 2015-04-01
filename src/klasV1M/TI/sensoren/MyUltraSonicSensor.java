package klasV1M.TI.sensoren;

import java.util.ArrayList;
import java.util.List;

import lejos.nxt.I2CPort;
import lejos.nxt.UltrasonicSensor;

/**
 * Overrides class UltraSonicSensor to implement SensorListener Pattern
 * 
 * @author koen
 *
 */
public class MyUltraSonicSensor extends UltrasonicSensor implements UpdatingSensor {
	private float oldVal, newVal;
	private List<SensorListener> upd;

	public MyUltraSonicSensor(I2CPort port) {
		super(port);
		upd = new ArrayList<SensorListener>();
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
			for (SensorListener s : upd) {
				s.stateChanged(this, oldVal, newVal);
			}
		}
		for (SensorListener s : upd) {
			s.stateNotification(this, newVal);
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
		if (upd.size() == 0) {
			SensorHandler.getInstance().addSensor(this);
		}
		upd.add(senin);
	}
	
	public void removeListener(SensorListener sensor) {
		upd.remove(sensor);
		if (upd.size() == 0) {
			SensorHandler.getInstance().removeSensor(this);
		}
	}
}

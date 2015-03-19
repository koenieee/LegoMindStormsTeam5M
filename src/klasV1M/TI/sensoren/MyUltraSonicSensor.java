package klasV1M.TI.sensoren;

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
	private SensorListener upd;

	public MyUltraSonicSensor(I2CPort port) {
		super(port);
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
		upd.stateChanged(this, oldVal, newVal);
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
		upd = senin;
		SensorHandler.getInstance().addSensor(this);
	}
}

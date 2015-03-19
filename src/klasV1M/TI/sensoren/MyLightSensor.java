package klasV1M.TI.sensoren;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;

/**
 * Overrides class LightSensor to implement SensorListener Pattern
 * @author koen
 *
 */
public class MyLightSensor extends LightSensor implements UpdatingSensor {
	private SensorListener sis;
	private float oldVal;
	private float newVal;

	public MyLightSensor(SensorPort port) {
		super(port);
	}
	
	/** Updates calls the method that implements the SensorListener with the new and old values
	 * 
	 */
	public void updateState() {
		oldVal = newVal;
		newVal = super.getLightValue();
		if (oldVal != newVal) {
			sis.stateChanged(this, oldVal, newVal);
		}
	}
	
	/**
	 * This method adds the SensorListener to this object, and this object is added to the SensorHandler.
	 * It also starts the Thread of SensorHandler to keep track of new sensor values.
	 * @param senin SensorListener
	 */
	public void addListener(SensorListener senin) {
		sis = senin;
		SensorHandler.getInstance().addSensor(this);
	}

}
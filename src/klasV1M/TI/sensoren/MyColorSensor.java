package klasV1M.TI.sensoren;

import lejos.nxt.ColorSensor;
import lejos.nxt.SensorPort;

/**
 * Overrides ColorSensor to implement Sensor Listener Pattern
 * 
 * @author koen
 * 
 */
public class MyColorSensor extends ColorSensor implements UpdatingSensor {
	private SensorListener sis;
	private float oldVal;
	private float newVal;

	public MyColorSensor(SensorPort port) {
		super(port);
	}

	private int _zero = 1023;
	private int _hundred = 0;

	/**
	 * method setLow is the lowest color (Black) to calibrate
	 * 
	 * @param int high The RawLightvalue number on a black spot.
	 */
	public void setLow(int low) {
		_zero = low;
	}

	/**
	 * method setHigh is the highest color (White) to calibrate
	 * 
	 * @param int high The RawLightvalue number on a white spot.
	 */
	public void setHigh(int high) {
		_hundred = high;
	}

	/**
	 * getLightValue overrides the lightvalue of ColorSensor because that one is
	 * not implement in LejOs
	 * 
	 * @return a value between 0 and 100 to see what color is under the Color
	 *         Sensor
	 */
	@Override
	public int getLightValue() {
		if (_hundred == _zero)
			return 0;

		int value = super.getRawLightValue();
		value = 100 * (value - _zero) / (_hundred - _zero);
		if (value < 0) {
			value = 0;

		} else if (value > 100) {
			value = 100;
		}
		return value;
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
			sis.stateChanged(this, oldVal, newVal);
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
		sis = senin;
		SensorHandler.getInstance().addSensor(this);
	}
}

package klasV1M.TI.sensoren;

import java.util.ArrayList;
import java.util.List;

import lejos.nxt.ColorSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.comm.RConsole;

/**
 * Overrides ColorSensor to implement Sensor Listener Pattern
 * 
 * @author koen
 * 
 */
public class MyColorSensor extends ColorSensor implements UpdatingSensor {
	private List<SensorListener> sis;
	private float oldVal;
	private float newVal;

	public MyColorSensor(SensorPort port) {
		super(port);
		sis = new ArrayList<SensorListener>();
	}

	public MyColorSensor(SensorPort port, int color) {
		super(port, color);
		sis = new ArrayList<SensorListener>();
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
	 * <br><b>NOTE: might return 0 when an error occures</b>
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
		//RConsole.print("^");
		int temp = getLightValue();
		if (temp == -1) {
			// -1 mean an error occured
			//RConsole.print("*");
			return;
		}
		oldVal = newVal;
		newVal = temp;
		if (oldVal != newVal) {
			for (SensorListener s : sis) {
				//RConsole.println("H|Z|T: " + _hundred + "|" + _zero + " | " + temp);
				s.stateChanged(this, oldVal, newVal);
			}
		}
		for (SensorListener s : sis) {
			s.stateNotification(this, newVal, getRawLightValue());//newVal);
		}
	}
	
	/**
	 * Return the Raw light reading.
	 * <br><b>NOTE: might return 0 when an error occures</b>
	 * @return Raw light reading 0-1023
	 */
	@Override
	public int getRawLightValue() {
		return super.getRawLightValue();
	};

	/**
	 * This method adds the {@link SensorListener} to this object, and this object is
	 * added to the {@link SensorHandler}. It also starts the {@link Thread} of {@link SensorHandler} to
	 * keep track of new sensor values.
	 * 
	 * @param senin
	 *            {@link SensorListener}
	 */
	public void addListener(SensorListener senin) {
		// Does not allow multiple of the same SensorListener
		// HashMap and HashSet are deprecated and as of yet unoptimized, so that can't be used at the moment
		
		if (hasListener(senin)) {
			return;
		}

		if (sis.size() == 0) {
			SensorHandler.getInstance().addSensor(this);
		}
		sis.add(senin);
	}
	
	public void removeListener(SensorListener senin) {
		sis.remove(senin);
		if (sis.size() == 0) {
			SensorHandler.getInstance().removeSensor(this);
		}
	}
	
	public boolean hasListener(SensorListener sensor) {
		return sis.contains(sensor);
	}
}

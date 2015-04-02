package klasV1M.TI.sensoren;

import java.util.ArrayList;
import java.util.List;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.comm.RConsole;

/**
 * Overrides class LightSensor to implement SensorListener Pattern
 * 
 * @author koen
 *
 */
public class MyLightSensor extends LightSensor implements UpdatingSensor {
	private List<SensorListener> sis;
	private float oldVal;
	private float newVal;

	public MyLightSensor(SensorPort port) {
		super(port);
		sis = new ArrayList<SensorListener>();
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
		// -1 returns when error occured?
		//RConsole.print("$");
		oldVal = newVal;
		newVal = getLightValue();

		if (oldVal != newVal) {
			for (SensorListener s : sis) {
				s.stateChanged(this, oldVal, newVal);
			}
		}
		for (SensorListener s : sis) {
			s.stateNotification(this, newVal, getNormalizedLightValue());//newVal);
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
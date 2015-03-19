package klasV1M.TI.sensoren;

import java.util.ArrayList;
import java.util.List;

public class SensorHandler extends Thread {
	private List<UpdatingSensor> theSensors;
	public static int PERIOD = 5000; // /< Period time that the SensorHandler
										// polls the Sensors.
	private static SensorHandler theHandler = null;

	private SensorHandler() {
		theSensors = new ArrayList<UpdatingSensor>();
		start();
	}

	/**
	 * This creates an single instance of SensorHandler to there is only one
	 * SensorHandler
	 */
	public static SensorHandler getInstance() {
		if (theHandler == null)
			theHandler = new SensorHandler();
		return theHandler;
	}

	/**
	 * Main function of SensorHandler to ask every <PERIOD> seconds if the
	 * sensor has been changes
	 */
	public void run() {
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

		for (;;) {
			try {
				synchronized (this) {
					for (UpdatingSensor ses : theSensors)
						ses.updateState();
				}
				Thread.sleep(PERIOD);
			} catch (InterruptedException _) {
			}
		}
	}

	/**
	 * This adds the Sensor the the sensor list of SensorHandler to see if there
	 * are any updates.
	 * 
	 * @param inS
	 *            UpdatingSensor
	 */
	public synchronized void addSensor(UpdatingSensor inS) {
		theSensors.add(inS);
	}

}

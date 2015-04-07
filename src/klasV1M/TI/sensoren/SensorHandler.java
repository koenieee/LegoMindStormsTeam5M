package klasV1M.TI.sensoren;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Remco, Koen, & Medzo
 * @version 1.0.0.0
 */
public class SensorHandler extends Thread {
	private List<UpdatingSensor> theSensors;
	/**
	 * The period time that the {@link SensorHandler} polls the Sensors.
	 */
	public static int PERIOD = 100;
	private static SensorHandler theHandler = null;

	private SensorHandler() {
		theSensors = new ArrayList<UpdatingSensor>();
		start();
	}

	/**
	 * Creates and starts a single instance of {@link SensorHandler} if there is not already one
	 */
	public static SensorHandler getInstance() {
		if (theHandler == null)
			theHandler = new SensorHandler();
		return theHandler;
	}

	/**
	 * Main function of {@link SensorHandler} to ask every <PERIOD> seconds if the
	 * sensor has been changes
	 * @exception If the thread has been interrupted there will be an exception thrown.
	 */
	public void run() {
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

		while(!Thread.interrupted()) {
			try {
				synchronized (this) {
					for (UpdatingSensor ses : theSensors)
						ses.updateState();
				}
				Thread.sleep(PERIOD);
			} catch (InterruptedException e) {
			}
		}
	}

	/**
	 * Adds an {@link UpdatingSensor} to the list of {@link SensorHandler} to see if there
	 * are any updates.
	 * 
	 * @param sensor The {@link UpdatingSensor} to add
	 */
	public synchronized void addSensor(UpdatingSensor sensor) {
		theSensors.add(sensor);
	}

	/**
	 * Removes an {@link UpdatingSensor} from the list of {@link SensorHandler} if it exists therein
	 * @param sensor The {@link UpdatingSensor} to add
	 */
	public synchronized void removeSensor(UpdatingSensor sensor) {
		theSensors.remove(sensor);
	}

}

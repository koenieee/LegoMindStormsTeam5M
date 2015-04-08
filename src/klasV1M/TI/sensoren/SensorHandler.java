package klasV1M.TI.sensoren;

import java.util.ArrayList;
import java.util.List;

/**
 * Keeps an internal list of {@link UpdatingSensor}s and updates these every {@value #PERIOD} milliseconds.
 * An {@link UpdatingSensor} can register and unregister itself from the {@link SensorHandler}.
 * When instantiated it runs on its own {@link Thread}.
 * 
 * @author Remco, Koen, & Medzo
 * @version 1.0.0.0
 */
public class SensorHandler extends Thread {
	private List<UpdatingSensor> theSensors;
	/**
	 * The interval in milliseconds that the {@link SensorHandler} polls the {@link UpdatingSensor}s.
	 */
	public static final int PERIOD = 100;
	private static SensorHandler theHandler = null; // handle to itself

	private SensorHandler() {
		theSensors = new ArrayList<UpdatingSensor>();
		start();
	}

	/**
	 * Creates and starts a single instance of {@link SensorHandler} if there is not already one
	 */
	public static SensorHandler getInstance() {
		// lazy initialization
		if (theHandler == null)
			theHandler = new SensorHandler();
		return theHandler;
	}

	/**
	 * Calls <code>updateState()</code> of every registered {@link UpdatingSensor} every {@value #PERIOD} milliseconds.
	 * @exception InterruptedException If the thread has been interrupted an exception will be thrown.
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
	 * Adds an {@link UpdatingSensor} to the internal list of {@link SensorHandler} if it does not already exist therein.
	 * 
	 * @param sensor The {@link UpdatingSensor} to add
	 */
	public synchronized void addSensor(UpdatingSensor sensor) {
		if (!hasSensor(sensor)) {
			theSensors.add(sensor);
		}
	}

	/**
	 * Removes an {@link UpdatingSensor} from the list of {@link SensorHandler} if it exists therein.
	 * @param sensor The {@link UpdatingSensor} to remove
	 */
	public synchronized void removeSensor(UpdatingSensor sensor) {
		if (hasSensor(sensor)) {
			theSensors.remove(sensor);
		}
	}

	/**
	 * Checks if an {@link UpdatingSensor} already is registered.
	 * 
	 * @param sensor The {@link UpdatingSensor} to check
	 * @return <code>true</code> if the {@link UpdatingSensor} is already registered, <code>false</code> otherwise.
	 */
	public synchronized boolean hasSensor(UpdatingSensor sensor) {
		return theSensors.contains(sensor);
	}
}
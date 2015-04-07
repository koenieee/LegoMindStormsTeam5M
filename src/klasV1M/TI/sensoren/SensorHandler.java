package klasV1M.TI.sensoren;

import java.util.ArrayList;
import java.util.List;

import klasV1M.TI.Globals;
import lejos.nxt.comm.RConsole;

public class SensorHandler extends Thread {
	private List<UpdatingSensor> theSensors;
	/**
	 * The period time that the {@link SensorHandler} polls the Sensors.
	 * Defaults to {@link Globals}'s StandardDelay.
	 */
	public static int PERIOD = Globals.StandardDelay; ///< Period time that the SensorHandler polls the Sensors.
	private static SensorHandler theHandler = null;

	private SensorHandler() {
		theSensors = new ArrayList<UpdatingSensor>();
		start();
	}

	/**
	 * This creates an single instance of {@link SensorHandler} as there should only be one.
	 * 
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
					//RConsole.print("#");
					for (UpdatingSensor ses : theSensors)
						ses.updateState();
				}
				Thread.sleep(PERIOD);
			} catch (InterruptedException e) {
				
			}
		}
	}

	/**
	 * This adds an {@link UpdatingSensor} to the sensor list of {@link SensorHandler} to see if there
	 * are any updates.
	 * 
	 * @param inS The {@link UpdatingSensor} to add
	 */
	public synchronized void addSensor(UpdatingSensor inS) {
		theSensors.add(inS);
	}

	public synchronized void removeSensor(UpdatingSensor sensor) {
		theSensors.remove(sensor);
	}

}

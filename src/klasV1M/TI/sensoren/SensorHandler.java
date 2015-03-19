package klasV1M.TI.sensoren;


import java.util.ArrayList;
import java.util.List;

public class SensorHandler extends Thread {
	private List<UpdatingSensor> theSensors;
	public static int PERIOD = 5000;
	private static SensorHandler theHandler = null;

	private SensorHandler() {
		theSensors = new ArrayList<UpdatingSensor>();
		start();
	}

	public static SensorHandler getInstance() {
		if (theHandler == null)
			theHandler = new SensorHandler();
		return theHandler;
	}

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

	public synchronized void addSensor(UpdatingSensor inS) {
		theSensors.add(inS);
	}

}

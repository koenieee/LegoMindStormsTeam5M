package klasV1M.TI;

import klasV1M.TI.sensoren.MyUltraSonicSensor;
import klasV1M.TI.sensoren.SensorListener;
import klasV1M.TI.sensoren.UpdatingSensor;
import lejos.robotics.navigation.DifferentialPilot;

/**
 * Avoids obstacles, reacting to input from the {@link MyUltraSonicSensor} and the maximum width of the obstacle is
 * {@value #obstacleWidth} centimeter.
 * 
 * @author Remco, Koen, & Medzo
 * @version 2.0.0.0
 */
public class ObstacleController implements Runnable, SensorListener {

	private DifferentialPilot diffPilot; // Used for advanced maneuvers

	/**
	 * The maximum width of an obstacle that the robot can avoid in centimeters.
	 * Also used as the threshold range to react to detected objects.
	 */
	private static final int obstacleWidth = 20;
	private boolean isAvoiding = false;
	
	private DriveController dvc;
	
	private Thread t;

	/**
	 * 
	 * @param dp Used for afvanced maneuvers.
	 */
	public ObstacleController(DifferentialPilot dp, DriveController dc) {
		dvc = dc;
		diffPilot = dp;
	}

	public synchronized void stateChanged(UpdatingSensor s, float oldVal, float newVal) {

		// Ultrasonic Sensor
		if (s instanceof MyUltraSonicSensor && isAvoiding == false) {
			/*
			 * instanceof could be replaces by .equals() if sensors are fields
			 * and parameters for constructor
			 */
			if (newVal <= obstacleWidth) { // if object is in 20cm of us.
				isAvoiding = true;
				
				// travel a pre-programmed path around an object.
				diffPilot.rotate(90, false);
				diffPilot.travel(obstacleWidth + 5);
				this.start();
			}
		}
	}


	/**
	 * Starts the {@link Thread} of {@link #t} if it doesn't already exist.
	 */
	public void start() {
		if (t == null) {
			t = new Thread(this);
			t.start();
		}
	}

	/**
	 * Stops the {@link Thread} of {@link #t} if it already exists.
	 */
	public void stop() {
		if (t != null) {
			t.interrupt();
			t = null;
		}
	}

	/**
	TODO
	 */
	@Override
	public void run() {
		dvc.suspend();
		diffPilot.rotate(-90, false);
		diffPilot.travel(obstacleWidth + 10, false);
		diffPilot.rotate(-90, false);
		diffPilot.forward();
		isAvoiding = false;
		this.stop();
	}
}
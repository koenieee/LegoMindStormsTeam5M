package klasV1M.TI;

import klasV1M.TI.sensoren.MyLightSensor;
import klasV1M.TI.sensoren.SensorListener;
import klasV1M.TI.sensoren.UpdatingSensor;
import lejos.nxt.Sound;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;

/**
 * Keep track of how long the robot has lost the line. Will activate if it
 * exceeds a certain time. It will then drive an arc until the line is found.
 * 
 * @author Remco, Koen, & Medzo
 * @version 1.0.0.0
 */

public class SearchLineController implements SensorListener {

	/**
	 * The {@link DifferentialPilot} used for advanced maneuvers.
	 */
	private DifferentialPilot diffPilot;

	/**
	 * Flag to indicate if the {@link SearchLineController} is counting.
	 */
	private boolean counting = false;

	/**
	 * Used to store the system time in milliseconds when the line was lost.
	 */
	private long millis;

	/**
	 * Amount of milliseconds needed to start driving an arc.
	 */
	private long timeUntilStarting = 5000;

	/**
	 * The {@link DriveController} to suspend when {@link SearchLineController} is busy avoiding.
	 */
	private DriveController dc;

	/**
	 * Constructor of the {@link SearchLineController}
	 * 
	 * @param dp
	 *            The {@link DifferentialPilot} to use for advanced maneuvers.
	 * @param drvl
	 *            The {@link DriveController} to be notified when the line is lost.
	 */
	public SearchLineController(DifferentialPilot dp, DriveController drvl) {
		diffPilot = dp;
		dc = drvl;
	}

	public void stateChanged(UpdatingSensor s, float oldVal, float newVal) {
		// Light Sensor
		if (s instanceof MyLightSensor) {
			/*
			 * instanceof could be replaces by .equals() if sensors are fields
			 * and parameters for constructor
			 */
			if (newVal > 80) { // line is lost
				if (!counting) {
					System.out.println("Timer started");
					millis = System.currentTimeMillis();
					counting = true;
				}
				/*
				 * Check if the earlier system time combined with
				 * the pre-set amount of milliseconds is smaller than the current time in milliseconds.
				 * This makes sure code is not execute before a certain amount of time has passed.
				 */
				else if (counting && (millis + timeUntilStarting < System.currentTimeMillis())) {
					dc.suspend();
					Sound.setVolume(Sound.VOL_MAX);
					Sound.beepSequence(); // beep to notify arcing starts.
					diffPilot.arcForward(50); // arcing in a radius of 50 degrees.
					Delay.msDelay(1000);
				}
			} else if (newVal < 60 && counting) { // Line found
				counting = false;
				millis = 0;
			}
		}
	}
}
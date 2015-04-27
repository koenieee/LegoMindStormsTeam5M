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
	private DifferentialPilot diffPilot; // Used for advanced maneuvers

	/**
	 * The boolean that will dictated when to start the Timer
	 */
	private boolean counting = false;

	/**
	 * Store the System time in milliseconds to use as Timer system.
	 */
	private long millis;

	/**
	 * Time until the robot is starting the arc driving.
	 */
	private long timeUntilStarting = 5000;

	/**
	 * Connection to DriveController used to tell the DriveController when the
	 * SearchLineController is active
	 */
	private DriveController dc;

	/**
	 * Constructor of the SearhLineController
	 * 
	 * @param dp
	 *            DifferentialPilot to use for advanced maneuvers.
	 * @param drvl
	 *            DriveController to be notified when the line is lost.
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
				} else if (counting
						&& (millis + timeUntilStarting < System
								.currentTimeMillis())) {
					dc.suspend();
					Sound.setVolume(Sound.VOL_MAX);
					Sound.beepSequence(); // beep to notify arcing starts.
					diffPilot.arcForward(50); // arcing in a radius of 35
												// degrees.
					Delay.msDelay(1000);
				}
			} else if (newVal < 60 && counting) { // line found
				counting = false;
				millis = 0;
			}
		}
	}
}
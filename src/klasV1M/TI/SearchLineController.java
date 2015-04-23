package klasV1M.TI;

import klasV1M.TI.sensoren.MyLightSensor;
import klasV1M.TI.sensoren.SensorListener;
import klasV1M.TI.sensoren.UpdatingSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Timer;
import lejos.util.TimerListener;

/**
 * keeps track of how long he is of the line. Will activate if it execeeds a certain time. It will then go along a 
 * path until the line is found again.
 * 
 * @author Remco, Koen, & Medzo
 * @version 2.0.0.0
 */
public class SearchLineController implements SensorListener, TimerListener {
	/**
	 * The {@link DifferentialPilot} used for advanced maneuvers.
	 */
	private DifferentialPilot diffPilot; // Used for advanced maneuvers
	/**
	 * The boolean that will dictated when to start the Timer
	 */
	private boolean counting = false;
	/**
	 * The Timer that will give a timeout if the robot does not see the line within 2 seconds 
	 */
	private Timer theTimer;
	/**
	 * connection to DriveController used to tell the DriveController when the SearchLineCOntroller is active
	 */
	private DriveController dc;
	/**
	 * Constructor of the SearhLineController 
	 * @param dp
	 * @param drvl
	 */

	public SearchLineController(DifferentialPilot dp, DriveController drvl) {
		diffPilot = dp;
		theTimer = new Timer(2000, this);
		dc = drvl;
	}

	@Override
	public void timedOut() {
		System.out.println("Line is lost");
		dc.suspend();
		System.out.println("Line searching, using arcs");
		diffPilot.arcForward(45);
	}

	public void stateChanged(UpdatingSensor s, float oldVal, float newVal) {
		// Light Sensor
		if (s instanceof MyLightSensor) {
			/*
			 * instanceof could be replaces by .equals() if sensors are fields
			 * and parameters for constructor
			 */
			if (newVal > 60) { // line is lost
				if (!counting) {
					theTimer.start();
					counting = true;
				}
			}
			if (newVal < 40) { // line found
				if (counting) {
					theTimer.stop();
					counting = false;
				}
			}
		}
	}

}
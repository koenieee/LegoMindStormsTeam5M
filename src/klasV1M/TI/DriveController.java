package klasV1M.TI;

import klasV1M.TI.sensoren.MyLightSensor;
import klasV1M.TI.sensoren.SensorListener;
import klasV1M.TI.sensoren.UpdatingSensor;
import lejos.nxt.Sound;
import lejos.robotics.navigation.DifferentialPilot;

/**
 * Follows the edge of the black line, by adjusting the steering of the robot
 * accordingly. It registers itself to the {@link MyLightSensor} and uses the
 * received data to calculate the adjustment needed for steering.
 * 
 * @author Remco, Koen, & Medzo
 * @version 3.0.0.0
 */
public class DriveController implements SensorListener {

	/**
	 * The {@link DifferentialPilot} used for advanced maneuvers.
	 */
	private DifferentialPilot diffPilot;

	/**
	 * Used as a flag for the suspended state.
	 */
	private boolean suspended = false;

	/**
	 * Initializes the {@link #diffPilot} and starts moving forward.
	 */
	public DriveController(DifferentialPilot dp) {
		diffPilot = dp;
		diffPilot.setTravelSpeed(DifferentialPilot.WHEEL_SIZE_NXT2 + 3);
		// Start moving forward
		diffPilot.forward();
		diffPilot.setRotateSpeed(30);
	}

	public void stateChanged(UpdatingSensor s, float oldVal, float newVal) {
		// Light Sensor
		if (s instanceof MyLightSensor) {
			/*
			 * instanceof could be replaces by .equals() if sensors are fields
			 * and parameters for constructor
			 */
			if (suspended) {
				if (newVal < 40) { // Black line detected
					Sound.setVolume(Sound.VOL_MAX);
					Sound.beep();
					diffPilot.travel(3);
					diffPilot.rotate(50);
					resume();
					diffPilot.forward();
				}
			} else if (!suspended) {
				/*
				 * Steers between -100 (left) and +100 (right) to adjust
				 * direction, since newVal is always between 0 and 100.
				 */
				diffPilot.steer((newVal - 50) * 2);
			}
		}
	}

	/**
	 * Suspends the {@link DriveController}
	 */
	public synchronized void suspend() {
		suspended = true;
	}

	/**
	 * Resumes the {@link DriveController}
	 */
	public synchronized void resume() {
		suspended = false;
	}

}
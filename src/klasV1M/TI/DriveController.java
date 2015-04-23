package klasV1M.TI;

import klasV1M.TI.sensoren.MyLightSensor;
import klasV1M.TI.sensoren.SensorListener;
import klasV1M.TI.sensoren.UpdatingSensor;
import lejos.nxt.Sound;
import lejos.robotics.navigation.DifferentialPilot;

/**
 * DriveController is used to follow the black line on the ground, it steers the
 * robot so the black line is always in the middle. DriveController is
 * registered to MyLightSensor and uses the data input from MyLightSensor to
 * control the robot.
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
	 * This boolean is used to react to different actions when the line is lost
	 * or the robot is avoiding an obstacle.
	 */
	private boolean suspended = false;

	/**
	 * Initializes the {@link #diffPilot} and starts moving forward. <br>
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
		//	System.out.println("Newval: " + newVal);
			if (suspended) {
				if (newVal < 40) { // black line detected

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
	 * This method is used for the outside world to set the contents of a
	 * private boolean. When suspend is called, the DriveController stops
	 * following a line.
	 */
	public synchronized void suspend() {
		suspended = true;
	}

	/**
	 * When the DriveController sees the black line again, it will rotate and
	 * starts following the line.
	 */
	public synchronized void resume() {
		suspended = false;
	}

}
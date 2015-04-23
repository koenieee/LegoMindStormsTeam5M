package klasV1M.TI;

import klasV1M.TI.sensoren.MyLightSensor;
import klasV1M.TI.sensoren.MyUltraSonicSensor;
import klasV1M.TI.sensoren.SensorListener;
import klasV1M.TI.sensoren.UpdatingSensor;
import lejos.nxt.Sound;
import lejos.robotics.navigation.DifferentialPilot;

/**
 * TODO
 * 
 * @author Remco, Koen, & Medzo
 * @version 2.0.0.0
 */
public class DriveController implements SensorListener {



	/**
	 * The {@link DifferentialPilot} used for advanced maneuvers.
	 */
	private DifferentialPilot diffPilot;

	/**
	 * The track width in centimeters from the robot, measured from the center
	 * of the left wheel to the center of the right wheel. <br>
	 * Used by {@link #diffPilot} for accurate driving.
	 */

	private boolean suspended;
	//private SearchLineController slc;

	/**
	 * Initializes the {@link #diffPilot} and starts moving forward. <br>
	 * Also registers itself and the {@link SearchLineController} at the
	 * {@link MyLightSensor}. The {@link ObstacleController} gets registered at
	 * the {@link MyUltraSonicSensor}.
	 * 
	 */
	public DriveController(DifferentialPilot dp) {
		
	//	slc = new SearchLineController(diffPilot);
		diffPilot = dp;

		//mls.addListener(slc);
		// Set speed to 1 rotation/second
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
			System.out.println("Newval: " + newVal);
			if (suspended) {
				if (newVal < 40) { //black line detected
					
					Sound.setVolume(Sound.VOL_MAX);
					Sound.beep();
					diffPilot.travel(3);
					diffPilot.rotate(50);
					resume();
					diffPilot.forward();
				//	slc.setIsLost(false);
				}
			}
			else if(!suspended) {
				/* Steers between -100 (left) and +100 (right) to adjust direction,
				 * since newVal is always between 0 and 100. */
				diffPilot.steer((newVal - 50) * 2);
			}
		}
	}
	
	
	public synchronized void suspend() {
		suspended = true;
	}

	public synchronized void resume() {
		suspended = false;
	}
	
}
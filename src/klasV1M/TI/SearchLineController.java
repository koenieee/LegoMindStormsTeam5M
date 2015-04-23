package klasV1M.TI;

import klasV1M.TI.sensoren.MyLightSensor;
import klasV1M.TI.sensoren.SensorListener;
import klasV1M.TI.sensoren.UpdatingSensor;
import lejos.nxt.Sound;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;

/**
 * TODO
 * 
 * @author Remco, Koen, & Medzo
 * @version 2.0.0.0
 */
public class SearchLineController implements SensorListener {

	/**
	 * The {@link DifferentialPilot} used for advanced maneuvers.
	 */
	private DifferentialPilot diffPilot; // Used for advanced maneuvers

	/**
	 * The {@link Thread} the method {@link #run()} will use.
	 */
	private boolean counting = false;
	private long millis;
	private long timeUntilStarting = 5000;
	private DriveController dc;

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
				if(!counting){
					System.out.println("Timer started");
					millis = System.currentTimeMillis();
					counting = true;
				} else if (counting && (millis+timeUntilStarting < System.currentTimeMillis())) {
					dc.suspend();
					Sound.setVolume(Sound.VOL_MAX);
					Sound.beepSequence();
					diffPilot.arcForward(50); //arcing in a radius of 35 degrees.
					Delay.msDelay(1000);
				}
			}
			else if (newVal < 60 && counting) { // line found
				counting = false;
				millis = 0;
			}
		}
	}
}
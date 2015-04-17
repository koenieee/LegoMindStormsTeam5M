package klasV1M.TI;

import klasV1M.TI.sensoren.MyLightSensor;
import lejos.nxt.Button;
import lejos.util.Delay;

/**
 * Provides methods to calibrate the LEGO NXT
 * 
 * @author Remco, Koen, & Medzo
 * @version 2.0.0.0
 */
public class CalibrationController {
	
	private MyLightSensor lightSensor;
	
	/**
	 * 
	 * @param sensor The {@link MyLightSensor} to use for the calibration.
	 */
	public CalibrationController(MyLightSensor sensor) {
		lightSensor = sensor;
	}
	
	/** 
	 * Function to calibrate the {@link MyLightSensor} to work in a range of 0 (darkest) to 100 (lightest). 
	 */
	public void calibrateLightSensor() {
		System.out.println("Place on white spot in five seconds");

		Button.waitForAnyPress(5000);

		System.out.println("Calibrating white...");
		int high = lightSensor.getNormalizedLightValue();
		lightSensor.setHigh(high);//calibrateHigh();
		
		System.out.println("Place on black spot in five seconds");

		Button.waitForAnyPress(5000);

		System.out.println("Calibrating black...");
		int low = lightSensor.getNormalizedLightValue();
		lightSensor.setLow(low);//calibrateLow();
		System.out.println("High: " + high + " | Low: " + low);
		Delay.msDelay(2000);
		if (high == 0 || high <= low) { // error checking
			System.out.println("Calibration failed! Restarting procedure...");
			calibrateLightSensor(); // restart procedure
		}
		if (high - low < 100) {
			System.out.println("Values differ less than 100 (" + (high - low) + "). Might be less accurate");
			Delay.msDelay(2000);
		}
		System.out.println("Press the enter button in 2 seconds to recalibrate");
		if (Button.waitForAnyPress(2000) == Button.ID_ENTER) {
			calibrateLightSensor();
		}
	}
}
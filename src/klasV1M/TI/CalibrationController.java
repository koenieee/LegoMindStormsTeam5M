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

	/**
	 * The {@link MyLightSensor} that you want to calibrate.
	 */
	private MyLightSensor lightSensor;

	/**
	 * Constructor for the {@link CalibrationController}
	 * 
	 * @param sensor
	 *            The {@link MyLightSensor} to use for the calibration.
	 */
	public CalibrationController(MyLightSensor sensor) {
		lightSensor = sensor;
	}

	/**
	 * Function to calibrate the {@link MyLightSensor} to work in a range of 0
	 * (darkest) to 100 (lightest).
	 */
	public void calibrateLightSensor() {
		System.out.println("Place on white spot in five seconds");

		Button.waitForAnyPress(5000);

		System.out.println("Calibrating white...");
		int high = lightSensor.getNormalizedLightValue();
		lightSensor.setHigh(high); // Configure high (brightest)

		System.out.println("Place on black spot in five seconds");

		Button.waitForAnyPress(5000);

		System.out.println("Calibrating black...");
		int low = lightSensor.getNormalizedLightValue();
		lightSensor.setLow(low); // Configure low (darkest)
		System.out.println("High: " + high + " | Low: " + low); // Display
																// values for
																// debugging
		Delay.msDelay(2000);
		
		if (high == 0 || high <= low) { // Error checking
			System.out.println("Calibration failed! Restarting procedure...");
			calibrateLightSensor(); // Restart procedure
		}
		if (high - low < 100) {
			// Warn user for less accurate readings
			System.out.println("Values differ less than 100 (" + (high - low)
					+ "). Might be less accurate");
			Delay.msDelay(2000);
		}
		// Allow user to recalibrate if deemed necessary
		System.out
				.println("Press the enter button in 2 seconds to recalibrate");
		if (Button.waitForAnyPress(2000) == Button.ID_ENTER) {
			calibrateLightSensor();
		}
	}
}
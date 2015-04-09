package klasV1M.TI;

import klasV1M.TI.sensoren.MyLightSensor;
import lejos.nxt.Button;
import lejos.util.Delay;

/**
 * Provides methods to configure the LEGO NXT
 * 
 * @author Remco, Koen, & Medzo
 * @version 1.0.0.0
 */
public class Configuration {
	
	public Configuration() {
	}
	
	/** Function to calibrate both the {@link MyLightSensor} to work in a range of 0 to 100 
	 * With 0 as most Black
	 * With 100 as most White
	 * @return <code>true</code> when configuration was succesfull, <code>false</code> otherwise.
	 */
	public void configureLightSensors() {
		System.out.println("Place on white spot in five seconds");

		Button.waitForAnyPress(5000);

		System.out.println("Calibrating white...");
		int high = MyLightSensor.getInstance().getNormalizedLightValue();
		MyLightSensor.getInstance().setHigh(high);//calibrateHigh();
		
		System.out.println("Place on black spot in five seconds");

		Button.waitForAnyPress(5000);

		System.out.println("Calibrating black...");
		int low = MyLightSensor.getInstance().getNormalizedLightValue();
		MyLightSensor.getInstance().setLow(low);//calibrateLow();
		System.out.println("High: " + high + " | Low: " + low);
		Delay.msDelay(2000);
		if (high == 0 || high <= low) { // error checking
			System.out.println("Calibration failed! Restarting procedure...");
			configureLightSensors(); // restart procedure
		}
		if (high - low < 100) {
			System.out.println("Values differ less than 100 (" + (high - low) + "). Might be less accurate");
			Delay.msDelay(2000);
		}
		System.out.println("Press the enter button in 2 seconds to recalibrate");
		if (Button.waitForAnyPress(2000) == Button.ID_ENTER) {
			configureLightSensors();
		}
	}
}
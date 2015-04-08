package klasV1M.TI;

import klasV1M.TI.sensoren.MyLightSensor;
import lejos.nxt.Button;

/**
 * Provides methods to configure the LEGO NXT
 * 
 * @author Remco, Koen, & Medzo
 * @version 1.0.0.0
 */
public class Configuration {
	
	public Configuration() {
	}
	
	/**
	 * Calibrates the {@link MyLightSensor} to work in a range of 0 (darkest) to 100 (lightest).
	 */
	public void configureLightSensors() {
		System.out.println("Place on white spot in five seconds");

		Button.waitForAnyPress(5000);

		System.out.println("Calibrating white...");
		MyLightSensor.getInstance().calibrateHigh();
		
		System.out.println("Place on black spot in five seconds");

		Button.waitForAnyPress(5000);

		System.out.println("Calibrating black...");
		MyLightSensor.getInstance().calibrateLow();
	}
}
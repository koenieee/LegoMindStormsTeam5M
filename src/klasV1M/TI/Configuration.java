package klasV1M.TI;

import klasV1M.TI.sensoren.MyLightSensor;
import lejos.nxt.Button;

/**
 * Configuration class can be used to configure some settings on the Lego Mindstorm Robot
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
		MyLightSensor.getInstance().calibrateHigh();
		
		System.out.println("Place on black spot in five seconds");

		Button.waitForAnyPress(5000);

		System.out.println("Calibrating black...");
		MyLightSensor.getInstance().calibrateLow();
	}
}
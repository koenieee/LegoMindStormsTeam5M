package klasV1M.TI;

import klasV1M.TI.sensoren.MyLightSensor;
import lejos.nxt.Button;
import lejos.nxt.SensorPort;

/**
 * The Entrypoint for the application
 * 
 * @author Remco, Koen, & Medzo
 * @version 2.0.0.0
 */
public class Main {

	/**
	 * Entrypoint for the application
	 * @param args Arguments that can be passed into the application. Current implementation ignores these.
	 */
	public static void main(String[] args) {
		//RConsole.open();
		System.out.println("Press button to start...");
		
		MyLightSensor mls = new MyLightSensor(SensorPort.S2);
		
		Button.waitForAnyPress();
		CalibrationController c = new CalibrationController(mls);
		c.calibrateLightSensor(); //calibrate lightSensor 
		System.out.println("Place lightsensor above the middle of the black line.");
		Button.waitForAnyPress();
		DriveController dc = new DriveController();
		
		while (true) {
			Thread.yield();
		}
	}
}
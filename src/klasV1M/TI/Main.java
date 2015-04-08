package klasV1M.TI;

import lejos.nxt.Button;

/**
 * The Entrypoint for the application
 * @author Remco, Koen, & Medzo
 * @version 1.0.0.0
 */
public class Main {

	/**
	 * Entrypoint for the application
	 * @param args Arguments that can be passed into the application. Current implementation ignores these
	 */
	public static void main(String[] args) {
		//RConsole.open();
		System.out.println("Press button to start...");
		Button.waitForAnyPress();
		Configuration c = new Configuration();
		c.configureLightSensors();
		System.out.println("Place lightsensor on the black line");
		Button.waitForAnyPress();
		ObstacleController oc = new ObstacleController();
		
		while (true) {
			Thread.yield();
		}
	}
}
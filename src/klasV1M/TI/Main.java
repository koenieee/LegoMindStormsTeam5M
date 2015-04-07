package klasV1M.TI;

import lejos.nxt.Button;

/**
 * 
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
		System.out.println("Starting...");
		Configuration c = new Configuration();
		c.configureLightSensors();
		System.out.println("Place lightsensor to the left of the black line");
		Button.waitForAnyPress();
		ObstacleController oc = new ObstacleController();
		
		while (true) {
			Thread.yield();
		}
	}
}
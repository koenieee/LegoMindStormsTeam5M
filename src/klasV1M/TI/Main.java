package klasV1M.TI;

import klasV1M.TI.controllers.Configuration;
import klasV1M.TI.controllers.LightTestController;
import klasV1M.TI.controllers.ObstacleController;
import lejos.nxt.Button;
import lejos.nxt.comm.RConsole;

/**
 * @author koen
 *
 */
public class Main {

	/**
	 * Entrypoint for the application
	 * @param args
	 */
	public static void main(String[] args) {
	//	RConsole.open();
		Configuration c = new Configuration();
		System.out.println("Starting...");
		//c.resetAndConfigureAll();
		c.configureLightSensors();
		//c.setAutoAdjust(true);
		//c.resetSoundSensor();
		System.out.println("Place lightsensor to the left of the black line");
		Button.waitForAnyPress();
		ObstacleController oc = new ObstacleController();
		oc.start();
//		SensorPair sp = new SensorPair();
//		sp.addListener(oc);
//		sp.start();
		//LightTestController lc = new LightTestController();
		
		while (true) {
			Thread.yield();
		}
	}
}
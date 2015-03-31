package klasV1M.TI;

import klasV1M.TI.controllers.Configuration;
import klasV1M.TI.controllers.ObstacleController;
import klasV1M.TI.controllers.TestController;
import lejos.nxt.comm.RConsole;

/**
 * @author koen
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Configuration c = new Configuration();
		System.out.println("Starting application...");
		//RConsole.open();
		//c.configureLightSensors();
		c.resetSoundSensor();
		ObstacleController oc = new ObstacleController(200);
		oc.start();
		//TestController tc = new TestController(200);
		while (true) {
			Thread.yield();
		}
	}

}

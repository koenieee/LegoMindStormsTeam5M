package klasV1M.TI;

import klasV1M.TI.controllers.Configuration;
import klasV1M.TI.controllers.LightTestController;
import klasV1M.TI.controllers.ObstacleController;
import klasV1M.TI.controllers.SensorPair;
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
		RConsole.open();
		Configuration c = new Configuration();
		System.out.println("Starting application...");
		//c.resetAndConfigureAll();
		c.configureLightSensors();
		c.resetSoundSensor();
		ObstacleController oc = new ObstacleController(200);
		oc.start();
		SensorPair sp = new SensorPair();
		sp.start();
		//LightTestController ltc = new LightTestController();
		//TestController tc = new TestController(200);
		while (true) {
			Thread.yield();
		}
	}
}
package klasV1M.TI;

import klasV1M.TI.controllers.Configuration;
import klasV1M.TI.controllers.ObstacleController;
import klasV1M.TI.controllers.SensorPair;
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
		//Globals.mMiddle.setSpeed(360);
		//Globals.mMiddle.rotateTo(45);
		
		
		RConsole.open();
		Configuration c = new Configuration();
		System.out.println("Starting application...");
		c.configureLightSensors();//c.resetAndConfigureAll();//configureLightSensors();
		//RConsole.open();
		//ObstacleController oc = new ObstacleController(200);
		//oc.start();
		SensorPair sp = new SensorPair();
		sp.start();
		//TestController tc = new TestController(200);
		while (true) {
			Thread.yield();
		}
	}

}
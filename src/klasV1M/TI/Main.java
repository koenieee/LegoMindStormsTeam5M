package klasV1M.TI;

import klasV1M.TI.controllers.Configuration;
import klasV1M.TI.controllers.Driver;
import klasV1M.TI.controllers.LogPath;
import klasV1M.TI.controllers.ObstacleController;
import klasV1M.TI.controllers.SensorPair;
import lejos.nxt.comm.RConsole;
import lejos.robotics.navigation.DifferentialPilot;

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
		DifferentialPilot dp = new DifferentialPilot(c.getDiameter(), 13, Globals.mLeft, Globals.mRight);
		LogPath lp = new LogPath();
		dp.addMoveListener(lp);
		Driver d = new Driver(dp);
		//LightTestController ltc = new LightTestController();
		//TestController tc = new TestController(200);
		while (true) {
			Thread.yield();
		}
	}
}
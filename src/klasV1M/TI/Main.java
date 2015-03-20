package klasV1M.TI;

import klasV1M.TI.controllers.Configuration;
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
		c.resetAndConfigureAll();//configureLightSensors();
		RConsole.open();
		TestController tc = new TestController(2000);
		while (true) {
			Thread.yield();
		}
	}

}

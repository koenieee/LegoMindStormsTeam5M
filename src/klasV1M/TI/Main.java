package klasV1M.TI;

import klasV1M.TI.controllers.Configuration;
import klasV1M.TI.controllers.TestController;

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
		c.configureLightSensors();
		TestController tc = new TestController(2000);
		while (true) {
			Thread.yield();
		}
	}

}

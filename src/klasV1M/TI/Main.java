package klasV1M.TI;

import klasV1M.TI.controllers.Configuration;
import klasV1M.TI.controllers.TestController;
import lejos.nxt.LCD;
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
		c.configureLightSensors();
		LCD.clear();
		//RConsole.open();
		System.out.println("Put one sensor on black and the other on white");
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TestController tc = new TestController(200);
		
		
		while (true) {
			Thread.yield();
		}
	}

}

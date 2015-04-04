package klasV1M.TI.LineTest;

import klasV1M.TI.controllers.Configuration;
import klasV1M.TI.controllers.SensorPair;
import klasV1M.TI.controllers.TestController;
import lejos.nxt.Button;
import lejos.nxt.comm.RConsole;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//RConsole.open();
		Button.waitForAnyEvent(7000);
		Configuration c = new Configuration();
		c.configureLightSensors();
	//	SensorPair sp = new SensorPair();
	//	sp.start();
		Button.waitForAnyEvent(7000);
		TestController tyc = new TestController(300);
		//tyc
		
		while (true) {
			Thread.yield();
		}
	}

}

package klasV1M.TI.LineTest;

import klasV1M.TI.controllers.Configuration;
import klasV1M.TI.controllers.SensorPair;
import lejos.nxt.comm.RConsole;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RConsole.open();
		Configuration c = new Configuration();
		c.configureLightSensors();
		SensorPair sp = new SensorPair();
		sp.start();
		
		while (true) {
			Thread.yield();
		}
	}

}

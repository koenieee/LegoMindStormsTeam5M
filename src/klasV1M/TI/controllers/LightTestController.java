package klasV1M.TI.controllers;

import klasV1M.TI.Globals;
import lejos.nxt.comm.RConsole;

public class LightTestController extends Thread {
	public LightTestController() {
		start();
	}

	@Override
	public void run() {
		while (true) {
			int l = Globals.MLS.getLightValue();
			int r = Globals.MCS.getLightValue();

			RConsole.println(System.currentTimeMillis() % 10000 + "L: " + l
					+ " | R: " + r);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}
}

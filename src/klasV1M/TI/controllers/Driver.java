package klasV1M.TI.controllers;

import lejos.robotics.navigation.DifferentialPilot;

public class Driver implements Runnable {

	private DifferentialPilot diffPilot;
	private Thread t;
	
	public Driver(DifferentialPilot diffPilot) {
		this.diffPilot = diffPilot;
	}
	
	@Override
	public void run() {
		
	}

	public void start() {
		if (t == null) {
			t = new Thread(this);
		}
	}
	
	public void stop() {
		if (t != null) {
			t.interrupt();
			t = null;
			t.interrupt();
		}
	}
}
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

	/**
	 * Starts a new {@link Thread} of this class if one is not already running.
	 */
	public void start() {
		if (t == null) {
			t = new Thread(this);
			t.start();
		}
	}
	
	/**
	 * Stops the {@link Thread} of this class if it is already running.
	 */
	public void stop() {
		if (t != null) {
			t.interrupt();
			t = null;
			t.interrupt();
		}
	}
}
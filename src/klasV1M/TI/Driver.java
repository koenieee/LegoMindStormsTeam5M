package klasV1M.TI;

import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;

public class Driver implements Runnable {

	private Thread t = null;
	private static Driver driver = null;
	private DifferentialPilot diffPilot = null;
	private double trackWidth = 13;
	
	private Driver() {
		diffPilot = new DifferentialPilot(DifferentialPilot.WHEEL_SIZE_NXT2, trackWidth, Motor.C, Motor.A);
		diffPilot.setTravelSpeed(DifferentialPilot.WHEEL_SIZE_NXT2);
	}
	
	public static Driver getInstance() {
		if (driver == null) {
			driver = new Driver();
		}
		return driver;
	}
	
	public void stopThread() {
		if (t != null && t.isAlive()) {
			t.interrupt();
			t = null;
		}
	}
	
	public void rotate(double angle) {
		stopThread();
		t = new Thread() {
			public void run() {
				diffPilot.rotate(angle);//, true);
			}
		};
	}
	
	public void travel(double distance) {
		stopThread();
		t = new Thread() {
			public void run() {
				diffPilot.travel(distance);//, true);
			}
		};
	}
	
	
	public void stop() {
		stopThread();
		t = new Thread() {
			public void run() {
				diffPilot.stop();
			}
		};
	}
	
	public boolean isMoving() {
		return diffPilot.isMoving();
	}
	
	public DifferentialPilot getDifferentialPilot() {
		return diffPilot;
	}
	
	/**
	 * 
	 * @return <code>true</code> if move completed succesfully, <code>false</code> otherwise
	 */
	public boolean waitForFinish() {
		while (!Thread.interrupted() && diffPilot.isMoving()) {
			Thread.yield();
		}
		if (Thread.interrupted()) {
			return false;
		}
		return true; // succesful finish
	}
	
	public void start() {
		stopThread();
		if (t == null) {
			t = new Thread(this);
			t.start();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//diffPilot.stop();
		//diffPilot.rotate(90, true);
		//if (!waitForFinish())
		//	return;
		//diffPilot.travel(30, true);
		//if (!waitForFinish())
		//	return;
		if (!waitForFinish())
			return;
		diffPilot.rotate(-90, true);
		if (!waitForFinish())
			return;
		diffPilot.travel(50, true);
		if (!waitForFinish())
			return;
		diffPilot.rotate(-90, true);
		if (!waitForFinish())
			return;
		diffPilot.travel(30, true);
		if (!waitForFinish())
			return;
		diffPilot.rotate(90, true);
		if (!waitForFinish())
			return;
	}
}
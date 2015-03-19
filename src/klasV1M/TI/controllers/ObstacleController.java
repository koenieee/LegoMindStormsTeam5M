package klasV1M.TI.controllers;

public class ObstacleController implements Runnable {

	private Thread t;
	
	public void calculateRoute() {
		
	}
	
	public void pollStatus() {
		
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

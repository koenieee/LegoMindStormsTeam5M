package klasV1M.TI.controllers;

import java.util.HashMap;

import klasV1M.TI.Globals;
import klasV1M.TI.sensoren.SensorListener;
import klasV1M.TI.sensoren.UpdatingSensor;
import lejos.nxt.LCD;

public class SensorPair implements Runnable, SensorListener {

	private Thread t;
	
	private int lineRelative = 0;
	private float heading;
	private boolean left;
	private boolean right;
	
	private HashMap<Long, Integer> log = new HashMap<Long, Integer>();

	public SensorPair() {
		Globals.MLS.addListener(this);
		Globals.MCS.addListener(this);
		
		if (Globals.MLS.getLightValue() <= Globals.BlackThreshold) {
			left = true;
		}
		if (Globals.MCS.getLightValue() <= Globals.BlackThreshold) {
			right = true;
		}
	}

	@Override
	public void run() {
		LCD.clearDisplay();
		while (true) {
			try {
				Thread.sleep(Globals.StandardDelay);
			} catch (InterruptedException e) {
			}
			
			lineRelative = left && right ? 0 : left ? -1 : right ? 1 : 8;
			
			log.put(System.currentTimeMillis(), lineRelative);
			
			
			System.out.println(lineRelative);
			if (lineRelative == 0) {
				continue;
			}
			
			
			
			//LCD.clearDisplay();
			
			
			for (int i = 0; i < log.size() && i < LCD.SCREEN_HEIGHT; i++) {
				int centerX = LCD.SCREEN_WIDTH / 2;
				
				for (int j = 0; j < 3; j++) {
					LCD.setPixel(centerX - 1 + j, LCD.SCREEN_HEIGHT - 1 - i, 0);
				}
				
				LCD.setPixel(centerX + lineRelative, LCD.SCREEN_HEIGHT - 1 - i, 1);
			}
			
			
			
			/*heading = 0;
			if (left < 20) {
				heading--;
			}
			if (right < 20) {
				heading++;
			}
			
			System.out.println("Heading " + (heading == -1 ? "Left" : heading == 1 ? "Right" : "None"));*/
		}
	}

	public void start() {
		if (t == null) {
			t = new Thread(this);
			t.start();
		}
	}

	public void stop() {
		if (t != null) {
			t.interrupt();
			t = null;
			t.interrupt();
		}
	}

	@Override
	public void stateChanged(UpdatingSensor s, float oldVal, float newVal) {
		//System.out.println("old: " + oldVal + " | new: " + newVal);

		if (s.equals(Globals.MLS)) {
			left = newVal <= Globals.BlackThreshold;
		}

		if (s.equals(Globals.MCS)) {
			right = newVal <= Globals.BlackThreshold;
		}
		t.interrupt();
	}
}
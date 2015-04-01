package klasV1M.TI.controllers;

import java.util.HashMap;

import javax.microedition.sensor.HeadingChannelInfo;

import klasV1M.TI.Globals;
import klasV1M.TI.sensoren.SensorListener;
import klasV1M.TI.sensoren.UpdatingSensor;
import lejos.nxt.LCD;
import lejos.nxt.comm.RConsole;

public class SensorPair implements Runnable, SensorListener {

	private Thread t;
	
	private boolean lost;
	private boolean found;

	private int lineRelative = 0;
	private boolean left;
	private boolean right;
	
	public static final int LINE_UNKNOWN = -1;
	public static final int LINE_MIDDLE = 0;
	public static final int LINE_LEFT = 1;
	public static final int LINE_RIGHT = 2;

	
	//private HashMap<Long, Integer> log = new HashMap<Long, Integer>();

	public SensorPair() {
		Globals.MLS.addListener(this);
		Globals.MCS.addListener(this);
		
		if (Globals.MLS.getLightValue() <= Globals.BlackThreshold) {
			left = true;
		}
		if (Globals.MCS.getLightValue() <= Globals.BlackThreshold) {
			right = true;
		}
		determineLine();
	}

	@Override
	public void run() {
		LCD.clearDisplay();
		while (true) {
			try {
				Thread.sleep(Globals.StandardDelay);
			} catch (InterruptedException e) {
			}
			
			// active polling
			determineLine();
			
			//lineRelative = left && right ? 0 : left ? -1 : right ? 1 : 8;
			
			//log.put(System.currentTimeMillis(), lineRelative);
			//RConsole.println(lineRelative + " " + (left && right ? "Middle" : left ? "Left" : right ? "Right" : "Unknown"));
			
			//LCD.clearDisplay();
			
			
			/*for (int i = 0; i < log.size() && i < LCD.SCREEN_HEIGHT; i++) {
				int centerX = LCD.SCREEN_WIDTH / 2;
				
				for (int j = 0; j < 3; j++) {
					LCD.setPixel(centerX - 1 + j, LCD.SCREEN_HEIGHT - 1 - i, 0);
				}
				
				LCD.setPixel(centerX + lineRelative, LCD.SCREEN_HEIGHT - 1 - i, 1);
			}*/
			
			
			
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
	
	private void determineLine() {
		if (left && right) {
			RConsole.println("On line");
			if (!found) {
				RConsole.println("Found!");
			}
			lineRelative = LINE_MIDDLE;
			found = true;
		} else if (left) {
			lineRelative = LINE_LEFT;
			RConsole.println("Left");
		} else if (right) {
			lineRelative = LINE_RIGHT;
			RConsole.println("Right");
		} else {
			if (!lost) {
				RConsole.println("Now lost!");
			}
			lineRelative = LINE_UNKNOWN;
			lost = true;
		}
	}
	
	public boolean isLost() {
		return lost;
	}
	
	public int getLineRelative() {
		return lineRelative;
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
		System.out.println(System.currentTimeMillis() % 1000 + ": " + (s.equals(Globals.MLS) ? "MLS" : "MCS"));
		if (s.equals(Globals.MLS)) {
			left = newVal <= Globals.BlackThreshold;
		}

		if (s.equals(Globals.MCS)) {
			right = newVal <= Globals.BlackThreshold;
		}
		//t.interrupt();
		// passive polling
		//determineLine();
	}

	@Override
	public void stateNotification(UpdatingSensor s, float value) {
		// TODO Auto-generated method stub
		// Ignore
	}
}
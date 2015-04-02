package klasV1M.TI.controllers;

import java.util.ArrayList;
import java.util.List;

import klasV1M.TI.Globals;
import klasV1M.TI.sensoren.SensorListener;
import klasV1M.TI.sensoren.UpdatingSensor;
import lejos.nxt.LCD;
import lejos.nxt.comm.RConsole;

public class SensorPair implements Runnable, SensorListener {

	private Thread t;
	
	private static boolean lost;
	private static boolean found;

	private int oldVal = LINE_UNKNOWN;
	private int newVal = LINE_UNKNOWN;
	private int lineRelative = 0;
	private boolean left;
	private boolean right;
	
	public static final int LINE_UNKNOWN = -1;
	public static final int LINE_MIDDLE = 0;
	public static final int LINE_LEFT = 1;
	public static final int LINE_RIGHT = 2;
	
	private int relativePosition = 0;
	
	private List<SensorPairListener> spl;

	
	//private HashMap<Long, Integer> log = new HashMap<Long, Integer>();

	public SensorPair() {
		Globals.MLS.addListener(this);
		Globals.MCS.addListener(this);
		
		spl = new ArrayList<SensorPairListener>();
		
		if (Globals.MLS.getLightValue() <= Globals.BlackThreshold) {
			left = true;
		}
		if (Globals.MCS.getLightValue() <= Globals.BlackThreshold) {
			right = true;
		}
		
		relativePosition = left && right ? LINE_MIDDLE : left ? LINE_LEFT : right ? LINE_RIGHT : LINE_UNKNOWN; 
		
		determineLine();
	}

	@Override
	public void run() {
		LCD.clearDisplay();
		while (true) {
			try {
				t.sleep(Globals.LongDelay);
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
			
		}
	}
	
	private void determineLine() {
		oldVal = newVal;
		
		if (left && right) {
			RConsole.println("On line");
			if (!found) {
				for (SensorPairListener s : spl) {
					s.lineFound();
				}
				RConsole.println("Found!");
			}
			newVal = LINE_MIDDLE;//lineRelative = LINE_MIDDLE;
			relativePosition = LINE_MIDDLE;
			found = true;
		} else if (left) {
			if (relativePosition != LINE_RIGHT) {
				newVal = LINE_LEFT; //lineRelative = LINE_LEFT;
				RConsole.println("Left");
				relativePosition = LINE_LEFT;
			}
		} else if (right) {
			if (relativePosition != LINE_LEFT) {
				newVal = LINE_RIGHT; //lineRelative = LINE_RIGHT;
				RConsole.println("Right");
				relativePosition = LINE_RIGHT;
			}
		} else if (!left && !right) {
			if (!lost) {
				for (SensorPairListener s : spl) {
					s.lineLost();
				}
				RConsole.println("Now lost!");
			}
			newVal = relativePosition;//LINE_UNKNOWN; //lineRelative = LINE_UNKNOWN;
			//relativePosition = LINE_UNKNOWN;
			lost = true;
		}
		
		if (oldVal != newVal) {
			for (SensorPairListener s : spl) {
				s.stateChanged(oldVal, newVal);
			}
		}
		RConsole.println("Rel: " + (relativePosition == LINE_MIDDLE ? "Middle" : relativePosition == LINE_LEFT ? "Left" : relativePosition == LINE_RIGHT ? "Right" : "Unknown"));
	}
	
	public static boolean isLost() {
		return lost;
	}
	
	public static boolean lineFound() {
		return found;
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
		//System.out.println(System.currentTimeMillis() % 1000 + ": " + (s.equals(Globals.MLS) ? "MLS" : "MCS"));
		//RConsole.println(System.currentTimeMillis() % 100000 + ": " + (s.equals(Globals.MLS) ? "MLS" : "MCS") + " old|new: " + oldVal + "|" + newVal);
		
		if (s.equals(Globals.MLS)) {
			left = newVal <= Globals.BlackThreshold;
		}

		if (s.equals(Globals.MCS)) {
			//RConsole.println(System.currentTimeMillis() % 100000 + ": old|new: " + oldVal + "|" + newVal);
			right = newVal <= Globals.BlackThreshold;
		}
		//t.interrupt();
		// passive polling
		determineLine();
	}

	@Override
	public void stateNotification(UpdatingSensor s, float value) {
		// TODO Auto-generated method stub
		// Ignore
	}
	
	public void addListener(SensorPairListener listener) {
		// Does not allow multiple of the same SensorListener
		// HashMap and HashSet are deprecated and as of yet unoptimized, so that can't be used at the moment
		if (hasListener(listener)) {
				return;
		}

		spl.add(listener);
	}
	
	public void removeListener(SensorPairListener senin) {
		spl.remove(senin);
	}
	
	public boolean hasListener(SensorPairListener sensor) {
		return spl.contains(sensor);
	}
}
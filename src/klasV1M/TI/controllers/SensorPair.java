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
	private static boolean onLine;

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
	
	private List<Integer> list;
	
	private int lSensorCount = 0;
	private int rSensorCount = 0;
	private int lLast = 0;
	private int rLast = 0;
	private long lLastTime = 0;
	private long lPrevTime = 0;
	private long rLastTime = 0;
	private long rPrevTime = 0;
	
	private List<SensorPairListener> spl;

	
	//private HashMap<Long, Integer> log = new HashMap<Long, Integer>();

	public SensorPair() {
		Globals.MLS.addListener(this);
		//Globals.MCS.addListener(this);
		
		spl = new ArrayList<SensorPairListener>();
		list = new ArrayList<Integer>();
		
		lLast = Globals.MLS.getLightValue();
		left = lLast <= Globals.BlackThreshold;
		//lPrevTime = lLastTime = System.currentTimeMillis();
		//right = Globals.MCS.getLightValue() <= Globals.BlackThreshold;
		//rPrevTime = rLastTime = System.currentTimeMillis();
		
		relativePosition = LINE_UNKNOWN;//left && right ? LINE_MIDDLE : left ? LINE_LEFT : right ? LINE_RIGHT : LINE_UNKNOWN; 
		
		//determineLine();
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
			//determineLine();
			
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
		int temp = LINE_UNKNOWN;
		if (left && right) {
			temp = LINE_MIDDLE;
		} else if (left) {
			temp = LINE_LEFT;
		} else if (right) {
			temp = LINE_RIGHT;
		}
		list.add(temp); // remove list?
		
		//RConsole.println(left && right ? "Middle" : left ? "Left" : right ? "Right" : "Unknown");
		
		if (!found && temp != LINE_UNKNOWN) {
			found = true;
		}
		
		lineRelative = temp;
		
		if (found && temp != LINE_UNKNOWN) {
			for (SensorPairListener s : spl) {
				RConsole.println(left && right ? "Middle" : left ? "Left" : right ? "Right" : "Unknown");
				s.stateChanged(oldVal, temp);
			}
		}
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
		
		if (s.equals(Globals.MLS)) {
			int curHeading = LINE_UNKNOWN;
			if (newVal > 40 && newVal < 60) {
				// on line, go straight
				curHeading = LINE_MIDDLE;
			} else if (newVal < 40 && newVal < oldVal) {
				// darker
				curHeading = LINE_LEFT;
			} else if (newVal > 60 && newVal > oldVal) {
				// lighter
				curHeading = LINE_RIGHT;
			}
			Globals.diffPilot.steer(newVal - 50);
			for (SensorPairListener sen : spl) {
					//sen.stateChanged(LINE_UNKNOWN, curHeading);
			}
		}
	}

	@Override
	public void stateNotification(UpdatingSensor s, float value, float rawValue) {
		// TODO Auto-generated method stub
		// Ignore?
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
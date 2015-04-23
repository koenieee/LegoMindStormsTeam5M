package klasV1M.TI;


import klasV1M.TI.sensoren.MyLightSensor;
import klasV1M.TI.sensoren.MyUltraSonicSensor;
import klasV1M.TI.sensoren.SensorListener;
import klasV1M.TI.sensoren.UpdatingSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.robotics.Tachometer;
import lejos.robotics.navigation.DifferentialPilot;

/**
TODO
 * 
 * @author Remco, Koen, & Medzo
 * @version 2.0.0.0
 */
public class SearchLineController implements Runnable, SensorListener {

	private DifferentialPilot diffPilot; // Used for advanced maneuvers

	/**
	 * The {@link Thread} the method {@link #run()} will use.
	 */
	private Thread t;

	boolean lost = false; 
	/**
	 * The left {@link NXTRegulatedMotor}.
	 */
	private NXTRegulatedMotor mLeft = Motor.C;
	/**
	 * The right {@link NXTRegulatedMotor}
	 */
	private NXTRegulatedMotor mRight = Motor.A;
	/**
	 * The amount of rotations measured by the {@link Tachometer} of
	 * {@link #mLeft} when the line was lost.
	 */
	private int leftLastTachoCount;
	/**
	 * The amount of rotations measured by the {@link Tachometer} of
	 * {@link #mRight} when the line was lost.
	 */
	private int rightLastTachoCount;

	/**
	 * The threshold combined with {@link #leftLastTachoCount} or
	 * {@link #rightLastTachoCount} that needs to be exceeded by the current
	 * amount of rotations by a {@link Tachometer}.
	 */
	private int tachoCountThreshold = 360 * 5;
	private DriveController dc;
	
	
	public SearchLineController(DifferentialPilot dp,NXTRegulatedMotor mLeft,NXTRegulatedMotor mRight, DriveController drvl) {
		diffPilot = dp;
		dc = drvl;
	}

	public void stateChanged(UpdatingSensor s, float oldVal, float newVal) {
		// Light Sensor
		if (s instanceof MyLightSensor) {
			/* instanceof could be replaces by .equals()
			 * if sensors are fields and parameters for constructor
			 */ 
			if(newVal > 60){
				if (leftLastTachoCount + tachoCountThreshold < mLeft.getTachoCount() ||
					rightLastTachoCount + tachoCountThreshold < mRight.getTachoCount()) {
					System.out.println("lijn kwijt");
					if(newVal > 60){
						dc.suspend();						
						this.start();
						System.out.println("reageert");
					}
							
				}
			}
			if(newVal < 40){
				this.stop();
			}
		}
	}
	
	public void setIsLost(boolean il){
		lost = il;
	}
	public boolean getIsLost(){
		return lost;
	}
	
	/**
	 * Starts the {@link Thread} of {@link #t} if it doesn't already exist.
	 */
	public void start() {
		if (t == null) {
			t = new Thread(this);
			t.start();
		}
	}

	/**
	 * Stops the {@link Thread} of {@link #t} if it already exists.
	 */
	public void stop() {
		if (t != null) {
			t.interrupt();
			t = null;
		}
	}

	/**
	 * Corrects overshooting by reversing the direction it is traveling, when a
	 * certain threshold is exceeded.
	 */
	@Override
	public void run() {
		diffPilot.arcForward(45);
	}
}
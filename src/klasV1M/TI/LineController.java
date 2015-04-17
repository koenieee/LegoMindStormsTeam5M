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
 * Avoids obstacles, reacting to input from the {@link MyLightSensor} and
 * {@link MyUltraSonicSensor}. The maximum width of the obstacle is
 * {@value #obstacleWidth} centimeter.
 * 
 * @author Remco, Koen, & Medzo
 * @version 1.0.0.0
 */
public class LineController implements Runnable, SensorListener {

	/**
	 * The left {@link NXTRegulatedMotor}.
	 */
	private NXTRegulatedMotor mLeft = Motor.C;
	/**
	 * The right {@link NXTRegulatedMotor}
	 */
	private NXTRegulatedMotor mRight = Motor.A;

	/**
	 * The {@link DifferentialPilot} used for advanced maneuvers.
	 */
	private DifferentialPilot diffPilot; // Used for advanced maneuvers

	/**
	 * The track width in centimeters from the robot, measured from the center
	 * of the left wheel to the center of the right wheel. <br>
	 * Used by {@link #diffPilot} for accurate driving.
	 */
	private double trackWidth = 13;
	/**
	 * The last known heading of the robot. Should be between -100 and 100.
	 */
	private double heading;
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
	private int tachoCountThreshold = 360 * 2;

	/**
	 * The {@link Thread} the method {@link #run()} will use.
	 */
	private Thread t;

	/**
	 * Initializes the {@link #diffPilot} and starts moving forward. <br>
	 * Also registers itself at the {@link MyLightSensor} and
	 * {@link MyUltraSonicSensor}.
	 */
	boolean lost = false; 
	public LineController(DifferentialPilot dp) {
		diffPilot = dp;
	}

	public void stateChanged(UpdatingSensor s, float oldVal, float newVal) {
		// Light Sensor
		if (s instanceof MyLightSensor) {
			/* instanceof could be replaces by .equals()
			 * if sensors are fields and parameters for constructor
			 */ 
			if(newVal > 40 && lost == true){
					
				this.start();
				
			}
				else{
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
	//	while (!Thread.interrupted()) {
		//	if (leftLastTachoCount + tachoCountThreshold < mLeft.getTachoCount() ||
		//			rightLastTachoCount + tachoCountThreshold < mRight.getTachoCount()) {
		//		diffPilot.steer(-heading); // reverse current heading
		//	}
		//}
	}
}
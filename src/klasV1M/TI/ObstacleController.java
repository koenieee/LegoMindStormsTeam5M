package klasV1M.TI;

import klasV1M.TI.sensoren.MyLightSensor;
import klasV1M.TI.sensoren.MyUltraSonicSensor;
import klasV1M.TI.sensoren.SensorListener;
import klasV1M.TI.sensoren.UpdatingSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.robotics.navigation.DifferentialPilot;

/**
 * Avoids obstacles, reacting to input from the {@link MyLightSensor} and
 * {@link MyUltraSonicSensor}. The maximum width of the obstacle is
 * {@value #obstacleWidth} centimeter.
 * 
 * @author Remco, Koen, & Medzo
 * @version 1.0.0.0
 */
public class ObstacleController implements Runnable, SensorListener {
	
	/**
	 * The left {@link NXTRegulatedMotor}.
	 */
	private NXTRegulatedMotor mLeft = Motor.C;
	/**
	 * The right {@link NXTRegulatedMotor}
	 */
	private NXTRegulatedMotor mRight = Motor.A;

	private DifferentialPilot diffPilot; // Used for advanced maneuvers
	/**
	 * The trackwidth from the robot, measured from the center of the left wheel to the center of the right wheel.
	 * <br>Used by diffPilot for accurate driving.
	 */
	private double trackWidth = 13;

	/**
	 * The maximum width of an obstacle that the robot can avoid.
	 */
	private static final int obstacleWidth = 20;
	
	private double heading;
	private int leftLastTachoCount;
	private int rightLastTachoCount;
	/**
	 * The threshold
	 */
	private int tachoCountThreshold = 360 * 2;
	private Thread t;

	/**
	 * Initializes the {@link #diffPilot} and starts moving forward.
	 * <br>Also registers itself at the {@link MyLightSensor} and {@link MyUltraSonicSensor}.
	 */
	public ObstacleController() {
		/*
		 * Motor.A is the right motor
		 * Motor.C is the left motor
		 */
		diffPilot = new DifferentialPilot(DifferentialPilot.WHEEL_SIZE_NXT2,
				trackWidth, mLeft, mRight);
		// Set speed to 1 rotation/second
		diffPilot.setTravelSpeed(DifferentialPilot.WHEEL_SIZE_NXT2);
		// Start moving forward
		diffPilot.forward();
		diffPilot.setRotateSpeed(30);
		// Register listeners
		MyLightSensor.getInstance().addListener(this);
		MyUltraSonicSensor.getInstance().addListener(this);

	}

	public void stateChanged(UpdatingSensor s, float oldVal, float newVal) {
		// Light Sensor
		if (s.equals(MyLightSensor.getInstance())) {
			System.out.println("Newval: " + newVal);
			if (newVal > 90) {
				// lost the line
				leftLastTachoCount = mLeft.getTachoCount();
				rightLastTachoCount = mRight.getTachoCount();
				start();
			} else {
				// Steers between -50 (left) and +50 (right) to adjust direction
				stop();
				heading = (newVal - 50) * 2;
				diffPilot.steer(heading);
			}
		}
		// Ultrasonic Sensor
		if (s.equals(MyUltraSonicSensor.getInstance())) {
			if (newVal <= obstacleWidth) { // if object is in 30cm of us.
			
				diffPilot.rotate(90);
				diffPilot.travel(obstacleWidth + 5);
				diffPilot.rotate(-90);
				diffPilot.travel(obstacleWidth + 30);
				
				diffPilot.rotate(-90);
			
				diffPilot.travel(obstacleWidth);
				diffPilot.rotate(90);
				diffPilot.travel(3);
			}

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
		}
	}

	/**
	 * Corrects overshooting by reversing the direction it is traveling
	 */
	@Override
	public void run() {
		while (!Thread.interrupted()) {
			if (leftLastTachoCount + tachoCountThreshold < mLeft.getTachoCount() ||
					rightLastTachoCount + tachoCountThreshold < mRight.getTachoCount()) {
				diffPilot.steer(-heading);
			}
		}
	}
}
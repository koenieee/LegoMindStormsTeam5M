package klasV1M.TI;

import klasV1M.TI.sensoren.MyLightSensor;
import klasV1M.TI.sensoren.MyUltraSonicSensor;
import klasV1M.TI.sensoren.SensorListener;
import klasV1M.TI.sensoren.UpdatingSensor;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;

/**
 * Avoids obstacles, reacting to input from the {@link MyLightSensor} and
 * {@link MyUltraSonicSensor}. The maximum width of the obstacle is
 * {@value #obstacleWidth} centimeter.
 * 
 * @author Remco, Koen, & Medzo
 * @version 1.0.0.0
 */
public class ObstacleController implements SensorListener {

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
	
	/**
	 * <code>true</code> if the robot is busy avoiding an object, <code>false</code> otherwise.
	 */
	private boolean avoidingObject;

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
				trackWidth, Motor.C, Motor.A);
		// Set speed to 1 rotation/second
		diffPilot.setTravelSpeed(DifferentialPilot.WHEEL_SIZE_NXT2);
		// Start moving forward
		diffPilot.forward();
		// Register listeners
		MyUltraSonicSensor.getInstance().addListener(this);
		MyLightSensor.getInstance().addListener(this);
	}

	public void stateChanged(UpdatingSensor s, float oldVal, float newVal) {
		// Ultrasonic Sensor
		if (s.equals(MyUltraSonicSensor.getInstance())) {
			if (newVal <= obstacleWidth || avoidingObject) { // if object is in 30cm of us.
				avoidingObject = true;
				diffPilot.rotate(90);
				diffPilot.travel(obstacleWidth + 5);
				diffPilot.rotate(-90);
				diffPilot.travel(obstacleWidth + 30);
				
				diffPilot.rotate(-90);
			
				diffPilot.travel(obstacleWidth + 5);
				diffPilot.rotate(90);
				avoidingObject = false;
			}

		}
		// Light Sensor
		if (s.equals(MyLightSensor.getInstance()) && !avoidingObject) {
			// Steers between -50 (left) and +50 (right) to adjust direction
			diffPilot.steer(newVal - 50);
		}
	}
}
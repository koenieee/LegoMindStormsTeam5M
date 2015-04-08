package klasV1M.TI;

import klasV1M.TI.sensoren.MyLightSensor;
import klasV1M.TI.sensoren.MyUltraSonicSensor;
import klasV1M.TI.sensoren.SensorListener;
import klasV1M.TI.sensoren.UpdatingSensor;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;

/**
 * The ObstacleController drives the wheels and decides if there is a obstacle in front. The max width of the obstacle is {@value #obstacleWidth} cm.
 * @author Remco, Koen, & Medzo
 * @version 1.0.0.0
 */
public class ObstacleController implements SensorListener {

	private DifferentialPilot diffPilot;
	private double trackWidth = 13;
	
	/**
	 * The maximum width of the obstacle that the robot can avoid.  
	 */
	private static final int obstacleWidth = 20;
	private boolean avoidingObject;

	public ObstacleController() {
		// Motor.A is the right motor
		// Motor.C is the left motor
		diffPilot = new DifferentialPilot(DifferentialPilot.WHEEL_SIZE_NXT2,
				trackWidth, Motor.C, Motor.A);
		// Set speed to 1 rot/sec
		diffPilot.setTravelSpeed(DifferentialPilot.WHEEL_SIZE_NXT2);
		// Move forward
		diffPilot.forward();
		// Register listeners
		MyUltraSonicSensor.getInstance().addListener(this);
		MyLightSensor.getInstance().addListener(this);
	}

	@Override
	public void stateChanged(UpdatingSensor s, float oldVal, float newVal) {
		// Ultrasonic Sensor
		if (s.equals(MyUltraSonicSensor.getInstance())) {
			if (newVal < obstacleWidth + 5) { // if object is in 30cm of us.
				avoidingObject = true;
			} else if (avoidingObject) {
				diffPilot.stop();
				diffPilot.rotate(90);
				diffPilot.travel(obstacleWidth);
				diffPilot.rotate(-90);
				diffPilot.travel(obstacleWidth + 20);
				
				diffPilot.rotate(-90);
				diffPilot.travel(10);
				avoidingObject = false;
				diffPilot.travel(obstacleWidth, true);
				diffPilot.rotate(90, true);
				
			}
		}
		// Light Sensor
		if (s.equals(MyLightSensor.getInstance()) && !avoidingObject) {
			diffPilot.steer(newVal - 50);
		}
	}
}
package klasV1M.TI;

import klasV1M.TI.sensoren.MyLightSensor;
import klasV1M.TI.sensoren.MyUltraSonicSensor;
import klasV1M.TI.sensoren.SensorListener;
import klasV1M.TI.sensoren.UpdatingSensor;

/**
 * 
 * @author Remco, Koen, & Medzo
 * @version 1.0.0.0
 */
public class ObstacleController implements SensorListener {

	private boolean avoidingObject = false;

	public ObstacleController() {
		// Motor.A is the right motor
		// Motor.C is the left motor
		Driver.getInstance().getDifferentialPilot().forward();
		// Register listeners
		MyUltraSonicSensor.getInstance().addListener(this);
		MyLightSensor.getInstance().addListener(this);
	}

	@Override
	public void stateChanged(UpdatingSensor s, float oldVal, float newVal) {
		// Ultrasonic Sensor
		if (s.equals(MyUltraSonicSensor.getInstance())) {
			if (newVal < 30) { // if object is in 30cm of us.
				avoidingObject = true;
			} else if (avoidingObject) {
				Driver.getInstance().stop();
				Driver.getInstance().getDifferentialPilot().rotate(90);
				Driver.getInstance().getDifferentialPilot().travel(30);
				System.out.println("Starting...");
				Driver.getInstance().start();
				System.out.println("False");
				avoidingObject = false;
			}
		}
		// Light Sensor
		if (s.equals(MyLightSensor.getInstance()) && !avoidingObject) {
			System.out.println("#");
			Driver.getInstance().stopThread();
			Driver.getInstance().getDifferentialPilot().steer((newVal - 50) * 2);
		}
	}
}
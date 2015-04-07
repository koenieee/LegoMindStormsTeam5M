package klasV1M.TI.controllers;

import klasV1M.TI.Globals;
import klasV1M.TI.sensoren.MyLightSensor;
import klasV1M.TI.sensoren.MyUltraSonicSensor;
import klasV1M.TI.sensoren.SensorListener;
import klasV1M.TI.sensoren.UpdatingSensor;
import lejos.robotics.navigation.DifferentialPilot;

/**
 * 
 * @author Remco
 *
 */
public class ObstacleController implements SensorListener {

	private Thread t = null;
	
	private boolean lost;
	private boolean found;
	private boolean onLine;
	
	private boolean objectDetected;
	private boolean evadingObject;
	private float heading;
	private int headingIncrement = 5;
	private int standardHeading = 25;
	
	public static final int LINE_UNKNOWN = -1;
	public static final int LINE_MIDDLE = 0;
	public static final int LINE_LEFT = 1;
	public static final int LINE_RIGHT = 2;
	
	private int relativePosition = 0;

	public ObstacleController() {
		Globals.diffPilot.setTravelSpeed(DifferentialPilot.WHEEL_SIZE_NXT2);//RotateSpeed(1);//360);//Globals.LowSpeed);
		Globals.diffPilot.forward();
		MyUltraSonicSensor.getInstance().addListener(this);
		MyLightSensor.getInstance().addListener(this);
	}

	@Override
	public void stateChanged(UpdatingSensor s, float oldVal, float newVal) {
		// Ultrasonic Sensor
		if (s.equals(MyUltraSonicSensor.getInstance())) {
			if (newVal < 30) { //if object is in 30cm of us.
				//Globals.diffPilot.rotate(30);
				evadingObject = true;
			} else {
				
				if(evadingObject){
					
					Globals.diffPilot.stop();
					Globals.diffPilot.rotate(90);
					Globals.diffPilot.travel(30);
					Globals.diffPilot.rotate(-90);
					Globals.diffPilot.travel(50);
					Globals.diffPilot.rotate(-90);
					Globals.diffPilot.travel(30);
					Globals.diffPilot.rotate(90);
					evadingObject = false;
					
					//Globals.diffPilot.arcForward(-40);
				}
			}
		}
		if (s.equals(MyLightSensor.getInstance()) && !evadingObject) {
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
		}
	}

	@Override
	public void stateNotification(UpdatingSensor s, float value, float rawValue) {
		// TODO Auto-generated method stub
		// Ignore
	}
}

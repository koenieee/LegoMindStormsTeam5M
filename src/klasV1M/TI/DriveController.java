package klasV1M.TI;

import klasV1M.TI.sensoren.MyLightSensor;
import klasV1M.TI.sensoren.MyUltraSonicSensor;
import klasV1M.TI.sensoren.SensorListener;
import klasV1M.TI.sensoren.UpdatingSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.robotics.Tachometer;
import lejos.robotics.navigation.DifferentialPilot;

/**
TODO
 * 
 * @author Remco, Koen, & Medzo
 * @version 2.0.0.0
 */
public class DriveController implements SensorListener {

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
	
	private ObstacleController oc;
	private LineController slc;

	/**
	 * Initializes the {@link #diffPilot} and starts moving forward. <br>
	 * Also registers itself at the {@link MyLightSensor} 
	 */
	
	public DriveController() {
		/*
		 * Motor.A is the right motor Motor.C is the left motor
		 */
		diffPilot = new DifferentialPilot(DifferentialPilot.WHEEL_SIZE_NXT2,
				trackWidth, mLeft, mRight);
		slc = new LineController(diffPilot);
		
		oc = new ObstacleController(diffPilot); // initializes and starts the obstacle controller
		MyUltraSonicSensor muss = new MyUltraSonicSensor(SensorPort.S4);
		MyLightSensor mls = new MyLightSensor(SensorPort.S2);
		// Register listeners
		muss.addListener(oc);
		mls.addListener(slc);
		// Set speed to 1 rotation/second
		diffPilot.setTravelSpeed(DifferentialPilot.WHEEL_SIZE_NXT2 + 2);
		// Start moving forward
		diffPilot.forward();
		diffPilot.setRotateSpeed(30);
	}

	public void stateChanged(UpdatingSensor s, float oldVal, float newVal) {
		// Light Sensor
		if (s instanceof MyLightSensor) {
			/* instanceof could be replaces by .equals()
			 * if sensors are fields and parameters for constructor
			 */ 
			System.out.println("Newval: " + newVal);
			if (oc.getIsRunning() && oc.isAvoiding){
				if(newVal < 40){
					Sound.setVolume(Sound.VOL_MAX);
					Sound.beep();
					diffPilot.travel(3);
					diffPilot.rotate(50);
					diffPilot.forward();
					oc.isAvoiding = false;
					oc.setIsRunning(false);
					oc.stop();
					slc.setIsLost(false);
				}
			}
			else if(!oc.getIsRunning() && !oc.isAvoiding && !slc.getIsLost()) {
				/* Steers between -100 (left) and +100 (right) to adjust direction,
				 * since newVal is always between 0 and 100. */
				heading = (newVal - 50) * 2;
				diffPilot.steer(heading);
			}
		}
	}
}
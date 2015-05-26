package klasV1M.TI;

import klasV1M.TI.sensoren.MyLightSensor;
import klasV1M.TI.sensoren.MyUltraSonicSensor;
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;

/**
 * The Entrypoint for the application
 * 
 * @author Remco, Koen, & Medzo
 * @version 2.0.0.0
 */
public class Main {

	/**
	 * Entrypoint for the application, also creates and initializes the other
	 * Objects and Controllers.
	 * 
	 * @param args
	 *            Arguments that can be passed into the application. Current
	 *            implementation ignores these.
	 */
	public static void main(String[] args) {
		// Sensors
		MyLightSensor mls = new MyLightSensor(SensorPort.S2);
		MyUltraSonicSensor muss = new MyUltraSonicSensor(SensorPort.S4);

		// Motors needed by the DifferentialPilot
		NXTRegulatedMotor mLeft = Motor.C;
		NXTRegulatedMotor mRight = Motor.A;

		double trackWidth = 13; // The track-width needed by the
								// DifferentialPilot

		DifferentialPilot diffPilot = new DifferentialPilot(
				DifferentialPilot.WHEEL_SIZE_NXT2, trackWidth, mLeft, mRight);

		// RConsole.open(); // For debugging purposes
		System.out.println("Press button to start...");

		Button.waitForAnyPress();
		CalibrationController c = new CalibrationController(mls);

		c.calibrateLightSensor(); // calibrate lightSensor
		System.out
				.println("Place lightsensor above the middle of the black line.");
		Button.waitForAnyPress();

		DriveController dc = new DriveController(diffPilot);
		mls.addListener(dc); // Register the DriveController to the
								// MyLightSensor

		SearchLineController slc = new SearchLineController(diffPilot, dc);
		mls.addListener(slc); // Register the SearchLineController to the
								// MyLightSensor

		ObstacleController obc = new ObstacleController(diffPilot, dc);
		muss.addListener(obc); // Register the SearchLineController to the
								// MyUltraSonicListener

		while (true) {
			Thread.yield();
		}
	}
}
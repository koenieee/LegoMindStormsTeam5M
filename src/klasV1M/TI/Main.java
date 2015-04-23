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
	 * Entrypoint for the application
	 * @param args Arguments that can be passed into the application. Current implementation ignores these.
	 */
	public static void main(String[] args) {
		
		MyLightSensor mls = new MyLightSensor(SensorPort.S2);
		MyUltraSonicSensor muss = new MyUltraSonicSensor(SensorPort.S4);
		NXTRegulatedMotor mLeft = Motor.C;
		NXTRegulatedMotor mRight = Motor.A;
		double trackWidth = 13;
		DifferentialPilot diffPilot = new DifferentialPilot(DifferentialPilot.WHEEL_SIZE_NXT2, trackWidth, mLeft, mRight);
		
		//RConsole.open();
		System.out.println("Press button to start...");

		
		Button.waitForAnyPress();
		CalibrationController c = new CalibrationController(mls);
		
		c.calibrateLightSensor(); //calibrate lightSensor 
		System.out.println("Place lightsensor above the middle of the black line.");
		Button.waitForAnyPress();

		DriveController dc = new DriveController(diffPilot);
		mls.addListener(dc);
		
		SearchLineController slc = new SearchLineController(diffPilot, mLeft, mRight, dc);
		mls.addListener(slc);
		
		ObstacleController obc = new ObstacleController(diffPilot, dc);
		muss.addListener(obc);
		
		while (true) {
			Thread.yield();
		}
	}
}
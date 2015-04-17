package klasV1M.TI;

import klasV1M.TI.sensoren.MyLightSensor;
import klasV1M.TI.sensoren.MyUltraSonicSensor;
import lejos.nxt.Button;
import lejos.nxt.Motor;
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
		//RConsole.open();
		System.out.println("Press button to start...");
		
		MyLightSensor mls = new MyLightSensor(SensorPort.S2);
		
		Button.waitForAnyPress();
		CalibrationController c = new CalibrationController(mls);
		c.configureLightSensors(); //calibrate lightSensor 
		System.out.println("Place lightsensor above the middle of the black line.");
		Button.waitForAnyPress();
		DriveController dc = new DriveController();

		mls.addListener(dc);
		
		while (true) {
			Thread.yield();
		}
	}
}
package klasV1M.TI;

import klasV1M.TI.sensoren.MyColorSensor;
import klasV1M.TI.sensoren.MyLightSensor;
import klasV1M.TI.sensoren.MyUltraSonicSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;

/**
 * Provides global access to the sensors and motors.
 * TODO:
 * - Test if synchronized access is needed/change class into a Singleton? 
 * @author Remco
 *
 */
public class Globals {
	/**
	 * The {@link MyColorSensor} mounted on the front-right
	 */
	public static MyColorSensor MCS = new MyColorSensor(SensorPort.S1);
	/**
	 * The {@link MyLightSensor} mounted on the front-left
	 */
	public static MyLightSensor MLS = new MyLightSensor(SensorPort.S2);
	/**
	 * The {@link MyUltraSonicSensor} mounted on the front
	 */
	public static MyUltraSonicSensor MUS = new MyUltraSonicSensor(SensorPort.S3);
	
	/**
	 * The right {@link NXTRegulatedMotor}
	 */
	public static NXTRegulatedMotor mRight = Motor.A;
	/**
	 * The middle {@link NXTRegulatedMotor}
	 */
	public static NXTRegulatedMotor mMiddle = Motor.B;
	/**
	 * The right {@link NXTRegulatedMotor}
	 */
	public static NXTRegulatedMotor mLeft = Motor.C;
}

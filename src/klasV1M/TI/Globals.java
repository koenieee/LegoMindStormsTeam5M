package klasV1M.TI;

import klasV1M.TI.sensoren.MyColorSensor;
import klasV1M.TI.sensoren.MyLightSensor;
import klasV1M.TI.sensoren.MyUltraSonicSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;

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
	 * The right motor
	 */
	public static NXTRegulatedMotor mRight = Motor.A;
	/**
	 * The middle motor
	 */
	public static NXTRegulatedMotor mMiddle = Motor.B;
	/**
	 * The right motor
	 */
	public static NXTRegulatedMotor mLeft = Motor.C;
}

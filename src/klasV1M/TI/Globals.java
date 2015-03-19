package klasV1M.TI;

import klasV1M.TI.sensoren.MyColorSensor;
import klasV1M.TI.sensoren.MyLightSensor;
import klasV1M.TI.sensoren.MyUltraSonicSensor;
import lejos.nxt.SensorPort;

public class Globals {
	public static MyColorSensor MCS = new MyColorSensor(SensorPort.S1);
	public static MyLightSensor MLS = new MyLightSensor(SensorPort.S2);
	public static MyUltraSonicSensor MUS = new MyUltraSonicSensor(SensorPort.S3);
}

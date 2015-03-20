package klasV1M.TI.controllers;

import klasV1M.TI.Globals;
import klasV1M.TI.sensoren.SensorHandler;
import klasV1M.TI.sensoren.SensorListener;
import klasV1M.TI.sensoren.UpdatingSensor;
import lejos.nxt.LCD;

/**
 * Example TestController to test the working and implementation of Sensors and
 * Sensor Listener Pattern
 * 
 * @author koen
 *
 */
public class TestController implements SensorListener {
	SensorHandler scc;

	/**
	 * TestController can be called with an integer to know how many times the
	 * SensorHandler must be called
	 * 
	 * @param period
	 *            how much second the SensorHandler can call the sensor for
	 *            updates
	 */
	public TestController(int period) {
		SensorHandler.PERIOD = period;
		Globals.MLS.addListener(this);
		Globals.MCS.addListener(this);
	}

	/**
	 * StateChanged is used for call this method from the Sensor if there was
	 * sensor reading changed.
	 * 
	 * @param s
	 *            UpdatingSensor the Sensor that has called stateChanged
	 * @param oldVal
	 *            The old value from the Sensor
	 * @param newVal
	 *            The new value that the Sensor has detected
	 */
	
	@Override
	public void stateChanged(UpdatingSensor s, float oldVal, float newVal) {
		boolean whiteC = true;
		boolean whiteL = true;
		if (s.equals(Globals.MLS)) {
			// LCD.clear();
			String text = "";
			if(newVal < 50){
				
				text = "Black";
				Globals.mLeft.stop();
				Globals.mRight.forward();
				whiteL=false;
			}
			else
			{
				text = "White";
				whiteL=true;
			}
			
			LCD.drawString(("LightSensor: " + text),
					0, 0);
		}
		
		if (s.equals(Globals.MCS)) { //
			
			String text = "";
			if(newVal < 50){
				text = "Black";

				Globals.mLeft.forward();
				Globals.mRight.stop();
				
				whiteC=false;
			}
			else
			{
				text = "White";
				whiteC=true;
			}
			
			LCD.drawString(("ColorSensor: " + text),
					0, 3);
		}
		
		/*if(whiteL && whiteC){
			Globals.mLeft.stop();
			Globals.mRight.stop();
		}*/

	}

}
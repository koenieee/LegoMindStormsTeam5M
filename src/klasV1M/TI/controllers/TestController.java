package klasV1M.TI.controllers;

import klasV1M.TI.Globals;
import klasV1M.TI.sensoren.SensorHandler;
import klasV1M.TI.sensoren.SensorListener;
import klasV1M.TI.sensoren.UpdatingSensor;
import lejos.nxt.LCD;
import lejos.nxt.comm.RConsole;

/**
 * Example TestController to test the working and implementation of Sensors and
 * Sensor Listener Pattern
 * 
 * @author koen
 *
 */
public class TestController implements SensorListener {
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
		Globals.mLeft.setAcceleration(180);
		Globals.mRight.setAcceleration(180);
		Globals.mLeft.setSpeed(250);
		Globals.mRight.setSpeed(250);
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
		if (s.equals(Globals.MLS)) { //lightsensor
			// LCD.clear();
			String text = "";
			if(newVal < 50){ //lightsensor zit op lijn
				
				text = "Black";
				Globals.mLeft.stop();
				Globals.mRight.forward();
			}
			else
			{
				text = "White";
			}
			
			
		}
		
		if (s.equals(Globals.MCS)) { //colorsensor
			
			String text = "";
			if(newVal < 50){ //kleuren sensor zit op de lijn
				text = "Black";
				Globals.mLeft.forward();
				Globals.mRight.stop();
				//Globals.mLeft.setSpeed(200);
				//Globals.mRight.setSpeed(4);
				
				
				
			}
			else
			{
				text = "White";
				
			}
			
			RConsole.println("ColorSensr: " + newVal);
		}
		
		/*if(whiteL && whiteC){
			Globals.mLeft.stop();
			Globals.mRight.stop();
		}*/

	}

	@Override
	public void stateNotification(UpdatingSensor s, float value, float rawValue) {
		// TODO Auto-generated method stub
		// Ignore
	}

}
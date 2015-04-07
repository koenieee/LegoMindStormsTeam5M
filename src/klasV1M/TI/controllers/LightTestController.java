package klasV1M.TI.controllers;

import klasV1M.TI.Globals;
import klasV1M.TI.sensoren.SensorListener;
import klasV1M.TI.sensoren.UpdatingSensor;
import lejos.nxt.comm.RConsole;

public class LightTestController implements SensorListener {
	public LightTestController() {
		Globals.MLS.addListener(this);
	}

	@Override
	public void stateChanged(UpdatingSensor s, float oldVal, float newVal) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stateNotification(UpdatingSensor s, float value, float rawValue) {
		// TODO Auto-generated method stub
		if (s.equals(Globals.MLS)) {
			RConsole.println("L: " + value);
		}
	}
}

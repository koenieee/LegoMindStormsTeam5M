package klasV1M.TI.sensoren;

public interface SensorListener {
	public void stateChanged(UpdatingSensor s, float oldVal, float newVal);
}
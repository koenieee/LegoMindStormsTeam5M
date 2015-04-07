package klasV1M.TI.sensoren;

public interface UpdatingSensor {
	public void addListener(SensorListener s); // add?
	public void removeListener(SensorListener s); // add?
	public boolean hasListener(SensorListener s); // add?
	public void updateState();
}

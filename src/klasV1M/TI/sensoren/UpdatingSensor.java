package klasV1M.TI.sensoren;

/**
 * Interface to create a generic Sensor Handler system. This can be used as implementation to listen to the Sensors.
 * @author Remco, Koen, & Medzo
 * @version 1.0.0.0
 */
public interface UpdatingSensor {
	/**
	 * 
	 * @param s The {@link SensorListener} to be added to the {@link SensorHandler} list. 
	 */
	public void addListener(SensorListener s);
	/**
	 * 
	 * @param s  The {@link SensorListener} to be removed from the {@link SensorHandler} list. 
	 */
	public void removeListener(SensorListener s);
	/**
	 * 
	 * @param s The {@link SensorListener} that wants to be checked if it does exists in the list that of {@link SensorHandler} calls. 
	 * @return True; if the {@link SensorListener} exists in the list, False; if the {@link SensorListener} doesn't exists in the list.
	 */
	public boolean hasListener(SensorListener s);
	/**
	 * 
	 */
	public void updateState();
}
package klasV1M.TI.sensoren;

/**
 * Interface to create a generic Sensor Handler system. This can be used as
 * implementation to listen to the Sensors. <br>
 * Implementing classes should keep an internal {@link List} of
 * {@link SensorListener}s.
 * 
 * @author Remco, Koen, & Medzo
 * @version 1.0.0.0
 */
public interface UpdatingSensor {

	/**
	 * Adds/registers a {@link SensorListener} to the internal list of listeners
	 * to notify. <br>
	 * Should use the {@link #hasListener(SensorListener)} method to verify it
	 * does not already exist in the internal list.
	 * 
	 * @param s
	 *            The {@link SensorListener} to be added to the
	 *            {@link SensorHandler} list.
	 */
	public void addListener(SensorListener s);

	/**
	 * Removes/unregisters a {@link SensorListener} from the internal list of
	 * listeners to notify. <br>
	 * Should use the {@link #hasListener(SensorListener)} method to verify it
	 * does exist in the internal list.
	 * 
	 * @param s
	 *            The {@link SensorListener} to be removed from the
	 *            {@link SensorHandler} list.
	 */
	public void removeListener(SensorListener s);

	/**
	 * Checks if a {@link SensorListener} already exists in the internal list.
	 * 
	 * @param s
	 *            The {@link SensorListener} that wants to be checked if it does
	 *            exists in the list that of {@link SensorHandler} calls.
	 * @return True; if the {@link SensorListener} exists in the list, False; if
	 *         the {@link SensorListener} doesn't exists in the list.
	 */
	public boolean hasListener(SensorListener s);

	/**
	 * Updates the state of an {@link UpdatingSensor}.
	 */
	public void updateState();
}
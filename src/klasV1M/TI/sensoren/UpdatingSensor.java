package klasV1M.TI.sensoren;

/**
 * 
 * @author Remco
 *
 */
public interface UpdatingSensor {
	/**
	 * 
	 * @param s
	 */
	public void addListener(SensorListener s);
	/**
	 * 
	 * @param s
	 */
	public void removeListener(SensorListener s);
	/**
	 * 
	 * @param s
	 * @return
	 */
	public boolean hasListener(SensorListener s);
	/**
	 * 
	 */
	public void updateState();
}
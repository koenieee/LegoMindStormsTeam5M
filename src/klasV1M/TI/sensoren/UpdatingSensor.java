package klasV1M.TI.sensoren;

/**
 * 
 * @author Remco, Koen, & Medzo
 * @version 1.0.0.0
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
package klasV1M.TI.sensoren;

/**
 * Allows a listener to receive notifications from an {@link UpdatingSensor}.
 * <br>Before a listener can receive notifications it needs to register itself at the {@link UpdatingSensor} to listen to.
 * 
 * @author Remco, Koen, & Medzo
 * @version 1.0.0.0
 */
public interface SensorListener {
	/**
	 * Notifies a listener when an {@link UpdatingSensor} measured a change.
	 * 
	 * @param s The {@link UpdatingSensor} that measured the change
	 * @param oldVal The previously measured value
	 * @param newVal The new measured value
	 */
	public void stateChanged(UpdatingSensor s, float oldVal, float newVal);
}
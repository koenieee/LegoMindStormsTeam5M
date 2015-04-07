package klasV1M.TI.sensoren;

/**
 * Allows classes to listen to an {@link UpdatingSensor}
 * @author Remco
 *
 */
public interface SensorListener {
	/**
	 * Notifies a listener when the {@link UpdatingSensor} measured a change.
	 * @param s The {@link UpdatingSensor} that measured the change
	 * @param oldVal The previously measured value
	 * @param newVal The new measured value
	 */
	public void stateChanged(UpdatingSensor s, float oldVal, float newVal);
}
package klasV1M.TI.sensoren;

public interface SensorListener {
	/**
	 * Notifies a listener when the {@link UpdatingSensor} measured a change.
	 * @param s The {@link UpdatingSensor} that measured the change
	 * @param oldVal The previously measured value
	 * @param newVal The new measured value
	 */
	public void stateChanged(UpdatingSensor s, float oldVal, float newVal);
	/**
	 * Notifies a listener about the current state at a regular interval.
	 * <br><b>NOTE: {@code rawValue} is the same as {@code value} if returned from the {@link MyUltraSonicSensor}</b>
	 * @param s The {@link UpdatingSensor} notifying the listener
	 * @param value The previously measured value
	 * @param rawValue The new measured value
	 */
	public void stateNotification(UpdatingSensor s, float value, float rawValue);
}

package klasV1M.TI.sensoren;

public interface SensorListener {
	public void stateChanged(UpdatingSensor s, float oldVal, float newVal);
	/**
	 * <b>NOTE: {@code rawValue} is the same as {@code value} if returned from the {@link MyUltraSonicSensor}</b>
	 * @param s
	 * @param value
	 * @param rawValue
	 */
	public void stateNotification(UpdatingSensor s, float value, float rawValue);
}

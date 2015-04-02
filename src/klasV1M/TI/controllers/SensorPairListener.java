package klasV1M.TI.controllers;

public interface SensorPairListener {
	public void lineFound(); // remove?
	public void lineLost(); // remove?
	public void stateChanged(int oldState, int newState);
}
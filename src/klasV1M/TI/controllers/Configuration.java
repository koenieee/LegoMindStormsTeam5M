package klasV1M.TI.controllers;

public class Configuration {
	private double radius;
	private double diameter;
	private double circumference;
	private boolean configured;
	
	public synchronized void resetAndConfigureAll() {
		measureWheelRadius();
		configureLightSensors();
		resetSoundSensor();
	}
	
	public synchronized void measureWheelRadius() {
		
	}
	
	public synchronized void configureLightSensors() {
		
	}
	
	public synchronized void resetSoundSensor() {
		
	}
	
	public synchronized double getRadius() {
		return radius;
	}

	public synchronized double getDiameter() {
		return diameter;
	}

	public synchronized double getCircumference() {
		return circumference;
	}

	public synchronized boolean isConfigured() {
		return configured;
	}
}
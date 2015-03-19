package klasV1M.TI.controllers;

import klasV1M.TI.Globals;

/**
 * Configuration class can be used to configure some settings on the Lego Mindstorm Robot
 * @author koen
 *
 */
public class Configuration {
	public boolean calibrated = false;
	
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
	
	
	/** Function to calibrate both the LightSensor and the ColorSensor to work in a range of 0 to 100 
	 * With 0 as most Black
	 * With 100 as most White
	 */
	public synchronized void configureLightSensors() {
		System.out.println("Put on white spot within next five seconds");

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {}

		System.out.println("Calibrating...");
		Globals.MCS.setHigh(Globals.MCS.getRawLightValue());
		Globals.MLS.calibrateHigh();
		
		System.out.println(Globals.MCS.getLightValue() + "\n" + Globals.MCS.getHigh());

		System.out.println("Put on black spot within next five seconds");

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {}

		System.out.println("Calibrating...");
		
		Globals.MCS.setLow(Globals.MCS.getRawLightValue());
		Globals.MLS.calibrateLow();
		
		System.out.println(Globals.MCS.getLightValue() + "\n" + Globals.MCS.getLow());
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {}
		
		calibrated = true;
		System.out.println("Calibrated == true");
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
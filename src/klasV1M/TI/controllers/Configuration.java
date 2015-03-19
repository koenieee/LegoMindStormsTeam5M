package klasV1M.TI.controllers;

import klasV1M.TI.sensoren.MyColorSensor;
import klasV1M.TI.sensoren.MyLightSensor;
import lejos.nxt.SensorPort;


/**
 * Configuration class can be used to configure some settings on the Lego Mindstorm Robot
 * @author koen
 *
 */
public class Configuration {
	public MyColorSensor MCS = new MyColorSensor(SensorPort.S1);
	public MyLightSensor MBS = new MyLightSensor(SensorPort.S2);
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
		MCS.setHigh(MCS.getRawLightValue());
		MBS.calibrateHigh();
		
		System.out.println(MCS.getLightValue() + "\n" + MCS.getHigh());

		System.out.println("Put on black spot within next five seconds");

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {}

		System.out.println("Calibrating...");
		
		MCS.setLow(MCS.getRawLightValue());
		MBS.calibrateLow();
		
		System.out.println(MCS.getLightValue() + "\n" + MCS.getLow());
		
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
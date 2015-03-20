package klasV1M.TI.controllers;

import klasV1M.TI.Globals;
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.TachoMotorPort;
import lejos.robotics.navigation.DifferentialPilot;

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
	
	/**
	 * Resets and configures all components of the NXT.
	 */
	public synchronized void resetAndConfigureAll() {
		measureWheelRadius();
		configureLightSensors();
		resetSoundSensor();
	}
	
	/**
	 * Measures the wheel radius.
	 */
	public synchronized void measureWheelRadius() {
		System.out.println("Please place a solid object about 50 cm away from this robot\nIncomplete method!");
		Button.waitForAnyPress();
		/* INCOMPLETE!! */
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
			Thread.sleep(1000);
		} catch (InterruptedException e) {}
		
		calibrated = true;
	}
	
	public synchronized void resetSoundSensor() {
		
	}
	
	/**
	 * Returns the radius of the wheels.
	 * @return the radius of the wheels
	 */
	public synchronized double getRadius() {
		return radius;
	}

	/**
	 * Returns the diameter of the wheels.
	 * @return the diameter of the wheels
	 */
	public synchronized double getDiameter() {
		return diameter;
	}

	/**
	 * Returns the circumference of the wheels.
	 * @return the circumference of the wheels
	 */
	public synchronized double getCircumference() {
		return circumference;
	}

	/**
	 * Tests if the NXT has been configured.
	 * @return <b>true</b> if configured, <b>false</b> otherwise
	 */
	public synchronized boolean isConfigured() {
		return configured;
	}
}
package klasV1M.TI.controllers;

import klasV1M.TI.Globals;
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.TachoMotorPort;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;

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
		// Force motors off
		Globals.mLeft.stop(true);
		Globals.mMiddle.stop(true);
		Globals.mRight.stop();
		
		System.out.println("Please place a solid object about 50 cm away from this robot\nIncomplete method!");
		while (Globals.MUS.getRange() == 255) {
			Button.waitForAnyPress();
		}
		
		// Slow acceleration to avoid slipping
		Globals.mLeft.setAcceleration(60);
		Globals.mRight.setAcceleration(60);
		Globals.mLeft.setSpeed(360);
		Globals.mRight.setSpeed(360);
		
		int iL = Globals.mLeft.getTachoCount();
		int iR = Globals.mRight.getTachoCount();
		
		float dStart = Globals.MUS.getRange();
		
		Globals.mLeft.forward();
		Globals.mRight.forward();
		System.out.println("Start " + dStart);
		System.out.println("Moving for " + 2 + " seconds...");//(long) ((dStart - 10)/Math.PI * 100));//((dStart - 10) * Math.PI * 1000) + " seconds...");
		Delay.msDelay(2000);
		// DELAY NOT WORKING!!!???
		//try {
		//	Thread.sleep(2000);//((dStart - 10)/Math.PI * 100));//(25000 / dStart));
		//} catch (InterruptedException e) {
		//}
		
		Globals.mLeft.flt(true);
		Globals.mRight.flt();
		
		Delay.msDelay(1000);
		
		System.out.println("Starting measurement...");
		
		float dEnd = Globals.MUS.getRange();
		
		int iLDiff = Globals.mLeft.getTachoCount() - iL;
		int iRDiff = Globals.mRight.getTachoCount() - iR;
		
		//System.out.println("iL " + iL + " | iLD " + iLDiff);
		//System.out.println("iR " + iR + " | iRD " + iRDiff);
		//System.out.println("d " + (dStart - dEnd));
		
		// average rotation
		int iDiff = (iLDiff + iRDiff) / 2;
		
		circumference = (dStart - dEnd) / (iDiff);
		diameter = circumference / Math.PI;
		radius = diameter / 2;
		
		System.out.println("C " + Math.round(circumference) + "\nD " + Math.round(diameter) + "\nR " + Math.round(radius));
		Delay.msDelay(5000);
	}
	
	
	/** Function to calibrate both the {@link MyLightSensor} and the {@link MyColorSensor} to work in a range of 0 to 100 
	 * With 0 as most Black
	 * With 100 as most White
	 */
	public synchronized void configureLightSensors() {
		System.out.println("Place sensors before black line on white spot.");

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {}

		System.out.println("Calibrating...");
		Globals.MCS.setHigh(Globals.MCS.getRawLightValue());
		Globals.MLS.calibrateHigh();
		
		System.out.println(Globals.MCS.getLightValue() + "\n" + Globals.MCS.getHigh());
		/*
		Globals.mLeft.setAcceleration(60);
		Globals.mRight.setAcceleration(60);
		Globals.mLeft.forward();
		Globals.mRight.forward();
		
		try{
			Thread.sleep(700);
		}
		catch (InterruptedException e){}
		Globals.mLeft.stop();
		Globals.mRight.stop();*/
		
		
		System.out.println("Put on black spot within next five seconds");

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {}

		System.out.println("Calibrating...");
		
		Globals.MCS.setLow(Globals.MCS.getRawLightValue());
		Globals.MLS.calibrateLow();
		
		System.out.println(Globals.MCS.getLightValue() + "\n" + Globals.MCS.getLow());
		//Globals.playSong();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {}
		
		calibrated = true;
	}
	
	public synchronized void resetSoundSensor() {
		Globals.mMiddle.resetTachoCount();
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
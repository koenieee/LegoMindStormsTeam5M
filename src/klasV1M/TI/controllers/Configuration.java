package klasV1M.TI.controllers;

import klasV1M.TI.Globals;
import klasV1M.TI.sensoren.SensorListener;
import klasV1M.TI.sensoren.UpdatingSensor;
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.TachoMotorPort;
import lejos.nxt.comm.RConsole;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;

/**
 * Configuration class can be used to configure some settings on the Lego Mindstorm Robot
 * @author koen
 *
 */
public class Configuration implements Runnable, SensorListener {
	public boolean calibrated = false;
	
	private double radius;
	private double diameter;
	private double circumference;
	private boolean configured;
	
	private boolean autoAdjust;
	private float lowest;
	private float lowestAverage;
	private float lowestTotal;
	private int lowestCount;
	private float highest;
	private float highestAverage;
	private float highestTotal;
	private int highestCount;
	
	private Thread t;
	
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
	 * @return <b>true</b> when configuration was succesfull, <b>false</b> otherwise.
	 */
	public synchronized boolean configureLightSensors() {
		System.out.println("Place on white spot in five seconds");

		Button.waitForAnyPress(5000);

		System.out.println("Calibrating white...");
		Globals.MLS.calibrateHigh();
		
		System.out.println("Place on black spot in five seconds");

		Button.waitForAnyPress(5000);

		System.out.println("Calibrating black...");
		Globals.MLS.calibrateLow();
		
		highestCount = 1;
		highestTotal = highest;
		highestAverage = highest;
		lowestCount = 1;
		lowestTotal = lowest;
		lowestAverage = lowest;
		return true;
	}
	
	public synchronized void resetSoundSensor() {
		Globals.mMiddle.flt();
		System.out.println("Please put the sound sensor in the default position");
		Button.waitForAnyPress();
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
	 * 
	 * @return
	 */
	public synchronized boolean isAutoAdjust() {
		return autoAdjust;
	}

	public synchronized void setAutoAdjust(boolean autoAdjust) {
		this.autoAdjust = autoAdjust;
		if (autoAdjust) {
			start();
		} else {
			stop();
		}
	}

	/**
	 * Tests if the NXT has been configured.
	 * @return <b>true</b> if configured, <b>false</b> otherwise
	 */
	public synchronized boolean isConfigured() {
		return configured;
	}

	@Override
	public void run() {
		Globals.MCS.addListener(this);
		Globals.MLS.addListener(this);
		while (!t.interrupted() && autoAdjust) {
			Thread.yield();
		}
		Globals.MCS.removeListener(this);
		Globals.MLS.removeListener(this);
	}
	
	public void start() {
		if (t == null) {
			t = new Thread(this);
			t.start();
		}
	}
	
	public void stop() {
		if (t != null) {
			t.interrupt();
			t = null;
			t.interrupt();
		}
	}

	@Override
	public void stateChanged(UpdatingSensor s, float oldVal, float newVal) {
		// TODO Auto-generated method stub
		// Ignore
	}

	@Override
	public void stateNotification(UpdatingSensor s, float value, float rawValue) {
		// higher than middle ground average
		if (rawValue > (highestAverage + lowestAverage) / 2) {//rawValue > highest) {
			//RConsole.println("Raising " + highest + " to " + rawValue);
			highestCount++;
			highestTotal += rawValue;
			highestAverage = highestTotal / highestCount;
			//highest = rawValue;
			Globals.MCS.setHigh((int) highestAverage);//highest);
			Globals.MLS.setHigh((int) highestAverage);//highest);
		}
		if (rawValue < (highestAverage + lowestAverage) / 2) {//rawValue < lowest) {
			//RConsole.println("Lowering " + lowest + " to " + rawValue);
			//lowest = rawValue;
			lowestCount++;
			lowestTotal += rawValue;
			lowestAverage = lowestAverage / lowestCount;
			Globals.MCS.setLow((int) lowestAverage);//lowest);
			Globals.MLS.setLow((int) lowestAverage);//lowest);
		}
	}
}
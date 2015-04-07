package klasV1M.TI.controllers;

import klasV1M.TI.sensoren.MyLightSensor;
import klasV1M.TI.sensoren.SensorListener;
import klasV1M.TI.sensoren.UpdatingSensor;
import lejos.nxt.Button;

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
		setAutoAdjust(false);
		configureLightSensors();
	}
	
	/** Function to calibrate both the {@link MyLightSensor} to work in a range of 0 to 100 
	 * With 0 as most Black
	 * With 100 as most White
	 * @return <code>true</code> when configuration was succesfull, <code>false</code> otherwise.
	 */
	public synchronized boolean configureLightSensors() {
		System.out.println("Place on white spot in five seconds");

		Button.waitForAnyPress(5000);

		System.out.println("Calibrating white...");
		MyLightSensor.getInstance().calibrateHigh();
		
		System.out.println("Place on black spot in five seconds");

		Button.waitForAnyPress(5000);

		System.out.println("Calibrating black...");
		MyLightSensor.getInstance().calibrateLow();
		
		highestCount = 1;
		highestTotal = highest;
		highestAverage = highest;
		lowestCount = 1;
		lowestTotal = lowest;
		lowestAverage = lowest;
		return true;
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
		MyLightSensor.getInstance().addListener(this);
		while (!t.interrupted() && autoAdjust) {
			Thread.yield();
		}
		MyLightSensor.getInstance().removeListener(this);
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
			MyLightSensor.getInstance().setHigh((int) highestAverage);//highest);
		}
		if (rawValue < (highestAverage + lowestAverage) / 2) {//rawValue < lowest) {
			//RConsole.println("Lowering " + lowest + " to " + rawValue);
			//lowest = rawValue;
			lowestCount++;
			lowestTotal += rawValue;
			lowestAverage = lowestAverage / lowestCount;
			MyLightSensor.getInstance().setLow((int) lowestAverage);//lowest);
		}
	}
}
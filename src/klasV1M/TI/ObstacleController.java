package klasV1M.TI;

import klasV1M.TI.sensoren.MyLightSensor;
import klasV1M.TI.sensoren.MyUltraSonicSensor;
import klasV1M.TI.sensoren.SensorListener;
import klasV1M.TI.sensoren.UpdatingSensor;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;

/**
 * Avoids obstacles, reacting to input from the {@link MyLightSensor} and
 * {@link MyUltraSonicSensor}. The maximum width of the obstacle is
 * {@value #obstacleWidth} centimeter.
 * 
 * @author Remco, Koen, & Medzo
 * @version 1.0.0.0
 */
public class ObstacleController implements SensorListener, Runnable {

	private DifferentialPilot diffPilot;
	private double trackWidth = 13;
	public int[] obRechts = new int[2];
	public int[] obLinks = new int[2];
	/**
	 * The maximum width of the obstacle that the robot can avoid.
	 */
	private static final int obstacleWidth = 20;
	private Thread t;
	private int aantalKeer;
	private boolean beginScan;

	public ObstacleController() {
		// Motor.A is the right motor
		// Motor.C is the left motor
		diffPilot = new DifferentialPilot(DifferentialPilot.WHEEL_SIZE_NXT2,
				trackWidth, Motor.C, Motor.A);
		// Set speed to 1 rot/sec
		diffPilot.setTravelSpeed(DifferentialPilot.WHEEL_SIZE_NXT2);
		// Move forward
		diffPilot.forward();
		// Register listeners
		MyUltraSonicSensor.getInstance().addListener(this);
		MyLightSensor.getInstance().addListener(this);
	}
	
	public double calculateObstacleWidth() {
		System.out.println("Obstacle width is called");
		// ArrayList<Number> anglas = returnFreeAngles();
		// ArrayList<Number> distances = lowDistances();
		// int angleWhenObjectIsGone = (int)averageOfList(anglas);
		// if(obLinks[0] != 0){

		int sidea = obLinks[0];// links
		int sideb = obRechts[0];// rechts
		int theAngle = Math.abs(obRechts[1]) + Math.abs(obLinks[1]);
		double theWidth = Math.sqrt((Math.pow(sidea, 2) + Math.pow(sideb, 2))
				- (2 * sidea * sideb * Math.cos(Math.toRadians(theAngle))));

		return theWidth;
	}


	@Override
	public void stateChanged(UpdatingSensor s, float oldVal, float newVal) {
		// Ultrasonic Sensor
		if (s.equals(MyUltraSonicSensor.getInstance())) {
			
			
			if (beginScan) { // linksom of rechtsom?
				int angle = Motor.B.getTachoCount();

				if ((100 < newVal && newVal <= 255) && angle > 0) { // rechts

					obRechts[0] = (int) oldVal;
					obRechts[1] = (int) angle;
					

					//RConsole.println("Rechts Centi: " + oldVal);
					//RConsole.println("Rechts Angle: " + angle);
					Motor.C.stop(true);
				} else if ((100 < newVal && newVal <= 255) && angle < 0) { // left
					obLinks[0] = (int) oldVal;
					obLinks[1] = (int) angle;
					
					//RConsole.println("Links Centi: " + oldVal);
					//RConsole.println("Links Angle: " + angle);
					Motor.C.stop(true);
				}
				else{
					aantalKeer = 2;
					driveAroundObstacle(obstacleWidth);
				}
			} 
			
			if (newVal <= 30 && !beginScan) { // if object is in 30cm of us.
				
				beginScan = true;
					// left to find new path.
				diffPilot.stop(); // stop driving
				
				aantalKeer = 0;
				// doneScanning = false;
				start(); // start scanning
				

				
			
			}

		}
		// Light Sensor
		if (s.equals(MyLightSensor.getInstance()) && !beginScan) {
			diffPilot.steer(newVal - 50);
		}
	}
		
	public void driveAroundObstacle(int obstWidth){
		
		
		diffPilot.rotate(90);
		diffPilot.travel(obstWidth + 5);
		diffPilot.rotate(-90);
		diffPilot.travel(obstWidth + 30);
		
		diffPilot.rotate(-90);
	
		diffPilot.travel(obstWidth + 5);
		diffPilot.rotate(90);
		avoidingObject = false;
	}
	
	public void start() {
		if (t == null) {
			t = new Thread(this);
			t.start();
		}
	}

	// @Override
	public void run() {
		/*
		 * Globals.mLeft.setSpeed(360); Globals.mRight.setSpeed(360);
		 * Globals.mLeft.forward(); Globals.mRight.forward();
		 */
		// mMiddle.setSpeed(90);
		Motor.B.setSpeed(30);
		while (aantalKeer < 1) {
			Motor.B.rotateTo(+90, false);// rechts

			// try {
			// Thread.sleep(400);
			// } catch (InterruptedException e) {
			// }
			Motor.B.rotateTo(-90, false); // links
			Motor.B.rotateTo(0, false); // links
			aantalKeer++;

		}
		Motor.B.stop();
		driveAroundObstacle((int) calculateObstacleWidth());

		// driveAroundObstacle();

	}
}
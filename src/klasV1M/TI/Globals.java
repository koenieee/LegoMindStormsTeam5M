package klasV1M.TI;

import klasV1M.TI.sensoren.MyLightSensor;
import klasV1M.TI.sensoren.MyUltraSonicSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.robotics.navigation.DifferentialPilot;

/**
 * Provides global access to the sensors and motors.
 * TODO:
 * - Test if synchronized access is needed/change class into a Singleton?
 * - Make (certain) statics final?
 * @author Remco
 *
 */
public class Globals {
	/**
	 * The track width used by the {@link DifferentialPilot}
	 */
	public static double TrackWidth = 13;
	
	/**
	 * The right {@link NXTRegulatedMotor}
	 */
	public static NXTRegulatedMotor mRight = Motor.A;

	/**
	 * The left {@link NXTRegulatedMotor}
	 */
	public static NXTRegulatedMotor mLeft = Motor.C;
	
	public static DifferentialPilot diffPilot = new DifferentialPilot(DifferentialPilot.WHEEL_SIZE_NXT2, TrackWidth, mLeft, mRight);

	public static void playSong() {
		Sound.setVolume(Sound.VOL_MAX);
		// NOTE: This tune was generated from a midi using Guy
		// Truffelli's Brick Music Studio www.aga.it/~guy/lego
		short[] note = { 2349, 115, 0, 5, 1760, 165, 0, 35, 1760, 28, 0, 13,
				1976, 23, 0, 18, 1760, 18, 0, 23, 1568, 15, 0, 25, 1480, 103,
				0, 18, 1175, 180, 0, 20, 1760, 18, 0, 23, 1976, 20, 0, 20,
				1760, 15, 0, 25, 1568, 15, 0, 25, 2217, 98, 0, 23, 1760, 88, 0,
				33, 1760, 75, 0, 5, 1760, 20, 0, 20, 1760, 20, 0, 20, 1976, 18,
				0, 23, 1760, 18, 0, 23, 2217, 225, 0, 15, 2217, 218 };
		for (int i = 0; i < note.length; i += 2) {
			final short w = note[i + 1];
			final int n = note[i];
			if (n != 0)
				Sound.playTone(n, w * 10);
			try {
				Thread.sleep(w * 2);
			} catch (InterruptedException e) {
			}
		}
		Sound.setVolume(0);
	}
}

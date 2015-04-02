package klasV1M.WaypointTest;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Waypoint;

public class Main {

	public static void main(String[] args) {
        DifferentialPilot pilot = new DifferentialPilot(3.4, 12, Motor.C, Motor.A, false);
        pilot.setRotateSpeed(10);
        pilot.rotate(-53, false);
        pilot.setTravelSpeed(3);
        pilot.travel(4);
        Button.ENTER.waitForPressAndRelease();

	}

}

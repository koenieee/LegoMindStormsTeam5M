package klasV1M.WaypointTest;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Waypoint;

public class Main {

	public static void main(String[] args) {
        DifferentialPilot pilot = new DifferentialPilot(3.4, 12, Motor.C, Motor.A, false);
        Navigator navigator = new Navigator(pilot);

        navigator.addWaypoint(new Waypoint(20, 40));
        navigator.addWaypoint(new Waypoint(20, 100));
        navigator.addWaypoint(new Waypoint(0, 100));
        navigator.addWaypoint(new Waypoint(0,0));

        navigator.followPath();
        Button.ENTER.waitForPressAndRelease();

	}

}

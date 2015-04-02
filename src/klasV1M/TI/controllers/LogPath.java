package klasV1M.TI.controllers;

import java.util.LinkedList;

import lejos.nxt.comm.RConsole;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.Move;
import lejos.robotics.navigation.MoveListener;
import lejos.robotics.navigation.MoveProvider;
import lejos.robotics.navigation.Pose;
import lejos.robotics.navigation.Waypoint;

public class LogPath implements Runnable,MoveListener,PoseProvider{
	private Thread t;
	public LinkedList<Waypoint> PreviousPoints= new LinkedList();
	

	@Override
	public void run() {
		
		
	}
	
	public void getPreviousPoints(){
		for (Waypoint W : PreviousPoints) {
			System.out.println(W);
			}
	}
	public void start(){
		if (t == null) {
		t = new Thread(this);
		t.start();
		}
	}
	public void stop(){
		if (t != null)
			t.interrupt();
	}

	@Override
	public void moveStarted(Move event, MoveProvider mp) {
		event.getDistanceTraveled();
		RConsole.println("" + event.getDistanceTraveled());
	}

	@Override
	public void moveStopped(Move event, MoveProvider mp) {
		event.getDistanceTraveled();
	RConsole.println("" + event.getDistanceTraveled());
		
	}

	@Override
	public Pose getPose() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPose(Pose aPose) {
		// TODO Auto-generated method stub
		
	}
}
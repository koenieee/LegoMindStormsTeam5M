package klasV1M.TI.controllers;

import java.util.LinkedList;

import lejos.robotics.navigation.Waypoint;

public class LogPath implements Runnable{
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
}
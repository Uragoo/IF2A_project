package application;

import java.util.Timer;
import java.util.TimerTask;

public class Reminder {
	static Timer fall;
	
	public Reminder(int movespeed, TimerTask task) {
		fall = new Timer();
		fall.schedule(task, 0, movespeed);
	}
	
	public static void stopIt() {
		fall.purge();
		fall.cancel();
	}
}

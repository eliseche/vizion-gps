package com.globalis.viziongps;

import java.util.Timer;
import java.util.TimerTask;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class GpsService extends Service {
	private Timer timer = new Timer();
	private int executionTime;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	@Override
	public void onCreate() {
		Toast.makeText(this, "GPS Service created.", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onDestroy() {
		Toast.makeText(this, "GPS Service destroyed.", Toast.LENGTH_SHORT).show();
		if(timer != null) {
			timer.cancel();
		}
	}
	
	@Override
	public void onStart(Intent intent, int startId) {	
		this.executionTime = 60000;
		timer.scheduleAtFixedRate(new TimerTask() {			
			@Override
			public void run() {
				Sender sender = new Sender();
				sender.run();
			}
		}, executionTime, executionTime);
	}
}
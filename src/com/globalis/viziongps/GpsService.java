package com.globalis.viziongps;

import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
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
		SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Settings.UPDATE_TIME, Activity.MODE_PRIVATE);
		String updateInterval = sharedPreferences.getString(Settings.UPDATE_TIME_VALUE, "180000");	
		this.executionTime = Integer.valueOf(updateInterval);
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
		timer.scheduleAtFixedRate(new TimerTask() {			
			@Override
			public void run() {
							 
				Sender sender = new Sender();
				sender.run();
			}
		}, executionTime, executionTime);
	}
}
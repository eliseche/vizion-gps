package com.globalis.viziongps;

import com.globalis.viziongps.Sender.ReportType;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class VizionGps extends Activity implements LocationListener, OnClickListener {	
	private LocationManager locationManager;	
	private Location location;
	private Sender sender = new Sender();
			
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vizion_gps);
        
        fillRemoteData();
        
        Button buttonPanic = (Button)findViewById(R.id.vizion_gps_btn_panic);
        buttonPanic.setOnClickListener(this);
        Button buttonPolice = (Button)findViewById(R.id.vizion_gps_btn_police);
        buttonPolice.setOnClickListener(this);
        Button buttonMechanic= (Button)findViewById(R.id.vizion_gps_btn_mechanic);
        buttonMechanic.setOnClickListener(this);
        
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    	//Get last known position
        updateLocation(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }
    
	@Override
	public void onLocationChanged(Location location) {
		updateLocation(location);
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}	
        
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main_menu, menu);
		return true;		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			Intent intentGpsStatus = new Intent(VizionGps.this, Settings.class);
			startActivity(intentGpsStatus);			
			break;
		default:
			break;
		}
		
		return true;
	}
	
	private void updateLocation(Location location) {
		if(location != null) {			
			GpsData.setLatLon(location.getLatitude(), location.getLongitude());
			this.location = location;			
		}		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.vizion_gps_btn_panic:
			if(location != null) {
				sender.send(ReportType.PANIC);
			}
			else {
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.vizion_gps_no_gps), Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.vizion_gps_btn_police:
			if(location != null) {
				sender.send(ReportType.POLICE);
			}
			else {
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.vizion_gps_no_gps), Toast.LENGTH_SHORT).show();				
			}
			break;
		case R.id.vizion_gps_btn_mechanic:
			if(location != null) {
				sender.send(ReportType.MECHANIC);
			}
			else {
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.vizion_gps_no_gps), Toast.LENGTH_SHORT).show();				
			}
			break;
		default:
			break;		
		}	
	}
	
	private void fillRemoteData() {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());		
		String server = sp.getString("server", "200.80.220.10");
		String port = sp.getString("port", "9999");
		String equipment = sp.getString("equipment", "9999");
		ServerData.setServer(server);
		ServerData.setPort(port);
		ServerData.setEquipment(equipment);
	}
}
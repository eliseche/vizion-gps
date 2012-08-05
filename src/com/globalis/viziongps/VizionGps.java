package com.globalis.viziongps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class VizionGps extends Activity implements LocationListener, OnClickListener {	
	private LocationManager locationManager;		
			
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vizion_gps);       
        
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    	//Get last known position
        updateLocation(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 50, this);
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
			//mapLocation = new MapLocation(Math.round(location.getLatitude() * 100.0) / 100.0 + ", " + Math.round(location.getLongitude() * 100.0) / 100.0, location.getLatitude(), location.getLongitude());			
		}		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case 1:
			
			break;
		default:
			break;		
		}	
	}
}
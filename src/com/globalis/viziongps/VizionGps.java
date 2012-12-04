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

public class VizionGps extends Activity implements LocationListener, OnClickListener {
	private GpsData gpsData;
	private GlobalConfiguration globalConfiguration;
	private LocationManager locationManager;	
	private Sender sender = new Sender();	
	
	public VizionGps() {
		gpsData = GpsData.getInstance();
        globalConfiguration = GlobalConfiguration.getInstance();
	}	
			
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vizion_gps);
        
        initViews();
        fillRemoteData();        
        initGps();   
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
			gpsData.setLatLon(location.getLatitude(), location.getLongitude());					
		}		
	}

	@Override
	public void onClick(View v) {	
		switch(v.getId()) {		
		case R.id.vizion_gps_btn_panic:
			Thread threadPanic = new Thread() {
				@Override
				public void run() {					
					try {
						sender.send(ReportType.PANIC);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			threadPanic.start();				
			break;
		case R.id.vizion_gps_btn_medical:
			Thread threadPolice = new Thread() {
				@Override
				public void run() {
					try {
						sender.send(ReportType.MEDICAL);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			threadPolice.start();				
			break;
		case R.id.vizion_gps_btn_mechanic:
			Thread threadMechanic = new Thread() {
				@Override
				public void run() {
					try {
						sender.send(ReportType.MECHANIC);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			threadMechanic.start();				
			break;
		default:
			break;		
		}
	}
	
	private void initViews() {
		Button buttonPanic = (Button)findViewById(R.id.vizion_gps_btn_panic);
        buttonPanic.setOnClickListener(this);
        Button buttonMedical = (Button)findViewById(R.id.vizion_gps_btn_medical);
        buttonMedical.setOnClickListener(this);
        Button buttonMechanic = (Button)findViewById(R.id.vizion_gps_btn_mechanic);
        buttonMechanic.setOnClickListener(this);
	}
	
	private void fillRemoteData() {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());		
		String server = "200.80.220.10";/*sp.getString("server", "200.80.220.10");*/
		String port = "9999";/*sp.getString("port", "9999");*/
		String equipment = "9993"; /*sp.getString("equipment", "9993");*/
		boolean packageDelivery = sp.getBoolean("package_delivery", false);
		globalConfiguration.setServer(server);
		globalConfiguration.setPort(port);
		globalConfiguration.setEquipment(equipment);
	}
	
	private void initGps() {
		locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    	//Get last known position
        updateLocation(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
	}
}
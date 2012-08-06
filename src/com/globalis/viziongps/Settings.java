package com.globalis.viziongps;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceChangeListener;

public class Settings extends PreferenceActivity {
	public static final String PACKAGE_DELIVERY = "package_delivery";	
	public static final String UPDATE_TIME = "update_time";
	public static final String UPDATE_TIME_VALUE = "120000";		
	private Intent registerService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
		
		CheckBoxPreference checkboxPreference = (CheckBoxPreference)getPreferenceManager().findPreference(PACKAGE_DELIVERY);
		checkboxPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {			
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				if(newValue.toString().equals("true")) {			
					registerService = new Intent(getApplicationContext(), GpsService.class);
			    	startService(registerService);		        			
				}
				else {
					stopService(new Intent(getApplicationContext(), GpsService.class));			
				}
				return true;				
			}
		});	
		
		ListPreference updateTime = (ListPreference)getPreferenceManager().findPreference(UPDATE_TIME);
        updateTime.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
        	@Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {                
                SharedPreferences sharedPreference = getSharedPreferences(UPDATE_TIME, Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreference.edit();                
                editor.putString(UPDATE_TIME_VALUE, (String)newValue);
                editor.commit();
                stopService(new Intent(getApplicationContext(), GpsService.class));        		        		
        		startService(registerService);                
                return true;
            }
        });
	}
}
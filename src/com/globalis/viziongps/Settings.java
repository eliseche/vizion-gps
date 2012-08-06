package com.globalis.viziongps;

import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;

public class Settings extends PreferenceActivity {	
	private Intent registerService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
		
		CheckBoxPreference checkboxPreference = (CheckBoxPreference)getPreferenceManager().findPreference("package_delivery");
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
		
		ListPreference updateTime = (ListPreference)getPreferenceManager().findPreference("update_time");
        updateTime.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
        	@Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {       
                stopService(new Intent(getApplicationContext(), GpsService.class));  
                registerService = new Intent(getApplicationContext(), GpsService.class);
        		startService(registerService);                
                return true;
            }
        });
	}
}
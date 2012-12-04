package com.globalis.viziongps;

import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;

public class Settings extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
		
		CheckBoxPreference checkboxPreference = (CheckBoxPreference)getPreferenceManager().findPreference("package_delivery");
		checkboxPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {			
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				if(newValue.toString().equals("true")) {
			    	startService(new Intent(getApplicationContext(), GpsService.class));		        			
				}
				else {
					stopService(new Intent(getApplicationContext(), GpsService.class));			
				}
				return true;				
			}
		});
		
		ListPreference listPreference = (ListPreference)getPreferenceManager().findPreference("update_time");
		listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {		
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				String oldValue = ((ListPreference)preference).getValue();
				if (newValue != oldValue) {
					stopService(new Intent(getApplicationContext(), GpsService.class));
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					startService(new Intent(getApplicationContext(), GpsService.class));
				}
				return true;
			}
		});
	}
}
package com.globalis.viziongps;

import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceChangeListener;

public class Settings extends PreferenceActivity implements OnPreferenceChangeListener {
	private static final String OPT_INTERVAL = "update_time";
	private static final int OPT_INTERVAL_VALUE = 120000;	
	private Intent registerService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
		
		CheckBoxPreference checkboxPreference = (CheckBoxPreference)getPreferenceManager().findPreference("package_delivery");
		checkboxPreference.setOnPreferenceChangeListener(this);
	}

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
}
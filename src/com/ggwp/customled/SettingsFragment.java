package com.ggwp.customled;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;
import android.widget.Toast;
import com.ggwp.customled.R;

public class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.settings);
        
        CheckBoxPreference checkBoxPreference = (CheckBoxPreference) this.findPreference("switch_on");

        checkBoxPreference.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
            	
            	if(newValue.equals(true)) {
	            	final Intent service_intent = new Intent(getActivity(), AppService.class);
	        		getActivity().startService(service_intent);
	            }
	            else{
	            	final Intent service_intent = new Intent(getActivity(), AppService.class);
	            	getActivity().stopService(service_intent);
	            }
            	
            	
                    return true;
                }
            });
        
        
    }
    @Override
	public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
	public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
	        String key) {
	        
		/*
		if (key.equals("switch_on")) {
	            Preference connectionPref = findPreference(key);
	            // Set summary to be the user-description for the selected value
	            //connectionPref.setSummary(sharedPreferences.getBoolean(key, true));
	            boolean val = sharedPreferences.getBoolean(key, true);
	            if(val) {
	            	final Intent service_intent = new Intent(getActivity(), AppService.class);
	        		getActivity().startService(service_intent);
	            }
	            else{
	            	final Intent service_intent = new Intent(getActivity(), AppService.class);
	            	getActivity().startService(service_intent);
	            }
	         }
	      */      	
	            
	            
	            
	        }
		
}

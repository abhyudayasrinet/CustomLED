package com.ggwp.customled;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent arg1) {
		// TODO Auto-generated method stub
		
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        boolean val = sharedPref.getBoolean("switch_on", true);
        
        
        if(val) {
        	context.startService(new Intent(context.getApplicationContext(),AppService.class));
        }
		
		
	}

}

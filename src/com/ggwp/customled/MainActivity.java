package com.ggwp.customled;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ggwp.customled.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;


import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {

	ListView listView;
	List<ResolveInfo> list;
	List<ListClass> apps;
	
	
	AppDB DB;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_main);
		
		
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayUseLogoEnabled(false);
		
		
		 getWindow().setFlags(
				    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
				    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
		
		DB = new AppDB(this);
				
		
		if(!NLService.isNotificationAccessEnabled)
			startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
        
        
        
        listView = (ListView) findViewById(R.id.list);
        listView.setFastScrollEnabled(true);
        listView.setScrollingCacheEnabled(false);
        
        Intent intent;
        apps = new ArrayList<ListClass>();	
        
        ListClass entry = new ListClass("Battery Charging","Battery Charging");
        apps.add(entry);
        entry = new ListClass("Battery Full","Battery Full");
        apps.add(entry);
        entry = new ListClass("Battery Low","Battery Low");
        apps.add(entry);
        
        
		PackageManager pm = this.getPackageManager();
        intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        
        list = pm.queryIntentActivities(intent,
                PackageManager.PERMISSION_GRANTED);
        
        
        
        Collections.sort(list, new ResolveInfo.DisplayNameComparator(pm));        
        for (ResolveInfo rInfo : list)
			try {
				
				    String appName = rInfo.activityInfo.applicationInfo.loadLabel(pm).toString();
				    
				    String PckgName = rInfo.activityInfo.applicationInfo.packageName;
				    
				    entry = new ListClass(PckgName,appName);
				    apps.add(entry);
				
			} catch (Exception e) {

				e.printStackTrace();
			}	
        
        
        ListViewAdapter adapter = new ListViewAdapter(this,
                R.layout.list_item, apps);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(listen);
        
        
        
        
        
        PreferenceManager.setDefaultValues(this, R.xml.settings, false);
        
        
        
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean val = sharedPref.getBoolean("switch_on", true);
        
        
        if(val) {
	        final Intent service_intent = new Intent(this, AppService.class);
			startService(service_intent);
        }
        
        
        AdView adview = (AdView) findViewById(R.id.adView);
        AdRequest adrequest = new AdRequest.Builder().build();
        adview.loadAd(adrequest);
        
	}
	
	OnItemClickListener listen = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, final View view, int position,
				long id) {
	
			final String package_name = apps.get(position).getPackageName();
			AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
				builder.setTitle("Choose option")
						.setItems(R.array.itemclickoptions, new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int option_number) {
								// TODO Auto-generated method stub
								
								
								
//						        Log.d("app name on click",package_name);
						        	       

								
								if(option_number == 0){
									
									int old_color = DB.getColor(package_name);
							        if(old_color == 0 )
							        	old_color = Color.BLACK;	
//							        Log.d("color picker",package_name);
									AmbilWarnaDialog color_picker_dialog = new AmbilWarnaDialog(view.getContext(),
								     		old_color, new OnAmbilWarnaListener() {
								     			
								     			@Override
								     			public void onOk(AmbilWarnaDialog dialog, int color) {
								     				// TODO Auto-generated method stub
								     				DB.updateColorEntry(package_name, color);
								     			}
								     			
								     			@Override
								     			public void onCancel(AmbilWarnaDialog dialog) {
								     				// TODO Auto-generated method stub
								     				
								     			}
								     		});
							        color_picker_dialog.show();									
									
								}
								else if(option_number == 1) { 
									
									AlertDialog.Builder blink_rate_builder = new AlertDialog.Builder(view.getContext());
									blink_rate_builder.setTitle("Choose Blink Rate")
											.setItems(R.array.blinkrateoptions, new DialogInterface.OnClickListener() {
												
												@Override
												public void onClick(DialogInterface dialog, int index) {
//													Log.d("app name rate picker",package_name);
											        
												
													Resources res = getResources();
													String[] options = res.getStringArray(R.array.blinkrateoptions);
													
													DB.updateBlinkRateEntry(package_name, index);
												
													
												}
											});
									
									AlertDialog blink_rate_dialog = blink_rate_builder.create();
									blink_rate_dialog.setCancelable(true);
									blink_rate_dialog.show();
								}
							
								
							}
						});
			    	   
		        	
			
			AlertDialog dialog = builder.create();
			dialog.setCancelable(true);
			dialog.show();
			
		    
		        

		        
		        
		        
		}
	};
	
	
	
	
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        getMenuInflater().inflate(R.menu.settings, menu);
	        return true;
	    }
	 
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {
	 
	        case R.id.menu_settings:
	            Intent intent = new Intent(this, SettingsActivity.class);
	            startActivity(intent);
	            break;
	 
	        }
	 
	        return true;
	    }
	
}

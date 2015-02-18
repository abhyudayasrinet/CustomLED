package com.ggwp.customled;



import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class AppService extends Service {

	public static boolean running = true;
	
	AppDB DB;
	Activity myMainActivity;
	Notification notif;
	private NotificationReceiver nReceiver;
	public static boolean isNotificationAccessEnabled = false;   
 

	
	@Override
	public IBinder onBind(Intent arg0) {		
		return null;
	}
	
	
	public void onCreate() {
		Toast.makeText(this, "CustomLED Started", Toast.LENGTH_SHORT).show();
//		Log.d("App Service", "Service oncreate");
		
		DB = new AppDB(this);
		
        nReceiver = new NotificationReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.ggwp.customled.NOTIFICATION_LISTENER_EXAMPLE");
        registerReceiver(nReceiver,filter);
        
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(nReceiver, ifilter);
	
	}
	
	@Override
	public void onDestroy() {

		super.onDestroy();
		Toast.makeText(this, "CustomLED Stopped", Toast.LENGTH_SHORT).show();
//		Log.d("App Service", "onDestroy");
		unregisterReceiver(nReceiver);
	}
	
	class NotificationReceiver extends BroadcastReceiver{

        private static final int LED_NOTIFICATION_ID = 0;

		@Override
        public void onReceive(Context context, Intent intent) {
			
			NotificationManager nm = ( NotificationManager ) getSystemService( NOTIFICATION_SERVICE );
    		
			int color = 0;
			int blinkrate = 0;
			
			
			//BATTERY STATUS VARIABLES-------------------------------
			int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
	        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING;
	        boolean isFull = status == BatteryManager.BATTERY_STATUS_FULL;
	        
	        int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
	        
	        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
			int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

			float batteryPct = (level / (float)scale)*100;
//			Log.d("Battery Level",String.valueOf(batteryPct));
	        
	        if(batteryPct<30.0) {

	        	color = DB.getColor("Battery Low");
        		blinkrate = DB.getBlinkRate("Battery Low");
//        		Log.d("battery low led on",String.valueOf(color));
        		
        		nm = ( NotificationManager ) getSystemService( NOTIFICATION_SERVICE );
        		notif = new Notification();

        		setLEDvalues(color,blinkrate);
        		nm.notify(LED_NOTIFICATION_ID, notif);
        	}	
	        
	        
	        else if(isFull) {
        		color = DB.getColor("Battery Full");
        		blinkrate = DB.getBlinkRate("Battery Full");
//        		Log.d("battery full led on",String.valueOf(color));
        		
        		nm = ( NotificationManager ) getSystemService( NOTIFICATION_SERVICE );
        		notif = new Notification();
        		
        		setLEDvalues(color,blinkrate);
 
        		nm.notify(LED_NOTIFICATION_ID, notif);
        	}	
	        
	        else if(isCharging) {
        		color = DB.getColor("Battery Charging");
        		blinkrate = DB.getBlinkRate("Battery Charging");
        		
//        		Log.d("battery charging blink rate",""+blinkrate);
//        		Log.d("battery charging led on",String.valueOf(color));
        		
        		nm = ( NotificationManager ) getSystemService( NOTIFICATION_SERVICE );
        		notif = new Notification();
        		setLEDvalues(color,blinkrate);
        		nm.notify(LED_NOTIFICATION_ID, notif);
        	}	

    		else {
//    			Log.d("battery discharging","led off");
        		nm = ( NotificationManager ) getSystemService( NOTIFICATION_SERVICE );
        		notif = new Notification();
        		notif.ledARGB = 0;
        		notif.flags = Notification.FLAG_SHOW_LIGHTS;
        		notif.ledOnMS = 0; 
        		notif.ledOffMS = 0; 
        		nm.notify(LED_NOTIFICATION_ID, notif);
    			
    		}
        	
        	String posted_packagename = intent.getStringExtra("notification_event_posted");
        	String remove_packagename = intent.getStringExtra("notification_event_removed");
        	
        	
    		
        	if(posted_packagename!=null) {
        		
        		color = DB.getColor(posted_packagename);
        		blinkrate = DB.getBlinkRate(posted_packagename);
//        		Log.d("Led on",String.valueOf(color));
        		nm = ( NotificationManager ) getSystemService( NOTIFICATION_SERVICE );
        		notif = new Notification();
        		setLEDvalues(color,blinkrate);
        		nm.notify(LED_NOTIFICATION_ID, notif);
        	}
        	
        	if(remove_packagename!=null)
        	{
//        		Log.d("Led off","off");
        		nm = ( NotificationManager ) getSystemService( NOTIFICATION_SERVICE );
        		notif = new Notification();
        		notif.ledARGB = 0;
        		notif.flags = Notification.FLAG_SHOW_LIGHTS;
        		notif.ledOnMS = 0; 
        		notif.ledOffMS = 0; 
        		nm.notify(LED_NOTIFICATION_ID, notif);
        	}
        	
        	
        	      	
     }

		private void setLEDvalues( int color,
				int blinkrate) {
			
			notif.defaults = 0;
			notif.ledARGB = color;
			notif.flags |= Notification.FLAG_SHOW_LIGHTS;
			
			if(blinkrate == 0){
				notif.ledOnMS = 1; 
        		notif.ledOffMS = 0;
			}
			else if(blinkrate == 1){
				notif.ledOnMS = 2000; 
        		notif.ledOffMS = 2000;
			}
			
			else if(blinkrate == 3){
				notif.ledOnMS = 1000; 
        		notif.ledOffMS = 1000;
			}
			else if(blinkrate == 4){
				notif.ledOnMS = 200; 
        		notif.ledOffMS = 100;
			}
			else{
				notif.ledOnMS = 2000; 
        		notif.ledOffMS = 1000;
			}
			
			
		}

	}
}

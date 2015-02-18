package com.ggwp.customled;



import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
//import android.util.Log;

public class NLService extends NotificationListenerService {

    private String TAG = this.getClass().getSimpleName();
    public static boolean isNotificationAccessEnabled = false;   

    @Override     
    public IBinder onBind(Intent mIntent) {         
       IBinder mIBinder = super.onBind(mIntent);        
       isNotificationAccessEnabled = true;      
       return mIBinder;     
    }          

    @Override     
    public boolean onUnbind(Intent mIntent) {       
        boolean mOnUnbind = super.onUnbind(mIntent);        
        isNotificationAccessEnabled = false;        
        return mOnUnbind;     
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.ggwp.customled.NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
       
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {

//        Log.i(TAG,"onNotificationPosted");
//        Log.i(TAG,"ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText +
//        		"\t" + sbn.getPackageName());
        
        Intent i = new  Intent("com.ggwp.customled.NOTIFICATION_LISTENER_EXAMPLE");
        i.putExtra("notification_event_posted", sbn.getPackageName());
        
        sendBroadcast(i);

    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        
//    	Log.i(TAG,"onNOtificationRemoved");
//        Log.i(TAG,"ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText 
//        		+"\t" + sbn.getPackageName());
        
        Intent i = new  Intent("com.ggwp.customled.NOTIFICATION_LISTENER_EXAMPLE");
        i.putExtra("notification_event_removed","onNotificationRemoved :" + sbn.getPackageName() + "n");

        sendBroadcast(i);
    }
}
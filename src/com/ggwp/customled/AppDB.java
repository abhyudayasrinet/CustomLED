package com.ggwp.customled;



import java.util.LinkedList;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
//import android.util.Log;

public class AppDB extends SQLiteOpenHelper{

	
	static String dbName = "AppDatabase";

	private String Id = "ID";
	private String appTable = "AppTable";
	private String packageName = "PackageName";
	private String color = "Color";
	private String blinkrate = "BlinkRate";
	
	public AppDB(Context context) {
		super(context, dbName, null,1);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		db.execSQL("CREATE TABLE "+appTable+" ("+Id+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+
				  packageName+" TEXT, "+color+ " INTEGER, "+ blinkrate+" INTEGER )");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS "+appTable);
		  this.onCreate(db);
		
	}
	

	public void updateColorEntry(String package_name,int new_Color) {
		
		
		int flag=0;
	       String query = "SELECT  * FROM " + appTable;
	 

	       SQLiteDatabase db = this.getWritableDatabase();
	       Cursor cursor = db.rawQuery(query, null);
	 
	       
	       if (cursor.moveToFirst()) {
	           do {
	               
	               String packagename;
	              

	               
	               packagename = cursor.getString(1);
	               int old_color = cursor.getInt(2);
	
	               
	               if(package_name.equals(packagename)) {
	            	   ContentValues values = new ContentValues();
	            	   values.put(color, new_Color);
	
	            	   
	            	   db.update(appTable, values,packageName+" = ?",
	            			   new String[] { package_name });
	            	   
//	            	   Log.d("update color entry","updated from "+old_color+" to "+new_Color);
	            	   flag=1;
	            	   break;
	               }
	            	   
	               
	           } while (cursor.moveToNext());
	       }
	       
	       if(flag==0){
	    	
	    	   ContentValues values = new ContentValues();
		   		
		   		values.put(packageName, package_name ); 
		   		values.put(color, new_Color);
		   		
		   		db.insert(appTable,  null,values); 
//		   		Log.d("update color entry","updated to"+new_Color);
	       }
	       
	    db.close();
	}


	public void updateBlinkRateEntry(String package_name,int newblink_rate) {
		
		
		int flag=0;
	       String query = "SELECT  * FROM " + appTable;
	 

	       SQLiteDatabase db = this.getWritableDatabase();
	       Cursor cursor = db.rawQuery(query, null);
	 
	       
	       if (cursor.moveToFirst()) {
	           do {
	               
	               String packagename;
	              

	               
	               packagename = cursor.getString(1);
	               int oldblink_rate = cursor.getInt(3);
	               
	               if(package_name.equals(packagename)) {
	            	   ContentValues values = new ContentValues();
	            	   values.put(blinkrate, newblink_rate);
	            	   
	            	   db.update(appTable, values,packageName+" = ?",
	            			   new String[] { package_name });
	            	   
//	            	   Log.d("update blink rate entry","updated from "+oldblink_rate+
//	            			   								" to "+newblink_rate);
	            	   
	            	   flag=1;
	            	   break;
	               }
	            	   
	               
	           } while (cursor.moveToNext());
	       }
	       
	       if(flag==0){
	    	
	    	   ContentValues values = new ContentValues();
		   		
		   		values.put(packageName, package_name ); 
		   		values.put(blinkrate, newblink_rate);
		   		db.insert(appTable,  null,values); 
//		   		Log.d("update blinkrate","updated to "+newblink_rate);
	       }
	       
	    db.close();
	}


	public int getColor(String package_name) {
	    String query = "SELECT  * FROM " + appTable;
	
	    
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(query, null);
	
	    
	    if (cursor.moveToFirst()) {
	        do {
	           
	            
	           
	            String packagename = cursor.getString(1);
	            int app_color  = cursor.getInt(2);
	          
	            if(package_name.equals(packagename) ){
//	            	Log.d("color value",String.valueOf(app_color));
	            	return app_color;
	            }
	            	
	            
	        } while (cursor.moveToNext());
	    }
	    db.close();
//	    Log.d("color value","blank");
	    return 0;
	}

	public int getBlinkRate(String package_name) {
		
	    String query = "SELECT  * FROM " + appTable;
	
//	    Log.d(" DB search ",package_name);
	    
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(query, null);
	
	    
	    if (cursor.moveToFirst()) {
	        do {
	           
	            
	           
	            String packagename = cursor.getString(1);
	            int blink_rate  = cursor.getInt(3);
//	            Log.d("DB",packagename+" "+package_name.equals(packagename));
	            
	            if(package_name.equals(packagename)){
	            	
//	            	Log.d("blink rate value",""+blink_rate);
	            	return blink_rate;
	            }
	            	
	            
	        } while (cursor.moveToNext());
	    }
	    db.close();
//	    Log.d("blink rate value","blank");
	    return 0;
	}
}

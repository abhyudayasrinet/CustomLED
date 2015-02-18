
package com.ggwp.customled;

import java.util.List;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.ggwp.customled.R;


public class ListViewAdapter extends ArrayAdapter<ListClass> {

	Context context;
	 
    public ListViewAdapter(Context context, int resourceId,
            List<ListClass> items) {
        super(context, resourceId, items);
        this.context = context;
    }
     
    /*private view holder class*/
    private class ViewHolder {
        ImageView AppIcon;
        TextView AppTitle;
    }
     
    
    //http://stackoverflow.com/questions/10120119/how-does-the-getview-method-work-when-creating-your-own-custom-adapter
    //Explanation
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	ViewHolder holder = null;
        ListClass rowItem = getItem(position);
         
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.AppTitle = (TextView) convertView.findViewById(R.id.app_name);
            holder.AppIcon = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        Drawable icon = null;
        

        if(rowItem.getAppName().equals("Battery Charging")) {
        	
        	Drawable myDrawable = context.getResources().getDrawable(R.drawable.battery_charging);
        	holder.AppTitle.setText(rowItem.getAppName());
	        holder.AppIcon.setImageDrawable(myDrawable);      	
        	
        }
        else if(rowItem.getAppName().equals("Battery Full")) {
        	
        	Drawable myDrawable = context.getResources().getDrawable(R.drawable.battery_full);
        	holder.AppTitle.setText(rowItem.getAppName());
	        holder.AppIcon.setImageDrawable(myDrawable);      	
        	
        }
        else if(rowItem.getAppName().equals("Battery Low")) {
        	
        	Drawable myDrawable = context.getResources().getDrawable(R.drawable.battery_low);
        	holder.AppTitle.setText(rowItem.getAppName());
	        holder.AppIcon.setImageDrawable(myDrawable);      	
        	
        }
        else {
        	try {
    			
           	 icon = context.getPackageManager().getApplicationIcon(rowItem.getPackageName());
   			
   		} catch (NameNotFoundException e) {
   			e.printStackTrace();
   		}
        	
	        holder.AppTitle.setText(rowItem.getAppName());
	        holder.AppIcon.setImageDrawable(icon);
        }
         
        return convertView;
    }

}


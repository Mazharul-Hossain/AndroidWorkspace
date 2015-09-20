package com.fest.project;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BatteryLavel extends Activity {
    /** Called when the activity is first created. */
   
	private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context c, Intent i) {
			
			int level = i.getIntExtra("level", 0);
			
			ProgressBar pb = (ProgressBar) findViewById(R.id.progressbar);
			pb.setProgress(level);
			
			TextView tv = (TextView) findViewById(R.id.textfield);
			tv.setText("Battery Level: " + Integer.toString(level) + "%");
			
		}

	};
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_battery);
        registerReceiver(mBatInfoReceiver, new IntentFilter(
				Intent.ACTION_BATTERY_CHANGED));
        
        
    }
}
package com.fest.project;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.net.TrafficStats;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class CalcDataUsage extends Service {
	
	String info= "";
	
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		super.onStartCommand(intent, flags, startId);
		Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();
		doSomethingRepeatedly();
		return START_STICKY;
	}
	
	
	public void doSomethingRepeatedly() {
		Timer timer  = new Timer();
		timer.scheduleAtFixedRate( new TimerTask() {
			
			@Override
			public void run() {
				info += "Mobile Interface:\n";
		        info += ("\tReceived: " + TrafficStats.getMobileRxBytes() + " bytes / " + TrafficStats.getMobileRxPackets() + " packets\n");
		        info += ("\tTransmitted: " + TrafficStats.getMobileTxBytes() + " bytes / " + TrafficStats.getMobileTxPackets() + " packets\n");
		         
		        info += "All Network Interface:\n";
		        info += ("\tReceived: " + TrafficStats.getTotalRxBytes() + " bytes / " + TrafficStats.getTotalRxPackets() + " packets\n");
		        info += ("\tTransmitted: " + TrafficStats.getTotalTxBytes() + " bytes / " + TrafficStats.getTotalTxPackets() + " packets\n");
		        
		        Log.d("Data usage:",info);
			}
		}, 0, 1000);
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}

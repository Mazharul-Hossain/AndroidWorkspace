package com.mazhar.androidalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class GiveAwayAlarmIBReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		Log.d("Alarm", "Alarm is received");

		// Toast.makeText(context, "Rise and Shine!", Toast.LENGTH_LONG).show();

		Intent i = new Intent();
		i.setClassName("com.mazhar.androidalarm", "com.mazhar.androidalarm.MainActivity");
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
	}
}

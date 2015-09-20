package com.bd.trackingstolenphone.talemul;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBroadcastreceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		Intent startServiceIntent = new Intent(context, MainService.class);
		context.startService(startServiceIntent);
	}
}
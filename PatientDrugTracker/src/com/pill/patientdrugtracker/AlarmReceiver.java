package com.pill.patientdrugtracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(final Context context, Intent arg1) 
	{
		String Med_name = arg1.getStringExtra("NM");//arg1.getExtras().getString("NM");
		String Med_VPID = arg1.getStringExtra("VPPID");//arg1.getExtras().getString("VPPID");
		 	
		Intent intent = new Intent(context, AlertDialogActivity.class); 
		intent.putExtra("NM",Med_name);
		intent.putExtra("VPPID",Med_VPID);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
		context.startActivity(intent); 
 		 
	} 
}

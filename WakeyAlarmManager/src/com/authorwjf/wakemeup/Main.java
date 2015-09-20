package com.authorwjf.wakemeup;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class Main extends Activity implements OnClickListener {
	final static private long ONE_SECOND = 1000;
	final static private long TWENTY_SECONDS = ONE_SECOND * 20;

	PendingIntent pi;
	BroadcastReceiver br;
	AlarmManager am;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setup();
		findViewById(R.id.the_button).setOnClickListener(this);
	}

	private void setup() {
		br = new BroadcastReceiver() {
			@Override
			public void onReceive(Context c, Intent i) {
				Toast.makeText(c, "Rise and Shine!", Toast.LENGTH_LONG).show();
			}
		};
		registerReceiver(br, new IntentFilter("com.authorwjf.wakeywakey"));
		pi = PendingIntent.getBroadcast(this, 0, new Intent("com.authorwjf.wakeywakey"),
				0);
		am = (AlarmManager) (this.getSystemService(Context.ALARM_SERVICE));
	}

	@Override
	public void onClick(View v) {
		am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()
				+ TWENTY_SECONDS, pi);
	}

	@Override
	protected void onDestroy() {
		am.cancel(pi);
		unregisterReceiver(br);
		super.onDestroy();
	}
}

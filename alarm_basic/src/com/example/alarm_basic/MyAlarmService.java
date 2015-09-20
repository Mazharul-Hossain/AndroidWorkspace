package com.example.alarm_basic;

import android.app.Service;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

public class MyAlarmService extends Service {

	@Override
	public void onCreate() {

		// TODO Auto-generated method stub

		Toast.makeText(this, "MyAlarmService.onCreate()", Toast.LENGTH_LONG).show();

	}

	@Override
	public IBinder onBind(Intent intent) {

		// TODO Auto-generated method stub

		Toast.makeText(this, "MyAlarmService.onBind()", Toast.LENGTH_LONG).show();

		return null;

	}

	@Override
	public void onDestroy() {

		// TODO Auto-generated method stub

		super.onDestroy();

		Toast.makeText(this, "MyAlarmService.onDestroy()", Toast.LENGTH_LONG).show();

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		Log.d("AlarmService", "Alarm Starting to ring");

		Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
		Ringtone r = RingtoneManager.getRingtone(getBaseContext(), alert);
		if (r == null) {
			alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			r = RingtoneManager.getRingtone(getBaseContext(), alert);
			if (r == null) {
				alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
				r = RingtoneManager.getRingtone(getBaseContext(), alert);
			}
		}
		if (r != null)
			r.play();
		Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

		if (vibrator != null) {
			vibrator.vibrate(400);
			Log.d("AlarmService", "Vibration finished");
		}

		Toast.makeText(this, "MyAlarmService.onStart()", Toast.LENGTH_LONG).show();

		return Service.START_STICKY;

	}

	@Override
	public boolean onUnbind(Intent intent) {

		// TODO Auto-generated method stub

		Toast.makeText(this, "MyAlarmService.onUnbind()", Toast.LENGTH_LONG).show();

		return super.onUnbind(intent);

	}

}
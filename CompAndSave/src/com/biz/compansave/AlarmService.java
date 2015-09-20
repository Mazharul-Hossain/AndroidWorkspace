package com.biz.compansave;

import java.util.Calendar;

import com.biz.compandsave.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

public class AlarmService extends Service {

	@Override
	public void onCreate() {

		// TODO Auto-generated method stub
	}

	@Override
	public IBinder onBind(Intent intent) {

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDestroy() {

		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		Log.d("AlarmService", "Alarm Starting to ring");
		/****************************/

		Calendar calendar = Calendar.getInstance();
		int thisDay = calendar.get(Calendar.DAY_OF_MONTH);
		int id = thisDay - 16;
		String key = "list_" + id;

		DateSharedPref dsf = new DateSharedPref(this);
		boolean bool = dsf.loadData(key);
		Uri alert = null;

		if (bool) {
			alert = RingtoneManager.getDefaultUri(R.raw.bloom);// strong
																// alarm
																// sound

		} else {
			alert = RingtoneManager.getDefaultUri(R.raw.turn);// weak alarm
																// sound
		}

		Ringtone r = RingtoneManager.getRingtone(getBaseContext(), alert);
		if (r == null) {
			alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
			r = RingtoneManager.getRingtone(getBaseContext(), alert);
			if (r == null) {
				alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
				r = RingtoneManager.getRingtone(getBaseContext(), alert);
				if (r == null) {
					alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
					r = RingtoneManager.getRingtone(getBaseContext(), alert);
				}
			}
		}
		if (r != null) {
			r.play();
			Log.d("AlarmService", "Ringtone finished");
		}
		Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

		if (vibrator != null) {
			vibrator.vibrate(400);
			Log.d("AlarmService", "Vibration finished");
		}

		showNotification();
		return Service.START_STICKY;
	}

	@Override
	public boolean onUnbind(Intent intent) {

		// TODO Auto-generated method stub
		return super.onUnbind(intent);
	}

	@SuppressWarnings("deprecation")
	public void showNotification() {
		NotificationManager notificationManager = (NotificationManager) getBaseContext()
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(android.R.drawable.stat_notify_more,
				"CAS Giftbox:Win amazing gifts by buying ink today!", System.currentTimeMillis());

		Intent notificationIntent = new Intent(getBaseContext(), SplashScreenActivity.class);

		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);

		PendingIntent intent = PendingIntent.getActivity(getBaseContext(), 0,
				notificationIntent, 0);

		notification.setLatestEventInfo(getBaseContext(), "CAS Giftbox",
				"Check here to win gift!", intent);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notificationManager.notify(0, notification);
	}
}
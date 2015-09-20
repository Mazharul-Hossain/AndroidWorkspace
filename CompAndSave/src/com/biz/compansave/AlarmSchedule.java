package com.biz.compansave;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmSchedule {

	private static final String TAG = "Alarm";
	Activity activity;

	public AlarmSchedule(Activity activity) {
		this.activity = activity;
	}

	public void AlarmSetter() {
		for (int i = 16; i <= 31; i++) {
			int id = i * 10 + 1;
			setAlarm(i, id, 9, 0);

			id = i * 10 + 2;
			setAlarm(i, id, 6, 30);
		}
	}

	public void setAlarm(int day, int id, int hour, int min) {

		Intent myIntent = new Intent(activity, AlarmService.class);

		PendingIntent pendingIntent = PendingIntent.getService(activity, id, myIntent, 0);

		AlarmManager alarmManager = (AlarmManager) activity
				.getSystemService(Context.ALARM_SERVICE);

		Calendar calendar = Calendar.getInstance();
		Log.d(TAG, "Alarm is setting : " + calendar.getTimeInMillis());

		calendar.set(Calendar.YEAR, 2013);
		calendar.set(Calendar.MONTH, 11);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, min);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
				pendingIntent);

		Log.d(TAG, "Alarm Started : " + calendar.getTimeInMillis());
	}
}

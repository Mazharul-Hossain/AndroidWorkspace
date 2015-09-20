package com.mazhar.androidalarm;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class GiveAwayAlarm extends Activity {

	int Dec_1 = 335;

	int morning = 9;
	int evening = 18;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		function();

		Log.d("Alarm", "Alarm is starting");
		setAlarm(Dec_1, morning, 30);
		// setAlarm(Dec_1, evening, 30);
		// finish();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void setAlarm(int day, int hour, int min) {

		int id = 0;

		Calendar cur_cal = new GregorianCalendar();
		cur_cal.setTimeInMillis(System.currentTimeMillis());// set the current
															// time and date for
															// this calendar

		Calendar cal = new GregorianCalendar();
		/*
		 * cal.add(Calendar.DAY_OF_YEAR, day); cal.set(Calendar.HOUR_OF_DAY,
		 * hour); cal.set(Calendar.MINUTE, min); cal.set(Calendar.SECOND, 0);
		 * cal.set(Calendar.MILLISECOND, 0);
		 */
		cal.add(Calendar.DAY_OF_YEAR, cur_cal.get(Calendar.DAY_OF_YEAR));
		cal.set(Calendar.HOUR_OF_DAY, cur_cal.get(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cur_cal.get(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cur_cal.get(Calendar.SECOND) + 15);
		cal.set(Calendar.MILLISECOND, cur_cal.get(Calendar.MILLISECOND));
		cal.set(Calendar.DATE, cur_cal.get(Calendar.DATE));
		cal.set(Calendar.MONTH, cur_cal.get(Calendar.MONTH));

		Intent intent = new Intent(this, GiveAwayAlarmIBReceiver.class);
		PendingIntent pintent = PendingIntent.getBroadcast(this, id, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		alarm.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pintent);

		Log.d("Alarm", "Alarm is set");
	}

	public void function() {
		String TAG = "Calander";
		Calendar calendar = Calendar.getInstance();

		int thisYear = calendar.get(Calendar.YEAR);
		Log.d(TAG, "# thisYear : " + thisYear);

		int thisMonth = calendar.get(Calendar.MONTH);
		Log.d(TAG, "@ thisMonth : " + thisMonth);

		int thisDay = calendar.get(Calendar.DAY_OF_MONTH);
		Log.d(TAG, "$ thisDay : " + thisDay);

		if (thisYear == 2024 && thisMonth == 0 && thisDay == 1) {
			Log.d(TAG, "Voila !!");
		} else
			Log.d(TAG, "Koila !!");
	}
}

package com.paresh.gridviewexample;

import java.util.ArrayList;

import android.app.Activity;
import android.content.SharedPreferences;

public class DateSharedPref {

	String KEY = "MYPREFKEY";
	SharedPreferences dateChoice;

	private final Activity activity;

	public DateSharedPref(Activity activity) {
		this.activity = activity;
	}

	public void saveArray(ArrayList<Boolean> list) {

		SharedPreferences settings = activity.getSharedPreferences(KEY, 0);
		SharedPreferences.Editor editor = settings.edit();

		int size = list.size();
		editor.putInt("list_size", size);

		for (int i = 0; i < size; i++) {
			editor.remove("list_" + i);
		}
		for (int i = 0; i < size; i++) {
			editor.putBoolean("list_" + i, list.get(i));
		}
		editor.commit();
	}

	public ArrayList<Boolean> loadArray() {

		SharedPreferences file = activity.getSharedPreferences(KEY, 0);
		ArrayList<Boolean> list = new ArrayList<Boolean>();
		int size = file.getInt("list_size", 0);

		for (int i = 0; i < size; i++) {
			boolean bool = file.getBoolean("list_" + i, false);
			list.add(bool);
		}
		return list;
	}
}

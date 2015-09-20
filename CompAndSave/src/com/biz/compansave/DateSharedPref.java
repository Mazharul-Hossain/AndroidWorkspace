package com.biz.compansave;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;

public class DateSharedPref {

	String KEY = "MYPREFKEY";
	SharedPreferences dateChoice;

	private final Context activity;

	public DateSharedPref(Context activity) {
		this.activity = activity;
	}

	public void saveChoices(HashMap<Integer,Boolean> choices) {

		SharedPreferences settings = activity.getSharedPreferences(KEY, 0);
		SharedPreferences.Editor editor = settings.edit();

		for(Integer key : choices.keySet())
		{
			editor.putBoolean("list_" + key, choices.get(key));
		}
		
		editor.commit();
	}

	public Boolean check_empty() {
		SharedPreferences sPrefs = activity.getSharedPreferences("MYPREFKEY", 0);

		int size = 11;

		for (int i = 0; i < size; i++) {
			Boolean bCheck = sPrefs.getBoolean("list_" + i, false);
			if (bCheck == true)
				return false;

		}
		return true;

	}

	public HashMap<Integer,Boolean> getPrevChoices() {

		SharedPreferences file = activity.getSharedPreferences(KEY, 0);
		HashMap<Integer,Boolean> choices = new HashMap<Integer, Boolean>();
		
		for(String key : file.getAll().keySet())
		{
			boolean value = file.getBoolean(key, false);
			Integer intKey = Integer.parseInt(key.substring(key.indexOf("_")+1,key.length()));
			choices.put(intKey, value);
		}
		return choices;
	}

	public Boolean loadData(String key) {

		SharedPreferences file = activity.getSharedPreferences(KEY, 0);
		boolean bool = file.getBoolean(key, false);
		return bool;
	}
}

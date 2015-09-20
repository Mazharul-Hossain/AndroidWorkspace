package com.pill.patientdrugtracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Settings extends Activity implements View.OnClickListener
{
	TextView sett_logout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
	
		sett_logout = (TextView) findViewById(R.id.sett_logout);
		sett_logout.setOnClickListener(this);
	}
	
	 public void destroy_sharedPrefernces() 
	 {
		 SharedPreferences preferences = getSharedPreferences("Login Credentials",Context.MODE_PRIVATE);
		 SharedPreferences.Editor editor = preferences.edit();
		 editor.clear();
		 editor.commit();
	 }
	 
	@Override
	public void onClick(View v) 
	{
		if( v == sett_logout)
		{
			destroy_sharedPrefernces();
			Intent act = new Intent(Settings.this , login.class);
			startActivity(act);
			Settings.this.finish();
		}
	}
}

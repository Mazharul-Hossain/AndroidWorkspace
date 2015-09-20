package com.pill.patientdrugtracker;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class profile_patient<SplashScreenActivity> extends Activity implements View.OnClickListener{

	ImageView prof_image_pat,profile_pat_med_img,prof_settings;
	TextView prof_text_pat_choose,profile_pat_med_text;
	String mon[]={"Jan","Feb","March","April","May","June","July","Aug","Sep","Oct","Nov","Dec"};
	String day_name[]={"Saturday","Sunday","Monday","Tuesday","Wednesday","Thursday","Friday"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_patient);
		 
		
		prof_image_pat = (ImageView) findViewById(R.id.prof_image_pat);
		prof_image_pat.setOnClickListener(this);
		
		prof_text_pat_choose = (TextView) findViewById(R.id.prof_text_pat_choose);
		prof_text_pat_choose.setOnClickListener(this);
		
		String name_of_pat= "Hello "+getIntent().getExtras().getString("extra");
		prof_text_pat_choose.setText(name_of_pat);
		
		profile_pat_med_img = (ImageView) findViewById(R.id.profile_pat_med_img);
		profile_pat_med_img.setOnClickListener(this);
		
		profile_pat_med_text = (TextView) findViewById(R.id.profile_pat_med_text);
		profile_pat_med_text.setOnClickListener(this);
		
		prof_settings = (ImageView) findViewById(R.id.prof_settings);
		prof_settings.setOnClickListener(this);
		
		set_name();
		
	}
	public void set_name()
	{
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR); // get the current year
		int month = cal.get(Calendar.MONTH); // month...
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int day_no = cal.get(Calendar.DAY_OF_WEEK);//DAY_OF_WEEK
		  
		TextView prof_pat_day_of_month = (TextView) findViewById(R.id.prof_pat_day_of_month);
		prof_pat_day_of_month.setText(day+"");  //prof_pat_mon_year
		
		TextView prof_pat_mon_year = (TextView) findViewById(R.id.prof_pat_mon_year);
		prof_pat_mon_year.setText(mon[month]+" "+year);  //prof_pat_mon_year
		
		TextView prof_pat_day_of_week = (TextView) findViewById(R.id.prof_pat_day_of_week);
		prof_pat_day_of_week.setText(day_name[day_no-1]);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_splash_screen, menu);
		return true;
	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		if( v == prof_image_pat || v == prof_text_pat_choose)
		{
			startActivity(new Intent(profile_patient.this , Choose_patient.class));
			profile_patient.this.finish();	
		}
		else if( v == profile_pat_med_img || v == profile_pat_med_text)
		{
			startActivity(new Intent(profile_patient.this , Search_medicine.class));
			 profile_patient.this.finish();	
		}
		else if( v == prof_settings )
		{
			startActivity(new Intent(profile_patient.this , Settings.class));
		}
		
	}

}

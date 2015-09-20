package com.pill.patientdrugtracker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

public class Take_medicine extends Activity implements View.OnClickListener
{
	Button take_med_btn;
	TextView take_med_start_day_text_2,take_medicine_timeschedule_text_2,take_medicine_dur_text2,take_med_interval2;
	int  year, month,day;
	AlertDialog dialog_for_time_schedule;
	static final int DATE_DIALOG_ID = 999; 
	Intent myIntent;
    PendingIntent pendingIntent;
    int select_radio,interval_rad=0,dur_rad=13;
    AlarmManager alarmManager;
     
	 CharSequence[] duration = {"1 Day","2 Day","3 Day","4 Day","5 week","6 Day","1 Week","2 weeks","3 weeks","1 month","3 month","6 month","1 year","Forever"};
	 
	 CharSequence[] interval = {" Everday "," every 3 day in week "," Every week "," Every month ","Every 3 mondth"};
	 
	 CharSequence[] time_schedule = {" 0.00 AM "," 0.30 AM "," 1.00 AM "," 1.30 AM ","2.00 AM","2.30 AM","3.00 AM","3.30 AM",
			 " 4.00 AM "," 4.30 AM "," 5.00 AM "," 5.30 AM ","6.00 AM","6.30 AM","7.00 AM","7.30 AM",
			 " 8.00 AM "," 8.30 AM "," 9.00 AM "," 9.30 AM ","10.00 AM","10.30 AM","11.00 AM","11.30 AM",
			 " 12.00 PM ","12.30 PM "," 1.00 PM "," 1.30 PM ","2.00 PM","2.30 PM","3.00 PM","3.30 PM",
			 " 4.00 PM "," 4.30 PM "," 5.00 PM "," 5.30 PM ","6.00 PM","6.30 PM","7.00 PM","7.30 PM",
			 " 8.00 PM "," 8.30 PM "," 9.00 PM "," 9.30 PM ","10.00 PM","10.30 PM","11.00 PM","11.30 PM" };
	 ArrayList time_schedule_array=null ;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.take_medicine);
		 
		take_med_btn = (Button) findViewById(R.id.take_med_btn);
		take_med_btn.setOnClickListener(this);
		
		take_med_start_day_text_2 = (TextView) findViewById(R.id.take_med_start_day_text_2);
		take_med_start_day_text_2.setOnClickListener(this);
		
		take_medicine_timeschedule_text_2 = (TextView) findViewById(R.id.take_medicine_timeschedule_text_2);
		take_medicine_timeschedule_text_2.setOnClickListener(this);
		take_medicine_timeschedule_text_2.setText(" Set Time ");
		
		take_medicine_dur_text2 = (TextView) findViewById(R.id.take_medicine_dur_text2);
		take_medicine_dur_text2.setOnClickListener(this);
		take_medicine_dur_text2.setText("Forever");
		
		take_med_interval2= (TextView) findViewById(R.id.take_med_interval2);
		take_med_interval2.setOnClickListener(this);
		 
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		String str = day+"/"+(month+1)+"/"+year;
		take_med_start_day_text_2.setText(str);
	}
	
	public void check_data_and_set_alarm()
	{ 
			List<Calendar> list = new ArrayList<Calendar>();
			
			Calendar calNow = Calendar.getInstance();
			Calendar calSet = (Calendar) calNow.clone();
			calSet.set(Calendar.SECOND, 0);
			calSet.set(Calendar.MONTH,month);
			calSet.set(Calendar.YEAR,year);
			calSet.set(Calendar.DAY_OF_MONTH, day);
			calSet.set(Calendar.MILLISECOND, 0);
			
			 
		 	for (int i = 0; i < time_schedule_array.size(); i++) 
		 	{
		 		int choice = (Integer) time_schedule_array.get(i);
		 		if(choice%2 == 0) //min ache
		 		{
		 			choice--;
		 			choice/=2;
		 			calSet.set(Calendar.HOUR_OF_DAY, choice);
					calSet.set(Calendar.MINUTE, 30);
		 		}
		 		else // min nai.
		 		{
		 			choice/=2;
		 			calSet.set(Calendar.HOUR_OF_DAY, choice);
		 		}
		 		
		 		if(calSet.compareTo(calNow) <= 0)
		 		{
					//Today Set time passed, count to tomorrow
				//	calSet.add(Calendar.DATE, 1);
				}
		 		list.add(calSet);
		 	}
		 	
		 	for(Calendar cal1 : list)
		 	{
		 		setAlarm(cal1);
		 	}
	}
	
	public void setAlarm(Calendar targetCal) 
	{ 
		String Med_name = getIntent().getExtras().getString("NM");
		String Med_VPID = getIntent().getExtras().getString("VPPID");

	 	int interval_var[]={1,3,7,30,90};
		
		Intent intent = new Intent(Take_medicine.this, AlarmReceiver.class);
		intent.putExtra("NM",Med_name);
		intent.putExtra("VPPID",Med_VPID);
		
        PendingIntent sender =  PendingIntent.getBroadcast(Take_medicine.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), 
				(AlarmManager.INTERVAL_DAY*interval_var[interval_rad]), sender);
		
	
	}
	
	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		if( v == take_med_btn)
		{
			if( time_schedule_array != null )
			{
				Intent intent = new Intent(Take_medicine.this, AlarmReceiver.class);
		        PendingIntent sender = PendingIntent.getBroadcast(Take_medicine.this,0, intent, 0);
		             
		        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
		        am.cancel(sender);
		        check_data_and_set_alarm();
		        Toast.makeText(Take_medicine.this, "Alarm is set",Toast.LENGTH_LONG).show();
			    time_schedule_array= null;
			}
			else
			{
				Toast.makeText(Take_medicine.this, " You have to select alarm time",Toast.LENGTH_LONG).show();
		    }
			 
			 SharedPreferences pref = getApplicationContext().getSharedPreferences("Login Credentials", 0); // 0 - for private mode
			 final String user_name = pref.getString("Username", null); // getting String
			 Intent in = new Intent(Take_medicine.this , profile_patient.class);
			 in.putExtra("extra", user_name);
		     startActivity(in);
		 	Take_medicine.this.finish();	
		}
		else if( v == take_med_start_day_text_2)
		{
			showDialog(DATE_DIALOG_ID);
		}
		else if(v == take_medicine_timeschedule_text_2)
		{
			 show_dialog_with_checkbox_ts(time_schedule);   
		}
		else if ( v == take_medicine_dur_text2 )
		{
			mySingleChoiceDialog(duration,"Select time duration of medicine taking");
			dur_rad = select_radio;
		}
		else if ( v == take_med_interval2)
		{
			mySingleChoiceDialog(interval,"Select Interval of medicine taking");
			interval_rad = select_radio;//,dur_rad
		}
	}
	
public void mySingleChoiceDialog(CharSequence[] items,String title){
    	
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle(title);
    
    	builder.setSingleChoiceItems(items, select_radio, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				select_radio = which;
			//	Toast.makeText(Take_medicine.this, "The no of radi button is "+which,Toast.LENGTH_LONG).show();
				dialog.cancel();
			}
		});
    	builder.create().show();
    }
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id ) {
		case DATE_DIALOG_ID:
			// set date picker as current date
			return new DatePickerDialog(this, datePickerListener, year, month,day);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,int selectedMonth, int selectedDay) 
		{
			year=selectedYear;
			month=selectedMonth;
			day=selectedDay;
			
			String dateAndTime=selectedDay+"/"+(selectedMonth+1)+"/"+selectedYear;
			take_med_start_day_text_2.setText(dateAndTime);
		
		}
	};
	
	void show_dialog_with_checkbox_ts(CharSequence[] itelms)
	{ 
		//following code will be in your activity.java file 
		
		final ArrayList seletedItems=new ArrayList();
		               
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Select The Difficulty Level");
		builder.setMultiChoiceItems(itelms, null,
		      new DialogInterface.OnMultiChoiceClickListener() 
		      {
		                 @Override
		                 public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) 
		                 {
		                     if (isChecked) 
		                     {
		                         seletedItems.add(indexSelected);
		                     }
		                     else if (seletedItems.contains(indexSelected)) 
		                     {
		                         seletedItems.remove(Integer.valueOf(indexSelected));
		                     }
		                }
		     }    )
		              // Set the action buttons
		     .setPositiveButton("OK", new DialogInterface.OnClickListener() 
		     {
		                 @Override
		           public void onClick(DialogInterface dialog, int id)
		           { 
		                time_schedule_array = seletedItems;
		           }
		     }
		         )
		    .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
		    {
		                 @Override
		                 public void onClick(DialogInterface dialog, int id)
		                 {
		                	 time_schedule_array = null;
		                 }
		    }   );
		       
		    dialog_for_time_schedule = builder.create();//AlertDialog dialog; create like this outside onClick
		    dialog_for_time_schedule.show();
  }
	
}

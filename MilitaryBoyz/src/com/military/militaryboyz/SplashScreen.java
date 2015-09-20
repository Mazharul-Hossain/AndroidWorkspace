package com.military.militaryboyz;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;

public class SplashScreen extends Activity {
	private SQLiteDatabase db; 
	private String item_name[]={"income_input_data_MISC","incme_inpt_dta_tk_sl_text",
			 "Publicity Media Promotions","Venue","Talent","Security",
			"Catering_or_Food","Equip. Rental","Transportation","Decorations","Misc. Services","Supplies"}; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);
		
		Intent in =  new Intent(getApplicationContext(),Option_choose.class);
		startActivity(in);
		
		create_database();
		for(int i = 0;i<item_name.length;i++)
			insert_into__database(item_name[i],i+1);
		
	}
	
	public void create_database()
    {
    	db = openOrCreateDatabase("ITEM_database", MODE_PRIVATE, null);
		db.execSQL("create table   if not exists ITEM_table ( item_details VARCHAR ,item_name VARCHAR ,deposit int , budget int , actual int,item_no INTEGER );"); 
		db.execSQL("create table if not exists ITEM_MENU_TABLE ( Menu_name VARCHAR Primary Key ,item_no INTEGER );"); 		
		db.close(); 
    }
	
	public long insert_into__database(String name,int item_no)
    {
    	db = openOrCreateDatabase("ITEM_database", MODE_PRIVATE, null); 
		
		  // This will get the number of rows
		  ContentValues cv = new ContentValues();
		   
		  cv.put("Menu_name",  name);
		  cv.put("item_no", item_no);
		  
		  long id = db.insert("ITEM_MENU_TABLE", null, cv);
		
		db.close(); 
		return id;
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}

}

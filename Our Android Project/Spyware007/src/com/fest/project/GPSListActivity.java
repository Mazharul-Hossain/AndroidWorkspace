package com.fest.project;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class GPSListActivity extends ListActivity {
	
	SQLiteDatabase db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,this.getAllGpsData());
		this.setListAdapter(arrayAdapter);
		
		
	}
	
	public String[] getAllGpsData() {
		//this function will return a cursor containing all data from the database
		db = openOrCreateDatabase("GPS_DATABASE", MODE_PRIVATE , null);
		Cursor cursor = db.rawQuery("Select * from gps_data_table", null);
		String []resultStrings = null;
		
		if (cursor != null ) 
		{
			int sizeOfArray = cursor.getCount();
			resultStrings = new String[sizeOfArray]; 
		   cursor.moveToFirst();
		   int i=0;
		   do{
			   String lat = cursor.getString(cursor.getColumnIndex("lat"));
			   String _long = cursor.getString(cursor.getColumnIndex("long"));
			   String acc = cursor.getString(cursor.getColumnIndex("acc"));
			   String prov = cursor.getString(cursor.getColumnIndex("prov"));
			   String time = cursor.getString(cursor.getColumnIndex("time"));
			   
			   resultStrings[i] = lat+" "+_long+" "+acc+" "+prov+" "+time;
			   i++;
		   }while (cursor.moveToNext());
		   cursor.close();
		}
		
		return resultStrings;
	}
}


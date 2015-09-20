package com.fest.project;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;

public class CallHistoryListActivity extends ListActivity {
	
	String []logHistories=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//Cursor logCursor = managedQuery( CallLog.Calls.CONTENT_URI,null, null,null, null);
		String orderByClause = CallLog.Calls.DATE+" DESC";
		Cursor logCursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, orderByClause);
		int sizeOfArray = logCursor.getCount();
		logHistories = new String[sizeOfArray];
		
		int i=0;
		while ( logCursor.moveToNext() ) {
			String phoneNumber = logCursor.getString( logCursor.getColumnIndex( CallLog.Calls.NUMBER ) );
			String callType = logCursor.getString( logCursor.getColumnIndex( CallLog.Calls.TYPE ) );
			String callDate = logCursor.getString( logCursor.getColumnIndex( CallLog.Calls.DATE) );
			//experimental
			long seconds=Long.parseLong(callDate);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy HH:mm",Locale.US);
            String callDayTime = formatter.format(new Date(seconds));
			//experimental
			//Date callDayTime = new Date(Long.valueOf(callDate));
			String callDuration = logCursor.getString( logCursor.getColumnIndex( CallLog.Calls.DURATION) );
			
			//we can show the name of this contact if we want, if it exists in contacts list
			String nameOfContact = getNameForThisNumber(phoneNumber);
			//Then, Show this name instaed of the number
			
			String direction = null;
			int dircode = Integer.parseInt( callType );
			switch( dircode ) {
					case CallLog.Calls.OUTGOING_TYPE:
					direction = "OUT";
					break;

					case CallLog.Calls.INCOMING_TYPE:
					direction = "IN";
					break;
		
					case CallLog.Calls.MISSED_TYPE:
					direction = "MISSED";
					break;
			}
			String durationOfThisCall = null;
			if(Integer.parseInt(callDuration)<60){
				//call duration is less than 1 minute
				durationOfThisCall = callDuration+"sec(s)";
			}else{
				//call duration is more than 1 minute, of course
				int callDurationInMinute = Integer.parseInt(callDuration);
				callDurationInMinute = callDurationInMinute/60;
				durationOfThisCall = callDurationInMinute+"min(s)" ;
				
			}
			
			if(nameOfContact!=null){ phoneNumber = nameOfContact; }
			logHistories[i] = phoneNumber+" "+direction+" "+callDayTime+" "+durationOfThisCall;
			
		i++;
		}
		
		logCursor.close();
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,logHistories);
		this.setListAdapter(arrayAdapter);
	}
	
	private String getNameForThisNumber(String phoneNumber) {
		Cursor contactslist = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, 
				new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME},
				ContactsContract.CommonDataKinds.Phone.NUMBER+ "=?",
				new String[]{phoneNumber},null);
		
		if(contactslist.moveToNext()) {
			return contactslist.getString(contactslist.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)) ;
		}
		return null;
	}

}

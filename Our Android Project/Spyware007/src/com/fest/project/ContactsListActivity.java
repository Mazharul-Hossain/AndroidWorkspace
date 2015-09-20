package com.fest.project;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.Data;
import android.widget.ArrayAdapter;

public class ContactsListActivity extends ListActivity {
	
	String []contacts= null;
	final String[] projection = new String[] {
			 Email.DATA,
			 Email.TYPE
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Cursor contactslist = getContentResolver().query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
				null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
		
		int sizeOfArray = contactslist.getCount();
		contacts = new String[sizeOfArray];
		
		int i=0;
		while (contactslist.moveToNext()){
			
			String contactId = contactslist.getString(contactslist.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
			
			String name = contactslist.getString(contactslist.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

			String phoneNumber = contactslist.getString(contactslist.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			
			String emailAddress = "";
			
			//Cursor email = managedQuery(Email.CONTENT_URI,projection,Data.CONTACT_ID + "=?",new String[]{String.valueOf(contactId)},null);
			Cursor email = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,projection,Data.CONTACT_ID + "=?",new String[]{String.valueOf(contactId)},null);
			if(email.moveToNext()){
				 emailAddress = email.getString(email.getColumnIndex(Email.DATA));
			}
			email.close();
			contacts[i] = name+" "+phoneNumber+" "+emailAddress;
			i++;
			//how can i get e-mail ID???
			
			
		}
		
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,contacts);
		this.setListAdapter(arrayAdapter);
	}

}

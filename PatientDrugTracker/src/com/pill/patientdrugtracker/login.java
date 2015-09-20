package com.pill.patientdrugtracker;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class login extends Activity implements View.OnClickListener
{

	Button register,login;
    private SQLiteDatabase db;
    TextView status ;
    SharedPreferences sh_Pref; 
    Editor toEdit;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.log_in_screen);
	 
		create_database();
		
		register = (Button) findViewById(R.id.register);
		register.setOnClickListener(this);
		login   = (Button) findViewById(R.id.login);
		login.setOnClickListener(this);
	    status = (TextView) findViewById(R.id.status);
		
	}
	
	public void create_database()
    {
    	db = openOrCreateDatabase("Patient_Care_database", MODE_PRIVATE, null);
		db.execSQL("create table if not exists Patient_table ( user_name VARCHAR Primary Key ,passwrd VARCHAR,patient_name VARCHAR ,patient_proff VARCHAR );"); 
		
		Cursor getStar = db.rawQuery("SELECT * FROM Patient_table  WHERE patient_name = 'Me'" , null);
		if (getStar.getCount() <= 0) { // This will get the number of rows
		    ContentValues cv = new ContentValues();
			cv.put("patient_name", "Me");
			cv.put("patient_proff", "Anonymous");
			db.insert("Patient_table", null, cv);
		}
		
		 
		db.close(); 
    }
	
	 public void save_sharedPrefernces(String user_name) 
	 {
	        sh_Pref = getSharedPreferences("Login Credentials", Context.MODE_PRIVATE);
	        toEdit = sh_Pref.edit();
	        toEdit.putString("Username", user_name);
	        toEdit.commit();
	 }
	 
	

 
	private void attempt_to_login(String user,String pass)
	{
		db = openOrCreateDatabase("Patient_Care_database", MODE_PRIVATE, null);
		String rawstr="SELECT * FROM Patient_table  WHERE user_name = '"+user+"' and passwrd = '"+pass+"';";
		Cursor getStar = db.rawQuery(rawstr , null);
		getStar.moveToFirst();
		if (getStar.getCount() <= 0) { // This will get the number of rows
		     status.setText("Wrong username or Password Combination!Try again.");
        	db.close(); 
		}
		else
		{
			
			String name =  getStar.getString(getStar.getColumnIndex("patient_name")); 
			String user_name = getStar.getString(getStar.getColumnIndex("patient_name")) ;
			save_sharedPrefernces(user_name);
			Intent act = new Intent(login.this , profile_patient.class);;
			act.putExtra("extra", name);
			startActivity(act);
			login.this.finish();
			db.close(); 
		} 
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_splash_screen, menu);
		return true;
	}

	@Override
	public void onClick(View v)
	{
		if( v == register)
		{
			status.setText("");
			Intent act = new Intent(login.this , Register.class);
			startActivity(act);
			login.this.finish();
		}
		else if(v==login)
		{
			EditText Ledituname = (EditText) findViewById(R.id.Ledituname);
			EditText Leditpw = (EditText) findViewById(R.id.Leditpw);
			attempt_to_login(Ledituname.getText().toString(),Leditpw.getText().toString());
			
		}
	}

}

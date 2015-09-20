package com.pill.patientdrugtracker;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends Activity implements View.OnClickListener{

	EditText pat_name,pat_proff,user_name,pass,ag_pass;
	Button submit,cancel;
    private SQLiteDatabase db;
    TextView reg_status;
    String error_msg="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		
		load_view();
	}
	
	void load_view()
	{
		submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener(this);
		
		cancel = (Button) findViewById(R.id.cancel);
		cancel.setOnClickListener(this);
		
		pat_name = (EditText) findViewById(R.id.ename);//
		pat_proff = (EditText) findViewById(R.id.eproof);
		user_name = (EditText) findViewById(R.id.reuname);
		pass = (EditText) findViewById(R.id.repass);
		ag_pass = (EditText) findViewById(R.id.ag_pass);
		reg_status = (TextView)findViewById(R.id.reg_status);
	}
	
	boolean check_username(String user)
	{
		if(user.length() != 0)
		{
			db = openOrCreateDatabase("Patient_Care_database", MODE_PRIVATE, null);
			String rawstr="SELECT * FROM Patient_table  WHERE user_name = '"+user+"';";
			
			Cursor getStar = db.rawQuery(rawstr , null);
			if (getStar.getCount() > 0)
			{ // This will get the number of rows
				db.close();
				error_msg+="User name is not available";
				return false;
			}
			else
			{
				db.close(); 
				return true;
			}
			
		}
		else
		{
			error_msg+="Enter valid user name"; 
			return false;
		}
		
	}
	
	boolean check_pass(String pass,String ag_pass)
	{
		if(pass.length() == 0 || ag_pass.length() == 0)
		{
			error_msg+=" \n Enter valid password"; 
			return false;
		}
		else
		{
			if( pass.equals(ag_pass) )
			{
				return true;
			}
			else
			{
				error_msg+=" \n password does not match"; 
				return false;
			}
		}
	}
	
	boolean check_others(String patient_name,String patient_proff)
	{
		if(patient_name.trim().length() == 0 )
		{
			error_msg+="\nEnter valid Patient Name";
			Toast.makeText(Register.this, "The length of string is "+patient_name.trim().length(), Toast.LENGTH_LONG).show();
			return false;
		}
		if(patient_proff.trim().length() == 0 )
		{
			error_msg+="\nEnter valid Patient profession";
			return false;
		}
		return true;
	}
	
	public long insert_into__database(String name,String proff,String user,String pass)
    {
    	db = openOrCreateDatabase("Patient_Care_database", MODE_PRIVATE, null); 
		
		  // This will get the number of rows
		  ContentValues cv = new ContentValues();
		  cv.put("patient_name", name);
		  cv.put("patient_proff", proff);//user_name
		  cv.put("user_name", user);
		  cv.put("passwrd", pass);
		  long id = db.insert("Patient_table", null, cv);
		
		db.close();
		return id;
    }
	
	void check_validation(String patient_name,String patient_proff,String user_name,String pass,String ag_pass)
	{
	  if(check_others(patient_name,patient_proff) && check_username(user_name) && check_pass(pass,ag_pass))
	  { 
		  if( insert_into__database(patient_name,patient_proff,user_name,pass) != -1 )
		  {
			  Toast.makeText(Register.this,"Registration is successful",Toast.LENGTH_LONG).show();
			  Intent act = new Intent(Register.this , login.class);;
		      startActivity(act);
			  Register.this.finish();  
		  }
		  else
		  {
			  reg_status.setText(error_msg);
			  error_msg="";
		  }
		  
	  }	
	  else
	  {
		  reg_status.setText(error_msg); 
		  error_msg="";
	  }
	}
	
	@Override
	public void onClick(View v) 
	{ 
		if( v ==submit )
		{
			String pat_nam = pat_name.getText().toString();
			String patient_proff = pat_proff.getText().toString();
			String u_name = user_name.getText().toString();
			String u_pass = pass.getText().toString();
			String u_ag_pass = ag_pass.getText().toString();
			check_validation(pat_nam,patient_proff,u_name,u_pass,u_ag_pass);
			
		}
		else if(v == cancel)
		{
			Intent act = new Intent(Register.this , login.class);;
			//	act.putExtra("extra", name);
				startActivity(act);
				Register.this.finish();
		}
	}

}

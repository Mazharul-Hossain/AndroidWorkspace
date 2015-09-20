package com.military.militaryboyz;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Mail_send extends Activity implements View.OnClickListener
{
	String fName;
	Button send_mail;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mail_send);
		
		Intent intent = getIntent();

	   fName = intent.getStringExtra("file_name"); 
		
		send_mail =(Button) findViewById(R.id.email_screen_send_mail);
		send_mail.setOnClickListener(this);
	}
	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		if( v == send_mail)
		{
			EditText sub=(EditText) findViewById(R.id.email_screen_sub);
			EditText rec = (EditText) findViewById(R.id.email_screen_mail);
			EditText mail = (EditText) findViewById(R.id.email_screen_mail_desc);
			
			String to=rec.getText().toString().trim();
			String subj=sub.getText().toString().trim();
			String msg=mail.getText().toString().trim();
			
			if(to.length() < 1)
			{
				Toast.makeText(getApplicationContext(), "Please Enter Recipent Email", Toast.LENGTH_LONG).show();
			}
			else if (subj.length() < 1) {
				Toast.makeText(getApplicationContext(), "Please Enter Subject", Toast.LENGTH_LONG).show();
			}
			else if (msg.length() < 1) {
				Toast.makeText(getApplicationContext(), " Please Enter Message", Toast.LENGTH_LONG).show();
			}
			else {
				
				Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND); 
			    emailIntent.setType("image/jpeg");
			    emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{to}); 
			    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subj); 
			    emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, msg); 
			    emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(fName));
			    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
			    Mail_send.this.finish() ;
			}	 
		}
			
		}
	}


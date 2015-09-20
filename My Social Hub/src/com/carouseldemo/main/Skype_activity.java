package com.carouseldemo.main;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Skype_activity extends Activity
{
	RadioButton radioCallType;
	RadioGroup radioGroupId;
	protected void onCreate(android.os.Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        final Dialog dialog9 = new Dialog(Skype_activity.this);
		dialog9.setContentView(R.layout.dialog_for_skype);
		dialog9.setTitle("Call to friends using skype : ");

        radioGroupId = (RadioGroup) dialog9.findViewById(R.id.radio_call_type);
	 	Button dialogButton9 = (Button) dialog9.findViewById(R.id.call_skype_user);
		Button cancelButton9 = (Button) dialog9.findViewById(R.id.skype_cancel);
		
		dialogButton9.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				
				int selectedOption = radioGroupId.getCheckedRadioButtonId();
			 	radioCallType = (RadioButton) findViewById(selectedOption);
			 	// Toast.makeText(Skype_activity.this, radioCallType.getText(), Toast.LENGTH_SHORT).show();
			 	EditText user_name = (EditText) findViewById(R.id.skype_user_name) ;
			 	String u_name = user_name.getText().toString();
			 	skype_call(u_name, getApplicationContext()) ;
				dialog9.dismiss();
			}
		});
		
		cancelButton9.setOnClickListener( new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				dialog9.cancel();
			}	
		});
		dialog9.show();
	}
	
	public static void skype_call(String number, Context ctx) 
	{
        try 
        {
            Intent sky = new Intent("android.intent.action.VIEW");
            sky.setData(Uri.parse("skype:" + number));
            Log.d("UTILS", "tel:" + number);
            ctx.startActivity(sky);
        }
        catch (ActivityNotFoundException  e)
        {
            Log.e("SKYPE CALL", "Skype failed", e);
        }
    } 
}

package com.carouseldemo.main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class WhatsAPP_Activity extends Activity
{
	protected void onCreate(android.os.Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        final Dialog dialog9 = new Dialog(WhatsAPP_Activity.this);
		dialog9.setContentView(R.layout.whatsapp_dialog_for_msg);
		dialog9.setTitle("Send Message...");
	
	 	Button dialogButton9 = (Button) dialog9.findViewById(R.id.ok);
		Button cancelButton9 = (Button) dialog9.findViewById(R.id.cancel);
		
		dialogButton9.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				
				EditText editText = (EditText) dialog9.findViewById(R.id.mText1);
				Intent waIntent = new Intent(Intent.ACTION_SEND);
			    waIntent.setType("text/plain") ;
			           
			    waIntent.setPackage("com.whatsapp");
			    if (waIntent != null)
			    {
			        waIntent.putExtra(Intent.EXTRA_TEXT, editText.getText().toString());
			        startActivity(Intent.createChooser(waIntent, "share"));
			    }
				dialog9.dismiss();
			}
		});
		
		cancelButton9.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				dialog9.cancel();
			}
			
		});
		
		
		dialog9.show();
        
	};
}

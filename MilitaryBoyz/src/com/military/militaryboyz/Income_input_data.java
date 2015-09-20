package com.military.militaryboyz;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Income_input_data extends Activity implements View.OnClickListener 
{	
	private SQLiteDatabase db;
	TextView Ticket_sales,MISC;
	Dialog myDialog;
	Button add_and_save_button,finishbtn,back_to_main_menu;
	String name,bdg,act ;
	int item_no;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.income_input_data);
		Ticket_sales = (TextView) findViewById(R.id.incme_inpt_dta_tk_sl_text);
		Ticket_sales.setOnClickListener(this);
		
		MISC = (TextView) findViewById(R.id.income_input_data_MISC);
		MISC.setOnClickListener(this);
		
		back_to_main_menu = (Button) findViewById(R.id.back_to_main_menu);
		back_to_main_menu.setOnClickListener(this);
		
	}
	
	public long insert_into__database(String Item_name ,int Budget , int Actual, int Item_no  )
    {
    	db = openOrCreateDatabase("ITEM_database", MODE_PRIVATE, null); 
		
		  // This will get the number of rows
		  ContentValues cv = new ContentValues();
		  
		  cv.put("item_name",  Item_name);
		  cv.put("budget", Budget);
		  cv.put("actual",  Actual);
		  cv.put("item_no", Item_no);
		  cv.put("item_details", " ");
		  cv.put("deposit",0);
		  
		  long id = db.insert("ITEM_table", null, cv);
		
		db.close(); 
		return id;
    }

	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		if( v ==  finishbtn)
		{
			myDialog.cancel();
		}
		else if( v == Ticket_sales)
		{
			item_no=1;
			SHOW_DIALOG();
			 
		}
		else if( v == MISC)
		{
			item_no=2;
			SHOW_DIALOG();
			
		}
		else if(v == back_to_main_menu)
		{
			Intent in = new Intent(getApplicationContext(),  Option_choose.class);
			startActivity(in);
		}
	}
	
	public void SHOW_DIALOG()
	{
		myDialog = new Dialog(Income_input_data.this);
        myDialog.setContentView(R.layout.input_screen_data_dialog);
        myDialog.setTitle("Enter Author Name Of Book");
              
         add_and_save_button = (Button)myDialog.findViewById(R.id.add_and_save);
         add_and_save_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{ 
				EditText input_data_name_etext = (EditText) myDialog.findViewById(R.id.input_data_name_etext);
		        EditText input_data_budget_etext = (EditText) myDialog.findViewById(R.id.input_data_budget_etext);
		        EditText input_data_act_etext = (EditText) myDialog.findViewById(R.id.input_data_act_etext);
		        
		        name = input_data_name_etext.getText().toString();
	        	bdg = input_data_budget_etext.getText().toString();
	        	act = input_data_act_etext.getText().toString();
	        	
	        	int bdg_var,act_var,det_issue ;
	        	
	        	  if(bdg.matches("")  )
	            	 {
		            	 bdg_var = 0;
	            	 }
		             else
		            	 bdg_var = Integer.parseInt(bdg);
		             
		             if(act.matches("")  )
	            	 {
		            	 act_var = 0;
	            	 }
		             else
		            	 act_var = Integer.parseInt(act);
		             
		              
	        	if( !name.matches("") )
			    { 	
					insert_into__database(name,bdg_var, act_var,item_no);
					Toast.makeText(Income_input_data.this, "Data is inserted into database Successfully",Toast.LENGTH_LONG).show();
			    }
				else
				{
		        	name = "" ; 
		        	bdg="";
		        	act="";
		        	Toast.makeText(Income_input_data.this, "Please enter item name",Toast.LENGTH_LONG).show();
				} 
				myDialog.cancel();
			}
		});
       
        finishbtn = (Button)myDialog.findViewById(R.id.cancel_btn);
        finishbtn.setOnClickListener(this);
        myDialog.show();
	} 
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}


}

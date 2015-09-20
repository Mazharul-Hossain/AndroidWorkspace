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

public class Expense_input_data extends Activity  implements View.OnClickListener 
{
	TextView Promotion,Venue,Talent,Security,Cat_food,Rental,Trnasportation,Decoration,MISC_Sevice,Supplies;
	Dialog myDialog;
	Button add_and_save_button,finishbtn,back_to_main_menu;
	String name,bdg,act,deposit,detail ;
	int item_no;
	private SQLiteDatabase db; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expense_input_data);
		
		get_view_from_layout();
	}
	
	void get_view_from_layout()
	{ 
		Promotion = (TextView) findViewById(R.id.expense_PMP);
		Promotion.setOnClickListener(this);
		
		Venue = (TextView) findViewById(R.id.expense_venue);
		Venue.setOnClickListener(this);
		
		Talent = (TextView) findViewById(R.id.expense_talent);
		Talent.setOnClickListener(this);
		
		Security = (TextView) findViewById(R.id.expense_security);
		Security.setOnClickListener(this);
	
		Cat_food = (TextView) findViewById(R.id.expense_Cat_food);
		Cat_food.setOnClickListener(this);
		
		Rental = (TextView) findViewById(R.id.expense_rental);
		Rental.setOnClickListener(this);
		
		Trnasportation = (TextView) findViewById(R.id.expense_trans);
		Trnasportation.setOnClickListener(this);
		
		Decoration = (TextView) findViewById(R.id.expense_decoration);
		Decoration.setOnClickListener(this);
		
		MISC_Sevice = (TextView) findViewById(R.id.expense_MISC_Service);
		MISC_Sevice.setOnClickListener(this);
		
		Supplies = (TextView) findViewById(R.id.expense_supplies);
		Supplies.setOnClickListener(this);
		
		back_to_main_menu = (Button) findViewById(R.id.back_to_main_menu);
		back_to_main_menu.setOnClickListener(this);
	}

	 @Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		if( v == add_and_save_button)
		{
			
		}
		else if( v ==  finishbtn)
		{
			myDialog.cancel();
		}
		else if( v == Promotion)
		{
			item_no=3;
			SHOW_DIALOG();

		}
		else if( v == Venue)
		{
			item_no =4;
			SHOW_DIALOG();
		}
		else if( v == Talent)
		{
			item_no=5;
			SHOW_DIALOG();
		}
		else if( v == Security)
		{
			item_no = 6 ;
			SHOW_DIALOG();
		}
		else if( v == Cat_food)
		{
			item_no = 7;
			SHOW_DIALOG();
		}
		else if( v == Rental)
		{
			item_no = 8 ;
			SHOW_DIALOG();
		}
		else if( v == Trnasportation)
		{
			item_no = 9;
			SHOW_DIALOG();
		}
		else if( v == Decoration)
		{
			item_no = 10;
			SHOW_DIALOG();
		}
		else if( v == Supplies)
		{
			item_no = 12 ;
			SHOW_DIALOG();
		}
		else if( v == MISC_Sevice)
		{
			item_no = 11;
			SHOW_DIALOG();
		}
		else if(v == back_to_main_menu)
		{
			Intent in = new Intent(getApplicationContext(), Option_choose.class);
			startActivity(in);
		}  
	}
	 
	 private long insert_into__database(String detail, String name,
				int bdg_var, int act_var, int item_no, int deposit_issue)
		{
			// TODO Auto-generated method stub
		
			db = openOrCreateDatabase("ITEM_database", MODE_PRIVATE, null); 
			
			  // This will get the number of rows
			  ContentValues cv = new ContentValues();
			  
			  cv.put("item_name",   name);
			  cv.put("budget", bdg_var);
			  cv.put("actual",  act_var);
			  cv.put("item_no", item_no);
			  cv.put("item_details", detail);
			  cv.put("deposit",deposit_issue);
			  
			  long id = db.insert("ITEM_table", null, cv);
			
			db.close(); 
			return  id;

		}
	 
 	public void SHOW_DIALOG()
	{
		myDialog = new Dialog(Expense_input_data.this);
        myDialog.setContentView(R.layout.expense_screen_data_dialog);
        myDialog.setTitle("Enter Author Name Of Book");
              
         add_and_save_button = (Button)myDialog.findViewById(R.id.expense_add_and_save);
         add_and_save_button.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText expense_data_details_etext = (EditText) myDialog.findViewById(R.id.expense_data_details_etext);
				EditText expense_data_name_etext = (EditText) myDialog.findViewById(R.id.expense_data_name_etext);
		        EditText expense_data_budget_etext = (EditText) myDialog.findViewById(R.id.expense_data_budget_etext);
		        EditText expense_data_act_etext = (EditText) myDialog.findViewById(R.id.expense_data_act_etext);
		        EditText expense_data_d_issues_etext = (EditText) myDialog.findViewById(R.id.expense_data_d_issues_etext);
	           	        
	             name = expense_data_name_etext.getText().toString();
	             bdg = expense_data_budget_etext.getText().toString();
	             act = expense_data_act_etext.getText().toString();
	             detail =expense_data_details_etext.getText().toString();
	             deposit=expense_data_d_issues_etext.getText().toString();	
	             
            	 int bdg_var,act_var,deposit_issue;
            	 
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
	             
	             if(deposit.matches("")  )
            	 {
	            	 deposit_issue = 0;
            	 }
	             else
	            	 deposit_issue = Integer.parseInt(deposit);
	             
	             
	             if(  !detail.matches("") )
				 { 	
	            	 
						insert_into__database(detail,name,bdg_var,act_var,item_no,deposit_issue);
				}
				else
				 {
			        	name = "" ; 
			        	bdg="";
			        	act="";
				 } 
			        
					myDialog.cancel();
	            
				myDialog.cancel();
				
			}

			
		});
       
        finishbtn = (Button) myDialog.findViewById(R.id.expense_cancel_btn);
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

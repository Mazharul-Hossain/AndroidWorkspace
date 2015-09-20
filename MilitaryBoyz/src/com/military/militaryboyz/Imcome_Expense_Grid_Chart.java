package com.military.militaryboyz;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Imcome_Expense_Grid_Chart extends Activity implements View.OnClickListener
{
	ImageView income_image,ticket_image,expense_image,chart_image;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.income_expense_grid_chart);
		income_image = (ImageView) findViewById(R.id.iegc_income);
		 income_image.setOnClickListener(this);
		
		expense_image = (ImageView) findViewById(R.id.iegc_expense);
		expense_image.setOnClickListener(this);
		//expense_image 
		ticket_image = (ImageView) findViewById(R.id.iegc_ticket);
		ticket_image.setOnClickListener(this);
		
		chart_image = (ImageView) findViewById(R.id.iegc_chart);
		chart_image.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}

	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		if( v == income_image )
		{
			Intent in = new Intent(getApplicationContext(), Income_input_data.class);
			startActivity(in);
			
		}
		else if( v == ticket_image )
		{
			
		}
		else if( v == expense_image )
		{
			Intent in = new Intent(getApplicationContext(), Expense_input_data.class);
			startActivity(in);
		}
		else if( v == chart_image )
		{
			
		}
		
	}

}

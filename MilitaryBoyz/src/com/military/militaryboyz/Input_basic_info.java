package com.military.militaryboyz;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class Input_basic_info extends Activity implements View.OnClickListener
{
	Button in_bs_info_btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.input_basic_info);
		in_bs_info_btn = (Button) findViewById(R.id.in_bs_info_btn);
		in_bs_info_btn.setOnClickListener(this);
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
		if( v == in_bs_info_btn )
		{
			Intent in = new Intent(getApplicationContext(), Imcome_Expense_Grid_Chart.class);
			startActivity(in);
		}
	}

}

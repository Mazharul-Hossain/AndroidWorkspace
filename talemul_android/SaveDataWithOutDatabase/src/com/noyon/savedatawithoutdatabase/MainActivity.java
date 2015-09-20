package com.noyon.savedatawithoutdatabase;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	EditText data;
	TextView result;
	Button btnSave, btnDisplay;
	
	public static String filename = "SharedString";
	SharedPreferences spData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		spData = getSharedPreferences(filename, 0);
		
		data = (EditText) findViewById(R.id.data);
		result = (TextView) findViewById(R.id.result);
		btnSave = (Button) findViewById(R.id.btnSave);
		btnDisplay = (Button) findViewById(R.id.btnDisplay);
		
		btnSave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				String str = data.getText().toString();
				SharedPreferences.Editor editor = spData.edit();
				editor.putString("sharedData", str);
				editor.commit();
			}
		});
		
		
		btnDisplay.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				spData = getSharedPreferences(filename, 0);
				String strResult = spData.getString("sharedData", "Not yet Saved");
				result.setText(strResult);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}

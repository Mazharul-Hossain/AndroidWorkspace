package com.bd.trackingstolenphone.talemul;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class usersetting extends Activity implements OnClickListener {

	private Button btnsubmit;
	private EditText etpass, etpasscon, etto, etsender, etspass;
	public static String filename = "user29";
	SharedPreferences spData;
	public String s,a,b,c,d,e;
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.usersetting);

		btnsubmit = (Button) findViewById(R.id.btnsubmit);
		btnsubmit.setOnClickListener(this);
		
		 etpass=(EditText)findViewById(R.id.etpass);
		 etspass=(EditText)findViewById(R.id.etspass);
		 etpasscon=(EditText)findViewById(R.id.etpasscon);
		 etto=(EditText)findViewById(R.id.etto);
		 etsender=(EditText)findViewById(R.id.etsender);
		 
			spData = getSharedPreferences(filename, 0);
		//spData = getSharedPreferences(filename, 0);
			//String e = spData.getString("stpass", "");
			
			//spData = getSharedPreferences(filename, 0);
		String f = spData.getString("stsender", "");
			//spData = getSharedPreferences(filename, 0);
			String g = spData.getString("stspass", "");
			//spData = getSharedPreferences(filename, 0);
		String h = spData.getString("stto", "");
		 
		 etspass.setText(g);
		 etto.setText(h);
		 etsender.setText(f);
		 		 
		 
		 
		 
		 

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	
			
			a=etpass.getText().toString();
			b=etsender.getText().toString();
			c=etspass.getText().toString();
			d=etto.getText().toString();
			s=a+b+c+d;
			//Toast.makeText(getApplicationContext(), " setting "+s+"   ", Toast.LENGTH_LONG)
			//.show();
				
			try {
				//da.open();
			//da.insertsetting(a,d,b,c);
			//da.getsetting();
		
				spData = getSharedPreferences(filename, 0);
				SharedPreferences.Editor editor = spData.edit();
				editor.putString("stpass", a);
				editor.putString("stsender", b);
				editor.putString("stspass", c);
				editor.putString("stto", d);			
				
				editor.commit();
				
				//spData = getSharedPreferences(filename, 0);
				//String e = spData.getString("stpass", "Not yet Saved");
				//spData = getSharedPreferences(filename, 0);
			//	String f = spData.getString("stsender", "Not yet Saved");
				//spData = getSharedPreferences(filename, 0);
				//String g = spData.getString("stspass", "Not yet Saved");
				//spData = getSharedPreferences(filename, 0);
			//	String h = spData.getString("stto", "Not yet Saved");
				
				//s=e+f+g+h;
				//Toast.makeText(getApplicationContext(), " save data "+s+"   ", Toast.LENGTH_LONG).show();
			
			
			Intent intent = new Intent(usersetting.this, settings.class);

			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
		} catch (Exception e) {

		}

	}

}

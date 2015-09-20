package com.fest.project;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Cpu_usage_process extends Activity {
	/** Called when the activity is first created. */

	ListView lv;
	String[] name_process;
	int[] percentage;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cpu_usage_process_layout);

		lv = (ListView) findViewById(R.id.listView1);
		name_process = new String[20];
		percentage=new int[20];

	}

	public void updateBtn(View v) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			// -m 10, how many entries you want, -d 1, delay by how much, -n 1,
			// number of iterations
			Process p = Runtime.getRuntime().exec("top -m 25 -d 1 -n 1");

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			
			int i = 0;
			String line = reader.readLine();
			String token="";
			
			
			while (line != null) {
				Log.e("Output "+i, token);
				list.add(line);
				line = reader.readLine();
				i++  ;
				
		/*		if(i==3){
					int row=1;
					int length=0;
					java.util.StringTokenizer tokenizer = new java.util.StringTokenizer(line, " %");
					while (tokenizer.hasMoreTokens()) {
					     token = tokenizer.nextToken();
					     if(row%3==1)
					    	 name_process[length]=new String(token);
					     else if(row%3==2)
					    	 percentage[length]=Integer.parseInt(token);
					   //  Toast.makeText(this, "The token is "+i, Toast.LENGTH_LONG).show();
						    
					//     Toast.makeText(this, "The token is "+percentage[length], Toast.LENGTH_LONG).show();
					     row++;
					     length++;
					}
				}  */
				
			}

			p.waitFor();

			Toast.makeText(getBaseContext(), "Got update",Toast.LENGTH_SHORT)
					.show();

		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(getBaseContext(), "Caught", Toast.LENGTH_SHORT)
					.show();
		}

		// display info
		lv.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list));

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int pos,
					long id) {
				// do nothing
			}
		});
	}

}
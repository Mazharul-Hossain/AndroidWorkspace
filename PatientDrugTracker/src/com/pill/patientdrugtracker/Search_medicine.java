package com.pill.patientdrugtracker;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Search_medicine extends Activity implements View.OnClickListener,AdapterView.OnItemClickListener{

	private static String url = "http://www.technopeers.eu/drug/search.php";
	ListView lv;
	Search_medicine_adapter adapter;
	boolean success,no_match;
	
	private static final String TAG_MEDICINE_NAME = "AMPP"; 
	private static final String TAG_NM = "NM";
	private static final String TAG_ENTRY_ID = "ENTRY_ID";
	
	ArrayList<String> name_of_medicine;
	ArrayList<String> id_of_medicine;
	
	EditText search_pat_edit;
	Button search_pat_btn;
	 
	JSONArray contacts = null;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_medicine);
		
		lv = (ListView) findViewById(R.id.listView1);
		lv.setOnItemClickListener(this);
		no_match=false;
		
		search_pat_edit=(EditText) findViewById(R.id.search_pat_edit);
		search_pat_btn = (Button) findViewById(R.id.search_pat_btn);
		search_pat_btn.setOnClickListener(this);
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		// TODO Auto-generated method stub
		String name = ((TextView) view.findViewById(R.id.textView1)).getText().toString();
		String med_id = adapter.getItem_id(position);
		
		Intent in = new Intent(getApplicationContext(), Info_of_medicine.class);
		in.putExtra(TAG_NM, name);
		in.putExtra(TAG_ENTRY_ID, med_id);
		startActivity(in);
		
	}

	@Override
	public void onClick(View v) 
	{
	 	if(v == search_pat_btn )
	 	{
	 		ExportDatabaseCSVTask task=new ExportDatabaseCSVTask(Search_medicine.this);
			task.execute();
			
	 	}
	}
	
	public class ExportDatabaseCSVTask extends AsyncTask<String, Void, Boolean>
	 { 
	    private final ProgressDialog dialog = new ProgressDialog(Search_medicine.this);
	    private Search_medicine search_medicine ;
	    public ExportDatabaseCSVTask(Search_medicine search_medicine) {
            this.search_medicine=search_medicine;
        }
		
	    @Override
	    protected void onPreExecute() 
	    { 
	    	this.dialog.setMessage("Exporting Info...");
	        this.dialog.show();
	        no_match =false;
	    }
	    
	    protected Boolean doInBackground(final String... args)
	    {

	 		String med_name_from_text = search_pat_edit.getText().toString();
	 	
	 		JSONParser jParser = new JSONParser();
	 		String keys[]={"search_key"};
	 		String values[]={med_name_from_text};
			JSONObject json = jParser.getJSONFromUrl(url,keys,values);
			if(json != null)
			{
				try 
				{
					contacts = json.getJSONArray(TAG_MEDICINE_NAME);
					 name_of_medicine = new ArrayList<String>();
					 id_of_medicine = new ArrayList<String>();
					 
					for(int i = 0; i < contacts.length(); i++)
					{
						JSONObject c  = contacts.getJSONObject(i);
						String name  = c.getString(TAG_NM);
						String id  = c.getString(TAG_ENTRY_ID);
						name_of_medicine.add(name);
						id_of_medicine.add(id);
					}
					
					success= true;
					return success; 
					
				} 
				catch (JSONException e) 
				{
					success = false;
					e.printStackTrace();
					return success;
				}
		 	}
			else
			{
				success = true;
				no_match=true;
				return success;
			}
	    } 
	    
	    @Override
	    protected void onPostExecute(final Boolean success)	
	    {
	    	if (this.dialog.isShowing())
	        {
	            this.dialog.dismiss();
	        }
	    	if(no_match)
	    	{
	    		Toast.makeText(Search_medicine.this, " Export failed!", Toast.LENGTH_SHORT).show();
	 	    }
	    	else if (success && !no_match)
	        { 
	        	adapter = new Search_medicine_adapter(search_medicine, name_of_medicine,id_of_medicine);
			    lv.setAdapter(adapter); 
	        }
	        else if( !success )
	        {
	        	 Toast.makeText(Search_medicine.this, "Sorry!There is no medicine starting with this name!", Toast.LENGTH_SHORT).show();
	        }
	         
	       
	    }
	} 

}
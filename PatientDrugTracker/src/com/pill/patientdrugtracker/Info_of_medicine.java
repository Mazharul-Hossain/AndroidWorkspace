package com.pill.patientdrugtracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Info_of_medicine  extends Activity implements View.OnClickListener {
	
	// JSON node keys
	
	private static final String TAG_NM = "NM";
	private static final String TAG_APPID = "APPID";
	private static final String TAG_VPPID = "VPPID";
	private static final String TAG_APID = "APID";
	private static final String TAG_LEGAL_CATCD = "LEGAL_CATCD";
	private static final String TAG_SUBP = "SUBP";
	private static final String TAG_DISCCD = "DISCCD";
	private static final String TAG_DISCDT = "DISCDT";
	private static final String TAG_ENTRY_ID = "ENTRY_ID";
	private boolean success_v;
	
	private static String  url = "http://www.technopeers.eu/drug/search_id.php";
 	private static final String TAG_MEDICINE_NAME = "AMPP";
	
	TextView info_med_name,info_med_APPID,info_med_VPPID, info_med_APID,
	info_med_LEGAL_CATCD,info_med_SUBP, info_med_DISCCD , info_med_DISCDT;
	JSONArray contacts = null;
	Button info_of_medicine_btn;
	String med_id,name_med ,VPPID_med,APID_med,  APPID_med ,LEGAL_CATCD_med ,SUBP_med , DISCCD_med ,DISCDT_med ;
	   
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_of_medicine);
        
        ImportInfoOfMedicine info_medicine = new ImportInfoOfMedicine();
        info_medicine.execute();
        

        info_of_medicine_btn = (Button) findViewById(R.id.info_of_medicine_btn);
        info_of_medicine_btn.setOnClickListener(this);
        
        // getting intent data
       
         
    }
	
	public class ImportInfoOfMedicine extends AsyncTask<String, Void, Boolean>
	 { 
	    private final ProgressDialog dialog = new ProgressDialog(Info_of_medicine.this);
	     
	   
	    
	    @Override
	    protected void onPreExecute() 
	    { 
	    	this.dialog.setMessage("Exporting Info...");
	        this.dialog.show();
	        
	        Intent in = getIntent();
	         // Get JSON values from previous intent 
	         med_id = in.getStringExtra(TAG_ENTRY_ID); 
	         
	         // Displaying all values on the screen
	         info_med_name = (TextView) findViewById(R.id.info_med_name_1);
	         info_med_APPID = (TextView) findViewById(R.id.info_med_APPID_2); 
	         info_med_VPPID = (TextView) findViewById(R.id.info_med_VPPID_2);
	         info_med_APID = (TextView) findViewById(R.id.info_med_APID_2); 
	         info_med_LEGAL_CATCD = (TextView) findViewById(R.id.info_med_LEGAL_CATCD_2);
	         info_med_SUBP = (TextView) findViewById(R.id.info_med_SUBP_2); 
	         info_med_DISCCD = (TextView) findViewById(R.id.info_med_DISCCD_2);
	         info_med_DISCDT = (TextView) findViewById(R.id.info_med_DISCDT_2); 
	    
	    }
	    
	    protected Boolean doInBackground(final String... args)
	    {
	    	 
	         
	 	 	
	  		JSONParser jParser = new JSONParser();

	  		String keys[]={"entry_id"};
	  		String values[]={med_id};
	  		
	 		JSONObject json = jParser.getJSONFromUrl(url,keys,values);
	 		

	 		try 
	 		{
	 			contacts = json.getJSONArray(TAG_MEDICINE_NAME);
	 			
	 			for(int i = 0; i < contacts.length(); i++)
	 			{ 
	 				JSONObject c  = contacts.getJSONObject(i);
		 			name_med  = c.getString(TAG_NM);
		 			APPID_med  = c.getString(TAG_APPID);
		 			VPPID_med  = c.getString(TAG_VPPID);
		 			APID_med  = c.getString(TAG_APID);
		 			LEGAL_CATCD_med  = c.getString(TAG_LEGAL_CATCD);
		 			SUBP_med  = c.getString(TAG_SUBP);
		 			DISCCD_med  = c.getString(TAG_DISCCD);
		 			DISCDT_med  = c.getString(TAG_DISCDT); 	 
	 			}
	 			return true;
	 			
	 		} 
	 		catch (JSONException e) 
	 		{
	 			e.printStackTrace();
	 			return false;
	 		} 
	    } 
	    
	    @Override
	    protected void onPostExecute(final Boolean success)	
	    {
	    	if (this.dialog.isShowing())
	        {
	            this.dialog.dismiss();
	        }
	    	if(success)
	    	{
	    		success_v = true;
	    		info_med_name.setText("Medicine Name :"+name_med);
	 	        info_med_APPID.setText(APPID_med);
	 	        info_med_VPPID.setText(VPPID_med);
	 	        info_med_APID.setText(APID_med );
	 	        info_med_LEGAL_CATCD.setText(LEGAL_CATCD_med);
	 	        info_med_SUBP.setText( SUBP_med);
	 	        info_med_DISCCD.setText(DISCCD_med);
	 	        info_med_DISCDT.setText(DISCDT_med); 
	    	}
	    	else
	    	{
	    		success_v = false;
	    		Toast.makeText(getApplicationContext(), "Sorry the data import process is not done . " +
	    				"Please check your net connection", Toast.LENGTH_LONG).show();
	    	}
	       
	    }
	} 

	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		if( v == info_of_medicine_btn && success_v)
		{
			Intent in = new Intent(getApplicationContext(), Take_medicine.class);
			in.putExtra(TAG_NM, name_med);
			in.putExtra(TAG_VPPID, VPPID_med);
			startActivity(in);
		}
		else
		{
			Toast.makeText(Info_of_medicine.this,"Sorry there is problem . ",Toast.LENGTH_LONG).show();
			Intent in = new Intent(getApplicationContext(), profile_patient.class);
			startActivity(in);	
			Info_of_medicine.this.finish();
		}
	}
}

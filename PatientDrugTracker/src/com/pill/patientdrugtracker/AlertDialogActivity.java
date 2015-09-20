package com.pill.patientdrugtracker;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

public class AlertDialogActivity extends Activity 
{
	private static String url = "http://www.technopeers.eu/drug/review.php"; 
	
@Override
protected void onCreate(Bundle savedInstanceState) 
{
    super.onCreate(savedInstanceState );
    SharedPreferences pref = getApplicationContext().getSharedPreferences("Login Credentials", 0); // 0 - for private mode
    final String user_name = pref.getString("Username", null); // getting String
    
    String Med_name = getIntent().getExtras().getString("NM");
	final String Med_VPID = getIntent().getExtras().getString("VPPID");
	
	
	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder
        .setTitle("Remainder to tkae medicine")
        .setMessage("It is your time to take medicine which name is \n"+Med_name)
        .setCancelable(false)
        .setPositiveButton("Take medicine", new DialogInterface.OnClickListener() 
        {
            public void onClick(DialogInterface dialog, int id) 
            {
            	ReceiveACKMSG task=new ReceiveACKMSG(AlertDialogActivity.this,user_name,Med_VPID);
        		task.execute();
                dialog.cancel();
                AlertDialogActivity.this.finish();
            }
        })
        .setNegativeButton("Do not take", new DialogInterface.OnClickListener() 
        {
            public void onClick(DialogInterface dialog, int id) 
            {
                AlertDialogActivity.this.finish();
                dialog.cancel();
            }
        });
    AlertDialog alert = builder.create();
    alert.show();
	}



	public class ReceiveACKMSG extends AsyncTask<String, Void, Boolean>
	 { 
	    private Context activty ; 
	    JSONObject json ;
	    JSONParser jParser ;
	    String user_name,Med_VPID;
	    public ReceiveACKMSG(Context arg0,String user_name,String Med_VPID) 
	    {
	    	this.activty = arg0; 
	    	this.user_name=user_name;
	    	this.Med_VPID=Med_VPID;
	    }
	     
		
	    @Override
	    protected void onPreExecute() 
	    {  
	    	jParser = new JSONParser();
	    }
	    
	    protected Boolean doInBackground(final String... args)
	    {
	    	
	    	String keys[]={"IMEI_no","username","medicine_id","comment"};
	    	String values[]={"0123456789",user_name,Med_VPID,"Good"};
			
			 json = jParser.getJSONFromUrl(url,keys,values);
	 		 boolean success = true;
	 		 
	 		return success; 
	    } 
	    
	    @Override
	    protected void onPostExecute(final Boolean success)	
	    {
	    	/* if (this.dialog.isShowing())
	        {
	            this.dialog.dismiss();
	        } */
	        if (success)
	        { 
	        	 Toast.makeText(activty,json.toString(), Toast.LENGTH_LONG).show();
		 		 
	        }
	        else
	        {
	             
	        }  
	       
	    }
	}



}
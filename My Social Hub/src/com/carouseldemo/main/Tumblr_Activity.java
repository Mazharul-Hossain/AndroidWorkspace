package com.carouseldemo.main;

import java.util.List;

import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.types.TextPost;
import com.tumblr.jumblr.types.User;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Tumblr_Activity extends Activity
{

	EditText title,body,tag;
	protected void onCreate(android.os.Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState); 
        

		final Dialog dialog5 = new Dialog(Tumblr_Activity.this);
		
		dialog5.setContentView(R.layout.dialog_for_tumblr);
		dialog5.setTitle("Status Update...");
		

		Button dialogButton5 = (Button) dialog5.findViewById(R.id.ok);
		Button cancelButton5 = (Button) dialog5.findViewById(R.id.cancel);
		
		
		
		dialogButton5.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) {
				
				title = (EditText) dialog5.findViewById(R.id.editText1);
				body = (EditText) dialog5.findViewById(R.id.editText2);
				tag = (EditText) dialog5.findViewById(R.id.editText3);
				
				ExportDatabaseCSVTask t=new ExportDatabaseCSVTask();
				t.execute("");
				dialog5.dismiss();
				
			}
		});
		
		cancelButton5.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog5.cancel();
			}
			
		});
		
		
		dialog5.show();
		
		     
	}
	
	public class ExportDatabaseCSVTask extends AsyncTask<String, Void, Boolean>
	 { 
	    private final ProgressDialog dialog = new ProgressDialog(Tumblr_Activity.this);
	    User user;
	    JumblrClient client;
	    String a,b,c;
	    int d,e;
	    @Override
	    protected void onPreExecute() 
	    { 
	    	this.dialog.setMessage("Updating Status...");
	        this.dialog.show();
	        
	         client = new JumblrClient( "olBvNLED99Y8XwmvfJIUCA8KejcnI4E6jvEgKMXruQFUtO9Vz4",
					  "IodLporX6WHjC0wBWTWnzhgyJJce717VvsidhBuUMdgYbm9gIx"
					);
					client.setToken(
					  "RlHtZuE73V9gnR0FzbdYjjLbzZEF1DjU5Li5DQUr1cANxb9IC7",
					  "5l0vWJeKUbPcWGYwMPpLWDHHnGoNZ4XoAidVezEiqrsxuouIJ2" );

	    }
	    
	    protected Boolean doInBackground(final String... args)
	    {
	    
			TextPost post;
			try {
				post = client.newPost(client.user().getName(), TextPost.class);
				post.setTitle(title.getText().toString());
				post.setBody(body.getText().toString());
				
				
				String employee =  tag.getText().toString();;
				String delims =  "[ .,?!]+";
				String[] tokens = employee.split(delims);
				
				for(int i=0; i<tokens.length;i++)
					post.addTag(tokens[i]);
				
				post.save();
				
			} 
			catch (IllegalAccessException e1)
			{ 
				e1.printStackTrace();
			}
			catch (InstantiationException e1) 
			{
				e1.printStackTrace();
			} 
	    	return true;
	    } 
	    
	    @Override
	    protected void onPostExecute(final Boolean success)	
	    {
	    	if (this.dialog.isShowing())
	        {
	            this.dialog.dismiss();
	        } 
	    	
	    	boolean flag = false;
	    	
	    	final PackageManager pm = getPackageManager();
			//get a list of installed apps.
			List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
			Intent openStartingPoint = new Intent("example.firstandroid.WEB");
	    	
			for (ApplicationInfo packageInfo : packages) {
				
				String s =  packageInfo.packageName;
				if(s.compareTo("com.tumblr")==0)
				{
					flag = true;
					Intent instagramIntent = getPackageManager().getLaunchIntentForPackage(s);
					startActivity(instagramIntent);
				}
			}
			if(!flag)
			{
				openStartingPoint.putExtra("key", "https://www.tumblr.com/");
				startActivity(openStartingPoint);
			} 
	    }
	} 
}

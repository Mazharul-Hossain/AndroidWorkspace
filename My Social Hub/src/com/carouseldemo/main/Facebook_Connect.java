package com.carouseldemo.main;

import com.easy.facebook.android.apicall.GraphApi;
import com.easy.facebook.android.data.User;
import com.easy.facebook.android.error.EasyFacebookError;
import com.easy.facebook.android.facebook.FBLoginManager;
import com.easy.facebook.android.facebook.Facebook;
import com.easy.facebook.android.facebook.LoginListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

public class Facebook_Connect extends Activity implements LoginListener
{
	public FBLoginManager fbLoginManager;
	 private GraphApi graphApi;
	 

	 public final String FBAPP_ID = "1450386768514153";
	 private User user;
	 //replace it with your own Facebook App ID
	 public boolean fbloginsuccess = false;
	 public String status = "";
	 
	 @Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	 	super.onCreate(savedInstanceState);
	    connectToFacebook();
	    post();
	}
	 
   public void connectToFacebook(){

		  //read about Facebook Permissions here:
		  //http://developers.facebook.com/docs/reference/api/permissions/
		  String permissions[] =
			  {
		    "user_about_me",
		    "user_birthday",
		    "user_checkins",
		    "user_education_history",
		    
		    "email",

		    "read_friendlists",
		    "read_insights",
		    "read_mailbox",
		    "read_requests",
		    "read_stream",
		    "publish_checkins",
		    "publish_stream",

		  };

		  fbLoginManager = new FBLoginManager(this, R.layout.main, FBAPP_ID, permissions);
		  
		  if(fbLoginManager.existsSavedFacebook()){
		   fbLoginManager.loadFacebook();
		   fbloginsuccess = true;
		  }
		  else{
		   fbLoginManager.login();
		  }
	}
	public void post(){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				this);
			final EditText et = new EditText(this);
			
			// set title
			alertDialogBuilder.setTitle("Set Status");

			// set dialog message
			alertDialogBuilder
				.setView(et)
				.setCancelable(false)
				.setPositiveButton("Post",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, close
						// current activity
						status = et.getText().toString();
						Log.d("TAG: ", status);
						LongOperation mytask = new LongOperation();
						mytask.execute("");
					}
				  })
				.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, just close
						// the dialog box and do nothing
						dialog.cancel();
					}
				});

				AlertDialog alertDialog = alertDialogBuilder.create();
           	alertDialog.show();
			
		

			  //fbLoginManager.displayToast("Hey, " + user.getFirst_name() + "! Login success!");
	}

	public void loginSuccess(Facebook facebook) {
		// TODO Auto-generated method stub
		graphApi = new GraphApi(facebook);

		user = new User();
		fbLoginManager.displayToast("Hey, " + "! Login success!");
		post();
		fbloginsuccess = true;
	}

	public void logoutSuccess() {
		// TODO Auto-generated method stub
		fbLoginManager.displayToast("Logout Success!");
	}

	public void loginFail() {
		// TODO Auto-generated method stub
		fbLoginManager.displayToast("Login Epic Failed!");
	}
	private class LongOperation extends AsyncTask<String, Void , String>
	 {
		ProgressDialog progressDialog;
		@Override
		protected String doInBackground(String... params) 
		{
			 try{
				   user = graphApi.getMyAccountInfo();

				   //update your status if logged in
				   graphApi.setStatus(status);
			 } catch(EasyFacebookError e){
				   Log.d("TAG: ", e.toString());
			 }
			
			return null;
		}
		@Override
	    protected void onPostExecute(String result) {  
			//progressDialog.dismiss();
			if(fbloginsuccess)
				fbLoginManager.displayToast("Hey, " + user.getFirst_name() + "! Post success!");
			Log.d("TAG: ", "onPostEx");
	    }

	    @Override
	    protected void onPreExecute() {
	    	Log.d("TAG: ", "onPreEx");
	    //	progressDialog = ProgressDialog.show(getApplicationContext(),"Loading...",  
           //        "Loading data, please wait...", false, true); 
	    }

	    @Override
	    protected void onProgressUpdate(Void... values) 
	    {	    	
	    }
		 
	 }
}

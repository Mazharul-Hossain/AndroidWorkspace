package com.carouseldemo.main;

import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.easy.facebook.android.apicall.GraphApi;
import com.easy.facebook.android.data.User;
import com.easy.facebook.android.error.EasyFacebookError;
import com.easy.facebook.android.facebook.FBLoginManager;
import com.easy.facebook.android.facebook.Facebook;
import com.easy.facebook.android.facebook.LoginListener;


public class fbConnect implements LoginListener{
	
	 public FBLoginManager fbLoginManager;
	 private GraphApi graphApi;
	 

	 private User user;
	 //replace it with your own Facebook App ID
	 public final String FBAPP_ID = "1450386768514153";
	 public boolean fbloginsuccess = false;
	 public String status = "";
	 private Context context;
	 
	 public String permissions[] =
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
	 
	public fbConnect(Context context){
		this.context = context;
	}
	
	public void connectToFacebook(FBLoginManager fbLM){

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

		  //fbLoginManager = new FBLoginManager((Activity) context.getApplicationContext(), R.layout.main, FBAPP_ID, permissions);
		  fbLoginManager = fbLM;
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
				context);
			final EditText et = new EditText(context);
			
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
						LongOperation mytask = new LongOperation();
						mytask.equals("");
					}
				  })
				.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, just close
						// the dialog box and do nothing
						dialog.cancel();
					}
				});
 
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
 
				// show it
				alertDialog.show();
			
		

			  //fbLoginManager.displayToast("Hey, " + user.getFirst_name() + "! Login success!");
	}

	public void loginSuccess(Facebook facebook) {
		// TODO Auto-generated method stub
		graphApi = new GraphApi(facebook);

		user = new User();
		fbLoginManager.displayToast("Hey, " + "! Login success!");
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

		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
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
			
			if(fbloginsuccess)
				fbLoginManager.displayToast("Hey, " + user.getFirst_name() + "! Post success!");
	    }

	    @Override
	    protected void onPreExecute() {
	    	
	    }

	    @Override
	    protected void onProgressUpdate(Void... values) {
	    	
	    }
		 
	 }

}

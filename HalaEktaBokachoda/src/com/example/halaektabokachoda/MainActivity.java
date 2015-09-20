package com.example.halaektabokachoda;

import java.util.List;

import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.provider.ContactsContract.Profile;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	SocialAuthAdapter adapter;
	Profile profileMap;
	List<Photo> photosList;
	boolean flag;
	Button update;
	EditText edit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.temp);

		TextView textview = (TextView) findViewById(R.id.text);
		textview.setText("Welcome to Share Application experiment");

		Button share = (Button) findViewById(R.id.sharebutton);
		share.setText("Share");
		share.setTextColor(Color.WHITE);
		// share.setBackgroundResource(R.drawable.button_gradient);

		adapter = new SocialAuthAdapter(new ResponseListener());

		update = (Button) findViewById(R.id.update);
		edit = (EditText) findViewById(R.id.editTxt);

		adapter.addProvider(Provider.FACEBOOK, R.drawable.facebook);
		adapter.addProvider(Provider.TWITTER, R.drawable.twitter);

		adapter.addProvider(Provider.LINKEDIN, R.drawable.linkedin);

		adapter.addProvider(Provider.MYSPACE, R.drawable.myspace);
		adapter.addProvider(Provider.YAHOO, R.drawable.yahoo);
		adapter.addProvider(Provider.YAMMER, R.drawable.yammer);

		adapter.addProvider(Provider.FOURSQUARE, R.drawable.foursquare);
		adapter.addProvider(Provider.GOOGLE, R.drawable.google);

		adapter.addProvider(Provider.SALESFORCE, R.drawable.salesforce);
		adapter.addProvider(Provider.RUNKEEPER, R.drawable.runkeeper);

		adapter.addCallBack(Provider.FOURSQUARE,
				"http://socialauth.in/socialauthdemo/socialAuthSuccessAction.do");

		adapter.addCallBack(Provider.GOOGLE, "http://socialauth.in/socialauthdemo");

		adapter.addCallBack(Provider.SALESFORCE,
				"https://socialauth.in:8443/socialauthdemo/socialAuthSuccessAction.do");

		adapter.addCallBack(Provider.YAMMER,
				"http://socialauth.in/socialauthdemo/socialAuthSuccessAction.do");

		adapter.enable(share);
	}

	private final class ResponseListener implements DialogListener {

		@Override
		public void onBack() {
			// TODO Auto-generated method stub
			Log.d("Share-Button", "Dialog Closed by pressing Back Key");
		}

		@Override
		public void onCancel() {
			// TODO Auto-generated method stub
			Log.d("ShareButton", "Authentication Cancelled");
		}

		@Override
		public void onComplete(Bundle values) {

			// TODO Auto-generated method stub
			Log.d("ShareButton", "Authentication Successful");

			final String providerName = values.getString(SocialAuthAdapter.PROVIDER);
			Log.d("ShareButton", "Provider Name = " + providerName);
			Toast.makeText(MainActivity.this, providerName + "connected",
					Toast.LENGTH_LONG).show();

			update.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// TODO Auto-generated method stub
					adapter.updateStatus(edit.getText().toString(),
							new MessageListener(), false);

					Toast.makeText(MainActivity.this,
							"Message posted on " + providerName, Toast.LENGTH_LONG)
							.show();
				}
			});
		}

		@Override
		public void onError(SocialAuthError error) {
			// TODO Auto-generated method stub
			Log.d("ShareButton", "Authentication Error: " + error.getMessage());
		}
	}

	private final class MessageListener implements SocialAuthListener<Integer> {

		@Override
		public void onExecute(String provider, Integer t) {

			Integer status = t;

			if (status.intValue() == 200 || status.intValue() == 201
					|| status.intValue() == 204) {

				Toast.makeText(MainActivity.this, "Message posted on " + provider,
						Toast.LENGTH_LONG).show();
			} else {

				Toast.makeText(MainActivity.this, "Message not posted on " + provider,
						Toast.LENGTH_LONG).show();
			}
		}

		@Override
		public void onError(SocialAuthError e) {
			Toast.makeText(MainActivity.this, "Message not posted on due to error",
					Toast.LENGTH_LONG).show();
		}
	}
}

package com.Sultan.notetakingfromvoice;

import android.accounts.Account;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.api.client.googleapis.extensions.android.accounts.GoogleAccountManager;

public class ScreenForOption extends Activity implements View.OnClickListener {
	ImageView give_note, take_note, preferences, help;

	private Account mAccount;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.screen_for_option_layout);

		give_note = (ImageView) findViewById(R.id.option_scr_note_taker);
		take_note = (ImageView) findViewById(R.id.option_scr_Take_note);
		preferences = (ImageView) findViewById(R.id.option_scr_browser);
		help = (ImageView) findViewById(R.id.option_scr_help);
	}

	@Override
	public void onResume() {
		super.onResume();
		int statusCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (statusCode == ConnectionResult.SUCCESS) {

			mAccount = new GoogleAccountManager(this).getAccountByName(PreferenceManager
					.getDefaultSharedPreferences(this).getString(
							"selected_account_preference", ""));
			if (mAccount == null) {

				// Show the Preferences screen.
				startActivity(new Intent(getApplicationContext(), Preferences.class));
			} else {
				give_note.setOnClickListener(this);
				take_note.setOnClickListener(this);
				preferences.setOnClickListener(this);
				help.setOnClickListener(this);
			}
		} else {

			Toast.makeText(this, "No google play service :( ", Toast.LENGTH_LONG).show();

			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse("market://details?id=com.google.android.gms"));
			startActivity(intent);

			finish();
		}
	}

	@Override
	public void onClick(View v) {
		if (v == give_note) {
			Intent intent = new Intent(ScreenForOption.this, SpeechRepeatActivity.class);
			startActivity(intent);
		} else if (v == take_note) {
			Intent intent = new Intent(ScreenForOption.this, NotesList.class);
			startActivity(intent);
		} else if (v == preferences) {
			Intent intent = new Intent(ScreenForOption.this, Preferences.class);
			startActivity(intent);
		} else if (v == help) {
			Intent intent = new Intent(ScreenForOption.this, HelpActivity.class);
			startActivity(intent);
		}
	}
}
package com.TermProject.EbookReader.store;

import android.app.Activity;
import android.os.Bundle;

import com.TermProject.EbookReader.R;

public class Advance_Search extends Activity {

	MyAppMyState appState;

	/************** Override onCreate Method ***************/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.store_adv_search);

		appState = (MyAppMyState) this.getApplicationContext();

		if (appState == null) {
			appState = ((MyAppMyState) getApplicationContext());
			finish();
		} else if (appState.getState().equalsIgnoreCase("LogIn")) {
			finish();
		}
	}

}
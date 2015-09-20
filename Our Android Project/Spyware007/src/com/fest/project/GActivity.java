package com.fest.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class GActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_achart_example);

		Intent achartIntent = new AChartExample().execute(this);
		startActivity(achartIntent);
	}

}

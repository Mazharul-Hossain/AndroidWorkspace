package com.rga.vzw;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.rga.vzw.datatypes.AMPP;

public class TestMain extends ListActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Log.d("main", "ACTIVITY ONCREATE");
		XMLTestApp appState = (XMLTestApp) this.getApplicationContext();
		/*
		 * ArrayList<App> apps = appState.getAppList();
		 * 
		 * // Log.d("main", "apps found" + apps.size()); // List<String> titles
		 * = new ArrayList<String>(apps.size());
		 * 
		 * for (App app : apps) { titles.add(app.getTitle()); Log.d("testmain",
		 * "arrayList>>" + app.getDescription()); }
		 */
		ArrayList<AMPP> ampps = appState.getAmppList();

		List<String> titles = new ArrayList<String>(ampps.size());
		for (AMPP ampp : ampps) {
			titles.add(ampp.getAPPID());
			Log.d("testmain", "arrayList>>" + ampp.getNM());
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.row,
				titles);
		this.setListAdapter(adapter);

	}
}
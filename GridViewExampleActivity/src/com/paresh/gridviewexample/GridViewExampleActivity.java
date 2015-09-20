package com.paresh.gridviewexample;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.Toast;

public class GridViewExampleActivity extends Activity {
	/** Called when the activity is first created. */

	private GridviewAdapter mAdapter;
	private ArrayList<String> listCountry;
	private ArrayList<String> listDate;
	private ArrayList<Integer> listFlag;
	private ArrayList<Boolean> thumbnailsselection;

	int size = 12;

	private GridView gridView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gridviewactivity);

		initSelection();
		prepareList();

		// prepared arraylist and passed it to the Adapter class
		mAdapter = new GridviewAdapter(this, listCountry, listFlag, listDate);

		// Set custom adapter to gridview
		gridView = (GridView) findViewById(R.id.gridView1);
		gridView.setAdapter(mAdapter);

		// Implement On Item click listener
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Toast.makeText(GridViewExampleActivity.this, mAdapter.getItem(position),
						Toast.LENGTH_SHORT).show();
			}
		});

	}

	private void initSelection() {
		thumbnailsselection = new ArrayList<Boolean>();
		boolean bool = false;
		for (int i = 0; i < size; i++) {
			thumbnailsselection.add(bool);
		}

	}

	public void onButtonClicked(View view) {

		switch (view.getId()) {
		case R.id.buttonSave:
			DateSharedPref dsf = new DateSharedPref(this);
			dsf.saveArray(thumbnailsselection);
			startIntent();
			finish();
			break;
		case R.id.buttonRemindLater:
			startIntent();
			finish();
			break;
		}
	}

	public void startIntent() {
		Intent intent = new Intent(GridViewExampleActivity.this,
				GridViewExampleActivity.class);
		startActivity(intent);
	}

	public void onCheckboxClicked(View view) {
		CheckBox cb = (CheckBox) view;
		int id = cb.getId();

		if (thumbnailsselection.get(id)) {
			cb.setChecked(false);
			thumbnailsselection.set(id, false);
		} else {
			cb.setChecked(true);
			Toast.makeText(GridViewExampleActivity.this, mAdapter.getItem(id),
					Toast.LENGTH_SHORT).show();
			thumbnailsselection.set(id, true);
		}
	}

	public void prepareList() {
		listCountry = new ArrayList<String>();

		listCountry.add("$300 in ink");
		listCountry.add("His/Her Gift");
		listCountry.add("iGifts");
		listCountry.add("iPhone 5c");
		listCountry.add("Diamond");
		listCountry.add("HP Laptop");
		listCountry.add("Canon SLR");
		listCountry.add("iPad Air");
		listCountry.add("Swarovski");
		listCountry.add("Xbox 360 + kinect");
		listCountry.add("HomeTheater");
		listCountry.add("50\" Plasma TV");

		listFlag = new ArrayList<Integer>();
		listFlag.add(R.drawable.ink);
		listFlag.add(R.drawable.his_her);
		listFlag.add(R.drawable.igifts);
		listFlag.add(R.drawable.iphone5c);
		listFlag.add(R.drawable.diamond);
		listFlag.add(R.drawable.hp);
		listFlag.add(R.drawable.canon);
		listFlag.add(R.drawable.ipad);
		listFlag.add(R.drawable.set);
		listFlag.add(R.drawable.xbox_kinect);
		listFlag.add(R.drawable.home_theater);
		listFlag.add(R.drawable.lgtv);

		listDate = new ArrayList<String>();

		listDate.add("16-Dec-2013");
		listDate.add("16-Dec-2013");
		listDate.add("16-Dec-2013");
		listDate.add("16-Dec-2013");
		listDate.add("16-Dec-2013");
		listDate.add("16-Dec-2013");
		listDate.add("16-Dec-2013");
		listDate.add("16-Dec-2013");
		listDate.add("16-Dec-2013");
		listDate.add("16-Dec-2013");
		listDate.add("16-Dec-2013");
		listDate.add("16-Dec-2013");
	}

}
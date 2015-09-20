package com.biz.compansave;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;

import com.biz.compandsave.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.Toast;

public class Choose_option_activity extends Activity {
	/** Called when the activity is first created. */

	private GridviewAdapter mAdapter;
	private ArrayList<String> listOfItems;
	private ArrayList<String> listDate;
	private ArrayList<Integer> listOfItemIcons;
	
	
	private ArrayList<String> items = new ArrayList<String>();
	private ArrayList<String> dates = new ArrayList<String>();
	private ArrayList<Integer> icons = new ArrayList<Integer>();
	
	DateSharedPref sharedPref = new DateSharedPref(this);
	

	int size = 12;
	private GridView gridView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.choose_option_layout);

		
		
		Button btn1 = (Button) findViewById(R.id.buttonSave);
		btn1 = (Button) findViewById(R.id.buttonRemindLater);
		
		// prepared arraylist and passed it to the Adapter class
		mAdapter = new GridviewAdapter(this, items, icons, dates);
		

		// Set custom adapter to gridview
		gridView = (GridView) findViewById(R.id.gridView1);
		gridView.setAdapter(mAdapter);

		// Implement On Item click listener
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				mAdapter.setChecked(position,!mAdapter.getChecked(position));
			}
		});
		
		CASGiftBoxApp app= (CASGiftBoxApp)getApplication();
		app.applyTypefaceToAllTextviews(getWindow().getDecorView().findViewById(android.R.id.content));
	}

	private void initSelection() {		
		if(sharedPref.getPrevChoices() !=null)
		{
			mAdapter.setCheckedList(sharedPref.getPrevChoices());
		}
	}

	public void onButtonClicked(View view) {

		switch (view.getId()) {
		case R.id.buttonSave:
			sharedPref.saveChoices(mAdapter.getCheckedList());
			Intent intent = new Intent(Choose_option_activity.this , Thanks_activity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.buttonRemindLater:
			
			 Calendar calendar = Calendar.getInstance();
	    	  int thisYear = calendar.get(Calendar.YEAR);
	    	  int thisMonth = calendar.get(Calendar.MONTH); 
	    	  int thisDay = calendar.get(Calendar.DAY_OF_MONTH);
	    	  
	    	if (thisDay >=16 && thisDay <=31 && thisMonth==11)
			{
					startActivity(new Intent(Choose_option_activity.this , Campaign_started_activity.class));
					Choose_option_activity.this.finish();
			} 
	    	else
	    	{
	    		 
				DateSharedPref dsf1 = new DateSharedPref(this);
				if( dsf1.check_empty() )
				{
				//	Toast.makeText(Choose_option_activity.this, "The list is empty", Toast.LENGTH_LONG).show();
				}
				else
					startIntent();
	    	} 
		//	finish();
			break;
		}
	}

	public void startIntent() 
	{
		Intent intent = new Intent(Choose_option_activity.this , Before_campaign_activity.class);
		startActivity(intent);
	//	Choose_option_activity.this.finish();
	}

	//only the dates which are after today will be shown in choice list
	public boolean isDateValid(String day) 
    {
		Calendar calendar = Calendar.getInstance();

  	  	int thisDay = calendar.get(Calendar.DAY_OF_MONTH);
  	  	int thisMonth = calendar.get(Calendar.MONTH);
  	  	int thisYear = calendar.get(Calendar.YEAR);
  	  	
  	  	if(thisMonth < Calendar.DECEMBER && thisYear == 2013)
		{
			return true;
		}
		
		StringTokenizer tokens = new StringTokenizer(day," ");
		String month = tokens.nextToken();
		String date = tokens.nextToken();
		StringTokenizer dateTokens = new StringTokenizer(date,"-");
		if(dateTokens.countTokens() > 1)
		{
			int dfirst=Integer.parseInt(dateTokens.nextToken());//first one
			int dlast = Integer.parseInt(dateTokens.nextToken());//2nd one
			if(thisMonth == Calendar.DECEMBER)
			{
				return (dfirst == thisDay) || (thisDay > dfirst && thisDay <dlast) || (dfirst > thisDay);
			}
		}
		else
		{
		
		
			int d= Integer.parseInt(date);
			if(thisMonth == Calendar.DECEMBER)
			{
				return (d>thisDay);
			}
		}
		
		return false;
    }
	public void prepareList() {
		
		
		listOfItems = new ArrayList<String>();

		listOfItems.add("$300 CAS Giftcard");
		listOfItems.add("His/Her Gift");
		listOfItems.add("iGifts");
		listOfItems.add("iPhone 5c");
		listOfItems.add("Diamond");
		listOfItems.add("HP Laptop");
		listOfItems.add("Canon SLR");
		listOfItems.add("iPad Air");
		listOfItems.add("Swarovski");
		listOfItems.add("Xbox 360+Kinect");
		listOfItems.add("HomeTheater");
		listOfItems.add("50\"Plasma TV");

		listOfItemIcons = new ArrayList<Integer>();
		listOfItemIcons.add(R.drawable.ink);
		listOfItemIcons.add(R.drawable.his_her);
		listOfItemIcons.add(R.drawable.igifts);
		listOfItemIcons.add(R.drawable.iphone5c);
		listOfItemIcons.add(R.drawable.diamond);
		listOfItemIcons.add(R.drawable.hp);
		listOfItemIcons.add(R.drawable.canon);
		listOfItemIcons.add(R.drawable.ipad);
		listOfItemIcons.add(R.drawable.set);
		listOfItemIcons.add(R.drawable.xbox_kinect);
		listOfItemIcons.add(R.drawable.home_theater);
		listOfItemIcons.add(R.drawable.lgtv);

		listDate = new ArrayList<String>();

		listDate.add("Dec 16");
		listDate.add("Dec 17");
		listDate.add("Dec 18");
		listDate.add("Dec 19");
		listDate.add("Dec 20");
		listDate.add("Dec 21-23");
		listDate.add("Dec 24");
		listDate.add("Dec 25");
		listDate.add("Dec 26");
		listDate.add("Dec 27");
		listDate.add("Dec 28-30");
		listDate.add("Dec 31");
		
		
		for(int i= 0;i<listDate.size();i++)
		{
			String date = listDate.get(i);
			
			if(isDateValid(date))
			{
				dates.add(date);
				String item = listOfItems.get(i);
				int icon = listOfItemIcons.get(i);
				items.add(item);
				icons.add(icon);
			}
		}
	}
	@Override
	public void onResume()
	{
		super.onResume();
		initSelection();
	}
	@Override
	public void onStart()
	{
		super.onStart();

		initSelection();
		prepareList();
	}

}
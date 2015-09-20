package com.mazhar.parserxmlsax;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ParserActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// get a reference to the layout
		LayoutInflater inflater = getLayoutInflater();
		LinearLayout mainLayout = (LinearLayout) inflater.inflate(R.layout.main, null);
		try {
			// create an instance of the DefaultHandler class
			// **ALTER THIS FOR YOUR CLASS NAME**
			DataHandler handler = new DataHandler(getApplicationContext());
			// get the string list by calling the public method
			ArrayList<TextView> newViews = handler.getData();
			// convert to an array
			Object[] products = newViews.toArray();
			// loop through the items, creating a View item for each
			for (int i = 0; i < products.length; i++) {
				// add the next View in the list
				mainLayout.addView((TextView) products[i]);
			}
		} catch (Exception pce) {
			Log.e("AndroidTestsActivity", "PCE " + pce.getMessage());
		}

		setContentView(mainLayout);
	}

}

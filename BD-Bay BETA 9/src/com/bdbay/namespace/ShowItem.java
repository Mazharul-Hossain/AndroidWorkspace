package com.bdbay.namespace;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ParseException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowItem extends Activity {

	private static final int REQUEST_CODE = 0;

	int prod_id;

	MyAppMyState appState;

	private Button prod_order_button;

	private String owner;

	/************** Override onCreate Method ***************/
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_item);

		appState = (MyAppMyState) this.getApplicationContext();
		if (appState == null) {
			appState = ((MyAppMyState) getApplicationContext());
		}

		prod_id = getIntent().getExtras().getInt("prod_id");
		prod_order_button = (Button) findViewById(R.id.prod_order_button);

		ShowProd();
	}

	/************** Override onButton Click Listener Method ***************/
	public void listener(View view) {
		switch (view.getId()) {
		case R.id.prod_back_button:
			finish();
			break;
		case R.id.prod_seller_TextView:
			// show seller profile
			break;
		case R.id.prod_order_button:
			if (owner.compareToIgnoreCase(appState.getState()) == 0) {
				// edit product details
			} else if (appState.getState().compareToIgnoreCase("LogIn") != 0) {
				buy_product();
			} else {
				Intent intent = new Intent(ShowItem.this, Login.class);
				startActivity(intent);
			}
			break;
		}
	}

	private void buy_product() {

		Intent intent = new Intent(ShowItem.this, Buy_Item.class);
		intent.putExtra("prod_id", prod_id);
		Log.d("JSON", "buy product activity starting");
		startActivityForResult(intent, REQUEST_CODE);

	}

	/************** List Item Method ***************/
	private void ShowProd() {
		try {

			JSONObject json = new JSONObject();
			json.put("prod_id", prod_id);

			Log.d("JSON", json.toString());

			/****************** url of the website ****************/
			String URI = "AndroidShowFinalItem";
			String Check = "show_item";

			// paring data
			JSONArray jArray = appState.loadWebValue(json, URI, Check);

			JSONObject json_data = null;

			if (jArray.length() > 0) {

				json_data = jArray.getJSONObject(0);

				TextView prod_title = (TextView) findViewById(R.id.prod_title);
				prod_title.setText(json_data.getString("name"));

				TextView prod_description = (TextView) findViewById(R.id.prod_description);
				prod_description.setText(json_data.getString("description"));

				TextView prod_price = (TextView) findViewById(R.id.prod_price);
				prod_price.setText(json_data.getString("price"));

				ImageView prod_imageview = (ImageView) findViewById(R.id.prod_imageView);
				String image = json_data.getString("image");
				if (!image.isEmpty()) {
					Drawable drawable = LoadImageFromWebOperations(image);
					prod_imageview.setImageDrawable(drawable);
				}

				TextView prod_seller = (TextView) findViewById(R.id.prod_seller_TextView);
				owner = json_data.getString("seller");
				prod_seller.setText(owner);

				if (owner.compareToIgnoreCase(appState.getState()) == 0) {
					prod_order_button.setText("Edit");
				} else {
					prod_order_button.setText("Order");
				}
			} else {
				finish();
			}
		} catch (JSONException e1) {
			Toast.makeText(getBaseContext(), "No Product Value Value Found",
					Toast.LENGTH_LONG).show();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
	}

	/************** Load Image From Web Method ***************/
	private Drawable LoadImageFromWebOperations(String url) {
		URL myFileUrl = null;
		try {
			myFileUrl = new URL(url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			HttpURLConnection conn = (HttpURLConnection) myFileUrl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
			java.io.InputStream is = conn.getInputStream();

			Bitmap bmImg = BitmapFactory.decodeStream(is);
			Drawable d = new BitmapDrawable(getResources(), bmImg);
			return d;
		} catch (IOException e) {
			// TODO Better error handling
			e.printStackTrace();
			return null;
		}

	}
}

package com.bdbay.namespace;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Buy_Item extends Activity {

	MyAppMyState appState;
	static final int PROGRESS_DIALOG_ID = 91;

	static final int requires_credit_PROGRESS_DIALOG_ID = 1;
	static final int available_credit_PROGRESS_DIALOG_ID = 2;

	private static final int REQUEST_CODE = 0;

	TextView requires_credit_textView;
	TextView available_credit_textView;

	int requires_credit;
	int available_credit;

	int transection_id;

	int typeBar;
	ProgressDialog progDialog;
	ProgressThread progThread;

	boolean finish_flag = false;

	/************** Override onCreate Method ***************/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_adv);

		appState = (MyAppMyState) this.getApplicationContext();

		if (appState == null) {
			appState = ((MyAppMyState) getApplicationContext());
			finish();
		} else if (appState.getState().equalsIgnoreCase("LogIn")) {
			finish();
		}
		Log.d("JSON", "buy product starting");
		requires_credit_textView = (TextView) findViewById(R.id.requires_credit_textView);
		available_credit_textView = (TextView) findViewById(R.id.available_credit_textView);

		webUpdate(available_credit_PROGRESS_DIALOG_ID);
		finish_flag = false;
	}

	/************** Override onButton Click Listener Method ***************/
	public void listener(View view) {
		switch (view.getId()) {
		case R.id.add_adv_back_button:
			finish();
			break;
		case R.id.add_credit_textView:
			break;
		case R.id.add_adv_next_button:
			next_page();
			break;
		}
	}

	/************** Next Page Method ***************/
	private void next_page() {
		if (finish_flag == true) {
			if (requires_credit <= available_credit) {
				finish_flag = false;
				Intent intent = new Intent(Buy_Item.this, Pin_request.class);
				intent.putExtra("transection_id", transection_id);
				startActivityForResult(intent, REQUEST_CODE);
			}
		} else {
			webUpdate(requires_credit_PROGRESS_DIALOG_ID);
		}
	}

	/************** Override onCreate Date Picker Dialog Method ***************/
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case PROGRESS_DIALOG_ID:
			progDialog = null;

			progDialog = new ProgressDialog(this);
			progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progDialog.setMessage("Loading...");
			return progDialog;
		}
		return null;
	}

	/************** Override onPrepare Dialog Method ***************/
	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		super.onPrepareDialog(id, dialog);
		switch (id) {
		case PROGRESS_DIALOG_ID:
			progThread = new ProgressThread(handler);
			progThread.start();
			break;
		}
	}

	/************** web Update Method ***************/
	private void webUpdate(int i) {
		typeBar = i;
		showDialog(PROGRESS_DIALOG_ID);
	}

	/************** Hnadler Class ***************/
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			int total = msg.getData().getInt("total");
			// progDialog.setProgress(total);
			if (total == 50) {
				requires_credit_textView.setText(String
						.valueOf(requires_credit));

			} else if (total == 40) {
				available_credit_textView.setText(String
						.valueOf(available_credit));
			} else if (total <= 0) {
				dismissDialog(PROGRESS_DIALOG_ID);
			}
		}
	};

	/************** Override on Activity Return Result Method ***************/
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE) {
			if (resultCode == Activity.RESULT_OK) {
				finish();
			}
		}
	}

	/************** Search Item Type Method ***************/
	private int SearchItem_catagory() {
		int checksum = 0;
		try {
			if (typeBar == requires_credit_PROGRESS_DIALOG_ID) {
				JSONObject json = new JSONObject();

				json.put("u_name", appState.getState());
				json.put("prod_id", getIntent().getExtras().getInt("prod_id"));

				/****************** url of the website ****************/
				String URI = "AndroidItemMeasureMoney";
				String Check = "requires_credit";

				// paring data
				JSONArray jArray = appState.loadWebValue(json, URI, Check);
				JSONObject json_data = null;

				if (jArray.length() > 0) {
					json_data = jArray.getJSONObject(0);

					requires_credit = json_data.getInt("requires_credit");
					transection_id = json_data.getInt("transection_id");

					checksum = 1;
				}
			} else if (typeBar == available_credit_PROGRESS_DIALOG_ID) {
				JSONObject json = new JSONObject();
				json.put("u_name", appState.getState());

				/****************** url of the website ****************/
				String URI = "AndroidMeasureMoney";
				String Check = "available_credit";

				// paring data
				JSONArray jArray = appState.loadWebValue(json, URI, Check);
				JSONObject json_data = null;

				if (jArray.length() > 0) {
					json_data = jArray.getJSONObject(0);

					available_credit = json_data.getInt("available_credit");
					checksum = 1;
				}
			}
		} catch (JSONException e1) {
			Toast.makeText(getBaseContext(), "No Value Found",
					Toast.LENGTH_LONG).show();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return checksum;
	}

	/************** Progress Threadr Class ***************/
	private class ProgressThread extends Thread {
		// Class constants defining state of the thread
		final static int DONE = 0;
		final static int RUNNING = 1;
		Handler mHandler;
		int mState;
		int total;

		ProgressThread(Handler h) {
			mHandler = h;
		}

		@Override
		public void run() {

			Message msg;
			Bundle b;
			int checksum = 0;

			mState = RUNNING;
			total = 100;
			while (mState == RUNNING) {
				checksum = SearchItem_catagory();
				if (checksum == 1) {
					finish_flag = true;
					total = 40;
					msg = mHandler.obtainMessage();
					b = new Bundle();
					b.putInt("total", total);
					msg.setData(b);
					mHandler.sendMessage(msg);

					typeBar = requires_credit_PROGRESS_DIALOG_ID;
					checksum = SearchItem_catagory();

					if (checksum == 1) {
						finish_flag = true;
						total = 50;
						msg = mHandler.obtainMessage();
						b = new Bundle();
						b.putInt("total", total);
						msg.setData(b);
						mHandler.sendMessage(msg);
					} else {
						finish_flag = false;
					}
				} else {
					finish_flag = false;
				}
				total = -1;
				msg = mHandler.obtainMessage();
				b = new Bundle();
				b.putInt("total", total);
				msg.setData(b);
				mHandler.sendMessage(msg);
				mState = DONE;
			}
		}
	}

	/************** Progress Threadr Class Ends ***************/

}

package com.bdbay.namespace;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ParseException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class List_Avtivity_Main extends ListActivity implements
		OnItemSelectedListener {

	final static int First_run_webupdate = 0;
	final static int Search_product_webupdate = 1;
	final static int Search_category_webupdate = 2;

	final static int Top_item = 1;
	final static int Top_search = 2;
	final static int Top_category = 3;
	final static int Sub_category = 4;

	int usage_mode;
	final static int USER_MODE = 1;
	final static int GUEST_MODE = 2;
	final static int SHOWROOM_MODE = 3;

	// this counts how many Gallery's are on the UI
	int mGalleryCount = 0;

	// this counts how many Gallery's have been initialized
	int mGalleryInitializedCount = 0;

	int typeBar = -1;
	int searchItemcategory = -1;
	int searchItem_category_select;

	ProgressDialog progDialog;
	ProgressThread progThread;

	private LayoutInflater mInflater;
	private Vector<RowData> data;
	RowData rd;

	MyAppMyState appState;
	String state;

	static String[] title;

	static String[] detail;

	private final Integer[] imgid = { R.drawable.bsfimg, R.drawable.bsfimg2,
			R.drawable.bsfimg4, R.drawable.bsfimg5 };

	static int[] prod_id;
	static String[] showroom_id;

	static String[] searchItemList;
	static int[] searchItemCat_id;
	static int[] searchItemCat_sub_id;

	private Spinner searchItem;

	private int check_showroom;
	String selected_showroom_id;

	private TextView search_error_textView;
	private boolean search_error_flag;

	/************** Override onCreate Method ***************/
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Log.d("Main", "Main : Activity Started");

		appState = (MyAppMyState) this.getApplicationContext();

		if (appState == null) {
			appState = ((MyAppMyState) getApplicationContext());
		}
		data = new Vector<RowData>();

		Log.d("Main", "Main : Activity Started 1");
		searchItem = (Spinner) findViewById(R.id.searchItem);
		searchItem.setOnItemSelectedListener(List_Avtivity_Main.this);

		search_error_textView = (TextView) findViewById(R.id.search_error_textView);

		Log.d("Main", "Main : Activity Moving 2");
		if (typeBar < 0) {
			Log.d("Main", "Main : Web Update Started");
			usage_mode = GUEST_MODE;
			webUpdate(First_run_webupdate); // first run
		}
		Log.d("Main ", "Hi !!! : Activity Crossed");
	}

	/************** web Update Method ***************/
	private void webUpdate(int i) {
		Log.d("Main", "Main : Web Update Started " + i);
		typeBar = i;
		showDialog(typeBar);
		Log.d("Main", "Main : Web Update Finished " + typeBar);
	}

	/************** Override onCreate Dialog Method ***************/
	@Override
	protected Dialog onCreateDialog(int id) {
		Log.d("Main", "Main : Progress Dialog Started");

		progDialog = null;

		progDialog = new ProgressDialog(this);
		progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progDialog.setMessage("Loading...");
		// progThread = new ProgressThread(handler);
		// progThread.start();
		Log.d("Main", "Main : Progress Dialog Finished");
		return progDialog;
	}

	/************** Override onPrepare Dialog Method ***************/
	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		Log.d("Main", "Main : Progress Dialog Started 2");

		// progDialog = null;

		// progDialog = new ProgressDialog(this);
		// progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		// progDialog.setMessage("Loading...");
		progThread = new ProgressThread(handler);
		progThread.start();
		Log.d("Main", "Main : Progress Dialog Finished 2");
	}

	/*
	 * Handler on the main (UI) thread that will receive messages from the
	 * second thread and update the progress.
	 */
	/************** Hnadler Class ***************/
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			/*
			 * Get the current value of the variable total from the message data
			 * and update the progress bar.
			 */
			Log.d("Main", "Main : Progress Dialog Handler Started");
			int total = msg.getData().getInt("total");
			// progDialog.setProgress(total);
			if (total == 50) {
				Log.d("Main", "Main : Spinner Inflater Started");
				SpinnerInflater(); // will be called inside
			} else if (total == 40) {
				Log.d("Main", "Main : List Inflater Started");
				ListInflater(); // will be called inside
			} else if (total <= 0) {

				if (search_error_flag) {
					search_error_textView.setVisibility(View.VISIBLE);
				} else {
					search_error_textView.setVisibility(View.INVISIBLE);
				}
				dismissDialog(typeBar);
				Log.d("Main", "Main : Progress Dialog Handler Finisheded");
				// progThread.setState(ProgressThread.DONE);
			}
		}
	};

	/************** Override onCreate Options Menu Method ***************/
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		menu.clear();

		if (appState == null) {
			appState = ((MyAppMyState) getApplicationContext());
		}
		state = appState.getState();

		if (state.equalsIgnoreCase("LogIn")) {
			inflater.inflate(R.menu.guest_menu_blogin, menu);
		} else {
			if (usage_mode == GUEST_MODE) {
				inflater.inflate(R.menu.guest_menu_alogin, menu);
			} else if (usage_mode == USER_MODE) {
				inflater.inflate(R.menu.user_menu_alogin, menu);
			} else if (usage_mode == SHOWROOM_MODE) {
				inflater.inflate(R.menu.user_menu_alogin, menu);
			}
		}
		return true;
	}

	/************** Override on Menu Options Item Selected Method ***************/
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.Menu_Login) {
			Intent intent = new Intent(List_Avtivity_Main.this, Login.class);
			startActivity(intent);
		} else if (item.getItemId() == R.id.Menu_Register) {
			Intent intent = new Intent(List_Avtivity_Main.this,
					Registration.class);
			startActivity(intent);
		} else if (item.getItemId() == R.id.Menu_Login_Showroom) {
			if (appState.getType().equalsIgnoreCase("showroom")) {
				usage_mode = SHOWROOM_MODE;
				webUpdate(First_run_webupdate); // first run
			} else {
				Toast.makeText(getBaseContext(), "You don't have any showroom",
						Toast.LENGTH_LONG).show();
			}
		} else if (item.getItemId() == R.id.Menu_Logout) {
			appState.setState("LogIn");
		} else if (item.getItemId() == R.id.Menu_Add_Item) {
			Intent intent = new Intent(List_Avtivity_Main.this, AddItem.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

	/*****************************************
	 * Search Item Type Method
	 * 
	 * @return
	 *****************************************/
	private int SearchItemType() {
		int checksum = 0;
		try {
			JSONObject json = new JSONObject();
			String URI = null;
			String Check = null;

			if (usage_mode == GUEST_MODE) {
				json.put("type", "type");
				/****************** url of the website ****************/
				URI = "AndroidShowItemType";
				Check = "type_search";
			} else if (usage_mode == USER_MODE) {
				return checksum;
			} else if (usage_mode == SHOWROOM_MODE) {
				json.put("user_id", showroom_id);
				if (appState.getState().equalsIgnoreCase(selected_showroom_id)) {
					json.put("type", "showroom");
				} else {
					json.put("type", "user");
				}
				/****************** url of the website ****************/
				URI = "AndroidShowroomCategory";
				Check = "showroomCategory";
			}

			// paring data
			JSONArray jArray = appState.loadWebValue(json, URI, Check);
			JSONObject json_data = null;

			if (jArray.length() > 0) {
				searchItemList = new String[jArray.length() + 1];
				searchItemCat_id = new int[jArray.length() + 1];
				searchItemCat_sub_id = new int[jArray.length() + 1];

				searchItemList[0] = "Popular Items";
				searchItemCat_id[0] = 0;
				searchItemCat_sub_id[0] = 0;
				for (int i = 0; i < jArray.length(); i++) {
					json_data = jArray.getJSONObject(i);

					searchItemList[i + 1] = json_data.getString("name");
					searchItemCat_id[i + 1] = json_data.getInt("id");
					searchItemCat_sub_id[i + 1] = 0;
				}
				checksum = 1;
			}
		} catch (JSONException e1) {
			Toast.makeText(getBaseContext(), "No Login Value Found",
					Toast.LENGTH_LONG).show();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return checksum;
	}

	/************** Type List Inflater Method ***************/
	private void SpinnerInflater() {
		// this counts how many Gallery's are on the UI
		mGalleryCount = 1;
		// this counts how many Gallery's have been initialized
		mGalleryInitializedCount = 0;

		ArrayAdapter<String> adapterSearchItem = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, searchItemList);
		searchItem.setAdapter(adapterSearchItem);

		Log.d("Main", "Main : Spinner Inflater Finished");
	}

	/************** Search Item Method ***************/
	private int SearchItem(int i) {
		String URI = null;
		String Check = null;
		int checksum = 0;
		try {
			JSONObject json = new JSONObject();

			if (usage_mode == SHOWROOM_MODE) {
				json.put("u_name", showroom_id);
				/****************** url of the website ****************/
				URI = "AndroidShowroomItem";

			} else if (usage_mode == GUEST_MODE) {
				/****************** url of the website ****************/
				URI = "AndroidSearchItem";
			}

			if (i == Top_item) {
				json.put("type", "top_item");

			} else if (i == Top_search) {
				json.put("type", "search");
				json.put("cat_id", searchItemCat_id[searchItemcategory]);
				json.put("sub_cat_id", searchItemCat_sub_id[searchItemcategory]);
				EditText Search = (EditText) findViewById(R.id.search);
				String search = Search.getText().toString();
				json.put("search", search);

			} else if (i == Top_category) {
				json.put("type", "category");
				json.put("cat_id", searchItemCat_id[searchItemcategory]);

			} else if (i == Sub_category) {
				json.put("type", "sub_category");
				json.put("cat_id", searchItemCat_id[searchItemcategory]);
				json.put("sub_cat_id", searchItemCat_sub_id[searchItemcategory]);

			}
			Log.d("JSON", json.toString());
			Check = "SearchItem";
			// paring data
			JSONArray jArray = appState.loadWebValue(json, URI, Check);

			JSONObject json_data = null;
			data = new Vector<RowData>();
			Log.d("JSON", "Result has");
			if (jArray.length() > 1) {
				json_data = jArray.getJSONObject(0);
				String string = json_data.getString("type");

				if (string.compareToIgnoreCase("sub_category") == 0) {

					searchItemList = new String[jArray.length()];
					searchItemCat_id = new int[jArray.length()];
					searchItemCat_sub_id = new int[jArray.length()];

					searchItemList[0] = "Popular Items";
					searchItemCat_id[0] = 0;
					searchItemCat_sub_id[0] = 0;
					for (i = 1; i < jArray.length(); i++) {
						json_data = jArray.getJSONObject(i);

						searchItemList[i] = json_data.getString("name");
						searchItemCat_id[i] = Integer.parseInt(json_data
								.getString("cat_id"));
						searchItemCat_sub_id[i] = Integer.parseInt(json_data
								.getString("sub_id"));
					}
					checksum = 1;

				} else if (string.compareToIgnoreCase("product") == 0) {

					prod_id = new int[jArray.length() - 1];
					for (i = 1; i < jArray.length(); i++) {
						json_data = jArray.getJSONObject(i);

						prod_id[i - 1] = json_data.getInt("id");
						String image = json_data.getString("image");
						if (!image.isEmpty()) {
							rd = new RowData(image,
									json_data.getString("name"),
									json_data.getString("price"));
						} else {
							rd = new RowData((i - 1) % 4,
									json_data.getString("name"),
									json_data.getString("price"));
						}
						data.add(rd);
					}
					checksum = 2;

				} else if (string.compareToIgnoreCase("showroom") == 0) {

					showroom_id = new String[jArray.length() - 1];
					for (i = 1; i < jArray.length(); i++) {
						json_data = jArray.getJSONObject(i);

						showroom_id[i - 1] = json_data.getString("id");
						String image = json_data.getString("image");
						if (!image.isEmpty()) {
							rd = new RowData(image,
									json_data.getString("name"),
									json_data.getString("description"));
						} else {
							rd = new RowData((i - 1) % 4,
									json_data.getString("name"),
									json_data.getString("description"));
						}
						data.add(rd);
					}
					checksum = 2;
				}
			}
		} catch (JSONException e1) {
			Toast.makeText(getBaseContext(), "No Login Value Found",
					Toast.LENGTH_LONG).show();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return checksum;
	}

	/************** List Inflater Method ***************/
	private void ListInflater() {
		mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		CustomAdapter adapter = new CustomAdapter(this, R.layout.list,
				R.id.title, data);
		setListAdapter(adapter);
		getListView().setTextFilterEnabled(true);
		Log.d("Main", "Main : List Inflater Finished");
	}

	/************** on List Item Click Method ***************/
	@Override
	public void onListItemClick(ListView parent, View v, int position, long id) {

		Log.d("Main", "Main : List Item selected !");
		if (check_showroom == 1) {
			Log.d("Main", "Main : Showroom List Item selected !");

			selected_showroom_id = showroom_id[position];
			if (appState.getState().equalsIgnoreCase("selected_showroom_id")) {
				usage_mode = SHOWROOM_MODE;
			}
			webUpdate(First_run_webupdate);
		} else {
			Log.d("Main", "Main : Product List Item selected !");
			Intent intent = new Intent(List_Avtivity_Main.this, ShowItem.class);
			intent.putExtra("prod_id", prod_id[position]);
			startActivity(intent);
		}
	}

	/************** on Spinner List Item Select Method ***************/
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {

		if (mGalleryInitializedCount < mGalleryCount) {
			mGalleryInitializedCount++;
		} else {
			if (searchItemcategory != position) {
				searchItemcategory = position;
				webUpdate(Search_category_webupdate);
			}

		}
		/*
		 * // On selecting a spinner item String item =
		 * parent.getItemAtPosition(position).toString();
		 * 
		 * // showing a toast on selecting an item Toast.makeText(
		 * parent.getContext(), "You have selected : " + item + " IN " +
		 * (position + 1) + "th position", Toast.LENGTH_LONG).show();
		 */
	}

	/************** on Spinner Nothing Selected Method ***************/
	public void onNothingSelected(AdapterView<?> parent) {
		searchItemcategory = 0;
	}

	/************** Override onButton Click Listener Method ***************/
	public void inMainlistener(View view) {
		switch (view.getId()) {
		case R.id.next_page_textView:
			// if not login show login
			// login show profile
			break;
		case R.id.prev_page_textView:
			// if not login show login
			// login show profile
			break;
		case R.id.searchButton:
			search();
			break;
		}
	}

	/************** Search Button Method ***************/
	private void search() {
		EditText Search = (EditText) findViewById(R.id.search);
		String search = Search.getText().toString();

		if (!search.isEmpty()) {
			webUpdate(Search_product_webupdate);
		}
	}

	/*
	 * Inner class that performs progress calculations on a second thread.
	 * Implement the thread by subclassing Thread and overriding its run()
	 * method. Also provide a setState(state) method to stop the thread
	 * gracefully.
	 */
	/************** Progress Threadr Class ***************/
	private class ProgressThread extends Thread {
		// Class constants defining state of the thread
		final static int DONE = 0;
		final static int RUNNING = 1;
		Handler mHandler;
		int mState;
		int total;

		/*
		 * Constructor with an argument that specifies Handler on main thread to
		 * which messages will be sent by this thread.
		 */
		ProgressThread(Handler h) {
			mHandler = h;
		}

		/*
		 * Override the run() method that will be invoked automatically when the
		 * Thread starts. Do the work required to update the progress bar on
		 * this thread but send a message to the Handler on the main UI thread
		 * to actually change the visual representation of the progress. In this
		 * example we count the index total down to zero, so the horizontal
		 * progress bar will start full and count down.
		 */
		@Override
		public void run() {

			Message msg;
			Bundle b;
			int checksum = 0;

			mState = RUNNING;
			total = 100;
			while (mState == RUNNING) {
				if (typeBar == First_run_webupdate) {
					Log.d("Main", "Main : Thread Started 0");
					checksum = SearchItemType(); // checking for category

					if (checksum == 1) {
						total = 50;
						msg = mHandler.obtainMessage();
						b = new Bundle();
						b.putInt("total", total);
						msg.setData(b);
						mHandler.sendMessage(msg);

						searchItem_category_select = 1;
						search_error_flag = false;
					}

					checksum = SearchItem(Top_item);// checking for top products
					if (checksum == 2) {
						total = 40;

						msg = mHandler.obtainMessage();
						b = new Bundle();
						b.putInt("total", total);
						msg.setData(b);
						mHandler.sendMessage(msg);

						search_error_flag = false;
					} else {
						search_error_flag = true;
					}

				} else if (typeBar == Search_product_webupdate) {

					Log.d("Main", "Main : Thread Started 1");

					checksum = SearchItem(Top_search); // searching according to
														// cat_id
					if (checksum == 2) {
						total = 40;

						msg = mHandler.obtainMessage();
						b = new Bundle();
						b.putInt("total", total);
						msg.setData(b);
						mHandler.sendMessage(msg);

						search_error_flag = false;
					} else {
						search_error_flag = true;
					}

				} else if (typeBar == Search_category_webupdate) {

					Log.d("Main", "Main : Tread Started 2");
					if (searchItem_category_select == 1) {
						checksum = SearchItem(Top_category);
					} else if (searchItem_category_select == 2) {
						checksum = SearchItem(Sub_category);
					}

					if (checksum == 1) {
						searchItem_category_select = 2;
						searchItemcategory = -1;

						check_showroom = searchItemcategory;

						total = 50;

						msg = mHandler.obtainMessage();
						b = new Bundle();
						b.putInt("total", total);
						msg.setData(b);
						mHandler.sendMessage(msg);
						search_error_flag = false;
					} else if (checksum == 2) {
						total = 40;

						msg = mHandler.obtainMessage();
						b = new Bundle();
						b.putInt("total", total);
						msg.setData(b);
						mHandler.sendMessage(msg);
						check_showroom = searchItemcategory;

						search_error_flag = false;
					} else {
						search_error_flag = true;
					}
				}

				/*
				 * Send message (with current value of total as data) to Handler
				 * on UI thread so that it can update the progress bar.
				 */
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

	/************** Row Data Class ***************/
	private class RowData {
		protected int mId = -1;
		protected String mTitle = null;
		protected String mImage = null;
		protected String mDetail = null;

		RowData(int id, String title, String detail) {
			mId = id;
			mTitle = title;
			mDetail = detail;
			mImage = null;
		}

		RowData(String image, String title, String detail) {
			mImage = image;
			mTitle = title;
			mDetail = detail;
			mId = -1;
		}

		@Override
		public String toString() {
			return mId + " " + mTitle + " " + mDetail;
		}

		@SuppressWarnings("unused")
		public String toString1() {
			return mImage + " " + mTitle + " " + mDetail;
		}
	}

	/************** Row Data Class Ends ***************/

	/************** Custom Adapter Class ***************/
	private class CustomAdapter extends ArrayAdapter<RowData> {

		public CustomAdapter(Context context, int resource,
				int textViewResourceId, List<RowData> objects) {

			super(context, resource, textViewResourceId, objects);
		}

		/************** get View Method ***************/
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;
			TextView title = null;
			TextView detail = null;
			ImageView i11 = null;
			RowData rowData = getItem(position);

			if (null == convertView) {
				convertView = mInflater.inflate(R.layout.list, null);
				holder = new ViewHolder(convertView);
				convertView.setTag(holder);
			}

			holder = (ViewHolder) convertView.getTag();
			title = holder.gettitle();
			title.setText(rowData.mTitle);
			detail = holder.getdetail();
			detail.setText(rowData.mDetail);

			i11 = holder.getImage();

			Log.d("Main", "Main : Before Gettin image correctly");
			if (rowData.mId != -1) {
				Log.d("Main", "Main : Gettin image correctly ");
				i11.setImageResource(imgid[rowData.mId]);
			} else {
				String URI = rowData.mImage;
				Log.d("Main", "Main : Gettin image correctly : " + URI);
				Drawable drawable = LoadImageFromWebOperations(URI);
				i11.setImageDrawable(drawable);
			}

			return convertView;
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
				InputStream is = conn.getInputStream();

				Bitmap bmImg = BitmapFactory.decodeStream(is);
				Drawable d = new BitmapDrawable(getResources(), bmImg);
				return d;
			} catch (IOException e) {
				// TODO Better error handling
				e.printStackTrace();
				return null;
			}

			/*
			 * try { Log.d("Main", "Main : Loading Image Started"); InputStream
			 * is = (InputStream) new URL(url).getContent(); Drawable d =
			 * Drawable.createFromStream(is, "src name"); Log.d("Main",
			 * "Main : Returning Image "); return d; } catch (Exception e) {
			 * System.out.println("Exc=" + e); return null; }
			 */
		}

		/************** View Holder Class ***************/
		private class ViewHolder {

			private final View mRow;
			private TextView title = null;
			private TextView detail = null;
			private ImageView i11 = null;

			/************** View Holder Method ***************/
			public ViewHolder(View row) {
				mRow = row;
			}

			/************** Title View Method ***************/
			public TextView gettitle() {
				if (null == title) {
					title = (TextView) mRow.findViewById(R.id.title);
				}
				return title;
			}

			/************** Text View Method ***************/
			public TextView getdetail() {
				if (null == detail) {
					detail = (TextView) mRow.findViewById(R.id.detail);
				}
				return detail;
			}

			/************** Image View Method ***************/
			public ImageView getImage() {
				if (null == i11) {
					i11 = (ImageView) mRow.findViewById(R.id.img);
				}
				return i11;
			}
		}
		/************** View Holder Class Ends ***************/
	}
	/************** Custom Adapter Class Ends ***************/
}

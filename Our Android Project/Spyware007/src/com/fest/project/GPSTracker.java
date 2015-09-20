package com.fest.project;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.util.Log;

public class GPSTracker extends Service implements LocationListener {

	private SQLiteDatabase db;
	private final Context mContext;

	// flag for GPS status
	boolean isGPSEnabled = false;

	// flag for network status
	boolean isNetworkEnabled = false;

	// flag for GPS status
	boolean canGetLocation = false;

	Location location; // location
	double latitude; // latitude
	double longitude; // longitude

	double accuracy; // accuracy

	// The minimum distance to change Updates in meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

	// The minimum time between updates in milliseconds
	static int millisecond = 1000;
	private static final long MIN_TIME_BW_UPDATES = 60 * 1; // 1
															// minute

	// Declaring a Location Manager
	protected LocationManager locationManager;

	private String provider;

	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		super.onStartCommand(intent, flags, startId);
		this.rubber();
		return START_STICKY;
	}
	
	
	public GPSTracker(Context context) {
		this.mContext = context;
		getLocation();
	}

	public Location getLocation() {
		try {
			locationManager = (LocationManager) mContext
					.getSystemService(LOCATION_SERVICE);

			// getting GPS status
			isGPSEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);

			// getting network status
			isNetworkEnabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (!isGPSEnabled && !isNetworkEnabled) {
				// no network provider is enabled
			} else {
				this.canGetLocation = true;
				// First get location from Network Provider
				if (isNetworkEnabled) {
					locationManager.requestLocationUpdates(
							LocationManager.NETWORK_PROVIDER,
							MIN_TIME_BW_UPDATES,
							MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
					Log.d("Network", "Network");
					if (locationManager != null) {
						location = locationManager
								.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						if (location != null) {
							latitude = location.getLatitude();
							longitude = location.getLongitude();
							accuracy = location.getAccuracy();
						}
					}
				}
				// if GPS Enabled get lat/long using GPS Services
				else if (isGPSEnabled) {
					if (location == null) {
						locationManager.requestLocationUpdates(
								LocationManager.GPS_PROVIDER,
								MIN_TIME_BW_UPDATES * millisecond,
								MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
						Log.d("GPS Enabled", "GPS Enabled");
						if (locationManager != null) {
							location = locationManager
									.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if (location != null) {
								latitude = location.getLatitude();
								longitude = location.getLongitude();
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return location;
	}

	/**
	 * Stop using GPS listener Calling this function will stop using GPS in your
	 * app
	 * */
	public void stopUsingGPS() {
		if (locationManager != null) {
			locationManager.removeUpdates(GPSTracker.this);
		}
	}

	/**
	 * Function to get latitude
	 * */
	public double getLatitude() {
		if (location != null) {
			latitude = location.getLatitude();
		}

		// return latitude
		return latitude;
	}

	/**
	 * Function to get longitude
	 * */
	public double getLongitude() {
		if (location != null) {
			longitude = location.getLongitude();
		}

		// return longitude
		return longitude;
	}

	/**
	 * Function to get accuracy
	 * */
	public double getAccuracy() {
		if (location != null) {
			accuracy = location.getAccuracy();
		}

		// return accuracy
		return accuracy;
	}

	/**
	 * Function to get provider
	 * */
	public String getProvider() {
		if (location != null) {
			provider = location.getProvider();
		}

		// return provider
		return provider;
	}

	/**
	 * Function to check GPS/wifi enabled
	 * 
	 * @return boolean
	 * */
	public boolean canGetLocation() {
		return this.canGetLocation;
	}

	/**
	 * Function to show settings alert dialog On pressing Settings button will
	 * lauch Settings Options
	 * */
	public void showSettingsAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

		// Setting Dialog Title
		alertDialog.setTitle("GPS is settings");

		// Setting Dialog Message
		alertDialog
				.setMessage("GPS is not enabled. Do you want to go to settings menu?");

		// On pressing Settings button
		alertDialog.setPositiveButton("Settings",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(
								Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						mContext.startActivity(intent);
					}
				});

		// on pressing cancel button
		alertDialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		// Showing Alert Message
		alertDialog.show();
	}

	@Override
	public void onLocationChanged(Location location) {

	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@SuppressWarnings("null")
	public void rubber() {
		Timer mTimer = null;
		mTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				// First get location from Network Provider
				if (locationManager != null) {
					if (isNetworkEnabled) {
						Log.d("Network", "Network");

						location = locationManager
								.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
					}
					// if GPS Enabled get lat/long using GPS Services
					else if (isGPSEnabled) {
						Log.d("GPS Enabled", "GPS Enabled");
						location = locationManager
								.getLastKnownLocation(LocationManager.GPS_PROVIDER);
					}
					if (location != null) {
						latitude = location.getLatitude();
						longitude = location.getLongitude();
						accuracy = location.getAccuracy();
						provider = location.getProvider();
						
						String lat = Double.toString(latitude);
						String _long = Double.toString(latitude);
						String acc = Double.toString(accuracy);
						
						insertData(lat, _long, acc, provider, "");
					}
				} else {
					getLocation();
				}
				Log.v("gps tracker", "position updated");
			}
		}, 0, MIN_TIME_BW_UPDATES * millisecond);
	}
	
	
	public void insertData(String lat,String _long,String acc,String prov,String time) {
		db = openOrCreateDatabase("GPS_DATABASE", MODE_PRIVATE , null);
		db.execSQL("CREATE TABLE IF NOT EXISTS gps_data_table (" +
				"lat VARCHAR" +
				"long VARCHAR" +
				"acc VARCHAR" +
				"prov VARCHAR" +
				"time VARCHAR);");
		
		Cursor mCursor = db.rawQuery("SELECT * from gps_data_table",null);
		if( mCursor == null)
		{
			ContentValues cv = new ContentValues();
			cv.put("lat",lat);
			cv.put("long",_long);
			cv.put("acc", acc);
			cv.put("prov", prov);
			cv.put("time", time);
			
			db.insert("gps_data_table", null , cv);
			db.close();
			return;
		}
		else
		{
			
			mCursor.moveToFirst();
		
			while( !mCursor.isAfterLast() )
			{
				mCursor.moveToNext();
			}
			
			ContentValues cv = new ContentValues();
			cv.put("lat",lat);
			cv.put("long",_long);
			cv.put("acc", acc);
			cv.put("prov", prov);
			cv.put("time", time);

			db.insert("gps_data_table", null , cv);
			db.close();
			return;
		}
		
	}
	
	public String[] getAllGpsData() {
		//this function will return a cursor containing all data from the database
		db = openOrCreateDatabase("GPS_DATABASE", MODE_PRIVATE , null);
		Cursor cursor = db.rawQuery("Select * from gps_data_table", null);
		String []resultStrings = null;
		
		if (cursor != null ) 
		{
			int sizeOfArray = cursor.getCount();
			resultStrings = new String[sizeOfArray]; 
		   cursor.moveToFirst();
		   int i=0;
		   do{
			   String lat = cursor.getString(cursor.getColumnIndex("lat"));
			   String _long = cursor.getString(cursor.getColumnIndex("long"));
			   String acc = cursor.getString(cursor.getColumnIndex("acc"));
			   String prov = cursor.getString(cursor.getColumnIndex("prov"));
			   String time = cursor.getString(cursor.getColumnIndex("time"));
			   
			   resultStrings[i] = lat+" "+_long+" "+acc+" "+prov+" "+time;
			   i++;
		   }while (cursor.moveToNext());
		   cursor.close();
		}
		
		return resultStrings;
	}
	
	
	
	
}

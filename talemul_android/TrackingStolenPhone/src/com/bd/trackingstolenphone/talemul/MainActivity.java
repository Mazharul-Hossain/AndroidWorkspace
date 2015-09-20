package com.bd.trackingstolenphone.talemul;


import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	Button btnlogin;//startButton, stopButton
	TextView distanceView;
	TextView locationNow;
	EditText sendingMailAddress;
	EditText etloginpass;
	String mailAddress = "";
	LocationManager locationManager;
	Location locationStart;
	private Mail mail;
	
	public static String filename = "user29";
	SharedPreferences spData;
	
	private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in
																		// Meters
	private static final long MINIMUM_TIME_BETWEEN_UPDATES = 15000; // in
																	// Milliseconds
	MyLocationListener locListener;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		spData = getSharedPreferences(filename, 0);

		startService(new Intent(this, MainService.class));

		//startButton = (Button) findViewById(R.id.button1);
		//stopButton = (Button) findViewById(R.id.stopButt);
		btnlogin=(Button) findViewById(R.id.btnlogin);
		//distanceView = (TextView) findViewById(R.id.distance);
		locationNow = (TextView) findViewById(R.id.loc);
		etloginpass = (EditText) findViewById(R.id.etloginpass);
		locListener = new MyLocationListener();
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			buildAlertMessageNoGps();// call to show alart box to turn on GSP
		}
		
		btnlogin.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// stop location update listener
				String lpass=etloginpass.getText().toString();
				
				spData = getSharedPreferences(filename, 0);
				String stpass = spData.getString("stpass", "qwerty");
				if(stpass.equals(lpass))
				{
					Toast.makeText(getApplicationContext(),
							"password match  "+stpass+" "+lpass+" ", Toast.LENGTH_LONG).show();
					//distanceView.setText("");
					
					Intent intent =new Intent(MainActivity.this, settings.class);
					//intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);	
					finish();
					
					
				}
				else
				{
					//distanceView.setText("You enter wrong password,Your default password is qwerty ");
					Toast.makeText(getApplicationContext(),
							"password wrong "+lpass+" ", Toast.LENGTH_LONG).show();
					
					
				}
				
				//result.setText(strResult);
				
				
				
			}
		});
/*
		startButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// function for start button
				if (!sendingMailAddress.getText().toString().equals("")) {
					mailAddress = sendingMailAddress.getText().toString();
					showCurrentLocation(); // Toast current location

					locationManager.requestLocationUpdates(
							// start location update listener
							LocationManager.GPS_PROVIDER,
							MINIMUM_TIME_BETWEEN_UPDATES,
							MINIMUM_DISTANCE_CHANGE_FOR_UPDATES, locListener);
				} else
					Toast.makeText(getApplicationContext(),
							"Mail Address is Empty!", Toast.LENGTH_LONG).show();
			}
		});

		stopButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// stop location update listener
				locationManager.removeUpdates(locListener);
				
			}
		});*/

	}

	private void buildAlertMessageNoGps() {// function to show GSP turn on alart
											// box
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(
				"Your GPS seems to be disabled, do you want to enable it?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(
									@SuppressWarnings("unused") final DialogInterface dialog,
									@SuppressWarnings("unused") final int id) {
								startActivity(new Intent(
										android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(final DialogInterface dialog,
							@SuppressWarnings("unused") final int id) {
						dialog.cancel();
					}
				});
		final AlertDialog alert = builder.create();
		alert.show();
	}

	protected void showCurrentLocation() {

		Location location = locationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		if (location != null) {
			locationStart = location;
			String message = String.format(
					"Current 22Location \n Longitude: %1$s \n Latitude: %2$s",
					location.getLongitude(), location.getLatitude());
			Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG)
					.show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private class MyLocationListener implements LocationListener {

		public void onLocationChanged(Location location) {
			double distanceMeters = location.distanceTo(locationStart);
			distanceView.setText("Distance = " + distanceMeters);
			String message = String.format(
					"New 22Location\n Longitude: %1$s \n Latitude: %2$s",
					location.getLongitude(), location.getLatitude()

			);

			locationNow.setText(message);// shows current location in user
											// interface
			mail = new Mail("talemulislam@gmail.com", "tale2929");
			String[] toArr = new String[1]; // array of receiver mail id
			toArr[0] = mailAddress;

			mail.setTo(toArr); // receiver mail id
			mail.setFrom("talemulislam@gmail.com"); // sender mail address
													// gps+0804029
			mail.setSubject("GPSTRACKER"); // to set the subject of the mail
			mail.setBody(message); // to set the body set of the mail

			Thread mailThread = new Thread(new Runnable() { // try to send mail
															// through
															// background thread

						public void run() {
							// TODO Auto-generated method stub
							try { //
								if (mail.send()) { // send() method Exception
													// throw
									Log.d("mailSend",
											"Mail Send successfully :)");
									// showToast(toBox.getText().toString()+" E Mail Send successfully  :)");
								} else {
									Log.d("mailSend", "Mail Send Hoinai");
									// showToast("Mail Send fail   :(");
								}
							} catch (Exception e) {
								Log.d("mailSend", e.toString()); // if there any
																	// exception,then
																	// show it
																	// in log
																	// trace
																	// //showToast("Error :O :O : "+e.toString());
							}
						}
					});

			mailThread.start(); // to start thread for send mail
		}

		public void onStatusChanged(String s, int i, Bundle b) {
			Toast.makeText(getApplicationContext(), "Provider status changed",
					Toast.LENGTH_LONG).show();
		}

		public void onProviderDisabled(String s) {
			Toast.makeText(
					getApplicationContext(),// shows toast if GSP is turned off
					"Provider disabled by the user. GPS turned off",
					Toast.LENGTH_LONG).show();
		}

		public void onProviderEnabled(String s) {
			Toast.makeText(
					getApplicationContext(),// shows toast if GSP is turned on
					"Provider enabled by the user. GPS turned on",
					Toast.LENGTH_LONG).show();
		}

	}
}

package com.bd.trackingstolenphone.talemul;



import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MainService extends Service {

	String mailAddress = "";
	LocationManager locationManager;
	Location locationStart;
	private Mail mail;
	private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in
																		// Meters
	private static final long MINIMUM_TIME_BETWEEN_UPDATES = 15000; // in
																	// Milliseconds
	MyLocationListener locListener;
	public static String filename = "user29";
	SharedPreferences spData;
	public String gtspass,gtto, gtsender;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO do something useful
		
//		spData = getSharedPreferences(filename, 0);
//		gtsender = spData.getString("stsender", "talemulislam@gmail.com");
//			//spData = getSharedPreferences(filename, 0);
//			gtspass = spData.getString("stspass", "tale2929");
//			//spData = getSharedPreferences(filename, 0);
//		gtto = spData.getString("stto", "talemulislam@hotmail.com");
		
		
		locListener = new MyLocationListener();
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		showCurrentLocation();
		
		locationManager.requestLocationUpdates(
				// start location update listener
				LocationManager.GPS_PROVIDER,
				MINIMUM_TIME_BETWEEN_UPDATES,
				MINIMUM_DISTANCE_CHANGE_FOR_UPDATES, locListener);
		
		return Service.START_NOT_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	protected void showCurrentLocation() {

		Location location = locationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		Toast.makeText(getApplicationContext(), "Talimul sucks", Toast.LENGTH_LONG)
		.show();

		if (location != null) {
			locationStart = location;
			String message = String.format(
					"Current service \n Longitude: %1$s \n Latitude: %2$s",
					location.getLongitude(), location.getLatitude());
			Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG)
					.show();
			
//again hbdfhb 			
			
	
			
//			mail = new Mail("talemulislam@gmail.com", "tale2929");
//			String[] toArr = new String[1]; // array of receiver mail id
//			toArr[0] = "talemulislam@yahoo.com";
//
//			mail.setTo(toArr); // receiver mail id
//			mail.setFrom("talemulislam@gmail.com"); // sender mail address
//													// gps+0804029
//			mail.setSubject("GPSTRACKER"); // to set the subject of the mail
//			mail.setBody(message); // to set the body set of the mail

//			Thread mailThread = new Thread(new Runnable() { // try to send mail
//															// through
//															// background thread
//
//						public void run() {
//							// TODO Auto-generated method stub
//							try { //
//								if (mail.send()) { // send() method Exception
//													// throw
//									Log.d("mailSend",
//											"Mail Send successfully :)");
//									// showToast(toBox.getText().toString()+" E Mail Send successfully  :)");
//								} else {
//									Log.d("mailSend", "Mail Send Hoinai");
//									// showToast("Mail Send fail   :(");
//								}
//							} catch (Exception e) {
//								Log.d("mailSend", e.toString()); // if there any
//																	// exception,then
//																	// show it
//																	// in log
//																	// trace
//																	// //showToast("Error :O :O : "+e.toString());
//							}
//						}
//					});
//			
//			
//			
//			mailThread.start();
			
			
			//dfdfdffffffffffffffffffffffffffffffffffffffffffffff
			
		}
	}
	private class MyLocationListener implements LocationListener {

		public void onLocationChanged(Location location) {
			double distanceMeters = location.distanceTo(locationStart);

			Toast.makeText(getApplicationContext(), "MAil IS send", Toast.LENGTH_LONG)
			.show();
			String message = String.format(
					"New service\n Longitude: %1$s \n Latitude: %2$s",
					location.getLongitude(), location.getLatitude()

			);

			
	
//			mail = new Mail(gtsender, gtspass);
//			String[] toArr = new String[1]; // array of receiver mail id
//			toArr[0] = gtto;
//
//			mail.setTo(toArr); // receiver mail id
//			mail.setFrom(gtsender); // sender mail address
//													// gps+0804029
			
			mail = new Mail("talemulislam@gmail.com", "tale2929");
			String[] toArr = new String[1]; // array of receiver mail id
			toArr[0] = "talemulislam@yahoo.com";

			mail.setTo(toArr); // receiver mail id
			mail.setFrom("talemulislam@gmail.com");
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
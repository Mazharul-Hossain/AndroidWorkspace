// Copyright 2012 Google Inc. All Rights Reserved.

package com.Sultan.notetakingfromvoice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * Service for the DriveSyncAdapter.
 */
public class DriveSyncService extends Service {

	private static final Object sSyncAdapterLock = new Object();

	private static DriveSyncAdapter sSyncAdapter = null;

	@Override
	public void onCreate() {
		int statusCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);

		if (statusCode == ConnectionResult.SUCCESS) {
			Log.d("Is Google Play services available?", " " + statusCode);

			synchronized (sSyncAdapterLock) {
				if (sSyncAdapter == null) {
					sSyncAdapter = new DriveSyncAdapter(
							getApplicationContext(), true);
				}
			}
		} else {

			Log.d("Is Google Play services available?", " " + statusCode);
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return sSyncAdapter.getSyncAdapterBinder();
	}
}

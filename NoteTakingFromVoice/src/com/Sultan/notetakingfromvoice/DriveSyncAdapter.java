// Copyright 2012 Google Inc. All Rights Reserved.

package com.Sultan.notetakingfromvoice;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

/**
 * SymcAdapter that will keep NotePad notes on the device in sync on Google
 * Drive.
 */
public class DriveSyncAdapter extends AbstractThreadedSyncAdapter {

	/** The context in which the Sync Adapter runs. */
	private final Context mContext;

	/**
	 * Constructs a new DriveSyncAdapter.
	 * 
	 * @see AbstractThreadedSyncAdapter
	 */
	public DriveSyncAdapter(Context context, boolean autoInitialize) {
		super(context, autoInitialize);
		mContext = context;
	}

	@Override
	public void onPerformSync(Account account, Bundle bundle, String authority,
			ContentProviderClient provider, SyncResult syncResult) {
		DriveSyncer syncer = new DriveSyncer(mContext, provider, account);
		syncer.performSync();
	}

}

package com.mazhar.drivequickstart;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.MediaStore.Files;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonFactory;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.FileList;

public class ActivityMain extends Activity implements
		DialogInterface.OnClickListener {

	// https://developers.google.com/drive/scopes
	private static final String AUTH_TOKEN_TYPE = "oauth2:https://www.googleapis.com/auth/drive";

	// https://code.google.com/apis/console/
	@SuppressWarnings("unused")
	private static final String CLIENT_ID = "737447877362.apps.googleusercontent.com";
	private static final String APIKEY_SIMPLE = "AIzaSyATkJztR9Tkp76w_BW57OjC4USrzwWRels";

	private static final int DIALOG_ACCOUNTCHOSER = 0;

	private AccountManager accountManager;
	private Account[] accounts;
	private String authName;
	private String authToken;

	@Override
	public void onClick(final DialogInterface dialogInterface, final int item) {

		processAccountSelected(accounts[item]);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(final Bundle bundle) {
		super.onCreate(bundle);

		setContentView(R.layout.activitymain);

		accountManager = AccountManager.get(this);
		accounts = accountManager.getAccountsByType("com.google");

		if (accounts == null || accounts.length == 0) {
			// TODO
		} else if (accounts.length == 1) {
			processAccountSelected(accounts[0]);
		} else if (accounts.length > 1) {
			showDialog(DIALOG_ACCOUNTCHOSER);
		}
	}

	@Override
	protected Dialog onCreateDialog(final int id) {
		switch (id) {
		case DIALOG_ACCOUNTCHOSER:
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);

			String[] names = new String[accounts.length];

			for (int i = 0; i < accounts.length; i++) {
				names[i] = accounts[i].name;
			}

			alertDialogBuilder.setItems(names, this);
			alertDialogBuilder.setTitle("Select a Google account");
			return alertDialogBuilder.create();
		}

		return null;
	}

	private void processAccountSelected(final Account account) {
		if (account != null) {
			authName = account.name.toString();
			if (!Tools.isEmpty(authName)) {
				Toast.makeText(this, authName, Toast.LENGTH_LONG).show();

				AccountManager.get(this).invalidateAuthToken("com.google",
						authToken);
				accountManager.getAuthToken(account, AUTH_TOKEN_TYPE, null,
						this, new AccountManagerCallback<Bundle>() {

							@Override
							public void run(
									final AccountManagerFuture<Bundle> future) {
								try {
									authToken = future.getResult().getString(
											AccountManager.KEY_AUTHTOKEN);
									processTokenReceived();
								} catch (OperationCanceledException exception) {
									// TODO
								} catch (Exception exception) {
									Log.d(this.getClass().getName(),
											exception.getMessage());
								}
							}
						}, null);
			}
		}
	}

	private void processListFiles(final Drive drive) {
		List<File> result = new ArrayList<File>();
		Files.List request = null;
		try {
			request = drive.files().list();
		} catch (IOException exception) {
		}

		do {
			try {
				FileList files = request.execute();

				result.addAll(files.getItems());
				request.setPageToken(files.getNextPageToken());
			} catch (IOException exception) {
				// --> 401 invalid credentials
			}
		} while (request.getPageToken() != null
				&& request.getPageToken().length() > 0);
	}

	private void processTokenReceived() {
		if (!Tools.isEmpty(authToken)) {

			GoogleCredential credential = new GoogleCredential();
			credential.setAccessToken(authToken);

			HttpTransport transport = AndroidHttp.newCompatibleTransport();

			JsonFactory jsonFactory = new AndroidJsonFactory();

			Drive drive = new Drive.Builder(transport, jsonFactory, credential)
					.setApplicationName(getString(R.string.app_name))
					.setJsonHttpRequestInitializer(
							new GoogleKeyInitializer(APIKEY_SIMPLE)).build();

			if (drive != null) {
				processListFiles(drive);
			}
		}
	}
}

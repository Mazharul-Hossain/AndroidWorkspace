package com.mazhar.drivequickstart;

/*given by google*/

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveRequest;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;

public class MainActivity extends Activity implements
		DialogInterface.OnClickListener {

	// https://developers.google.com/drive/scopes
	private static final String AUTH_TOKEN_TYPE = "oauth2:https://www.googleapis.com/auth/drive";

	// https://code.google.com/apis/console/
	@SuppressWarnings("unused")
	private static final String CLIENT_ID = "737447877362.apps.googleusercontent.com";
	private static final String APIKEY_SIMPLE = "AIzaSyATkJztR9Tkp76w_BW57OjC4USrzwWRels";

	private static final int DIALOG_ACCOUNTCHOSER = 4;

	static final int REQUEST_ACCOUNT_PICKER = 1;
	static final int REQUEST_AUTHORIZATION = 2;
	static final int CAPTURE_IMAGE = 3;

	private static Uri fileUri;
	private static Drive service;
	private GoogleAccountCredential credential;

	private AccountManager accountManager;
	private Account[] accounts;
	private String authName;
	private String authToken;

	@Override
	public void onClick(final DialogInterface dialogInterface, final int item) {

		accountManager = AccountManager.get(this);
		accountManager.getAuthToken(
				accountManager.getAccountsByType("com.google")[0], "ouath2:"
						+ DriveScopes.DRIVE, new Bundle(), true,
				new OnTokenAcquired(), null);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		credential = GoogleAccountCredential.usingOAuth2(this,
				DriveScopes.DRIVE);
		startActivityForResult(credential.newChooseAccountIntent(),
				REQUEST_ACCOUNT_PICKER);
	}

	@Override
	protected void onActivityResult(final int requestCode,
			final int resultCode, final Intent data) {

		switch (requestCode) {

		case REQUEST_ACCOUNT_PICKER:
			if (resultCode == RESULT_OK && data != null
					&& data.getExtras() != null) {
				String accountName = data
						.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);

				if (accountName != null) {
					credential.setSelectedAccountName(accountName);
					service = getDriveService(credential);
					startCameraIntent();
				}
			}
			break;

		case REQUEST_AUTHORIZATION:
			if (resultCode == Activity.RESULT_OK) {
				saveFileToDrive();
			} else {
				startActivityForResult(credential.newChooseAccountIntent(),
						REQUEST_ACCOUNT_PICKER);
			}
			break;

		case CAPTURE_IMAGE:
			if (resultCode == Activity.RESULT_OK) {
				saveFileToDrive();
			}
		}
	}

	private void startCameraIntent() {

		String mediaStorageDir = Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_PICTURES).getPath();

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)
				.format(new Date());

		fileUri = Uri.fromFile(new java.io.File(mediaStorageDir
				+ java.io.File.separator + "IMG_" + timeStamp + ".jpg"));

		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		startActivityForResult(cameraIntent, CAPTURE_IMAGE);
	}

	private void saveFileToDrive() {

		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// File's binary content
					java.io.File fileContent = new java.io.File(fileUri
							.getPath());
					FileContent mediaContent = new FileContent("image/jpeg",
							fileContent);

					// File's metadata.
					File body = new File();
					body.setTitle(fileContent.getName());
					body.setMimeType("image/jpeg");

					File file = service.files().insert(body, mediaContent)
							.execute();
					if (file != null) {
						showToast("Photo uploaded: " + file.getTitle());
						startCameraIntent();
					} else {
						showToast("Photo upload failed");
						startCameraIntent();
					}
				} catch (UserRecoverableAuthIOException e) {
					startActivityForResult(e.getIntent(), REQUEST_AUTHORIZATION);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		t.start();
	}

	private Drive getDriveService(GoogleAccountCredential credential) {
		return new Drive.Builder(AndroidHttp.newCompatibleTransport(),
				new GsonFactory(), credential).build();
	}

	public void showToast(final String toast) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(getApplicationContext(), toast,
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	private class OnTokenAcquired implements AccountManagerCallback<Bundle> {

		@Override
		public void run(AccountManagerFuture<Bundle> result) {
	        try {
	            final String token = result.getResult().getString(AccountManager.KEY_AUTHTOKEN);
	            HttpTransport httpTransport = new NetHttpTransport();
	            JacksonFactory jsonFactory = new JacksonFactory();
	            Drive.Builder b = new Drive.Builder(httpTransport, jsonFactory, null);
	            b.setJsonHttpRequestInitializer(new JsonHttpRequestInitializer() {
	                @Override
	                public void initialize(JSonHttpRequest request) throws IOException {
	                    @SuppressWarnings("rawtypes")
						DriveRequest driveRequest = (DriveRequest) request;
	                    driveRequest.setPrettyPrint(true);
	                    driveRequest.setKey(APIKEY_SIMPLE);
	                    driveRequest.setOauthToken(token);
	                }
	            });

	            final Drive drive = b.build();

	            final com.google.api.services.drive.model.File body = new com.google.api.services.drive.model.File();
	            body.setTitle("My Test File");
	    body.setDescription("A Test File");
	    body.setMimeType("text/plain");

	            final FileContent mediaContent = new FileContent("text/plain", an ordinary java.io.File you'd like to upload. Make it using a FileWriter or something, that's really outside the scope of this answer.)
	            new Thread(new Runnable() {
	                public void run() {
	                    try {
	                        com.google.api.services.drive.model.File file = drive.files().insert(body, mediaContent).execute();
	                        alreadyTriedAgain = false; // Global boolean to make sure you don't repeatedly try too many times when the server is down or your code is faulty... they'll block requests until the next day if you make 10 bad requests, I found.
	                    } catch (IOException e) {
	                        if (!alreadyTriedAgain) {
	                            alreadyTriedAgain = true;
	                            AccountManager am = AccountManager.get(activity);
	                            am.invalidateAuthToken(am.getAccounts()[0].type, null); // Requires the permissions MANAGE_ACCOUNTS & USE_CREDENTIALS in the Manifest
	                            am.getAuthToken (same as before...)
	                        } else {
	                            // Give up. Crash or log an error or whatever you want.
	                        }
	                    }
	                }
	            }).start();
	            Intent launch = (Intent)result.getResult().get(AccountManager.KEY_INTENT);
	            if (launch != null) {
	                startActivityForResult(launch, 3025);
	                return; // Not sure why... I wrote it here for some reason. Might not actually be necessary.
	            }
	        } catch (OperationCanceledException e) {
	            // Handle it...
	        } catch (AuthenticatorException e) {
	            // Handle it...
	        } catch (IOException e) {
	            // Handle it...
	        }
	    }
	}
}
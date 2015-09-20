package com.mazhar.drivequickstart;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;

import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveRequest;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;

public class Main1 {

	private static final int RESULT_OK = 0;
	private static final int RESULT_CANCELED = 1;

	AccountManager am;

	public void function() {
		am = AccountManager.get(this);
		am.getAuthToken(am.getAccounts())[0],
		    "ouath2:" + DriveScopes.DRIVE,
		    new Bundle(),
		    true,
		    new OnTokenAcquired(),
		    null);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == 3025) {
	        switch (resultCode) {
	            case RESULT_OK:
	                am = AccountManager.get(activity);
	                am.getAuthToken(
	                		am.getAccounts())[0], "ouath2:" + DriveScopes.DRIVE, new Bundle(), true, new OnTokenAcquired(), null);
	    		    break;
	            case RESULT_CANCELED:
	                // This probably means the user refused to log in. Explain to them why they need to log in.
	                break;
	            default:
	                // This isn't expected... maybe just log whatever code was returned.
	                break;
	        }
	    } else {
	        // Your application has other intents that it fires off besides the one for Drive's log in if it ever reaches this spot. Handle it here however you'd like.
	    }
	}

	private java.io.File downloadGFileToJFolder(Drive drive, String token,

	File gFile, java.io.File jFolder) throws IOException {
		if (gFile.getDownloadUrl() != null
				&& gFile.getDownloadUrl().length() > 0) {
			if (jFolder == null) {
				jFolder = Environment.getExternalStorageDirectory();
				jFolder.mkdirs();
			}
			try {

				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet(gFile.getDownloadUrl());
				get.setHeader("Authorization", "Bearer " + token);
				HttpResponse response = client.execute(get);

				InputStream inputStream = response.getEntity().getContent();
				jFolder.mkdirs();
				java.io.File jFile = new java.io.File(jFolder.getAbsolutePath()
						+ "/" + getGFileName(gFile)); // getGFileName() is my
														// own method... it just
														// grabs
														// originalFilename if
														// it exists or title if
														// it doesn't.
				FileOutputStream fileStream = new FileOutputStream(jFile);
				byte buffer[] = new byte[1024];
				int length;
				while ((length = inputStream.read(buffer)) > 0) {
					fileStream.write(buffer, 0, length);
				}
				fileStream.close();
				inputStream.close();
				return jFile;
			} catch (IOException e) {
				// Handle IOExceptions here...
				return null;
			}
		} else {
			// Handle the case where the file on Google Drive has no length
			// here.
			return null;
		}
	}

	public void updateGFileFromJFile(Drive drive, File gFile, java.io.File jFile)
			throws IOException {
		FileContent gContent = new FileContent("text/csv", jFile);
		gFile.setModifiedDate(new DateTime(false, jFile.lastModified(), 0));
		gFile = drive.files().update(gFile.getId(), gFile, gContent)
				.setSetModifiedDate(true).execute();
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
	                    DriveRequest driveRequest = (DriveRequest) request;
	                    driveRequest.setPrettyPrint(true);
	                    driveRequest.setKey(CLIENT ID YOU GOT WHEN SETTING UP THE CONSOLE BEFORE YOU STARTED CODING)
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

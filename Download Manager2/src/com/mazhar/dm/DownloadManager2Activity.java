package com.mazhar.dm;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class DownloadManager2Activity extends Activity {
	// Mazhar bla bla
	String file_URL = "http://10.0.2.2/bdbay_beta/Stupid.pdf";
	private DownloadManager mgr;
	private long lastDownload;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mgr = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
	}

	public void startDownload(View v) {
		// String file_URL = "http://commonsware.com/misc/test.mp4";
		// private DownloadManager mgr;
		// mgr = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
		Uri uri = Uri.parse(file_URL);
		Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_DOWNLOADS).mkdirs();

		lastDownload = mgr.enqueue(new DownloadManager.Request(uri)
				.setAllowedNetworkTypes(
						DownloadManager.Request.NETWORK_WIFI
								| DownloadManager.Request.NETWORK_MOBILE)
				.setAllowedOverRoaming(false)
				.setTitle("Demo1")
				.setDescription("Something useful. No, really.")
				.setDestinationInExternalPublicDir(
						Environment.DIRECTORY_DOWNLOADS, "Stupid.pdf"));
		v.setEnabled(false);
		findViewById(R.id.query).setEnabled(true);
	}

	public void queryStatus(View v) {

		Cursor c = mgr.query(new DownloadManager.Query()
				.setFilterById(lastDownload));
		if (c == null) {
			Toast.makeText(this, "Download not found!", Toast.LENGTH_LONG)
					.show();
		} else {
			c.moveToFirst();
			Log.d(getClass().getName(),
					"COLUMN_ID: "
							+ c.getLong(c
									.getColumnIndex(DownloadManager.COLUMN_ID)));
			Log.d(getClass().getName(),
					"COLUMN_BYTES_DOWNLOADED_SO_FAR: "
							+ c.getLong(c
									.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)));
			Log.d(getClass().getName(),
					"COLUMN_LAST_MODIFIED_TIMESTAMP: "
							+ c.getLong(c
									.getColumnIndex(DownloadManager.COLUMN_LAST_MODIFIED_TIMESTAMP)));
			Log.d(getClass().getName(),
					"COLUMN_LOCAL_URI: "
							+ c.getString(c
									.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)));
			Log.d(getClass().getName(),
					"COLUMN_STATUS: "
							+ c.getInt(c
									.getColumnIndex(DownloadManager.COLUMN_STATUS)));
			Log.d(getClass().getName(),
					"COLUMN_REASON: "
							+ c.getInt(c
									.getColumnIndex(DownloadManager.COLUMN_REASON)));
			String statusMessage = null;
			int columnIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
			if (DownloadManager.STATUS_SUCCESSFUL == c.getInt(columnIndex)) {
				statusMessage = "Download is SUCCESSFUL";
			} else if (DownloadManager.STATUS_RUNNING == c.getInt(columnIndex)) {
				statusMessage = "Download is RUNNING";
			} else if (DownloadManager.STATUS_FAILED == c.getInt(columnIndex)) {
				statusMessage = "Download is FAILED";
			}
			Toast.makeText(this, statusMessage, Toast.LENGTH_LONG).show();
		}
	}

	public void viewLog(View v) {

		Intent i = new Intent();
		i.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
		startActivity(i);
	}
}
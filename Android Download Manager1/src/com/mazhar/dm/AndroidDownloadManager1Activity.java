package com.mazhar.dm;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class AndroidDownloadManager1Activity extends Activity {

	String file_URL = "http://www.vogella.de/img/lars/LarsVogelArticle7.png";

	private long enqueue111;
	private DownloadManager dm;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Log.d("Main", "Main : Started correctly correctly ");

		BroadcastReceiver receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {

				String action = intent.getAction();
				if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {

					long downloadId = intent.getLongExtra(
							DownloadManager.EXTRA_DOWNLOAD_ID, 0);

					Query query = new Query();
					query.setFilterById(enqueue111);
					Cursor c = dm.query(query);
					if (c.moveToFirst()) {
						int columnIndex = c
								.getColumnIndex(DownloadManager.COLUMN_STATUS);
						if (DownloadManager.STATUS_SUCCESSFUL == c
								.getInt(columnIndex)) {

							ImageView view = (ImageView) findViewById(R.id.imageView1);
							String uriString = c
									.getString(c
											.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));

							/*
							 * AndroidFileFunctions.setFileValue("ABC.pdf",
							 * uriString, AndroidDownloadManagerActivity.this);
							 */

							view.setImageURI(Uri.parse(uriString));
						}
					}
				}
			}
		};

		registerReceiver(receiver, new IntentFilter(
				DownloadManager.ACTION_DOWNLOAD_COMPLETE));
	}

	public void onClick(View view) {

		Log.d("onClick", "onClick : Gettin File correctly 1");
		dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
		Log.d("onClick", "onClick : Gettin File correctly 2");

		Uri uri = Uri.parse(file_URL);
		Request request = new Request(uri);
		Log.d("onClick", "onClick : Gettin File correctly 3");

		enqueue111 = dm.enqueue(request);
		Log.d("onClick", "onClick : Gettin File correctly 4");
	}

	public void showDownload(View view) {

		Intent i = new Intent();
		i.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
		startActivity(i);
	}
}
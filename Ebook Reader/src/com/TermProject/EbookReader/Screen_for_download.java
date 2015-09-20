package com.TermProject.EbookReader;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class Screen_for_download extends Main_activity implements
		View.OnClickListener {

	WebView wv;
	ImageView home, store;

	// private long downloadReference;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.download_layout);

		home = (ImageView) findViewById(R.id.download_logo_top_left);
		home.setOnClickListener(this);

		store = (ImageView) findViewById(R.id.download_logo_top_right);
		store.setOnClickListener(this);

		wv = (WebView) findViewById(R.id.mainwebview);
		wv.getSettings().setJavaScriptEnabled(true);

		this.wv.getSettings().setSupportZoom(false);
		this.wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		this.wv.loadUrl("https://https://ovy-saba.rhcloud.com/bookstore/index.php/home");

		wv.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});

		wv.setDownloadListener(new DownloadListener() {
			public void onDownloadStart(String url, String userAgent,
					String contentDisposition, String mimetype,
					long contentLength) {
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
			}
		});
	}

	public void onClick(View v) {
		if (v == home) {
			Intent intent = new Intent(Screen_for_download.this,
					Screen_for_read_book.class);
			startActivity(intent);
		} else if (v == store) {
			startActivity(new Intent(Screen_for_download.this,
					Screen_for_download.class));

		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if ((keyCode == KeyEvent.KEYCODE_BACK) && wv.canGoBack()) {
			wv.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
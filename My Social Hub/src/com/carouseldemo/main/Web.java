package com.carouseldemo.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class Web extends Activity{

	WebView ourBrow;
	String url;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web);
		
		ourBrow = (WebView) findViewById(R.id.webView1);
		
		ourBrow.getSettings().setJavaScriptEnabled(true);
		ourBrow.getSettings().setLoadWithOverviewMode(true);
		ourBrow.getSettings().setUseWideViewPort(true);
		ourBrow.setWebViewClient(new ourViewClient());
		
		Intent myIntent= getIntent();
		url = myIntent.getStringExtra("key");
		try
		{
			ourBrow.loadUrl(url);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void onBackPressed(){
	     // do something here and don't write super.onBackPressed()
		if(ourBrow.canGoBack())
			ourBrow.goBack();
		else
		{
			super.onBackPressed();
		}
		
	}
	

}

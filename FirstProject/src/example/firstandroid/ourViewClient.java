package example.firstandroid;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ourViewClient extends WebViewClient {
	
	public boolean shouldOverrrideUrlLoading(WebView v, String url)
	{
		v.loadUrl(url);
		return true;
	}
	

}

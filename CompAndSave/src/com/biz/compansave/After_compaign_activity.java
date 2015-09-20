package com.biz.compansave;
 
import com.biz.compandsave.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
 
public class After_compaign_activity extends Activity implements OnClickListener {
    Button go_to_page;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.after_campaign);
    
        go_to_page = (Button) findViewById(R.id.after_compaign_btn);
        go_to_page.setOnClickListener(this);
        
        CASGiftBoxApp app= (CASGiftBoxApp)getApplication();
		app.applyTypefaceToAllTextviews(getWindow().getDecorView().findViewById(android.R.id.content));
        
    }

	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		if(v == go_to_page)
		{
			Uri uriUrl = Uri.parse("http://www.compandsave.com/");
		    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
		    startActivity(launchBrowser);
		}
	}
    
}
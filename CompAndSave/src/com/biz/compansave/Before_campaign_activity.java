package com.biz.compansave;
 
import java.text.SimpleDateFormat;
import java.util.Date;

import com.biz.compandsave.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
 
public class Before_campaign_activity extends Activity implements OnClickListener {
 
    private santimer timer;
    private long startTime = 50000;
    private final long interval = 1000;
    private TextView text,text1;
    private Button btn1,btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        
        setContentView(R.layout.before_campaign);
        text = (TextView) findViewById(R.id.before_campaign_timer);
        text1 = (TextView) findViewById(R.id.before_campaign_tV1);
        text1 = (TextView) findViewById(R.id.before_campaign_tV2);
        text1 = (TextView) findViewById(R.id.before_campaign_tV3);
        text1 = (TextView) findViewById(R.id.before_campaign_tV4);
        
        
        btn1= (Button) findViewById(R.id.before_campaign_btn1);
        btn1.setOnClickListener(this);
        btn2= (Button) findViewById(R.id.before_campaign_btn2);       
        btn2.setOnClickListener(this);
        //    time_eplapsed = (TextView) findViewById(R.id.timeElapsed);
        set_text_view();
        timer = new santimer(startTime, interval);
        timer.start();
        CASGiftBoxApp app= (CASGiftBoxApp)getApplication();
		app.applyTypefaceToAllTextviews(getWindow().getDecorView().findViewById(android.R.id.content));
     //   text.setText(text.getText() + String.valueOf(startTime));
    }
    
    
    void set_text_view()
    {
    	try {   
        	SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss_yyyy.MM.dd");
    	    Date date1 = new java.util.Date();
    	    Date date2 = df.parse("04:00:00_2013.12.16");
    	    long diff = date2.getTime() - date1.getTime();

    		startTime = diff;
    	    diff =diff/1000;
    	    long days = diff / 86400;
    	    long left = diff % 86400;
    	    long hours = left/3600;
    	    long moreLeft = left%3600;
    	    long mins = moreLeft/60;
    	    long secs = moreLeft%60;
    	    text.setText("   "+ days +"     " + hours+"     "+ mins +"     "+secs+"     ");
    	} 
    	catch (ParseException e)
    	{
    	    Log.e("ki ar komu", "Exception", e);
    	}
    	catch (java.text.ParseException e) 
    	{
		    Log.e("SorryT", "Exception", e);
		}
    }
    
    @Override
    public void onClick(View v) 
    {
        // TODO Auto-generated method stub
    	if(v == btn1)
    	{
    		String url = "http://www.compandsave.com/christmas";
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(url));
			startActivity(i);
    	}
    	else if (v== btn2)
    	{
    		Intent intent = new Intent( Before_campaign_activity.this,Choose_option_activity.class );
    		startActivity(intent);
    		Before_campaign_activity.this.finish();
    	}
    	
    }
    
     private class santimer extends CountDownTimer
     {
        public santimer(long millisInFuture, long countDownInterval) 
        {
        	super(millisInFuture, countDownInterval);
       }
        
        @Override
        public void onFinish() 
        {
        	startActivity(new Intent(Before_campaign_activity.this , Campaign_started_activity.class));
        	Before_campaign_activity.this.finish();
        }
 
        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub
            set_text_view();
        }
 
    }
}
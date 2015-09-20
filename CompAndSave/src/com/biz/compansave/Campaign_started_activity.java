package com.biz.compansave;
 
import java.util.Calendar;

import com.biz.compandsave.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
 
public class Campaign_started_activity extends Activity implements OnClickListener{
    Button go_to_page,edit_gift ;
    ImageView img;
    TextView txt_view1;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.campaign_started);
        
        go_to_page = (Button) findViewById(R.id.camp_strt_btn1);
        go_to_page.setOnClickListener(this);
        
        edit_gift = (Button) findViewById(R.id.camp_strt_btn2);
        edit_gift.setOnClickListener(this);
        
        img= (ImageView) findViewById(R.id.camp_strt_imageview1);
        
        
        txt_view1 = (TextView) findViewById(R.id.camp_strt_textview1);
        
        
        CASGiftBoxApp app= (CASGiftBoxApp)getApplication();
		app.applyTypefaceToAllTextviews(getWindow().getDecorView().findViewById(android.R.id.content));
        
        check_date() ;
    }
    
    public void check_date() 
    {
    	  Calendar calendar = Calendar.getInstance();
     	  int thisYear = calendar.get(Calendar.YEAR);
    	  int thisMonth = calendar.get(Calendar.MONTH);
    	  int thisDay = calendar.get(Calendar.DAY_OF_MONTH);
		  
    	  if (thisYear == 2013 && thisMonth == 11 && thisDay == 16) 
    	  {
    		  String s =  (String) txt_view1.getText();
    		  s =s+"\n $300 in ink";
    		  txt_view1.setText(s);
    		  img.setImageResource(R.drawable.ink);
    	  } 
    	  else if (thisYear == 2013 && thisMonth == 11 && thisDay == 17 ) 
    	  {
    		  String s =  (String) txt_view1.getText();
    		   s =s+"\n His/Her Gift";
    		  txt_view1.setText(s);
    		  img.setImageResource(R.drawable.his_her); 
    	  } 
    	  else if (thisYear == 2013 && thisMonth == 11 && thisDay == 18) 
    	  {
    		  String s =  (String) txt_view1.getText();
    		  img.setImageResource(R.drawable.igifts);
    		  s =s+"\n iGifts";
    		  txt_view1.setText(s);  
    	  } 
    	  else if (thisYear == 2013 && thisMonth == 11 && thisDay == 19) 
    	  {
    		  String s =  (String) txt_view1.getText();
    		  img.setImageResource(R.drawable.iphone5c);
    		  s =s+"\n iPhone 5C";
    		  txt_view1.setText(s);
    		  
    	  } 
    	  else if (thisYear == 2013 && thisMonth == 11 && thisDay == 20) 
    	  {
    		  String s =  (String) txt_view1.getText();
    		  img.setImageResource(R.drawable.diamond);
    		  s =s+"\n Diamond";
    		  txt_view1.setText(s);
    	  } 
    	  else if (thisYear == 2013 && thisMonth == 11 && thisDay >= 21 && thisDay <= 23) 
    	  {
    		  String s =  (String) txt_view1.getText();
    		  img.setImageResource(R.drawable.hp);
    		  s =s+"\n Hp Laptop";
    		  txt_view1.setText(s);
    	  } 
    	  else if (thisYear == 2013 && thisMonth == 11 && thisDay == 24) 
    	  {
    		  String s =  (String) txt_view1.getText();
    		  img.setImageResource(R.drawable.canon);
    		  s =s+"\n Canon SLR";
    		  txt_view1.setText(s);
    		  
    	  } 
    	  else if (thisYear == 2013 && thisMonth == 11 && thisDay == 25) 
    	  {
    		  String s =  (String) txt_view1.getText();
    		  img.setImageResource(R.drawable.ipad);
    		  s =s+"\n ipad Air";
    		  txt_view1.setText(s);
    		  
    	  } 
    	  else if (thisYear == 2013 && thisMonth == 11 && thisDay == 26) 
    	  {
    		  String s =  (String) txt_view1.getText();
    		  img.setImageResource(R.drawable.set);
    		  s =s+"\n Swarovski";
    		  txt_view1.setText(s);
          } 
    	  else if (thisYear == 2013 && thisMonth == 11 && thisDay == 27) 
    	  {
    		  String s =  (String) txt_view1.getText();
    		  img.setImageResource(R.drawable.xbox_kinect);
    		  s =s+"\n Xbox 360 + Kinect";
    		  txt_view1.setText(s);
    	  } 
    	  else if (thisYear == 2013 && thisMonth == 11 && thisDay >= 28 && thisDay <= 30)
    	  {
    		  String s =  (String) txt_view1.getText();
    		  img.setImageResource(R.drawable.home_theater);
    		  s =s+"\n Home Theater";
    		  txt_view1.setText(s);
    		  
    	  }
    	  else if (thisYear == 2013 && thisMonth == 11 && thisDay == 31) 
    	  {
    		  String s =  (String) txt_view1.getText();
    		  img.setImageResource(R.drawable.lgtv);
    		  s =s+"\n 50\" Plasma Tv";
    		  txt_view1.setText(s);
    		  
    	  }     
    }

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		if( v == go_to_page)
		{
			Uri uriUrl = Uri.parse("http://www.compandsave.com/giveaway");
		    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
		    startActivity(launchBrowser);
		}
		else if( v ==  edit_gift)
		{
			startActivity(new Intent(Campaign_started_activity.this , Choose_option_activity.class));
			Campaign_started_activity.this.finish();	
		}
	}
    
}
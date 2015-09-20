package com.biz.compansave;
 
import java.util.Calendar;

import com.biz.compandsave.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
 
public class SplashScreenActivity extends Activity{
 
	ImageView topImageView;
	ImageView lowerImageView;
	final long splashDelay = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);
        topImageView = (ImageView)findViewById(R.id.topImage);
        lowerImageView = (ImageView)findViewById(R.id.lowerImage);
        runCheck();
        
        
        view_animation(); 
        
    //    set_icon_text();
    }
    
    public void runCheck() {
		SharedPreferences runCheck = getSharedPreferences("hasRunBefore", 0);
		Boolean hasRun = runCheck.getBoolean("hasRun", false);

		if (!hasRun) {
			SharedPreferences settings = getSharedPreferences("hasRunBefore", 0);
			SharedPreferences.Editor edit = settings.edit();
			edit.putBoolean("hasRun", true); // set to has run
			edit.commit(); // apply
			// code for if this is the first time the app has run
			AlarmSchedule aAlarmSchedule = new AlarmSchedule(this);
			aAlarmSchedule.AlarmSetter();
		}
	}
    

    
    public int check_date() 
    {
    	  String TAG = "Calander";
    	  Calendar calendar = Calendar.getInstance();

    	  int thisYear = calendar.get(Calendar.YEAR);
    	  int thisMonth = calendar.get(Calendar.MONTH);
    	  int thisDay = calendar.get(Calendar.DAY_OF_MONTH);

    	  return thisDay;
   
    }
    
    
    @Override
	protected void onPause() {
		super.onPause();
		topImageView= (ImageView) findViewById(R.id.topImage);
		topImageView.clearAnimation();
		
		lowerImageView = (ImageView) findViewById(R.id.lowerImage);
		lowerImageView.clearAnimation(); 
		
	 }
    
    private TranslateAnimation getTranslationAnimation(float startX,float startY,float endX,float endY,long delay)
    {
    	TranslateAnimation ta = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,startX, Animation.RELATIVE_TO_PARENT,endX, Animation.RELATIVE_TO_PARENT,startY, Animation.RELATIVE_TO_PARENT, endY);
    	ta.setInterpolator(new DecelerateInterpolator());
    	ta.setStartOffset(delay);
    	ta.setDuration(300);
    	ta.setFillAfter(true); //HERE
    	
    	return ta;
    }
    public void view_animation()
	 {
		 TranslateAnimation rightToLeftOut = getTranslationAnimation(topImageView.getX(),topImageView.getY(),-topImageView.getWidth(),topImageView.getY(),splashDelay);
	     
	     TranslateAnimation leftToRightOut = getTranslationAnimation(lowerImageView.getX(),lowerImageView.getY(),lowerImageView.getWidth(),lowerImageView.getY(),splashDelay);
	     
	     topImageView.startAnimation(rightToLeftOut);
	     
	     lowerImageView.startAnimation(leftToRightOut);
	     
	    
	     rightToLeftOut.setAnimationListener(new AnimationListener() 
	     {
			
			public void onAnimationStart(Animation animation){}
			public void onAnimationRepeat(Animation animation) {} 
			 
			public void onAnimationEnd(Animation animation)
			{
		    	  Calendar calendar = Calendar.getInstance();
		    	  int thisYear = calendar.get(Calendar.YEAR);
		    	  int thisMonth = calendar.get(Calendar.MONTH); 
		    	  int thisDay = calendar.get(Calendar.DAY_OF_MONTH);
		    	  
				if( thisYear==2014)
				{
					startActivity(new Intent(SplashScreenActivity.this , After_compaign_activity.class));
					SplashScreenActivity.this.finish();	
				}
				else if (thisDay >=16 && thisDay <=31 && thisMonth==Calendar.DECEMBER && thisYear == 2013)
				{
					startActivity(new Intent(SplashScreenActivity.this , Campaign_started_activity.class));
					SplashScreenActivity.this.finish();
				} 
				else
				{
					change_activity();
				}
			}
		});
	     
	 }
    
    public void change_activity()
    {
    	DateSharedPref dsf1 = new DateSharedPref(this);
		if( dsf1.check_empty() )
		{
			startActivity(new Intent(SplashScreenActivity.this , Choose_option_activity.class));
			SplashScreenActivity.this.finish();
		}
		else
		{
			startActivity(new Intent(SplashScreenActivity.this , Before_campaign_activity.class));
			SplashScreenActivity.this.finish();	
		}
    }

    
}
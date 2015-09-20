package com.biz.compansave;

import android.app.Application;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class CASGiftBoxApp extends Application {
	public static final String FNT_MUSEO_NORMAL = "museo_regular.otf";
	public static final String FNT_MUSEO_ITALIC = "museo_italic.otf";
	@Override
	public void onCreate()
	{
	   super.onCreate();
	}
	public Typeface getFontWithName(String fnt)
	{
		Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/"+fnt);
		return tf;
	}
	
	public void applyTypefaceToAllTextviews(View parent)
	{
		if(parent instanceof ViewGroup)
		{
			ViewGroup rootGroup = (ViewGroup)parent;
			for(int i= 0; i< rootGroup.getChildCount() ;i++)
			{
				applyTypefaceToAllTextviews(rootGroup.getChildAt(i));
			}
		}
		if(parent instanceof TextView)
		{
			TextView tv = (TextView)parent;
			tv.setTypeface(getFontWithName(FNT_MUSEO_NORMAL));
			tv.setShadowLayer(2.0f, 1.0f, 1.0f, Color.BLACK);
		}
		if(parent instanceof Button)
		{
			Button b = (Button)parent;
			b.setTypeface(getFontWithName(FNT_MUSEO_NORMAL));
			b.setShadowLayer(2.0f, 1.0f, 1.0f, Color.BLACK);
			b.setPadding(10,0,10,0);
		}
	}
	

}

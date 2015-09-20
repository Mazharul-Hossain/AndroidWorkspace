package com.carouseldemo.main;

import java.util.List;

import com.carouseldemo.controls.CarouselAdapter;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.View;

public class ItemClickTask 
{
	Activity activity;
	ItemClickTask(Activity act)
	{
		activity = act ; 
	}
	
	void Task_After_clicking(CarouselAdapter<?> parent, View view,int position, long id)
	{
		boolean flag = false;
		Intent openStartingPoint = new Intent(activity.getApplicationContext(),Web.class);
		
		final PackageManager pm = activity.getPackageManager();
		//get a list of installed apps.
		List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
		switch(position)
		{
			case 0:
				
				for (ApplicationInfo packageInfo : packages) {
					
					String s =  packageInfo.packageName;
					if(s.compareTo("com.facebook.katana")==0)
					{
						flag = true;
						Intent facebookIntent = activity.getPackageManager().getLaunchIntentForPackage(s);
						activity.startActivity(facebookIntent);
					}
					
				}
				if(!flag)
				{
					openStartingPoint.putExtra("key", "https://www.facebook.com");
					activity.startActivity(openStartingPoint);
				}
				
				break;
			case 1:
				
				for (ApplicationInfo packageInfo : packages) {
					
					String s =  packageInfo.packageName;
					if(s.compareTo("com.twitter.android")==0)
					{
						flag = true;
						Intent twitterIntent = activity.getPackageManager().getLaunchIntentForPackage(s);
						activity.startActivity(twitterIntent);
					}
					
				}
				if(!flag)
				{
					openStartingPoint.putExtra("key", "https://twitter.com/");
					activity.startActivity(openStartingPoint);
				}
				
				
				
				break;
			case 2:
				
				for (ApplicationInfo packageInfo : packages) {
					
					String s =  packageInfo.packageName;
					if(s.compareTo("com.instagram.android")==0)
					{
						flag = true;
						Intent instagramIntent = activity.getPackageManager().getLaunchIntentForPackage(s);
						activity.startActivity(instagramIntent);
					}
					
				}
				if(!flag)
				{
					openStartingPoint.putExtra("key", "http://instagram.com/");
					activity.startActivity(openStartingPoint);
				}
								
				break;
			case 3:
				
				
				for (ApplicationInfo packageInfo : packages) {
					
					String s =  packageInfo.packageName;
					if(s.compareTo("com.pinterest")==0)
					{
						flag = true;
						Intent instagramIntent = activity.getPackageManager().getLaunchIntentForPackage(s);
						activity.startActivity(instagramIntent);
					}
					
				}
				if(!flag)
				{
					openStartingPoint.putExtra("key", "https://www.pinterest.com/");
					activity.startActivity(openStartingPoint);
				}
				
							
				break;
			case 4:
				
				for (ApplicationInfo packageInfo : packages) {
					
					String s =  packageInfo.packageName;
					if(s.compareTo("com.linkedin.android")==0)
					{
						flag = true;
						Intent instagramIntent = activity.getPackageManager().getLaunchIntentForPackage(s);
						activity.startActivity(instagramIntent);
					}
					
				}
				if(!flag)
				{
					openStartingPoint.putExtra("key", "https://www.linkedin.com/");
					activity.startActivity(openStartingPoint);
				}
				
				
				
				break;
			case 5:
				
				for (ApplicationInfo packageInfo : packages) {
					
					String s =  packageInfo.packageName;
					if(s.compareTo("com.tumblr")==0)
					{
						flag = true;
						Intent instagramIntent = activity.getPackageManager().getLaunchIntentForPackage(s);
						activity.startActivity(instagramIntent);
					}
					
				}
				if(!flag)
				{
					openStartingPoint.putExtra("key", "https://www.tumblr.com/");
					activity.startActivity(openStartingPoint);
				}
				
				
				
				break;
			case 6:
				
				for (ApplicationInfo packageInfo : packages) {
					
					String s =  packageInfo.packageName;
					if(s.compareTo("com.yahoo.mobile.client.android.flickr")==0)
					{
						flag = true;
						Intent instagramIntent = activity.getPackageManager().getLaunchIntentForPackage(s);
						activity.startActivity(instagramIntent);
					}
					
				}
				if(!flag)
				{
					openStartingPoint.putExtra("key", "http://www.flickr.com/");
					activity.startActivity(openStartingPoint);
				}
				
				
				break;
				
			case 7:
				
				for (ApplicationInfo packageInfo : packages) {
					
					String s =  packageInfo.packageName;
					if(s.compareTo("com.google.android.gms.samples.plus")==0)
					{
						flag = true;
						Intent instagramIntent = activity.getPackageManager().getLaunchIntentForPackage(s);
						activity.startActivity(instagramIntent);
					}
					
				}
				if(!flag)
				{
					openStartingPoint.putExtra("key", "https://accounts.google.com/ServiceLogin?service=oz&passive=1209600&continue=https://plus.google.com/?gpsrc%3Dgplp0%26partnerid%3Dgplp0");
					activity.startActivity(openStartingPoint);
				}
				
				
				break;
				
			case 8:
				
				for (ApplicationInfo packageInfo : packages) {
					
					String s =  packageInfo.packageName;
					if(s.compareTo("com.tencent.mm")==0)
					{
						flag = true;
						Intent instagramIntent = activity.getPackageManager().getLaunchIntentForPackage(s);
						activity.startActivity(instagramIntent);
					}
					
				}
				if(!flag)
				{
					openStartingPoint.putExtra("key", "http://www.wechat.com/");
					activity.startActivity(openStartingPoint);
				}
				
				
				
				break;
				
			case 9:
				
				for (ApplicationInfo packageInfo : packages) {
					
					String s =  packageInfo.packageName;
					if(s.compareTo("com.whatsapp")==0)
					{
						flag = true;
						Intent instagramIntent = activity.getPackageManager().getLaunchIntentForPackage(s);
						activity.startActivity(instagramIntent);
					}
					
				}
				if(!flag)
				{
					openStartingPoint.putExtra("key", "http://www.whatsapp.com/");
					activity.startActivity(openStartingPoint);
				}
								
				
				break;
				
			case 10:
				
				for (ApplicationInfo packageInfo : packages) {
					
					String s =  packageInfo.packageName;
					if(s.compareTo("com.vkontakte.android")==0)
					{
						flag = true;
						Intent instagramIntent = activity.getPackageManager().getLaunchIntentForPackage(s);
						activity.startActivity(instagramIntent);
					}
					
				}
				if(!flag)
				{

					openStartingPoint.putExtra("key", "http://vk.com/");
					activity.startActivity(openStartingPoint);
				}
				
				
				break;
				
			case 11:
				
				for (ApplicationInfo packageInfo : packages) {
					
					String s =  packageInfo.packageName;
					if(s.compareTo("com.myspace.android.apk")==0)
					{
						flag = true;
						Intent instagramIntent = activity.getPackageManager().getLaunchIntentForPackage(s);
						activity.startActivity(instagramIntent);
					}
					
				}
				if(!flag)
				{

					openStartingPoint.putExtra("key", "https://myspace.com/");
					activity.startActivity(openStartingPoint);
				}
				
								
				break;
			case 12:
				
				
			/*	for (ApplicationInfo packageInfo : packages) {
					
					String s =  packageInfo.packageName;
					if(s.compareTo("com.myspace.android.apk")==0)
					{
						flag = true;
						Intent instagramIntent = getPackageManager().getLaunchIntentForPackage(s);
						startActivity(instagramIntent);
					}
					
				}*/
				if(!flag)
				{

					openStartingPoint.putExtra("key", "http://www.cafemom.com/");
					activity.startActivity(openStartingPoint);
				}
				
				
				
				
				break;
			case 13:
				
				
				for (ApplicationInfo packageInfo : packages) {
					
					String s =  packageInfo.packageName;
					if(s.compareTo("com.skype.raider")==0)
					{
						flag = true;
						Intent skypeIntent = activity.getPackageManager().getLaunchIntentForPackage(s);
						activity.startActivity(skypeIntent);
					}
					
				}
				if(!flag)
				{
					openStartingPoint.putExtra("key", "http://www.skype.com/");
					activity.startActivity(openStartingPoint);
				}
				break; 
		}
	}
}

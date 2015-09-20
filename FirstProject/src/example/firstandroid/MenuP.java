package example.firstandroid;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuP extends ListActivity{
	
	ArrayList<String> stringArrayList = new ArrayList<String>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		final PackageManager pm = getPackageManager();
		//get a list of installed apps.
		List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

		for (ApplicationInfo packageInfo : packages) {
			
			String s =  packageInfo.packageName;
			
			//if(s.startsWith("facebook") || s.startsWith("twitter") || s.startsWith("skype"))
				//+ "  "+ "Launch Activity :" +
					//pm.getLaunchIntentForPackage(packageInfo.packageName) +"\n";
			try
			{
				if(s.toLowerCase().contains("facebook") || s.toLowerCase().contains("twitter")|| s.toLowerCase().contains("instagram")
				|| s.toLowerCase().contains("pinterest")|| s.toLowerCase().contains("linkedin")|| s.toLowerCase().contains("tumblr")
				|| s.toLowerCase().contains("flickr")|| s.toLowerCase().contains("google")|| s.toLowerCase().contains("wechat")
				|| s.toLowerCase().contains("whatsapp")|| s.toLowerCase().contains("vk")|| s.toLowerCase().contains("myspace")
				|| s.toLowerCase().contains("cafemom")|| s.toLowerCase().contains("skype"))
				{
					//s = s.replace("com.", "");
					stringArrayList.add(s);
				}
				
			}
			catch(Exception e){
				e.printStackTrace();
			}
		   
		}
		
		
		//full screen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN );
		
		setListAdapter(new ArrayAdapter<String>(MenuP.this, android.R.layout.simple_list_item_1, stringArrayList));
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		String cheese = stringArrayList.get(position);
		
		Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(cheese);
		startActivity(LaunchIntent);
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuInflater blowUp = getMenuInflater();
		blowUp.inflate(R.menu.cool_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
			case R.id.aboutUs:
				Intent i = new Intent("example.firstandroid.ABOUT");
				startActivity(i);
				break;
			case R.id.preferences:
				Intent p = new Intent("example.firstandroid.PREFS");
				startActivity(p);
				break;
				
			case R.id.exit:
				finish();
				break;
		}
		
		return false;
	}

	
	

}

package example.firstandroid;

import java.util.List;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

public class PackageList extends Activity{

	TextView display;
	String str = " ";
	int count = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.packagelist);
		
		display=(TextView)findViewById(R.id.userName);
		
		final PackageManager pm = getPackageManager();
		//get a list of installed apps.
		List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

		for (ApplicationInfo packageInfo : packages) {
			str = str + (++count) +":Installed package :" + packageInfo.packageName + "\n";//+ "  "+ "Launch Activity :" +
					//pm.getLaunchIntentForPackage(packageInfo.packageName) +"\n";
			
		   // Log.d(TAG, "Installed package :" + packageInfo.packageName);
		    //Log.d(TAG, "Launch Activity :" + pm.getLaunchIntentForPackage(packageInfo.packageName)); 
		}
		
		display.setText(str);
	}

}

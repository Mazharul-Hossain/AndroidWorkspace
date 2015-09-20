package com.fest.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Running_apps extends Activity implements OnItemClickListener {
   
   /* whether or not to include system apps */
   private static final boolean INCLUDE_SYSTEM_APPS = false;
   
   private ListView mAppsList;
   private RunningAppListAdapter mAdapter;
   private List<App> mApps;
   
   Button click,ok_button,finishbtn,database;
   TextView app_title_ins,app_show_perm;
   ImageView imgv_for_ins_app;
   private Dialog myDialog;
   
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.listrunningapps);
      
      mAppsList = (ListView) findViewById(R.id.appslist);
      mAppsList.setOnItemClickListener(this);
   
      mApps = loadInstalledApps(INCLUDE_SYSTEM_APPS);
      
      mAdapter = new RunningAppListAdapter(getApplicationContext());
      mAdapter.setListItems(mApps);
      mAppsList.setAdapter(mAdapter);
      
   //   new LoadIconsTask().execute(mApps.toArray(new App[]{}));
   }
   
   @Override
   public void onItemClick(AdapterView<?> parent, View view, int position, long id)
   {
	   final App app = (App) parent.getItemAtPosition(position);
	   String[] reqPermission=app.getPermissionInfo();
	     
	      String str2 =" ";
	      if(reqPermission!=null)
	        for(int i=0;i<reqPermission.length;i++)
	        {
	        	String str1 = reqPermission[i] ;
	        	str2 = str2.concat("  "+(i+1)+". "+str1+"\n");
	            Log.d("permission list",reqPermission[i]);
	         //  Toast.makeText(this, "the permission is "+reqPermission[i], Toast.LENGTH_LONG).show();
	        }
	      else
	      {
	    	  str2 ="There is no permission for this app";
	      }
	      SHOW_DIALOG(app,str2);
   }
   
   private void SHOW_DIALOG(App app,String reqPermission) 
   {
	// TODO Auto-generated method stub
	   myDialog = new Dialog(Running_apps.this);
       myDialog.setContentView(R.layout.my_custom_dialog);
       myDialog.setTitle(app.getTitle());
       
       app_title_ins = (TextView)myDialog.findViewById(R.id.app_title_ins);
       app_title_ins.setText("Permissions");
       
       app_show_perm =  (TextView)myDialog.findViewById(R.id.app_show_perm);
       app_show_perm.setText(reqPermission);
       
       imgv_for_ins_app = (ImageView) myDialog.findViewById(R.id.imgv_for_ins_app);
       String pkgName = app.getPackageName();
       Drawable ico = null;
       PackageManager manager = getApplicationContext().getPackageManager();
       try {
          Intent i = manager.getLaunchIntentForPackage(pkgName);
          if (i != null) {
             ico = manager.getActivityIcon(i);
          }
       } catch (NameNotFoundException e) {
          Log.e("ERROR", "Unable to find icon for package '" + pkgName + "': " + e.getMessage());
       }
       imgv_for_ins_app.setImageDrawable(ico);
       
       ok_button = (Button)myDialog.findViewById(R.id.import_to_reader);
       
       ok_button.setOnClickListener(new OnClickListener() {
           
       	public void onClick(View v) 
          {
             myDialog.cancel();
          }
         
          });
       
       
       finishbtn = (Button)myDialog.findViewById(R.id.cancel_btn);
       finishbtn.setOnClickListener(new OnClickListener() {
       
    	public void onClick(View v) 
       {
          myDialog.cancel();
       }
      
       });
       myDialog.show();
   }
   
   private List<App> loadInstalledApps(boolean includeSysApps) 
   {
	   List<App> apps = new ArrayList<App>();
	   ActivityManager am = (ActivityManager)this.getSystemService(ACTIVITY_SERVICE);
	   
	   PackageManager packageManager = getPackageManager();
	   
	   List<RunningAppProcessInfo> l = am.getRunningAppProcesses();
	   Iterator<RunningAppProcessInfo> i = l.iterator();
	  
	   PackageManager pm = this.getPackageManager();
	//   int row_count = 0 ;
	  
	   while(i.hasNext()) {
	     ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo)(i.next());
	     try 
	     {
	    	 
	    	 CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));
	         App app = new App();
	         app.setTitle(c.toString());
	         app.setPackageName(info.processName);

	         PackageInfo packageInfo =  packageManager.getPackageInfo(info.processName, PackageManager.GET_PERMISSIONS);
	         String[] reqPermission= packageInfo.requestedPermissions;
	         app.set_Permission_Info(reqPermission);
	    	 
	       
	    //   PackageInfo packageInfo =  packageManager.getPackageInfo(l.get(row_count).processName, PackageManager.GET_PERMISSIONS);
	   //    String[] reqPermission= packageInfo.requestedPermissions;
	   //    app.set_Permission_Info(reqPermission);
	       
	    //   app.setVersionName(p.versionName);
	     //  app.setVersionCode(p.versionCode);
	      // CharSequence description = p.applicationInfo.loadDescription(packageManager);
	     //  app.setDescription(description != null ? description.toString() : "");
	   
	       // app.setSize(p.s)
	       apps.add(app);
	       
	     }
	     
	     catch(Exception e){}
	     
	   }
      return apps;
   }
   
   /**
    * An asynchronous task to load the icons of the installed applications.
    */
   private class LoadIconsTask extends AsyncTask<App, Void, Void> {
      @Override
      protected Void doInBackground(App... apps) {
         
         Map<String, Drawable> icons = new HashMap<String, Drawable>();
         PackageManager manager = getApplicationContext().getPackageManager();
         
         for (App app : apps) {
            String pkgName = app.getPackageName();
            Drawable ico = null;
            try {
               Intent i = manager.getLaunchIntentForPackage(pkgName);
               if (i != null) {
                  ico = manager.getActivityIcon(i);
               }
            } catch (NameNotFoundException e) {
               Log.e("ERROR", "Unable to find icon for package '" + pkgName + "': " + e.getMessage());
            }
            icons.put(app.getPackageName(), ico);
         }
         mAdapter.setIcons(icons);
         
         return null;
      }
      
      @Override
      protected void onPostExecute(Void result) {
         mAdapter.notifyDataSetChanged();
      }
  }

}
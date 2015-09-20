package com.fest.project;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class Tab_activity_install_apps extends TabActivity implements View.OnClickListener {
    /** Called when the activity is first created. */
	TabHost tabHost;
	ImageView home,web_site;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        home = (ImageView) findViewById(R.id.home_icon_for_library_tab);
		home.setOnClickListener(this);
		
		web_site = (ImageView) findViewById(R.id.web_icon_for_library);
		web_site.setOnClickListener(this);
        
        tabHost = getTabHost(); 
     
        Intent intentApple = new Intent().setClass(this,Install_apps.class);
		TabSpec tabSpecBook = tabHost.newTabSpec("Book");
		tabSpecBook.setIndicator("Installed");
		tabSpecBook.setContent(intentApple);
		
		Intent intentguava = new Intent().setClass(this,Running_apps.class);
		TabSpec tabSpecguava = tabHost.newTabSpec("guya mara kha");
		tabSpecguava.setIndicator("Running");
		tabSpecguava.setContent(intentguava);
		
		tabHost.addTab(tabSpecBook);
		tabHost.addTab(tabSpecguava);
        
		tabHost.getTabWidget().getChildTabViewAt(0).setBackgroundColor(Color.parseColor("#ABAD7F"));
		tabHost.getTabWidget().getChildTabViewAt(1).setBackgroundColor(Color.parseColor("#D5BB74"));
		
		tabHost.setCurrentTab(0);
		
    }
	@Override
	public void onClick(View v) {
		if( v == home)
		{
			Intent intent = new Intent(Tab_activity_install_apps.this,Screen_for_menu.class);
			startActivity(intent);	
		}
		else if( v == web_site)
		{
		}
		
	}
}
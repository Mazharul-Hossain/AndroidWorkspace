package com.TermProject.EbookReader;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class Screen_for_list_tab extends TabActivity implements View.OnClickListener
{
	ImageView home,web_site,tab_selected;
	TextView tv_for_tab_view;
	TabHost tabHost;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout_for_book_tab);
		
		home = (ImageView) findViewById(R.id.home_icon_for_library_tab);
		home.setOnClickListener(this);
		
		web_site = (ImageView) findViewById(R.id.web_icon_for_library);
		web_site.setOnClickListener(this);
		
		tab_selected=(ImageView) findViewById(R.id.desc_tab_selected_tab_view);
		tab_selected.setOnClickListener(this);
		
		tv_for_tab_view = (TextView) findViewById(R.id.textView_for_tab_View);
		
		tabHost = getTabHost(); 
		
		Intent intentApple = new Intent().setClass(this, Book_Tab_Activity.class);
		TabSpec tabSpecBook = tabHost.newTabSpec("Book");
		tabSpecBook.setIndicator("Books");
		tabSpecBook.setContent(intentApple);
		
		// Windows tab
		Intent intentWindows = new Intent().setClass(this, Author_Tab_Activity.class);
		TabSpec tabSpecAuthor = tabHost.newTabSpec("Author");
		tabSpecAuthor.setIndicator("Authors");
		tabSpecAuthor.setContent(intentWindows);
		
		Intent intentBerry = new Intent().setClass(this, Tag_tab_activity.class);
		TabSpec tabSpecTag = tabHost.newTabSpec("Tag");
		tabSpecTag.setIndicator("Tags");
		tabSpecTag.setContent(intentBerry);
	
		Intent intentAndroid = new Intent().setClass(this, Collection_tab_activity.class);
		TabSpec tabSpecCollection = tabHost.newTabSpec("Collection");
		tabSpecCollection.setIndicator("Collections");
		tabSpecCollection.setContent(intentAndroid);
		
		tabHost.addTab(tabSpecBook);
		tabHost.addTab(tabSpecAuthor);
		tabHost.addTab(tabSpecTag );
		tabHost.addTab(tabSpecCollection);
		
		tabHost.getTabWidget().getChildTabViewAt(0).setBackgroundColor(Color.parseColor("#ABAD7F"));
		tabHost.getTabWidget().getChildTabViewAt(1).setBackgroundColor(Color.parseColor("#D5BB74"));
		tabHost.getTabWidget().getChildTabViewAt(2).setBackgroundColor(Color.parseColor("#ABAD7F"));
		tabHost.getTabWidget().getChildTabViewAt(3).setBackgroundColor(Color.parseColor("#D5BB74"));
		
		TextView tv = (TextView) tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
		tv.setTextSize(25);
		tv.setTextColor(Color.BLACK);

		TextView tv1 = (TextView) tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
		tv1.setTextSize(25);
		tv1.setTextColor(Color.BLACK);

		TextView tv2 = (TextView) tabHost.getTabWidget().getChildAt(2).findViewById(android.R.id.title);
		tv2.setTextSize(25);
		tv2.setTextColor(Color.BLACK);

		TextView tv3 = (TextView) tabHost.getTabWidget().getChildAt(3).findViewById(android.R.id.title);
		tv3.setTextSize(25);
		tv3.setTextColor(Color.BLACK);
		
		tabHost.setCurrentTab(0);
	
		tabHost.setOnTabChangedListener(new OnTabChangeListener() 
		{
            public void onTabChanged(String str) 
            {
            	if (tabHost.getCurrentTab() == 0) 
            	{
            		tab_selected.setImageResource(R.drawable.book_icon_for_book_tab);
                	tv_for_tab_view.setText("Books");
            	}
                else if (tabHost.getCurrentTab() == 1) 
                {
            		tab_selected.setImageResource(R.drawable.author_icon_for_author_tab);
            		tv_for_tab_view.setText("Authors");
                }
            }
        });
	}

	public void onClick(View v) 
	{
		if( v == home)
		{
			Intent intent = new Intent(Screen_for_list_tab.this,Screen_for_read_book.class);
			startActivity(intent);	
		}else if( v == web_site)
		{
			Intent intent = new Intent(Screen_for_list_tab.this,Screen_for_download.class);
			startActivity(intent);	
		}
	}
}
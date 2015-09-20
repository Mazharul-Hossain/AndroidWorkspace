package com.fest.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

public class Screen_for_menu extends Activity  implements View.OnClickListener,AdapterView.OnItemClickListener
{
	ListView list_for_menu_screen;
    ImageView shelf,store,list,catalog,sd_card,l_read,home_icon,store_icon;
    AdapterMenuScreen adapter;
    String[] names={"GPS Location","Contact List","Call History","SMS History","Applications",
    		"Photos","Browser History","Battery Level"};
    
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout); 
        
        list_for_menu_screen = (ListView) findViewById(R.id.List_for_menu_screen);
        list_for_menu_screen.setOnItemClickListener(this);
        
        adapter = new AdapterMenuScreen(this,names);
        list_for_menu_screen.setAdapter(adapter);
        
	}
	

	@Override
	public void onItemClick(AdapterView<?> arg0, View itemClicked, int position, long id)
	{
		int click_item = position;
	//	Toast.makeText(this,"The no of item clicked is "+click_item, Toast.LENGTH_LONG).show();
		if( click_item == 0 )
		{
			startActivity(new Intent(Screen_for_menu.this , Screen_for_menu.class));
		}
		else if( click_item == 1 )
		{
			startActivity(new Intent(Screen_for_menu.this , ContactsListActivity.class));
		}
		else if( click_item == 2 )
		{
			startActivity(new Intent(Screen_for_menu.this , CallHistoryListActivity.class));
		}
		else if( click_item == 3 )
		{
			
		}
		else if( click_item == 4 )
		{
			startActivity(new Intent(Screen_for_menu.this , Tab_activity_install_apps.class));
		}
		else if( click_item == 5 )
		{
			startActivity(new Intent(Screen_for_menu.this , photo_tag.class));
		}
		else if( click_item == 6 )
		{
			
		}
		else if( click_item == 7 )
		{
			startActivity(new Intent(Screen_for_menu.this , BatteryLavel.class));
		}
		else if( click_item == 8 )
		{
			
		}
		else if( click_item == 9 )
		{
			
		}
		else if( click_item == 10 )
		{
			
		}
		else if( click_item == 11 )
		{
			
		}
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
}	
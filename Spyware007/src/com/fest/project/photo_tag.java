package com.fest.project;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class photo_tag  extends Activity implements View.OnClickListener,AdapterView.OnItemClickListener
{
	
	TextView text_sd_card;
    ImageView home_icon,store_icon;
	
    private List<String> item = null;
    File sdCard;
    String last_file_name;
    
    String current_directory;
	String root_str;
	
	String[] directory_1={"a","b","c"};
	String[] path_name_1={"d","e","f"};;
	
	String[] directory;
	String[] path_name;
	String path_name_of_our_folder;
	
	Button btn_sd_prev_page;
    ListView list_sd_card;
    MyArrayAdapter adapter;
    String address_path_of_file;
    
    Button click,import_button,finishbtn,database;
    EditText author_name;
    Dialog myDialog;
    String a_name ;
    
	 public void onCreate(Bundle savedInstanceState)
		{
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.sd_card_layout); 
	        
	        home_icon = (ImageView) findViewById(R.id.home_icon_for_SD_Card);
	        home_icon.setOnClickListener(this);
	        
	        store_icon = (ImageView) findViewById(R.id.store_icon_for_SD_Card);
	        store_icon.setOnClickListener(this);
	       
	        btn_sd_prev_page = (Button) findViewById(R.id.btn_for_sd_card_previous_page);
	        btn_sd_prev_page.setOnClickListener(this);
	        
	        sdCard = Environment.getExternalStorageDirectory();
	        root_str = sdCard.getAbsolutePath();
	        current_directory = root_str ;
	        
	        Toast.makeText(this, "The directory is "+current_directory, Toast.LENGTH_LONG).show();
	        list_sd_card = (ListView) findViewById(R.id.listView_sd_card);
	    //    process_list_ingredients( root_str );
	      
	       adapter = new MyArrayAdapter(this,directory_1,path_name_1);
	       list_sd_card.setAdapter(adapter);
	        
	        list_sd_card.setOnItemClickListener(this);
	      //  list_sd_card .setOnItemLongClickListener(this);
	        
	       
	    }
	 
	 public boolean GetFileExtension(String path_of_file)
		{
			File file = new File(path_of_file);
			
			if( file.isDirectory() )
				return true;
			else
			{
				  String extension;
				  int mid= path_of_file.lastIndexOf(".");
				  extension =path_of_file.substring(mid+1,path_of_file.length());  
				  
				  if( extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("png") )
					  return true;
				  else
					  return false;
			}
		}
	 
	 public void process_list_ingredients(String currentDirectory )
		{
			 File file = new File(currentDirectory);
	         if( file.isDirectory() )
	         {
	             	String[] temp_directory = file.list();
	             	item = Arrays.asList(temp_directory);
	             	Collections.sort(item);
	             	int i=0;
	             	for(String temp:item)
	             		if(!temp.startsWith(".") && GetFileExtension(currentDirectory +"/"+temp) )
	             			++i;
	             		
	             	directory = new String[i];
	                path_name = new String[i];
	                
	                i=0;
	             	for(String temp:item)
	             		if(!temp.startsWith(".") && GetFileExtension(currentDirectory +"/"+temp) )
	             		{
	             			directory[i]= temp;
	             			path_name[i] = new String();
	             			path_name[i] = currentDirectory +"/"+temp;
	             			Toast.makeText(this, "The path name is " + path_name[i], Toast.LENGTH_LONG).show();
	             			Toast.makeText(this, "The directory name is " + directory[i], Toast.LENGTH_LONG).show();
	             			++i;
	             		}
	     			adapter = new MyArrayAdapter(this,directory_1,path_name_1);
	               list_sd_card.setAdapter(adapter);
	         }
	     } 
	 
	@Override
	public void onClick(View v) 
	{
		if( v == home_icon)
		{
		//	Intent intent = new Intent(Screen_SD_card.this,Screen_for_read_book.class);
		//	startActivity(intent);			
		}
		else if(v == store_icon  )
		{
		//	Intent intent = new Intent(Screen_SD_card.this,Screen_for_download.class);
		//	startActivity(intent);
		}
		else if( v == btn_sd_prev_page )//&& current_directory.compareTo(root_str)!=0 )
		{
			try
			{
				File file = new File(current_directory);
				String temp = file.getParent(); 
				process_list_ingredients(temp);
				current_directory = temp;
			}
			catch(Exception e)
			{
				btn_sd_prev_page.setVisibility(View.INVISIBLE);
			}
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}
}

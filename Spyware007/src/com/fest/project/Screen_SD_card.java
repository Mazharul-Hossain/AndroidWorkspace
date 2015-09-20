package com.fest.project;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Screen_SD_card extends Activity implements View.OnClickListener,AdapterView.OnItemClickListener
,AdapterView.OnItemLongClickListener
{
	TextView text_sd_card;
    ImageView home_icon,store_icon;
	
    private List<String> item = null;
    File sdCard;
    String last_file_name;
    
    String current_directory;
	String root_str;
	
	String[] directory={"Sultan","Masum","Tarek"};
	String[] path_name={"Mousumi","Tanha","Laila"};
	String path_name_of_our_folder;
	
	Button btn_sd_prev_page;
    ListView list_sd_card;
    MyArrayAdapter adapter;
    String address_path_of_file;
    
    Button click,import_button,finishbtn,database;
    EditText author_name;
    Dialog myDialog;
    String a_name ;
    
    @Override
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

        list_sd_card = (ListView) findViewById(R.id.listView_sd_card);
    //    process_list_ingredients( root_str );
      
        Toast.makeText(this, "Hellow Man", Toast.LENGTH_LONG).show();
       adapter = new MyArrayAdapter(this,directory,path_name);
       list_sd_card.setAdapter(adapter);
        
        list_sd_card.setOnItemClickListener(this);
        list_sd_card .setOnItemLongClickListener(this);
        
       
    }
    
    public boolean onItemLongClick(AdapterView<?> arg0, View view, int position,long id)
	{
    	address_path_of_file = adapter.getItem(position);
		
		if( current_directory.contains(".jpg") || current_directory.contains("png")  )
		{
			 File file = new File(current_directory);
			 String temp1 = file.getParent(); 
			 current_directory = temp1;
		 }
   	     
		SHOW_DIALOG();
		return true;
	}
	
    public void SHOW_DIALOG()
	{
		myDialog = new Dialog(Screen_SD_card.this);
        myDialog.setContentView(R.layout.my_custom_dialog_photo);
        myDialog.setTitle("Enter Author Name Of Book");
        
        author_name = (EditText) myDialog.findViewById(R.id.author_name_editText);
        import_button = (Button)myDialog.findViewById(R.id.import_to_reader);
        import_button.setOnClickListener(new OnClickListener()
        {
           public void onClick(View v) 
            {
        	   
     		   a_name = author_name.getText().toString();
     		   make_dir();
     		  
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
   
	public void make_dir()
	{
		File temp_SD = Environment.getExternalStorageDirectory();
		path_name_of_our_folder = temp_SD.getAbsolutePath()+"/BOOK FOR CHALLENGE READER";
		File temp_directory = new File(path_name_of_our_folder);
		if( !temp_directory.exists() )
			temp_directory.mkdirs();
	}
	
	
	public void onItemClick(AdapterView<?> arg0, View itemClicked, int position, long id) 
	{
	/*	String temp = adapter.getItem(position);
		
		if( current_directory.contains(".pdf") || current_directory.contains("epub") )
		{
			 File file = new File(current_directory);
			 String temp1 = file.getParent(); 
			 current_directory = temp1;
		}
		 
		 current_directory +="/"+temp;
		 File file = new File(current_directory);
	
		 if( file.isDirectory() )
		 {
		 	process_list_ingredients(current_directory);
		 	btn_sd_prev_page.setVisibility(View.VISIBLE);
		}
		else
		{
			Uri path_sultan = Uri.fromFile(file); 
            Intent intent = new Intent(Intent.ACTION_VIEW); 
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
            String ext = adapter.GetFileExtension(temp);
           
            if(ext.equalsIgnoreCase("pdf")  )
            	ext = "application/" +ext.toLowerCase() ;
            else if( ext.equalsIgnoreCase("epub") )
            	ext = "application/" +ext.toLowerCase();
            intent.setDataAndType(path_sultan,ext); 
            
            try 
            { 
                startActivity(intent); 
            }  
            catch (ActivityNotFoundException e) 
            { 
               // Toast.makeText(this,"No Application Available to View PDF",Toast.LENGTH_SHORT).show(); 
            }		
            
            last_file_name = file.getAbsolutePath(); 
		} */
	}
	
/*	public void process_list_ingredients(String currentDirectory )
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
             	
     			adapter = new MyArrayAdapter(this,directory,path_name);
                list_sd_card.setAdapter(adapter);
         }
     } */
	
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
			//	process_list_ingredients(temp);
			//	current_directory = temp;
			}
			catch(Exception e)
			{
				btn_sd_prev_page.setVisibility(View.INVISIBLE);
			}
		}
	}
}	
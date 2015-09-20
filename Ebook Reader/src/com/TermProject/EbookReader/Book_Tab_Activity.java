package com.TermProject.EbookReader;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Book_Tab_Activity extends Activity implements View.OnClickListener,AdapterView.OnItemClickListener
{
    
	ListView list_for_book_tab;
	String[] directory;
	String[] path_name;
	String[] author_name;
    ArrayAdapterForTabViewBookTab adapter;
    SharedPreferences mAppSettings_pref;
    
    public static final String APP_PREFERENCES = "AppPrefs";
	public static final String LAST_READ = "BOOK_NAME_OF_LAST_READ";
	
    private List<String> item = null;
    Button btn_sd_prev_page;
    
    File sdCard;
    String current_directory;
	String root_str;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_tab_layout);
        
         	
    	sdCard = Environment.getExternalStorageDirectory();
        root_str = sdCard.getAbsolutePath();
        current_directory = root_str ;
        
        list_for_book_tab = (ListView) findViewById(R.id.List_for_tab_book);
        process_list_ingredients( root_str );
        list_for_book_tab.setOnItemClickListener(this);
        mAppSettings_pref = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

	public void onItemClick(AdapterView<?> arg0, View itemClicked, int position, long id) 
	{
		 String temp = adapter.getItem(position);
		
		 if( current_directory.contains(".pdf") || current_directory.contains("epub") )
		 {
			 File file = new File(current_directory);
			 String temp1 = file.getParent(); 
			 current_directory = temp1;
		 } 
		 
		 current_directory +="/"+temp;
		 File file = new File(current_directory);
		 
		 if( file.isDirectory() )
		 	process_list_ingredients(current_directory);
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
	                Toast.makeText(this,"No Application Available to View PDF",Toast.LENGTH_SHORT).show(); 
	            }
	            SharedPreferences.Editor editor = mAppSettings_pref.edit();
	            editor.putString("BOOK_NAME_OF_LAST_READ", file.getAbsolutePath());
	            editor.commit();
		 }
		 
	}

	public void onClick(View v) 
	{
		if( v == btn_sd_prev_page )//&& current_directory.compareTo(root_str)!=0 )
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
	
	public void process_list_ingredients(String currentDirectory )
	{	
		SQLiteDatabase db = openOrCreateDatabase("Challenge__EBookBook_Reader", MODE_PRIVATE , null);
		Cursor result = db.rawQuery("Select * from Book_description", null);
		
		if( result != null )
		{
			result.moveToFirst();
			int count = result.getCount(),i=0;
			directory = new String[count];
	        path_name = new String[count];
	        author_name = new String[count];
			while( !result.isAfterLast() )
			{
				String Path_name = result.getString(result.getColumnIndex("Path_name"));
				String book_name = result.getString(result.getColumnIndex("Book_name"));
				
				long id = result.getLong(result.getColumnIndex("Aid"));
				Cursor mCursor = db.rawQuery("Select * from Author_table",null);
				
				mCursor.moveToFirst();
				String a_name ="Null" ;
				while( !mCursor.isAfterLast() )
				{
					long id1 = mCursor.getLong(mCursor.getColumnIndex("Aid"));
					
					if(id == id1)
					{
						a_name = mCursor.getString(mCursor.getColumnIndex("Author_name"));
						break;
					}
					mCursor.moveToNext();
				}
				mCursor.close();
				
				directory[i]= book_name;
				path_name[i] = Path_name;
				author_name[i]=a_name;
				++i;
				result.moveToNext();
			}
			result.close(); 
		}
		else
		{
			directory = new String[1];
	        path_name = new String[1];
	        
	        directory[0]="No data in the database";
	        path_name[0]="No data in the database";
		}
	
		adapter = new ArrayAdapterForTabViewBookTab(this,directory,path_name);
		adapter.setAuthor_name(author_name);
		list_for_book_tab.setAdapter(adapter);
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
			  
			  if( extension.equalsIgnoreCase("PDF") || extension.equalsIgnoreCase("EPUB") )
				  return true;
			  else
				  return false;
		}
	}
}
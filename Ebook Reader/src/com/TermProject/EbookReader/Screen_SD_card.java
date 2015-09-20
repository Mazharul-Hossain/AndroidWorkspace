package com.TermProject.EbookReader;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

public class Screen_SD_card extends Main_activity implements View.OnClickListener,AdapterView.OnItemClickListener
,AdapterView.OnItemLongClickListener
{
	TextView text_sd_card;
    ImageView home_icon,store_icon;
	
    private List<String> item = null;
    File sdCard;
    SharedPreferences mAppSettings_pref;
    String last_file_name;
    
    String current_directory;
	String root_str;
	
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
    SQLiteDatabase db;
    
    public static final String first_col_first_table = "Path_name";
	public static final String second_col_first_table = "Aid";
	public static final String third_col_first_table = "Book_Name";
	
	public static final String first_col_second_table = "AID";
	public static final String second_col_second_table = "Author_name";
   
	 
	public static final String first_col_third_table = "book_path_addr";
	public static final String second_col_third_table = "Serial";
	public static final String third_col_third_table = "image_path_addr";
    
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

        create_database();
        list_sd_card = (ListView) findViewById(R.id.listView_sd_card);
        process_list_ingredients( root_str );
      
        list_sd_card.setOnItemClickListener(this);
        list_sd_card .setOnItemLongClickListener(this);
        
        mAppSettings_pref = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }
    public void create_database()
    {
    	db = openOrCreateDatabase("Challenge__EBookBook_Reader", MODE_PRIVATE, null);
		db.execSQL("create table if not exists Book_description ( Path_name VARCHAR Primary Key ,Aid integer , Book_name varchar);");
		db.execSQL("create table if not exists Author_table ( Aid integer Primary Key autoincrement, Author_name varchar);");
		db.execSQL("create table if not exists Self_view ( book_path_addr varchar Primary Key,Serial integer ,image_path_addr varchar);");
		db.close(); 
    }
    public boolean onItemLongClick(AdapterView<?> arg0, View view, int position,long id)
	{
    	address_path_of_file = adapter.getItem(position);
		
		if( current_directory.contains(".pdf") || current_directory.contains("epub") || current_directory.contains("txt") || current_directory.contains("docx") )
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
        myDialog.setContentView(R.layout.my_custom_dialog);
        myDialog.setTitle("Enter Author Name Of Book");
        
        author_name = (EditText) myDialog.findViewById(R.id.author_name_editText);
        import_button = (Button)myDialog.findViewById(R.id.import_to_reader);
        import_button.setOnClickListener(new OnClickListener()
        {
           public void onClick(View v) 
            {
        	   
     		   a_name = author_name.getText().toString();
     		   make_dir();
     		  
     		   
     		   String temp2 = path_name_of_our_folder+"/"+address_path_of_file;
    		   long AID = insertData_SecondTable(a_name);
    		   
    		  
    		   db = openOrCreateDatabase("Challenge__EBookBook_Reader", MODE_PRIVATE , null);
    		   Cursor result3 = db.rawQuery("Select * from Self_view", null);
    		   int rowcount = result3.getCount();
    		   db.close();		   
    				   
    				   
    	       long AID3 = insertData_ThirdTable(temp2,rowcount+1,temp2);
    	       
    		   if( AID >=0 && AID3>=0)
    		   {
    			   insertData_FirstTable(temp2,AID,address_path_of_file); 
    			   goto_showTaost();
    		   }
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
   
    public void goto_showTaost()
	{
		Toast.makeText(this, "Insertion is successful", Toast.LENGTH_LONG).show();
	}
    
    public long insertData_FirstTable(String Path_name_1,long aID, String address_path_of_file_1)
	{
		db = openOrCreateDatabase("Challenge__EBookBook_Reader", MODE_PRIVATE , null);
		
		ContentValues cv = new ContentValues();
		cv.put(first_col_first_table,Path_name_1);
		cv.put(second_col_first_table,aID);
		cv.put(third_col_first_table,address_path_of_file_1);
		
		long id = db.insert("Book_description", null , cv);
		db.close();
		return id;
	
	}
    
    public long insertData_ThirdTable(String path_name_file_2,long serial_2,String image_path_2)
	{
		db = openOrCreateDatabase("Challenge__EBookBook_Reader", MODE_PRIVATE , null);
		
		ContentValues cv = new ContentValues();
		cv.put(first_col_third_table,path_name_file_2);
		cv.put(second_col_third_table,serial_2);
		cv.put(third_col_third_table,image_path_2);
		
		long id = db.insert("Self_view", null , cv);
		db.close();
		return id;
	
	}
	
	public long insertData_SecondTable(String Author_name)
	{
		db = openOrCreateDatabase("Challenge__EBookBook_Reader", MODE_PRIVATE , null);
		Cursor mCursor = db.rawQuery("Select Aid,Author_name from Author_table",null);
		
		if( mCursor == null)
		{
			ContentValues cv = new ContentValues();
			cv.put(second_col_second_table,Author_name);
			
			long id = db.insert("Author_table", null , cv);
			db.close();
			return id;
		}
		else
		{
			
			mCursor.moveToFirst();
		
			while( !mCursor.isAfterLast() )
			{
				long id = mCursor.getLong(mCursor.getColumnIndex("Aid"));
				String name = mCursor.getString(mCursor.getColumnIndex("Author_name"));
				
				if(name.equals(Author_name))
				{
					Toast.makeText(this,"Sorry. A previous copy of this file is already exists in our System.", Toast.LENGTH_LONG).show();
					return -1;
				}
				mCursor.moveToNext();
			}
			
			ContentValues cv = new ContentValues();
			cv.put(second_col_second_table,Author_name);

			long id = db.insert("Author_table", null , cv);
			db.close();
			return id;
		}
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
            
            SharedPreferences.Editor editor = mAppSettings_pref.edit();
            editor.putString(LAST_READ, file.getAbsolutePath());
            editor.commit();
            last_file_name = file.getAbsolutePath();
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
             			Toast.makeText(this, "The path name is " + path_name[i], Toast.LENGTH_LONG);
             			Toast.makeText(this, "The directory name is " + directory[i], Toast.LENGTH_LONG);
             			++i;
             		}
             	
     			adapter = new MyArrayAdapter(this,directory,path_name);
                list_sd_card.setAdapter(adapter);
         }
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
	
	public void onClick(View v)
	{
		if( v == home_icon)
		{
			Intent intent = new Intent(Screen_SD_card.this,Screen_for_read_book.class);
			startActivity(intent);			
		}
		else if(v == store_icon  )
		{
			Intent intent = new Intent(Screen_SD_card.this,Screen_for_download.class);
			startActivity(intent);
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
}	
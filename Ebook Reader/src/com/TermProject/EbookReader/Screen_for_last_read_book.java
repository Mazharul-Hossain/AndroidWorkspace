package com.TermProject.EbookReader;

import java.io.File;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;

public class Screen_for_last_read_book extends Main_activity 
{
    
	WebView wv;
	ImageView home,store;
 	SharedPreferences mAppSettings_pref1;
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_layout);
        mAppSettings_pref1 = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        String last_read_book_name = mAppSettings_pref1.getString(LAST_READ,"Sultan");
        File file =new File(last_read_book_name);
        
        if(file.exists())
        {
        	Uri path_sultan = Uri.fromFile(file); 
            Intent intent = new Intent(Intent.ACTION_VIEW); 
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
            String ext =GetFileExtension(file.getAbsolutePath());
           
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
        }
	}
	
	public String GetFileExtension(String path_of_file)
	{	
		String extension;
		int mid = path_of_file.lastIndexOf(".");
		extension = path_of_file.substring(mid+1,path_of_file.length());  
			  
		if( extension.equalsIgnoreCase("PDF")   )
		{
			  String temp = "PDF" ;
			  return temp;
		}
		else{
			  String temp = "EPUB" ;
			  return temp;
		}
	}
}	
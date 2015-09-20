package com.TermProject.EbookReader;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class Author_Tab_Activity extends Activity 
{
	private String[] author_name;
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.author_tab_layout);
        
        SQLiteDatabase db = openOrCreateDatabase("Challenge__EBookBook_Reader", MODE_PRIVATE , null);
        Cursor result = db.rawQuery("Select * from Author_table", null);
        
        String a_name;
        int count = result.getCount(),i=0;
        author_name= new String[count];
        
        if( result != null)
        {
        	result.moveToFirst()
        	;while( !result.isAfterLast() )
			{
				a_name = result.getString(result.getColumnIndex("Author_name"));
				author_name[i] = a_name;
				++i;
				result.moveToNext();
			}
        	result.close();
        }
        else
        {
        	author_name = new String[1];
        	author_name[0]="No Name Of Author Is Exists";
        }
    }
}
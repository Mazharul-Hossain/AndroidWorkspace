package com.TermProject.EbookReader;


import java.io.File;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ArrayAdapterForTabViewBookTab extends ArrayAdapter<String>
{
	private final Activity context;
	private final String[] names;
	private final String[] path;
	private String[] author_name;
	
	public ArrayAdapterForTabViewBookTab(Activity context , String[] names ,String[] path )
	{
		super(context , R.layout.row_layout_for_tab_book,names);
		this.context = context;
		this.names = names;
 		this.path = path;
	}
	
	public void setAuthor_name(String[] a_name)
	{
		author_name = a_name ;
	} 
	
	public String getItem(int position)
	{
		return names[position];
	}
	
	static class ViewHolder
	{
		public ImageView imageview;
		public TextView textView_for_BookName,textView_For_fileType,textView_Forauthor;
	}
	
	public View getView(int position, View convertView , ViewGroup parent )
	{
		ViewHolder holder;
		View row_view = convertView ; 
		
		if( row_view == null)
		{
			LayoutInflater inflater = context.getLayoutInflater();
			row_view  = inflater.inflate(R.layout.row_layout_for_tab_book, null,true) ;
			holder = new ViewHolder();
			holder.textView_for_BookName = (TextView) row_view.findViewById(R.id.book_text_view_book_tab);
			holder.textView_For_fileType = (TextView) row_view.findViewById(R.id.type_for_book_tab);
			holder.textView_Forauthor = (TextView) row_view.findViewById(R.id.author_text_view_book_tab);
			holder.imageview = (ImageView) row_view.findViewById(R.id.image_sd_list_view);
			row_view.setTag(holder);
		}
		else
			holder = (ViewHolder) row_view.getTag();
		
		holder.textView_for_BookName.setText(names[position]);
		
		 File file = new File(path[position]);
		if( file.isDirectory() )
		{
			
				holder.imageview.setImageResource(R.drawable.folder_icon_sd_list_view);
				holder.textView_For_fileType.setText("Folder");
				holder.textView_Forauthor.setText("No Author Exists");
		}
		else
		{
			String temp =  GetFileExtension( path[position] );
			String a_Name="Author Name:  "+author_name[position];
			holder.textView_For_fileType.setText("File Type: "+temp);
			holder.textView_Forauthor.setText(a_Name);
			
			
			if( temp.equalsIgnoreCase("PDF") )
				holder.imageview.setImageResource(R.drawable.fil_icon_sd_list_view);
			else
				holder.imageview.setImageResource(R.drawable.fil_icon_sd_list_view_for_epub);
		}
		return row_view;
		
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
		else
		{
			  String temp = "EPUB" ;
			  return temp;
		}
	}

	
	
}

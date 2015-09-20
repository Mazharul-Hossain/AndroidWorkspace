package com.TermProject.EbookReader;

import java.io.File;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyArrayAdapter extends ArrayAdapter<String>
{
	private final Activity context;
	private final String[] names;
	private final String[] path;
	
	public MyArrayAdapter(Activity context , String[] names ,String[] path )
	{
		super(context , R.layout.row_layout,names);
		this.context = context;
		this.names = names;
 		this.path = path;
	}
	public String getItem(int position)
	{
		return names[position];
	}
	static class ViewHolder
	{
		public ImageView imageview;
		public TextView textView;
	}
	
	public View getView(int position, View convertView , ViewGroup parent )
	{
		ViewHolder holder;
		View row_view = convertView ; 
		
		if( row_view == null)
		{
			LayoutInflater inflater = context.getLayoutInflater();
			row_view  = inflater.inflate(R.layout.row_layout, null,true) ;
			holder = new ViewHolder();
			holder.textView = (TextView) row_view.findViewById(R.id.textView_sd_list_view);
			holder.imageview = (ImageView) row_view.findViewById(R.id.image_sd_list_view);
			row_view.setTag(holder);
		}
		else
			holder = (ViewHolder) row_view.getTag();
		
		holder.textView.setText(names[position]);
		
		 File file = new File(path[position]);
		if( file.isDirectory() )
		{
			
				holder.imageview.setImageResource(R.drawable.folder_icon_sd_list_view);
		}
		else
		{
			String temp =  GetFileExtension( path[position] );
			if( temp.equalsIgnoreCase("PDF") )
				holder.imageview.setImageResource(R.drawable.fil_icon_sd_list_view);
			else if( temp.equalsIgnoreCase("EPUB") )
				holder.imageview.setImageResource(R.drawable.fil_icon_sd_list_view_for_epub);
			else if( temp.equalsIgnoreCase("txt") )
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
		else if ( extension.equalsIgnoreCase("EPUB") )
		{
			  String temp = "EPUB" ;
			  return temp;
		}
		else if ( extension.equalsIgnoreCase("txt") )
		{
			String temp = "txt" ;
			  return temp;
		}
		else return null;
	}
 
}

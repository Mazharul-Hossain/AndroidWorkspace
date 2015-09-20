package com.fest.project;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
public class AdapterMenuScreen extends ArrayAdapter<String>
{
	private final Activity context;
	private final String[] names;
	
	public AdapterMenuScreen(Activity context , String[] names )
	{
		super(context , R.layout.row_layout_for_menu_screen,names);
		this.context = context;
		this.names = names;
	}
	
	static class ViewHolder
	{
		public TextView textView_for_AppName,textView_For_fileType;
	}
	
	public View getView(int position, View convertView , ViewGroup parent )
	{
		ViewHolder holder;
		View row_view = convertView ; 
		
		if( row_view == null)
		{
			LayoutInflater inflater = context.getLayoutInflater();
			row_view  = inflater.inflate(R.layout.row_layout_for_menu_screen,null,true) ;
			holder = new ViewHolder();
			holder.textView_for_AppName = (TextView) row_view.findViewById(R.id.app_name_menu_screen);
			holder.textView_For_fileType = (TextView) row_view.findViewById(R.id.app_desc_menu_screen);
			row_view.setTag(holder);
		}
		else
			holder = (ViewHolder) row_view.getTag();
		
		holder.textView_for_AppName.setText(names[position]);
		
		return row_view;
		
	}
		
}

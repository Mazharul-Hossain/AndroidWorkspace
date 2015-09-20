package com.pill.patientdrugtracker;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Search_medicine_adapter extends ArrayAdapter<String>
{
	private final Activity context;
	private final ArrayList<String> med_name;
	private final ArrayList<String> id_of_medicine; 
	
	public Search_medicine_adapter(Activity a, ArrayList<String> med_name,ArrayList<String> id_of_medicine  )
	{
		super(a , R.layout.list_item,med_name);
		this.context =  a;
		this.med_name =med_name; 
		this.id_of_medicine = id_of_medicine;
	}
	 
	public String getItem_name(int position)
	{
		return med_name.get(position);
	}
	
	public String getItem_id(int position)
	{
		return id_of_medicine.get(position);
	}
	 
	static class ViewHolder
	{ 
		public TextView med_name; 
	}
	
	public View getView(int position, View convertView , ViewGroup parent )
	{
		ViewHolder holder;
		View row_view = convertView ; 
		
		if( row_view == null)
		{
			LayoutInflater inflater = context.getLayoutInflater();
			row_view  = inflater.inflate(R.layout.list_item, null,true) ;
			holder = new ViewHolder();
		 	holder.med_name = (TextView) row_view.findViewById(R.id.textView1); 
			row_view.setTag(holder);
		}
		else
			holder = (ViewHolder) row_view.getTag();
		
		holder.med_name.setText(med_name.get(position)); 
		return row_view;
		
	}
}

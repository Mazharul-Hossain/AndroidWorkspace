package com.pill.patientdrugtracker;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyArrayAdapter extends ArrayAdapter<String>
{
	private final Activity context;
	private final ArrayList<String> pat_name;
	private final ArrayList<String> pat_proff; 
	
	public MyArrayAdapter(Activity context ,   ArrayList<String> pat_name , ArrayList<String> pat_proff )
	{
		super(context , R.layout.row_for_patient_choosing,pat_name);
		this.context = context;
		this.pat_name =pat_name;
 		this.pat_proff = pat_proff ;
	}
	public String getItem_name(int position)
	{
		return pat_name.get(position);
	}
	
	public String getItem_proff(int position)
	{
		return pat_proff.get(position);
	}
	static class ViewHolder
	{ 
		public TextView text_name;
		public TextView text_profession;
	}
	
	public View getView(int position, View convertView , ViewGroup parent )
	{
		ViewHolder holder;
		View row_view = convertView ; 
		
		if( row_view == null)
		{
			LayoutInflater inflater = context.getLayoutInflater();
			row_view  = inflater.inflate(R.layout.row_for_patient_choosing, null,true) ;
			holder = new ViewHolder();
			holder.text_name = (TextView) row_view.findViewById(R.id.patient_name);
			holder.text_profession = (TextView) row_view.findViewById(R.id.patient_profession);
			row_view.setTag(holder);
		}
		else
			holder = (ViewHolder) row_view.getTag();
		
		holder.text_name.setText(pat_name.get(position));
		holder.text_profession.setText( pat_proff.get(position));
		return row_view;
		
	}
}

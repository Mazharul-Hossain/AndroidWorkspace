package com.paresh.gridviewexample;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class GridviewAdapter extends BaseAdapter {
	private final ArrayList<String> listOffer;
	private final ArrayList<Integer> listIcon;
	private final ArrayList<String> listDate;
	private final Activity activity;

	public GridviewAdapter(Activity activity, ArrayList<String> listOffer,
			ArrayList<Integer> listIcon, ArrayList<String> listDate) {
		super();
		this.listOffer = listOffer;
		this.listIcon = listIcon;
		this.listDate = listDate;
		this.activity = activity;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listOffer.size();
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return listOffer.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static class ViewHolder {
		public ImageView imgViewFlag;
		public TextView txtViewTitle;
		public TextView txtViewDate;
		public CheckBox chkbxChoice;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder view;
		LayoutInflater inflator = activity.getLayoutInflater();

		if (convertView == null) {
			view = new ViewHolder();
			convertView = inflator.inflate(R.layout.gridview_row, null);

			view.txtViewTitle = (TextView) convertView.findViewById(R.id.textView1);
			view.imgViewFlag = (ImageView) convertView.findViewById(R.id.imageView1);
			view.chkbxChoice = (CheckBox) convertView.findViewById(R.id.checkBox1);
			view.txtViewDate = (TextView) convertView.findViewById(R.id.textViewL);

			convertView.setTag(view);
		} else {
			view = (ViewHolder) convertView.getTag();
		}

		view.txtViewTitle.setText(listOffer.get(position));
		view.imgViewFlag.setImageResource(listIcon.get(position));
		view.txtViewDate.setText(listDate.get(position));
		view.chkbxChoice.setId(position);

		return convertView;
	}

}

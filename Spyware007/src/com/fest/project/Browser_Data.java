package com.fest.project;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Browser_Data extends Activity implements View.OnClickListener,
		AdapterView.OnItemClickListener {

	TextView text_sd_card;
	ImageView home_icon, store_icon;

	private final List<String> item = null;
	File sdCard;
	String last_file_name;

	String current_directory;
	String root_str;

	String[] directory_1 = { "a", "b", "c" };
	String[] path_name_1 = { "d", "e", "f" };;

	String[] directory;
	String[] path_name;
	String path_name_of_our_folder;

	Button btn_sd_prev_page;
	ListView list_sd_card;
	MyArrayAdapter adapter;
	String address_path_of_file;

	Button click, import_button, finishbtn, database;
	EditText author_name;
	Dialog myDialog;
	String a_name;

	@Override
	public void onCreate(Bundle savedInstanceState) {
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
		current_directory = root_str;

		Toast.makeText(this, "The directory is " + current_directory,
				Toast.LENGTH_LONG).show();
		list_sd_card = (ListView) findViewById(R.id.listView_sd_card);
		// process_list_ingredients( root_str );

		adapter = new MyArrayAdapter(this, directory_1, path_name_1);
		list_sd_card.setAdapter(adapter);

		list_sd_card.setOnItemClickListener(this);
		// list_sd_card .setOnItemLongClickListener(this);

	}

	@Override
	public void onClick(View v) {
		/*
		 * if (v == home_icon) { // Intent intent = new //
		 * Intent(Screen_SD_card.this,Screen_for_read_book.class); //
		 * startActivity(intent); } else if (v == store_icon) { // Intent intent
		 * = new // Intent(Screen_SD_card.this,Screen_for_download.class); //
		 * startActivity(intent); } else if (v == btn_sd_prev_page)// && //
		 * current_directory.compareTo(root_str)!=0 // ) { try { File file = new
		 * File(current_directory); String temp = file.getParent();
		 * process_list_ingredients(temp); current_directory = temp; } catch
		 * (Exception e) { btn_sd_prev_page.setVisibility(View.INVISIBLE); } }
		 */
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}
}

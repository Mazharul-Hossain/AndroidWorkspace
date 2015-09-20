package com.TermProject.EbookReader;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.TermProject.EbookReader.store.Login;

public class Screen_for_read_book extends Main_activity implements
		View.OnClickListener {
	ImageView shelf, store, list, catalog, sd_card, l_read, home_icon,
			store_icon;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.read_book_layout);

		shelf = (ImageView) findViewById(R.id.read_screen_shelf_view);
		shelf.setOnClickListener(this);

		store = (ImageView) findViewById(R.id.read_screen_store);
		store.setOnClickListener(this);

		list = (ImageView) findViewById(R.id.read_screen_list_view);
		list.setOnClickListener(this);

		catalog = (ImageView) findViewById(R.id.read_screen_catalogs);
		catalog.setOnClickListener(this);

		sd_card = (ImageView) findViewById(R.id.read_screen_sd_card);
		sd_card.setOnClickListener(this);

		l_read = (ImageView) findViewById(R.id.read_screen_last_view);
		l_read.setOnClickListener(this);

		home_icon = (ImageView) findViewById(R.id.read_screen_logo_1);
		home_icon.setOnClickListener(this);

		store_icon = (ImageView) findViewById(R.id.read_screen_logo_2);
		store_icon.setOnClickListener(this);

	}

	public void onClick(View v) {
		if (v == shelf) {
			startActivity(new Intent(Screen_for_read_book.this,
					screen_for_self_view.class));
		} else if (v == store) {
			startActivity(new Intent(Screen_for_read_book.this,
					Screen_for_download.class));
		} else if (v == list) {
			Intent intent = new Intent(Screen_for_read_book.this,
					Screen_for_list_tab.class);
			startActivity(intent);
		} else if (v == catalog) {

		} else if (v == sd_card) {
			startActivity(new Intent(Screen_for_read_book.this,
					Screen_SD_card.class));
		} else if (v == l_read) {
			Intent intent = new Intent(Screen_for_read_book.this,
					Screen_for_last_read_book.class);
			startActivity(intent);
		} else if (v == home_icon) {
			Intent intent = new Intent(Screen_for_read_book.this,
					Screen_for_read_book.class);
			startActivity(intent);
		} else if (v == store_icon) {
			startActivity(new Intent(Screen_for_read_book.this, Login.class));
		}

	}

}
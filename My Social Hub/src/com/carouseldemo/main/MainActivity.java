package com.carouseldemo.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.carouseldemo.controls.Carousel;
import com.carouseldemo.controls.CarouselAdapter;
import com.carouseldemo.controls.CarouselAdapter.OnItemClickListener;
import com.carouseldemo.controls.CarouselAdapter.OnItemLongClickListener;

public class MainActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Carousel carousel = (Carousel) findViewById(R.id.carousel);

		carousel.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(CarouselAdapter<?> parent, View view, int position,
					long id) {
				ItemClickTask ICT = new ItemClickTask(MainActivity.this);
				ICT.Task_After_clicking(parent, view, position, id);
			}

		});

		carousel.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(CarouselAdapter<?> parent, View view,
					int position, long id) {
				LongClickListener LCL = new LongClickListener(MainActivity.this);
				LCL.LongClickTask(parent, view, position, id);
				return false;
			}
		});

	}

}

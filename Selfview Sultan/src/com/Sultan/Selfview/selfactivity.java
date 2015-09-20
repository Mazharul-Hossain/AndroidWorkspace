package com.Sultan.Selfview;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class selfactivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		int numRow = 6;
		int numCol = 4;

		TableLayout tblLayout = (TableLayout) findViewById(R.id.tblLayout);

		for (int i = 0; i < numRow; i++) {
			HorizontalScrollView HSV = new HorizontalScrollView(this);
			HSV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.FILL_PARENT));

			TableRow tblRow = new TableRow(this);
			tblRow.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT));
			tblRow.setBackgroundResource(R.drawable.bookshelf);

			for (int j = 0; j < numCol; j++) {
				ImageView imageView = new ImageView(this);
				imageView.setImageResource(R.drawable.icon);

				TextView textView = new TextView(this);
				textView.setText("Java Tester");
				textView.setTextColor(Color.BLUE);
				textView.setLayoutParams(new LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				textView.setGravity(1);
				// Sultan
				tblRow.addView(imageView, j);
				tblRow.addView(textView, j);
			}

			HSV.addView(tblRow);
			tblLayout.addView(HSV, i);
		}
	}
}
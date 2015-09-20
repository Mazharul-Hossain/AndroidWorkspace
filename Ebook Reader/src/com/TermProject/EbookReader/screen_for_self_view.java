package com.TermProject.EbookReader;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class screen_for_self_view extends Activity implements View.OnClickListener
{
	SQLiteDatabase db;
	
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.self_view);

     db = openOrCreateDatabase("Challenge__EBookBook_Reader", MODE_PRIVATE , null);
     Cursor result3 = db.rawQuery("Select * from Self_view", null);
	int rowcount = result3.getCount();
      
	ImageView imageView[];
	imageView = new ImageView[rowcount];	
	
    int numRow = (int) (Math.ceil(rowcount/5));
    int numCol = rowcount%5;
    int diff=0 ;
    
    if(numRow < 5)
    	 diff  = 5 - numRow; 
    
    TableLayout tblLayout = (TableLayout)findViewById(R.id.tblLayout);

    for(int i = 0; i <= numRow; i++) 
    {
    	
        HorizontalScrollView HSV = new HorizontalScrollView(this);
        HSV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

        TableRow tblRow = new TableRow(this);
        tblRow.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        tblRow.setBackgroundResource(R.drawable.bookshelf);

        for(int j = 0; j <numCol; j++) // 
        {
        	imageView[i*5+j] = new  ImageView(this);
        	imageView[i*5+j].setImageResource(R.drawable.icon);
        	imageView[i*5+j].setMaxHeight(80);
        	imageView[i*5+j].setMinimumHeight(80);
        	imageView[i*5+j].setMaxWidth(90);
        	imageView[i*5+j].setMinimumWidth(90);
            tblRow.addView(imageView[i*5+j],j); 
            imageView[i*5+j].setOnClickListener(this);
            imageView[i*5+j].setTag(new Integer(i*5+j));
            
       }

            HSV.addView(tblRow);
            tblLayout.addView(HSV, i);
            
            if( diff+i  == 5 )
            {
            	for(int k = 1; k < diff; k++) 
                {
                	
            		HorizontalScrollView HSV1 = new HorizontalScrollView(this);
                    HSV1.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

                    TableRow tblRow1 = new TableRow(this);
                    tblRow1.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
                    tblRow1.setBackgroundResource(R.drawable.bookshelf);
                    HSV1.addView(tblRow1);
                    tblLayout.addView(HSV1, i+k);
                }
            }
            
            
        }
    
        db.close();	
        
    }

	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		Integer i = (Integer)v.getTag();
		Toast.makeText(this, "The no of imageview that is clicked  is "+i, Toast.LENGTH_LONG).show();
	}
    
}
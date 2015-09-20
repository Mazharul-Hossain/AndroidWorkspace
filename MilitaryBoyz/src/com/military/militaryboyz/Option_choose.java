package com.military.militaryboyz;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import au.com.bytecode.opencsv.CSVWriter;

public class Option_choose extends Activity implements View.OnClickListener
{
	ImageView excel_image,mail_image,data_image,help_image;
	String path_name_of_our_folder;
	private int item_numbr=0;
    private SQLiteDatabase db;
    File temp ;
    private String item_name[]={"income_input_data_MISC","incme_inpt_dta_tk_sl_text",
			 "Publicity Media Promotions","Venue","Talent","Security",
			"Catering_or_Food","Equip. Rental","Transportation","Decorations","Misc. Services","Supplies"}; 

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.option_choose);
		make_dir();
		
		excel_image = (ImageView) findViewById(R.id.option_excel_image);
		 excel_image.setOnClickListener(this);
		
		data_image = (ImageView) findViewById(R.id.option_data_image);
		data_image.setOnClickListener(this);
		//expense_image 
		mail_image = (ImageView) findViewById(R.id.option_mail_image);
		mail_image.setOnClickListener(this);
		
		help_image = (ImageView) findViewById(R.id.option_help_image);
		help_image.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}

	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		if( v == excel_image )
		{
			Intent in = new Intent(getApplicationContext(), Input_basic_info.class);
			startActivity(in);
			
		}
		else if( v == data_image )
		{
			Intent in = new Intent(getApplicationContext(), Imcome_Expense_Grid_Chart.class);
			startActivity(in);
		}
		else if( v == mail_image )
		{
			ExportDatabaseCSVTask task=new ExportDatabaseCSVTask();
			task.execute();
		}
		else if( v == help_image )
		{
			
		}
		
	}
	
	public void make_dir()
	{
		File temp_SD = Environment.getExternalStorageDirectory();
		path_name_of_our_folder = temp_SD.getAbsolutePath()+"/Excel Sheet";
		File temp_directory = new File(path_name_of_our_folder);
		if( !temp_directory.exists() )
			temp_directory.mkdirs();
	}
	
	 private class ExportDatabaseCSVTask extends AsyncTask<String, Void, Boolean>
	 {
	    private final ProgressDialog dialog = new ProgressDialog(Option_choose.this);
	    
	    @Override
	    protected void onPreExecute() 
	    {
	        this.dialog.setMessage("Exporting database...");
	        this.dialog.show();
	    }
	    protected Boolean doInBackground(final String... args)
	    {
	    	String TAG= "Sultan";
	    	File dbFile=getDatabasePath("ITEM_database.db");
	        Log.v(TAG, "Db path is: "+dbFile);  //get the path of db
	    	File dfile = new File(path_name_of_our_folder);
 
	        File file = new File(dfile, "Excel_file.csv");
	        temp = file;
	        try
	        {	
	            file.createNewFile();
	            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
	            String arrStr[] ={"","#item","Detail","Names", "Budget","Actual","Difference Budget Vs. Actual","Deposit Issues","Due On Show Date"};
           	    csvWrite.writeNext(arrStr);
	            
           	    String arrStr1[] ={"Publicity"+"\n"+" Media "+"\n"+"Promotions"," "," "," ", " "," "," "," "," "};
           	    csvWrite.writeNext(arrStr1);
           	    
           	 db = openOrCreateDatabase("ITEM_database", MODE_PRIVATE, null);
         	 int j=0;
           	 for(int i=2;i< 10 /*item_name.length*/;i++)
           	 {
           		 String qs = "Select item_details,Menu_name ,item_name ,budget,actual from ITEM_table,ITEM_MENU_TABLE where ITEM_table.item_no=ITEM_MENU_TABLE.item_no and  ITEM_MENU_TABLE.item_no= "+i+" ;";
     		     Cursor result = db.rawQuery( qs, null);
	     	     result.moveToFirst();
	     	    
	     	 /*    int i1,i2,i3,i4,i5;
	     	     i1= result.getColumnIndex("budget");
	     	     i2 = result.getColumnIndex("actual");
	     	     i3 = result.getColumnIndex("deposit");
	     	     i4 = result.getColumnIndex("ITEM_table.item_name");
	     	     i5 = result.getColumnIndex("item_details");
	     	 
	     	     Toast.makeText(Option_choose.this, " the value of i1 is "+i1, Toast.LENGTH_LONG).show();
	     	     Toast.makeText(Option_choose.this, " the value of i1 is "+i2, Toast.LENGTH_LONG).show();
	     	     Toast.makeText(Option_choose.this, " the value of i1 is "+i3, Toast.LENGTH_LONG).show();
	     	     Toast.makeText(Option_choose.this, " the value of i1 is "+i4, Toast.LENGTH_LONG).show();
	     	     Toast.makeText(Option_choose.this, " the value of i1 is "+i5, Toast.LENGTH_LONG).show();
	     	     
	     	  */ while( !result.isAfterLast() )
	     	    {
	     	    	int bdg =  result.getInt(result.getColumnIndex("budget"));
	     	    	int act = result.getInt(result.getColumnIndex("actual"));
	     	    	int dpt = result.getInt(result.getColumnIndex("deposit")); 
	     	    	String item_name = result.getString(result.getColumnIndex("item_name"));
 	     	    	String item_detail = result.getString(result.getColumnIndex("item_details"));
 	     	    	
 	     	    	
 	     	    	int res = bdg-act;
 	     	    	int res1= act-dpt;
 	     	    	
 	     	    	if(res<0)
 	     	    	  res*=(-1);
 	     	    	
 	     	    	String arrStr2[] ={""," "+j,item_detail,item_name , "$ "+bdg,"$ "+act,"$ "+res,"$ "+dpt,"$ "+res1};
 	     	    	csvWrite.writeNext(arrStr2);
 	     	    	
 	     	    	++j;
	     		    result.moveToNext();
	     	    } 
	     	    result.close(); 
	     	 }
 
	            csvWrite.close();
	            return true;
	        }
	        catch (IOException e){
	            Log.e("MainActivity", e.getMessage(), e);
	            return false;
	        } 
	    } 
	    
	    @Override
	    protected void onPostExecute(final Boolean success)	
	    {

	       if (this.dialog.isShowing())
	        {
	            this.dialog.dismiss();
	        }
	     /*   if (success)
	        {
	            Toast.makeText(MainActivity.this, "Export successful!", Toast.LENGTH_SHORT).show();
	        }
	        else
	        {
	            Toast.makeText(MainActivity.this, "Export failed!", Toast.LENGTH_SHORT).show();
	        } */
	       
	       Intent in =  new Intent(getApplicationContext(),Mail_send.class);
	       in.putExtra("file_name", temp.getAbsolutePath());
			startActivity(in);
	    }
	} 

}

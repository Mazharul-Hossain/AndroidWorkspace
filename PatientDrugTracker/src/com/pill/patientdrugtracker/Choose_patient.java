package com.pill.patientdrugtracker;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Choose_patient extends Activity  implements View.OnClickListener,AdapterView.OnItemClickListener
,AdapterView.OnItemLongClickListener
{
	
    private ArrayList<String> pat_name = null,pat_proff = null;
    private SQLiteDatabase db;
    MyArrayAdapter adapter;
    ListView choose_patient_list;
    Button add_patient;
    Dialog myDialog;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_patient);
		
		create_database();
		
		add_patient =(Button) findViewById(R.id.add_patient);
		add_patient.setOnClickListener(this);
		
		choose_patient_list = (ListView) findViewById(R.id.listView_choose_patient);
		choose_patient_list.setOnItemClickListener(this);
		
		load_data_from_database();
		
	}
	
    private void load_data_from_database() 
    {
    	pat_name = new ArrayList<String>();
    	pat_proff = new ArrayList<String>();

    	db = openOrCreateDatabase("Patient_Care_database", MODE_PRIVATE, null);
    	
		// TODO Auto-generated method stub
	    Cursor result = db.rawQuery("Select * from Patient_table order by patient_name;", null);
	    result.moveToFirst();
	    
	    while( !result.isAfterLast() )
	    {
	    	String name =  result.getString(result.getColumnIndex("patient_name"));
	    	String proff = result.getString(result.getColumnIndex("patient_proff"));
	    	
	    	pat_name.add(name);
	    	pat_proff.add(proff);
	    	
		    result.moveToNext();
	    }
	    result.close(); 
    	
	    adapter = new MyArrayAdapter(this,pat_name,pat_proff);
	    choose_patient_list.setAdapter(adapter);
	    
	    db.close();
        
    }

	public void create_database()
    {
    	db = openOrCreateDatabase("Patient_Care_database", MODE_PRIVATE, null);
		db.execSQL("create table if not exists Patient_table ( patient_name VARCHAR Primary Key ,patient_proff VARCHAR );"); 
		
		Cursor getStar = db.rawQuery("SELECT * FROM Patient_table  WHERE patient_name = 'Me'" , null);
		if (getStar.getCount() <= 0) { // This will get the number of rows
		    ContentValues cv = new ContentValues();
			cv.put("patient_name", "Me");
			cv.put("patient_proff", "Anonymous");
			db.insert("Patient_table", null, cv);
		}
		
		 
		db.close(); 
    }
	
	public long insert_into__database(String name,String proff)
    {
    	db = openOrCreateDatabase("Patient_Care_database", MODE_PRIVATE, null); 
		
		  // This will get the number of rows
		  ContentValues cv = new ContentValues();
		  cv.put("patient_name", name);
		  cv.put("patient_proff", proff);
		  long id = db.insert("Patient_table", null, cv);
		 db.close(); 
		 return id;
    }
    

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View itemClicked, int position, long id ) 
	{
		// TODO Auto-generated method stub
		String name = adapter.getItem_name(position);
		Intent act = new Intent(Choose_patient.this , profile_patient.class);;
		act.putExtra("extra", name);
		startActivity(act);
		Choose_patient.this.finish();
		
	}

	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		if(v == add_patient)
		{
			show_dialog();
		}
		
	}
	
	private void show_dialog()
	{
		// TODO Auto-generated method stub
		
		myDialog = new Dialog(Choose_patient.this);
        myDialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
        myDialog.setContentView(R.layout.my_custom_dialog);
        myDialog.setTitle("Enter Details Of Patient");
       
        final EditText choose_pat_name_edit = (EditText) myDialog.findViewById(R.id.choose_pat_name_edit);
        final EditText choose_pat_proof_edit = (EditText) myDialog.findViewById(R.id.choose_pat_proof_edit);
        
        Button import_button = (Button)myDialog.findViewById(R.id.btn_add_pat);
        import_button.setOnClickListener(new OnClickListener()
        {
           public void onClick(View v) 
            {
        	   String name = choose_pat_name_edit.getText().toString();	  
        	   if(name.matches(""))
        	   {
        		  Toast.makeText(Choose_patient.this, "The name can not be empty", Toast.LENGTH_LONG).show(); 
        	   }
        	   else
        	   {
        		   String proff = choose_pat_proof_edit.getText().toString();	  
        		   insert_into__database(name,proff);
        			load_data_from_database();
        	   }
        	   myDialog.cancel();
            }
        });
       
        Button finishbtn = (Button)myDialog.findViewById(R.id.btn_cancel_add_pat);
        finishbtn.setOnClickListener(new OnClickListener() {
        
     	public void onClick(View v) 
        {
           myDialog.cancel();
        }
       
        });
        myDialog.show(); 
        myDialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.img_for_patient);
	} 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_splash_screen, menu);
		return true;
	}

}

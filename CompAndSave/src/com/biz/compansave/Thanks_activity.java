package com.biz.compansave;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.biz.compandsave.R;

public class Thanks_activity extends Activity{
    /** Called when the activity is first created. */
    Button btn1,btn2,btn3;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thanks_layout);
        

        btn1= (Button) findViewById(R.id.button1);
        btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String url = "http://www.compandsave.com/christmas";
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
			}
		});
       
        btn2= (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Thanks_activity.this,Choose_option_activity.class);
				startActivity(intent);
			}
		});
        CASGiftBoxApp app= (CASGiftBoxApp)getApplication();
		app.applyTypefaceToAllTextviews(getWindow().getDecorView().findViewById(android.R.id.content));

    }

    /**
     * Converts an input stream to a string
     * 
     * @param is
     *            The {@code InputStream} object to read from
     * @return A {@code String} object representing the string for of the input
     * @throws IOException
     *             Thrown on read failure from the input
     */
    public String inputStreamToString(InputStream is) throws IOException {
        StringBuffer sBuffer = new StringBuffer();
        
        DataInputStream data = new DataInputStream(is);
        BufferedReader dataIO = new BufferedReader(new InputStreamReader(data)); 
        String strLine = null;

        while ((strLine = dataIO.readLine()) != null) {
            sBuffer.append(strLine + "\n");
        }

        dataIO.close();
        is.close();

        return sBuffer.toString();
    }

	
}
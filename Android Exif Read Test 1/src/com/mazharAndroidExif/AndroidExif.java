package com.mazharAndroidExif;

import java.io.IOException;

import android.app.Activity;
import android.media.ExifInterface;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class AndroidExif extends Activity {

	TextView myTextView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_android_exif);

		myTextView = (TextView) findViewById(R.id.textview);

		// change with the filename & location of your photo file
		String filename = "/sdcard/DSC00158.JPG";
		try {
			ExifInterface exif = new ExifInterface(filename);
			ShowExif(exif);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(this, "Error!", Toast.LENGTH_LONG).show();
		}
	}

	private void ShowExif(ExifInterface exif) {
		String myAttribute = "Exif information ---\n";
		myAttribute += getTagString(ExifInterface.TAG_DATETIME, exif);
		myAttribute += getTagString(ExifInterface.TAG_FLASH, exif);
		myAttribute += getTagString(ExifInterface.TAG_GPS_LATITUDE, exif);
		myAttribute += getTagString(ExifInterface.TAG_GPS_LATITUDE_REF, exif);
		myAttribute += getTagString(ExifInterface.TAG_GPS_LONGITUDE, exif);
		myAttribute += getTagString(ExifInterface.TAG_GPS_LONGITUDE_REF, exif);
		myAttribute += getTagString(ExifInterface.TAG_IMAGE_LENGTH, exif);
		myAttribute += getTagString(ExifInterface.TAG_IMAGE_WIDTH, exif);
		myAttribute += getTagString(ExifInterface.TAG_MAKE, exif);
		myAttribute += getTagString(ExifInterface.TAG_MODEL, exif);
		myAttribute += getTagString(ExifInterface.TAG_ORIENTATION, exif);
		myAttribute += getTagString(ExifInterface.TAG_WHITE_BALANCE, exif);
		myTextView.setText(myAttribute);
	}

	private String getTagString(String tag, ExifInterface exif) {
		return (tag + " : " + exif.getAttribute(tag) + "\n");
	}
}
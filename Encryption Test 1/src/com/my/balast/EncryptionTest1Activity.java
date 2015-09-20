package com.my.balast;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

public class EncryptionTest1Activity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(" onCreate ", "onCreate starts.........");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		String seed = "8NJEAAVRRnniiezzvvqmmii$$},,|==*";
		File inFile;
		File outFile;
		/*
		 * inFile = new File(Environment.getExternalStorageDirectory()
		 * .getAbsolutePath() + File.separator + "original.jpg");
		 * 
		 * outFile = new File(Environment.getExternalStorageDirectory()
		 * .getAbsolutePath() + File.separator + "En_" + inFile.getName());
		 * 
		 * try { SecurityUtils.encryptAES(seed, inFile, outFile); } catch
		 * (Exception e) { // TODO Auto-generated catch block
		 * Log.e(" onCreate ", "encryptAES Fails.........");
		 * e.printStackTrace(); }
		 */

		inFile = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + File.separator + "New Text Document.txt");

		outFile = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + File.separator + "De_" + inFile.getName());

		try {
			SecurityUtils.decryptAES(seed, inFile, outFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(" onCreate ", "decryptAES Fails.........");
			e.printStackTrace();
		}

		Log.d(" onCreate ", "onCreate Finish.........");
	}
}
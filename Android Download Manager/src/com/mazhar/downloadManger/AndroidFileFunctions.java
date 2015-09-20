package com.mazhar.downloadManger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class AndroidFileFunctions {

	// String fileNAME = "/sdcard/download/possible.txt";
	// String ext_Storage_Directory =
	// Environment.getExternalStorageDirectory().toString();
	// File file = new File(ext_Storage_Directory, "FileName.extension");
	// File file = new File(getExternalFilesDir(null), "FileName.extension");

	static boolean mExternalStorageAvailable = false;
	static boolean mExternalStorageWriteable = false;

	public static void checking_media_availability() {

		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			// We can read and write the media
			mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			// We can only read the media
			mExternalStorageAvailable = true;
			mExternalStorageWriteable = false;
		} else {
			// Something else is wrong. It may be one of many other states, but
			// all we need
			// to know is we can neither read nor write
			mExternalStorageAvailable = mExternalStorageWriteable = false;
		}
	}

	public static String getFileValue(String fileName, Context context) {
		try {

			StringBuffer outStringBuf = new StringBuffer();
			String inputLine = "";

			/*
			 * We have to use the openFileInput()-method the ActivityContext
			 * provides. Again for security reasons with openFileInput(...)
			 */
			FileInputStream fIn = context.openFileInput(fileName);
			InputStreamReader isr = new InputStreamReader(fIn);
			BufferedReader inBuff = new BufferedReader(isr);

			while ((inputLine = inBuff.readLine()) != null) {
				outStringBuf.append(inputLine);
				outStringBuf.append("\n");
			}

			inBuff.close();

			Log.i("File Reading stuff", "success = ");

			return outStringBuf.toString();
		}

		catch (IOException e) {
			return null;
		}
	}

	public static boolean appendFileValue(String fileName, String value,
			Context context) {
		return writeToFile(fileName, value, context, Context.MODE_APPEND);
	}

	public static boolean setFileValue(String fileName, String value,
			Context context) {
		return writeToFile(fileName, value, context,
				Context.MODE_WORLD_READABLE);
	}

	public static boolean writeToFile(String fileName1, String value,
			Context context, int writeOrAppendMode) {

		checking_media_availability();

		if (mExternalStorageAvailable == false
				|| mExternalStorageWriteable == false) {
			return false;
		}

		// just make sure it's one of the modes we support
		if (writeOrAppendMode != Context.MODE_WORLD_READABLE
				&& writeOrAppendMode != Context.MODE_WORLD_WRITEABLE
				&& writeOrAppendMode != Context.MODE_APPEND) {
			return false;
		}

		try {

			/*
			 * We have to use the openFileOutput()-method the ActivityContext
			 * provides, to protect your file from others and This is done for
			 * security-reasons. We chose MODE_WORLD_READABLE, because we have
			 * nothing to hide in our file
			 */

			String ext_Storage_Directory = Environment
					.getExternalStorageDirectory().toString();
			String fileName = ext_Storage_Directory
					+ "com.mazhar.downloadManger/" + fileName1;

			FileOutputStream fOut = context.openFileOutput(fileName,
					writeOrAppendMode);
			OutputStreamWriter osw = new OutputStreamWriter(fOut);

			// Write the string to the file
			osw.write(value);

			// save and close
			osw.flush();
			osw.close();
		}

		catch (IOException e) {
			return false;
		}

		return true;
	}

	public static void deleteFile(String fileName, Context context) {
		context.deleteFile(fileName);
	}
}
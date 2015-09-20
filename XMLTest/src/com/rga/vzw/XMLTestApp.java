package com.rga.vzw;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Application;
import android.util.Log;

import com.rga.vzw.datatypes.AMPP;
import com.rga.vzw.datatypes.Album;
import com.rga.vzw.datatypes.App;

public class XMLTestApp extends Application {
	String TAG = "XMLTestApp";

	private static XMLTestApp singleton;
	private DigitalLoungeParser parser;
	ArrayList<App> apps;
	ArrayList<AMPP> ampps;

	public static XMLTestApp getInstance() {
		return singleton;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		singleton = this;
		Log.d(TAG, "test app created");

		parser = new DigitalLoungeParser();

		try {
			parser.parseXML();
			// apps = parser.getAppList();
			ampps = parser.getAmppList();
			Log.d(TAG, "Parsing Completed");
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// create list adapters.
	}

	public ArrayList<App> getAppList() {
		Log.d(TAG, "requesting appList");
		return apps;
	}

	public ArrayList<Album> getMusicList() {
		return parser.getMusicList();
	}

	public ArrayList<AMPP> getAmppList() {
		return ampps;
	}

}

package com.rga.vzw;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.os.Environment;
import android.util.Log;

import com.rga.vzw.datatypes.AMPP;
import com.rga.vzw.datatypes.Album;
import com.rga.vzw.datatypes.App;
import com.rga.vzw.datatypes.AttractVideo;
import com.rga.vzw.datatypes.Game;
import com.rga.vzw.datatypes.Video;

public class DigitalLoungeParser {

	String TAG = "PARSER";
	private static final String APPLICATIONS = "applications";
	private static final String GAMES = "games";
	private static final String VIDEOS = "videos";
	private static final String MUSIC = "music";
	private static final String ATTRACT_VIDEOS = "attract_loops";
	private static final String AMPP = "AMPP";

	// repeating nodes
	private static final String APP = "app";
	private static final String DESCRIPTION = "description";
	private static final String DEVELOPER = "developer";
	private static final String LARGE_IMAGE = "large_image";
	private static final String SMALL_IMAGE = "small_image";

	// repeating nodes
	private static final String APPID = "APPID";
	private static final String NM = "NM";
	private static final String VPPID = "VPPID";
	private static final String APID = "APID";
	private static final String LEGAL_CATCD = "LEGAL_CATCD";
	private static final String SUBP = "SUBP";

	private int currentSection = -1;

	private ArrayList<App> appList;
	private ArrayList<Game> gameList;
	private ArrayList<Album> musicList;
	private ArrayList<Video> videoList;
	private ArrayList<AttractVideo> attractList;
	private ArrayList<AMPP> amppList;

	private boolean isParsed = false;

	private final String fileName = "/Music/xmltest21.xml";

	public void parseXML() throws XmlPullParserException, IOException {
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XmlPullParser xpp = factory.newPullParser();

		// get a reference to the file.
		File file = new File(Environment.getExternalStorageDirectory() + fileName);

		// create an input stream to be read by the stream reader.
		FileInputStream fis = new FileInputStream(file);

		// set the input for the parser using an InputStreamReader
		xpp.setInput(new InputStreamReader(fis));

		int eventType = xpp.getEventType();

		// /

		while (eventType != XmlPullParser.END_DOCUMENT) {

			// set flags for main tags.
			if (eventType == XmlPullParser.START_DOCUMENT) {
				// TODO only parse if the timestamps don't match.
				Log.d(TAG, "Start document");

			} else if (eventType == XmlPullParser.END_DOCUMENT) {

				Log.d(TAG, "End document");

			} else if (eventType == XmlPullParser.START_TAG) {

				String nodeName = xpp.getName();

				if (nodeName.contentEquals(APPLICATIONS)) {
					appList = new ArrayList<App>();
					currentSection = 0;
				} else if (nodeName.contentEquals(GAMES)) {
					gameList = new ArrayList<Game>();
					currentSection = 1;
				} else if (nodeName.contentEquals(MUSIC)) {
					musicList = new ArrayList<Album>();
					currentSection = 2;
				} else if (nodeName.contentEquals(VIDEOS)) {
					videoList = new ArrayList<Video>();
					currentSection = 3;
				} else if (nodeName.contentEquals(ATTRACT_VIDEOS)) {
					attractList = new ArrayList<AttractVideo>();
					currentSection = 4;
				} else if (nodeName.contentEquals(AMPP)) {
					amppList = new ArrayList<AMPP>();
					currentSection = 5;
				}

				Log.d(TAG, "current section :>>" + currentSection);
				App app = new App();
				AMPP ampp = new AMPP();

				switch (currentSection) {

				case 0:
					Log.d("xml loop", "parsing through applications>> " + nodeName);
					if (nodeName.contentEquals(APP)) {
						app = new App();
						appList.add(app);
						app.setTitle(xpp.getAttributeValue(0));
					}
					if (nodeName.contentEquals(DESCRIPTION)) {
						app.setDescription(xpp.nextText());
					}
					if (nodeName.contentEquals(DEVELOPER)) {
						app.setDeveloper(xpp.nextText());
					}
					if (nodeName.contentEquals(LARGE_IMAGE)) {
						app.setLargeImage(xpp.nextText());
					}
					if (nodeName.contentEquals(SMALL_IMAGE)) {
						app.setSmallImage(xpp.nextText());
					}
					break;
				case 1:
					Log.d("xml loop", "parsing through games>>>:" + nodeName);
					break;
				case 2:
					Log.d("xml loop", "parsing through videos>>>:" + nodeName);
					break;
				case 3:
					Log.d("xml loop", "parsing through music:" + nodeName);
					break;
				case 4:
					Log.d("xml loop", "parsing through attract videos:" + nodeName);
					break;

				case 5:

					Log.d("xml loop", "parsing through applications>> " + nodeName);

					if (nodeName.contentEquals(APPID)) {
						Log.d("xml loop", "parsing through applications 1 >> " + nodeName);
						ampp = new AMPP();
						amppList.add(ampp);
						Log.d("xml loop", "parsing through applications 2 >> " + nodeName);
						String appid = new String();
						int eventType1 = xpp.next();
						while (eventType1 != XmlPullParser.END_TAG) {
							Log.d(TAG, "current node 1 >> " + nodeName + " >> " + appid);
							appid = appid + " " + xpp.getText();
							eventType1 = xpp.next();
							Log.d(TAG, "current node 2 >> " + nodeName + " >> " + appid);
						}
						ampp.setAPPID(appid);
						Log.d(TAG, "current node :>>" + nodeName + " >> " + appid);

					} else if (nodeName.contentEquals(NM)) {
						Log.d("xml loop", "parsing through applications>> " + nodeName);
						String nm = "";
						int eventType1 = xpp.next();
						while (eventType1 != XmlPullParser.END_TAG) {
							Log.d(TAG, "current node :>>" + nodeName + " >> " + nm);
							nm = nm + " " + xpp.getText();
							eventType1 = xpp.next();
						}
						ampp.setNM(nm);
						Log.d(TAG, "current node :>>" + nodeName + " >> " + nm);

					} else if (nodeName.contentEquals(VPPID)) {
						Log.d("xml loop", "parsing through applications>> " + nodeName);
						String vppid = "";
						int eventType1 = xpp.next();
						while (eventType1 != XmlPullParser.END_TAG) {
							vppid = vppid + " " + xpp.getText();
							eventType1 = xpp.next();
						}
						ampp.setVPPID(vppid);
						Log.d(TAG, "current node :>>" + nodeName + " >> " + vppid);

					} else if (nodeName.contentEquals(APID)) {
						Log.d("xml loop", "parsing through applications>> " + nodeName);
						String apid = "";
						int eventType1 = xpp.next();
						while (eventType1 != XmlPullParser.END_TAG) {
							apid = apid + " " + xpp.getText();
							eventType1 = xpp.next();
						}
						ampp.setAPID(apid);

						Log.d(TAG, "current node :>>" + nodeName + " >> " + apid);

					} else if (nodeName.contentEquals(LEGAL_CATCD)) {
						Log.d("xml loop", "parsing through applications>> " + nodeName);
						String catcd = "";
						int eventType1 = xpp.next();
						while (eventType1 != XmlPullParser.END_TAG) {
							catcd = catcd + " " + xpp.getText();
							eventType1 = xpp.next();
						}
						ampp.setLEGAL_CATCD(catcd);
						Log.d(TAG, "current node :>>" + nodeName + " >> " + catcd);

					} else if (nodeName.contentEquals(SUBP)) {
						Log.d("xml loop", "parsing through applications>> " + nodeName);
						String catcd = "";
						int eventType1 = xpp.next();
						while (eventType1 != XmlPullParser.END_TAG) {
							catcd = catcd + " " + xpp.getText();
							eventType1 = xpp.next();
						}
						ampp.setSUBP(catcd);
						Log.d(TAG, "current node :>>" + nodeName + " >> " + catcd);
					}
					break;

				default:
					break;
				}
			} else if (eventType == XmlPullParser.END_TAG) {
				String nodeName = xpp.getName();
				Log.d(TAG, "End tag " + nodeName);
			} else if (eventType == XmlPullParser.TEXT) {
			}
			eventType = xpp.next();
		}
		Log.d(TAG, "TOTAL APPS CREATED :" + amppList.size());
		setAppList(appList);
		isParsed = true;
	}

	public void setAppList(ArrayList<App> appList) {
		this.appList = appList;
	}

	/**
	 * @return the currentSection
	 */
	public int getCurrentSection() {
		return currentSection;
	}

	/**
	 * @return the appList
	 */
	public ArrayList<App> getAppList() {
		Log.d(TAG, "applist>>" + appList.size());
		return appList;
	}

	/**
	 * @return the musicList
	 */
	public ArrayList<Album> getMusicList() {
		return musicList;
	}

	/**
	 * @return the videoList
	 */
	public ArrayList<Video> getVideoList() {
		return videoList;
	}

	/**
	 * @return the attractList
	 */
	public ArrayList<AttractVideo> getAttractList() {
		return attractList;
	}

	/**
	 * @return the isParsed
	 */
	public boolean isParsed() {
		return isParsed;
	}

	public ArrayList<AMPP> getAmppList() {
		return amppList;
	}

}

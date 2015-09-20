package com.itcuties.android.apps;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class AsyncDataTread implements Runnable {

	static boolean flag = true;
	private int Number_Connection_Retry = 0;
	private final String base_URI = "http://10.0.2.2/bdbay_beta/index.php/Welcome/";
	Context mContext;

	public AsyncDataTread(Context context) {
		mContext = context;
	}

	@Override
	public void run() {
		if (flag) {
			flag = false;

			JSONObject json = new JSONObject();
			String URI = "Android_Transection";
			String Check = "transection";

			try {
				json.put("type", "transection");

				JSONArray jArray = loadWebValue(json, URI, Check);
				JSONObject json_data = null;

				if (!(jArray == null) && jArray.length() > 0) {

					Log.d("JSON", "Enterde into parsing service");

					string json_data.getString("name")
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			flag = true;
		}

	}

	/************** Check Internet Method ***************/
	private boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni == null)
			// There are no active networks.
			return false;
		else
			return true;
	}

	/************** Http Method Retry Internet Handler Method ***************/

	/************** load Web Value Method ***************/
	public JSONArray loadWebValue(JSONObject json, String provided_URI, String Check) {

		InputStream is = null;
		StringBuilder sb;
		String result = new String();
		JSONArray jArray = null;
		try {
			jArray = new JSONArray(result);
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		String URI = base_URI + provided_URI;

		if (isNetworkConnected()) {
			try {
				Log.d(Check, json.toString());

				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair(Check, json.toString()));

				HttpParams httpParameters = new BasicHttpParams();

				/*
				 * Set the timeout in milliseconds until a connection is
				 * established. The default value is zero, that means the
				 * timeout is not used.
				 */
				int timeoutConnection = 5 * 1000;
				HttpConnectionParams.setConnectionTimeout(httpParameters,
						timeoutConnection);

				/*
				 * Set the default socket timeout (SO_TIMEOUT) in milliseconds
				 * which is the timeout for waiting for data.
				 */
				int timeoutSocket = 8 * 1000;
				HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

				HttpClient httpclient = new DefaultHttpClient(httpParameters);
				HttpPost httppost = new HttpPost(URI);
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				HttpResponse response = null;
				response = httpclient.execute(httppost);

				HttpEntity entity = null;
				if (response != null) {
					entity = response.getEntity();
				}
				if (entity != null) {
					is = entity.getContent();
				}
				// convert response to string
				if (is != null) {
					BufferedReader reader = new BufferedReader(new InputStreamReader(is,
							"iso-8859-1"), 8);
					sb = new StringBuilder();
					sb.append(reader.readLine() + "\n");

					String line = "0";
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
					is.close();
					result = sb.toString();
					Log.d("log_tag", "Result in connection : " + result);

					jArray = new JSONArray(result);
				}
			} catch (Exception e) {
				Number_Connection_Retry++;
				Log.d("exception_tag", "Number_Connection_Retry : "
						+ Number_Connection_Retry);
				if (Number_Connection_Retry < 5) {
					jArray = loadWebValue(json, provided_URI, Check);
				} else {
					Number_Connection_Retry = 0;
					return jArray;
				}
			}
		}
		Number_Connection_Retry = 0;
		return jArray;
	}

}

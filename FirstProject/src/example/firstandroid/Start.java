package example.firstandroid;

import java.util.List;

import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.types.TextPost;
import com.tumblr.jumblr.types.User;

public class Start extends ListActivity {

	String str[] = { "FaceBook", "Twitter", "Instagram", "Pinterest", "Linkedin",
			"Tumblr", "Flickr", "Google+", "WeChat", "Whatsapp", "VK", "Myspace",
			"Cafemom", "Skype", "Installed Program" };
	final Context context = this;
	EditText title, body, tag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// full screen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setListAdapter(new ArrayAdapter<String>(Start.this,
				android.R.layout.simple_list_item_1, str));

		this.getListView().setLongClickable(true);
		this.getListView().setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				switch (arg2) {
				case 0:
					break;
				case 1:

					final Dialog dialog = new Dialog(context);

					dialog.setContentView(R.layout.alert_dialog);
					dialog.setTitle("Title...");
					dialog.setTitle("fdsf");

					Button dialogButton = (Button) dialog.findViewById(R.id.ok);
					Button cancelButton = (Button) dialog.findViewById(R.id.cancel);

					dialogButton.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							dialog.dismiss();
						}
					});

					cancelButton.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							dialog.cancel();
						}

					});

					dialog.show();

					break;

				case 5:

					final Dialog dialog5 = new Dialog(context);

					dialog5.setContentView(R.layout.tumblr);
					dialog5.setTitle("Status Update...");

					Button dialogButton5 = (Button) dialog5.findViewById(R.id.ok);
					Button cancelButton5 = (Button) dialog5.findViewById(R.id.cancel);

					dialogButton5.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {

							title = (EditText) dialog5.findViewById(R.id.editText1);
							body = (EditText) dialog5.findViewById(R.id.editText2);
							tag = (EditText) dialog5.findViewById(R.id.editText3);

							ExportDatabaseCSVTask t = new ExportDatabaseCSVTask();
							t.execute("");
							dialog5.dismiss();

						}
					});

					cancelButton5.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							dialog5.cancel();
						}

					});

					dialog5.show();

					break;
				case 9:

					final Dialog dialog9 = new Dialog(context);

					dialog9.setContentView(R.layout.whatsapp);
					dialog9.setTitle("Send Message...");

					Button dialogButton9 = (Button) dialog9.findViewById(R.id.ok);
					Button cancelButton9 = (Button) dialog9.findViewById(R.id.cancel);

					dialogButton9.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {

							EditText editText = (EditText) dialog9
									.findViewById(R.id.mText1);

							Intent waIntent = new Intent(Intent.ACTION_SEND);
							waIntent.setType("text/plain");

							waIntent.setPackage("com.whatsapp");
							if (waIntent != null) {
								waIntent.putExtra(Intent.EXTRA_TEXT, editText.getText()
										.toString());

								startActivity(Intent.createChooser(waIntent, "share"));
							}

							dialog9.dismiss();
						}
					});

					cancelButton9.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							dialog9.cancel();
						}

					});

					dialog9.show();

					break;
				}

				return false;
			}
		});

	}

	public class ExportDatabaseCSVTask extends AsyncTask<String, Void, Boolean> {
		private final ProgressDialog dialog = new ProgressDialog(Start.this);
		User user;
		JumblrClient client;
		String a, b, c;
		int d, e;

		@Override
		protected void onPreExecute() {
			this.dialog.setMessage("Updating Status...");
			this.dialog.show();

			client = new JumblrClient(
					"olBvNLED99Y8XwmvfJIUCA8KejcnI4E6jvEgKMXruQFUtO9Vz4",
					"IodLporX6WHjC0wBWTWnzhgyJJce717VvsidhBuUMdgYbm9gIx");
			client.setToken("RlHtZuE73V9gnR0FzbdYjjLbzZEF1DjU5Li5DQUr1cANxb9IC7",
					"5l0vWJeKUbPcWGYwMPpLWDHHnGoNZ4XoAidVezEiqrsxuouIJ2");

		}

		@Override
		protected Boolean doInBackground(final String... args) {

			TextPost post;
			try {
				post = client.newPost(client.user().getName(), TextPost.class);
				post.setTitle(title.getText().toString());
				post.setBody(body.getText().toString());

				String employee = tag.getText().toString();
				;
				String delims = "[ .,?!]+";
				String[] tokens = employee.split(delims);

				for (int i = 0; i < tokens.length; i++)
					post.addTag(tokens[i]);

				post.save();

			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			} catch (InstantiationException e1) {
				e1.printStackTrace();
			}
			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			if (this.dialog.isShowing()) {
				this.dialog.dismiss();
			}

			boolean flag = false;

			final PackageManager pm = getPackageManager();
			// get a list of installed apps.
			List<ApplicationInfo> packages = pm
					.getInstalledApplications(PackageManager.GET_META_DATA);
			Intent openStartingPoint = new Intent("example.firstandroid.WEB");

			for (ApplicationInfo packageInfo : packages) {

				String s = packageInfo.packageName;
				if (s.compareTo("com.tumblr") == 0) {
					flag = true;
					Intent instagramIntent = getPackageManager()
							.getLaunchIntentForPackage(s);
					startActivity(instagramIntent);
				}
			}
			if (!flag) {
				openStartingPoint.putExtra("key", "https://www.tumblr.com/");
				startActivity(openStartingPoint);
			}
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		boolean flag = false;

		Intent openStartingPoint = new Intent("example.firstandroid.WEB");

		final PackageManager pm = getPackageManager();
		// get a list of installed apps.
		List<ApplicationInfo> packages = pm
				.getInstalledApplications(PackageManager.GET_META_DATA);

		switch (position) {
		case 0:

			for (ApplicationInfo packageInfo : packages) {

				String s = packageInfo.packageName;
				if (s.compareTo("com.facebook.katana") == 0) {
					flag = true;
					Intent facebookIntent = getPackageManager()
							.getLaunchIntentForPackage(s);
					startActivity(facebookIntent);
				}

			}
			if (!flag) {
				openStartingPoint.putExtra("key", "https://www.facebook.com");
				startActivity(openStartingPoint);
			}

			break;
		case 1:

			for (ApplicationInfo packageInfo : packages) {

				String s = packageInfo.packageName;
				if (s.compareTo("com.twitter.android") == 0) {
					flag = true;
					Intent twitterIntent = getPackageManager().getLaunchIntentForPackage(
							s);
					startActivity(twitterIntent);
				}

			}
			if (!flag) {
				openStartingPoint.putExtra("key", "https://twitter.com/");
				startActivity(openStartingPoint);
			}

			break;
		case 2:

			for (ApplicationInfo packageInfo : packages) {

				String s = packageInfo.packageName;
				if (s.compareTo("com.instagram.android") == 0) {
					flag = true;
					Intent instagramIntent = getPackageManager()
							.getLaunchIntentForPackage(s);
					startActivity(instagramIntent);
				}

			}
			if (!flag) {
				openStartingPoint.putExtra("key", "http://instagram.com/");
				startActivity(openStartingPoint);
			}

			break;
		case 3:

			for (ApplicationInfo packageInfo : packages) {

				String s = packageInfo.packageName;
				if (s.compareTo("com.pinterest") == 0) {
					flag = true;
					Intent instagramIntent = getPackageManager()
							.getLaunchIntentForPackage(s);
					startActivity(instagramIntent);
				}

			}
			if (!flag) {
				openStartingPoint.putExtra("key", "https://www.pinterest.com/");
				startActivity(openStartingPoint);
			}

			break;
		case 4:

			for (ApplicationInfo packageInfo : packages) {

				String s = packageInfo.packageName;
				if (s.compareTo("com.linkedin.android") == 0) {
					flag = true;
					Intent instagramIntent = getPackageManager()
							.getLaunchIntentForPackage(s);
					startActivity(instagramIntent);
				}

			}
			if (!flag) {
				openStartingPoint.putExtra("key", "https://www.linkedin.com/");
				startActivity(openStartingPoint);
			}

			break;
		case 5:

			for (ApplicationInfo packageInfo : packages) {

				String s = packageInfo.packageName;
				if (s.compareTo("com.tumblr") == 0) {
					flag = true;
					Intent instagramIntent = getPackageManager()
							.getLaunchIntentForPackage(s);
					startActivity(instagramIntent);
				}

			}
			if (!flag) {
				openStartingPoint.putExtra("key", "https://www.tumblr.com/");
				startActivity(openStartingPoint);
			}

			break;
		case 6:

			for (ApplicationInfo packageInfo : packages) {

				String s = packageInfo.packageName;
				if (s.compareTo("com.yahoo.mobile.client.android.flickr") == 0) {
					flag = true;
					Intent instagramIntent = getPackageManager()
							.getLaunchIntentForPackage(s);
					startActivity(instagramIntent);
				}

			}
			if (!flag) {
				openStartingPoint.putExtra("key", "http://www.flickr.com/");
				startActivity(openStartingPoint);
			}

			break;

		case 7:

			for (ApplicationInfo packageInfo : packages) {

				String s = packageInfo.packageName;
				if (s.compareTo("com.google.android.gms.samples.plus") == 0) {
					flag = true;
					Intent instagramIntent = getPackageManager()
							.getLaunchIntentForPackage(s);
					startActivity(instagramIntent);
				}

			}
			if (!flag) {
				openStartingPoint
						.putExtra(
								"key",
								"https://accounts.google.com/ServiceLogin?service=oz&passive=1209600&continue=https://plus.google.com/?gpsrc%3Dgplp0%26partnerid%3Dgplp0");
				startActivity(openStartingPoint);
			}

			break;

		case 8:

			for (ApplicationInfo packageInfo : packages) {

				String s = packageInfo.packageName;
				if (s.compareTo("com.tencent.mm") == 0) {
					flag = true;
					Intent instagramIntent = getPackageManager()
							.getLaunchIntentForPackage(s);
					startActivity(instagramIntent);
				}

			}
			if (!flag) {
				openStartingPoint.putExtra("key", "http://www.wechat.com/");
				startActivity(openStartingPoint);
			}

			break;

		case 9:

			for (ApplicationInfo packageInfo : packages) {

				String s = packageInfo.packageName;
				if (s.compareTo("com.whatsapp") == 0) {
					flag = true;
					Intent instagramIntent = getPackageManager()
							.getLaunchIntentForPackage(s);
					startActivity(instagramIntent);
				}

			}
			if (!flag) {
				openStartingPoint.putExtra("key", "http://www.whatsapp.com/");
				startActivity(openStartingPoint);
			}
			break;

		case 10:

			for (ApplicationInfo packageInfo : packages) {

				String s = packageInfo.packageName;
				if (s.compareTo("com.vkontakte.android") == 0) {
					flag = true;
					Intent instagramIntent = getPackageManager()
							.getLaunchIntentForPackage(s);
					startActivity(instagramIntent);
				}

			}
			if (!flag) {

				openStartingPoint.putExtra("key", "http://vk.com/");
				startActivity(openStartingPoint);
			}

			break;

		case 11:

			for (ApplicationInfo packageInfo : packages) {

				String s = packageInfo.packageName;
				if (s.compareTo("com.myspace.android.apk") == 0) {
					flag = true;
					Intent instagramIntent = getPackageManager()
							.getLaunchIntentForPackage(s);
					startActivity(instagramIntent);
				}

			}
			if (!flag) {

				openStartingPoint.putExtra("key", "https://myspace.com/");
				startActivity(openStartingPoint);
			}

			break;
		case 12:

			for (ApplicationInfo packageInfo : packages) {

				String s = packageInfo.packageName;
				if (s.compareTo("com.myspace.android.apk") == 0) {
					flag = true;
					Intent instagramIntent = getPackageManager()
							.getLaunchIntentForPackage(s);
					startActivity(instagramIntent);
				}

			}
			if (!flag) {

				openStartingPoint.putExtra("key", "http://www.cafemom.com/");
				startActivity(openStartingPoint);
			}

			break;
		case 13:

			for (ApplicationInfo packageInfo : packages) {

				String s = packageInfo.packageName;
				if (s.compareTo("com.skype.raider") == 0) {
					flag = true;
					Intent skypeIntent = getPackageManager().getLaunchIntentForPackage(s);
					startActivity(skypeIntent);
				}
			}
			if (!flag) {
				openStartingPoint.putExtra("key", "http://www.skype.com/");
				startActivity(openStartingPoint);
			}

			break;
		case 14:
			Intent app = new Intent("example.firstandroid.MENUP");
			startActivity(app);
			break;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuInflater blowUp = getMenuInflater();
		blowUp.inflate(R.menu.cool_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.aboutUs:
			Intent i = new Intent("example.firstandroid.ABOUT");
			startActivity(i);
			break;
		case R.id.preferences:
			Intent p = new Intent("example.firstandroid.PREFS");
			startActivity(p);
			break;

		case R.id.exit:
			finish();
			break;
		}

		return false;
	}

}

package com.carouseldemo.main;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.View;

import com.carouseldemo.controls.CarouselAdapter;

public class LongClickListener {
	Activity activity;

	public LongClickListener(Activity act) {
		activity = act;
	}

	public void ShowDialog(String title, String msg) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
		alertDialogBuilder.setTitle(title);

		alertDialogBuilder.setMessage(msg);
		alertDialogBuilder.setCancelable(false);
		alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				activity.finish();
			}
		});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	void LongClickTask(CarouselAdapter<?> parent, View view, int position, long id) {
		boolean flag = false;
		final PackageManager pm = activity.getPackageManager();
		List<ApplicationInfo> packages = pm
				.getInstalledApplications(PackageManager.GET_META_DATA);

		switch (position) {
		case 0:
			Intent act = new Intent(activity, Facebook_Connect.class);
			;
			activity.startActivity(act);

			break;
		case 3:
			for (ApplicationInfo packageInfo : packages) {

				String s = packageInfo.packageName;
				if (s.compareTo("com.tumblr") == 0) {
					flag = true;
				}

			}
			if (flag) {
				Intent act1 = new Intent(activity, Pin_Url_In_Pinterest.class);
				;
				activity.startActivity(act1);
			} else {
				String title = "Pinterest app is not installed in your phone";
				String msg = "To Pin a image \n you have to \n"
						+ "install the official app \n of pinterest";
				ShowDialog(title, msg);
			}
			break;
		case 5:

			for (ApplicationInfo packageInfo : packages) {
				String s = packageInfo.packageName;
				if (s.compareTo("com.tumblr") == 0)
					flag = true;
			}

			if (flag) {
				Intent act2 = new Intent(activity, Tumblr_Activity.class);
				activity.startActivity(act2);
			} else {
				String title = "Tumblr app is not installed in your phone";
				String msg = "To write a status \n you have to \n"
						+ "install the official app \n of Tumblr";
				ShowDialog(title, msg);
			}
			break;

		case 9:

			for (ApplicationInfo packageInfo : packages) {
				String s = packageInfo.packageName;
				if (s.compareTo("com.whatsapp") == 0)
					flag = true;
			}
			if (flag) {
				Intent act3 = new Intent(activity, WhatsAPP_Activity.class);
				activity.startActivity(act3);
			} else {
				String title = "WhatsApp app is not installed in your phone";
				String msg = "To write a chat msg \n you have to \n"
						+ "install the official app \n of Tumblr";
				ShowDialog(title, msg);
			}
			break;

		case 13:
			for (ApplicationInfo packageInfo : packages) {
				String s = packageInfo.packageName;
				if (s.compareTo("com.skype.raider") == 0)
					flag = true;
			}
			if (flag) {
				Intent act3 = new Intent(activity, Skype_activity.class);
				activity.startActivity(act3);
			} else {
				String title = "Skype app is not installed in your phone";
				String msg = "To write a chat msg \n you have to \n"
						+ "install the official app \n of Skype";
				ShowDialog(title, msg);
			}
			break;

		default:
			break;
		}
	}
}

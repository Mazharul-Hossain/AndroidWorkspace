package com.carouseldemo.main;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.carouseldemo.controls.Carousel;
import com.carouseldemo.controls.CarouselAdapter;
import com.carouseldemo.controls.CarouselAdapter.OnItemClickListener;

public class MainActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Carousel carousel = (Carousel) findViewById(R.id.carousel);
		carousel.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(CarouselAdapter<?> parent, View view, int position,
					long id) {

				/*
				 * Toast.makeText(MainActivity.this,
				 * String.format("%s has been clicked %s",
				 * ((CarouselItem)parent.
				 * getChildAt(position)).getName(),position),
				 * Toast.LENGTH_SHORT).show();
				 */
				boolean flag = false;

				Intent openStartingPoint = new Intent(getApplicationContext(), Web.class);

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
							Intent twitterIntent = getPackageManager()
									.getLaunchIntentForPackage(s);
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

					/*
					 * for (ApplicationInfo packageInfo : packages) {
					 * 
					 * String s = packageInfo.packageName;
					 * if(s.compareTo("com.myspace.android.apk")==0) { flag =
					 * true; Intent instagramIntent =
					 * getPackageManager().getLaunchIntentForPackage(s);
					 * startActivity(instagramIntent); }
					 * 
					 * }
					 */
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
							Intent skypeIntent = getPackageManager()
									.getLaunchIntentForPackage(s);
							startActivity(skypeIntent);
						}

					}
					if (!flag) {
						openStartingPoint.putExtra("key", "http://www.skype.com/");
						startActivity(openStartingPoint);
					}

					break;
				// case 14:
				// Intent app = new Intent("example.firstandroid.MENUP");
				// startActivity(app);
				// break;
				}
			}

		});

		/*
		 * carousel.setOnItemSelectedListener(new OnItemSelectedListener(){
		 * 
		 * public void onItemSelected(CarouselAdapter<?> parent, View view, int
		 * position, long id) {
		 * 
		 * final TextView txt = (TextView)(findViewById(R.id.selected_item));
		 * 
		 * switch(position){ case 0: txt.setText(
		 * "The cat (Felis catus), also known as the domestic cat or housecat to distinguish it from other felids and felines, is a small, usually furry, domesticated, carnivorous mammal that is valued by humans for its companionship and for its ability to hunt vermin and household pests. Cats have been associated with humans for at least 9,500 years, and are currently the most popular pet in the world. Owing to their close association with humans, cats are now found almost everywhere in the world."
		 * ); break; case 1: txt.setText(
		 * "The hippopotamus (Hippopotamus amphibius), or hippo, from the ancient Greek for \"river horse\" (ἱπποπόταμος), is a large, mostly herbivorous mammal in sub-Saharan Africa, and one of only two extant species in the family Hippopotamidae (the other is the Pygmy Hippopotamus.) After the elephant, the hippopotamus is the third largest land mammal and the heaviest extant artiodactyl."
		 * ); break; case 2: txt.setText(
		 * "A monkey is a primate, either an Old World monkey or a New World monkey. There are about 260 known living species of monkey. Many are arboreal, although there are species that live primarily on the ground, such as baboons. Monkeys are generally considered to be intelligent. Unlike apes, monkeys usually have tails. Tailless monkeys may be called \"apes\", incorrectly according to modern usage; thus the tailless Barbary macaque is called the \"Barbary ape\"."
		 * ); break; case 3: txt.setText(
		 * "A mouse (plural: mice) is a small mammal belonging to the order of rodents. The best known mouse species is the common house mouse (Mus musculus). It is also a popular pet. In some places, certain kinds of field mice are also common. This rodent is eaten by large birds such as hawks and eagles. They are known to invade homes for food and occasionally shelter."
		 * ); break; case 4: txt.setText(
		 * "The giant panda, or panda (Ailuropoda melanoleuca, literally meaning \"black and white cat-foot\") is a bear native to central-western and south western China.[4] It is easily recognized by its large, distinctive black patches around the eyes, over the ears, and across its round body. Though it belongs to the order Carnivora, the panda's diet is 99% bamboo."
		 * ); break; case 5: txt.setText(
		 * "Rabbits (or, colloquially, bunnies) are small mammals in the family Leporidae of the order Lagomorpha, found in several parts of the world. There are eight different genera in the family classified as rabbits, including the European rabbit (Oryctolagus cuniculus), cottontail rabbits (genus Sylvilagus; 13 species), and the Amami rabbit (Pentalagus furnessi, an endangered species on Amami Ōshima, Japan)"
		 * ); break; }
		 * 
		 * }
		 * 
		 * public void onNothingSelected(CarouselAdapter<?> parent) { }
		 * 
		 * } );
		 */

	}

}

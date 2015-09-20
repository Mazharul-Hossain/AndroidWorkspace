package com.carouseldemo.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		view_animation();
	}

	@Override
	protected void onPause() {
		super.onPause();

		ImageView logo1 = (ImageView) findViewById(R.id.logo_txt);
		logo1.clearAnimation();

		ImageView logo2 = (ImageView) findViewById(R.id.logo);
		logo2.clearAnimation();

	}

	public void view_animation() {
		ImageView logo = (ImageView) findViewById(R.id.logo);
		Animation fade1 = AnimationUtils.loadAnimation(this, R.anim.custom_anim);
		logo.startAnimation(fade1);

		logo = (ImageView) findViewById(R.id.logo_txt);
		Animation fade2 = AnimationUtils.loadAnimation(this, R.anim.fade_in2);
		logo.startAnimation(fade2);

		fade2.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				startActivity(new Intent(SplashScreen.this, MainActivity.class));
				SplashScreen.this.finish();
			}
		});

	}
}

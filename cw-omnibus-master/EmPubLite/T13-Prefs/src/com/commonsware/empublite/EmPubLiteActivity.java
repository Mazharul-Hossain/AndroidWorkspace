package com.commonsware.empublite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class EmPubLiteActivity extends SherlockFragmentActivity {
  private static final String MODEL="model";
  private static final String PREF_LAST_POSITION="lastPosition";
  private static final String PREF_SAVE_LAST_POSITION=
      "saveLastPosition";
  private static final String PREF_KEEP_SCREEN_ON="keepScreenOn";
  private ViewPager pager=null;
  private ContentsAdapter adapter=null;
  private SharedPreferences prefs=null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getSupportFragmentManager().findFragmentByTag(MODEL) == null) {
      getSupportFragmentManager().beginTransaction()
                                 .add(new ModelFragment(), MODEL)
                                 .commit();
    }

    setContentView(R.layout.main);

    pager=(ViewPager)findViewById(R.id.pager);
    getSupportActionBar().setHomeButtonEnabled(true);
  }

  @Override
  public void onPause() {
    if (prefs != null) {
      int position=pager.getCurrentItem();
      prefs.edit().putInt(PREF_LAST_POSITION, position).apply();
    }
    super.onPause();
  }

  @Override
  public void onResume() {
    super.onResume();
    if (prefs != null) {
      pager.setKeepScreenOn(prefs.getBoolean(PREF_KEEP_SCREEN_ON, false));
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    new MenuInflater(this).inflate(R.menu.options, menu);
    return(super.onCreateOptionsMenu(menu));
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        pager.setCurrentItem(0, false);
        return(true);

      case R.id.about:
        Intent i=new Intent(this, SimpleContentActivity.class);

        i.putExtra(SimpleContentActivity.EXTRA_FILE,
                   "file:///android_asset/misc/about.html");
        startActivity(i);

        return(true);

      case R.id.help:
        i=new Intent(this, SimpleContentActivity.class);
        i.putExtra(SimpleContentActivity.EXTRA_FILE,
                   "file:///android_asset/misc/help.html");

        startActivity(i);

        return(true);

      case R.id.settings:
        startActivity(new Intent(this, Preferences.class));
        return(true);
    }

    return(super.onOptionsItemSelected(item));
  }

  void setupPager(SharedPreferences prefs, BookContents contents) {
    this.prefs=prefs;

    adapter=new ContentsAdapter(this, contents);
    pager.setAdapter(adapter);

    findViewById(R.id.progressBar1).setVisibility(View.GONE);
    findViewById(R.id.pager).setVisibility(View.VISIBLE);

    if (prefs.getBoolean(PREF_SAVE_LAST_POSITION, false)) {
      pager.setCurrentItem(prefs.getInt(PREF_LAST_POSITION, 0));
    }

    pager.setKeepScreenOn(prefs.getBoolean(PREF_KEEP_SCREEN_ON, false));
  }
}

package info.tekguc.umut.googlemapsmapsandroidv2;

import android.os.Bundle;
import android.view.Menu;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends android.support.v4.app.FragmentActivity {

	GoogleMap mMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/*
		 * mMap = ((MapFragment)
		 * getFragmentManager().findFragmentById(R.id.map)) .getMap();
		 */

		mMap = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();

		// mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

		new LatLng(23.21843892856462, 90.41662287712097);

		// @SuppressWarnings("unused")
		// Marker ciu = mMap.addMarker(new MarkerOptions().position(CIU).title(
		// "My Office"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

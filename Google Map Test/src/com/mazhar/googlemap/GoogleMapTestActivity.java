package com.mazhar.googlemap;

import android.app.FragmentTransaction;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapTestActivity extends FragmentActivity implements
		LocationListener, LocationSource {

	private GoogleMap mMap;
	MapFragment mMapFragment;
	GoogleMapOptions options;

	private OnLocationChangedListener mListener;
	private LocationManager locationManager;

	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.basic_map);

		if (GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getApplicationContext()) == 1) {

			locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

			if (locationManager != null) {
				boolean gpsIsEnabled = locationManager
						.isProviderEnabled(LocationManager.GPS_PROVIDER);
				boolean networkIsEnabled = locationManager
						.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

				if (gpsIsEnabled) {
					locationManager.requestLocationUpdates(
							LocationManager.GPS_PROVIDER, 5000L, 10F, this);
				} else if (networkIsEnabled) {
					locationManager.requestLocationUpdates(
							LocationManager.NETWORK_PROVIDER, 5000L, 10F, this);
				} else {
					// Show an error dialog that GPS is disabled.
				}
			} else {
				// Show a generic error dialog since LocationManager is null for
				// some reason
			}

			mMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.mapviewtest)).getMap();

			mMapFragment = MapFragment.newInstance();
			FragmentTransaction fragmentTransaction = getFragmentManager()
					.beginTransaction();
			fragmentTransaction.add(R.id.mapviewtest, mMapFragment);
			fragmentTransaction.commit();

			// Sets the map type to be "hybrid"
			mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

			options = new GoogleMapOptions();

			options.mapType(GoogleMap.MAP_TYPE_SATELLITE).compassEnabled(false)
					.rotateGesturesEnabled(false).tiltGesturesEnabled(false);

			mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0))
					.title("Marker"));
			mMap.setMyLocationEnabled(true);

			setUpMapIfNeeded();
		}
	}

	@Override
	public void onPause() {
		if (locationManager != null) {
			locationManager.removeUpdates(this);
		}

		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();

		setUpMapIfNeeded();

		if (locationManager != null) {
			mMap.setMyLocationEnabled(true);
		}
	}

	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the
		// map.
		if (mMap == null) {
			// Try to obtain the map from the SupportMapFragment.
			mMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.mapviewtest)).getMap();
			// Check if we were successful in obtaining the map.

			if (mMap != null) {
				setUpMap();
			}

			// This is how you register the LocationSource
			// mMap.setLocationSource(this);
		}
	}

	private void setUpMap() {
		mMap.setMyLocationEnabled(true);
	}

	@Override
	public void activate(OnLocationChangedListener listener) {
		mListener = listener;

	}

	@Override
	public void deactivate() {
		mListener = null;

	}

	@Override
	public void onLocationChanged(Location location) {
		if (mListener != null) {
			mListener.onLocationChanged(location);

			LatLngBounds bounds = this.mMap.getProjection().getVisibleRegion().latLngBounds;

			if (!bounds.contains(new LatLng(location.getLatitude(), location
					.getLongitude()))) {
				// Move the camera to the user's location if they are
				// off-screen!
				mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(
						location.getLatitude(), location.getLongitude())));
			}
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		Toast.makeText(this, "provider disabled", Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onProviderEnabled(String provider) {
		Toast.makeText(this, "provider enabled", Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		Toast.makeText(this, "status changed", Toast.LENGTH_SHORT).show();

	}
}
package com.example.googlemapsetcenterexample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity {

	private GoogleMap mmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 구글맵을 사용하기 위한 코드
		GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String provider = locationManager.getBestProvider(criteria, true);

		if (provider == null) { // 위치정보 설정이 안되어 있으면 설정하는 엑티비티로 이동합니다
			new AlertDialog.Builder(this)
					.setTitle("위치서비스 동의")
					.setNeutralButton("이동",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									startActivityForResult(
											new Intent(
													android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS),
											0);
								}
							})
					.setOnCancelListener(
							new DialogInterface.OnCancelListener() {
								@Override
								public void onCancel(DialogInterface dialog) {
									finish();
								}
							}).show();
		} else { // 지도를 설정합니다
			setUpMapIfNeeded();
		}
	}

	private void setUpMapIfNeeded() {
		if (mmap == null) {
			mmap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.mapview)).getMap();
			// 지도를 클릭하면 그 위치를 지도 중심으로 잡습니다.
			mmap.setOnMapClickListener(new OnMapClickListener() {

				@Override
				public void onMapClick(LatLng arg0) {
					mmap.moveCamera(CameraUpdateFactory.newLatLng(arg0));
				}
			});
			if (mmap != null) {
				setUpMap();
			}
		}
	}

	/**
	 * 지도 초기화
	 */
	private void setUpMap() {
		mmap.getUiSettings().setZoomControlsEnabled(false);
		mmap.getUiSettings().setScrollGesturesEnabled(false);
		// 캐나다 토론토
		double lat = 43.663401;
		double lng = -79.384568;
		// Create a LatLng object for the current location
		LatLng latLng = new LatLng(lat, lng);

		// Show the current location in Google Map
		mmap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
		mmap.animateCamera(CameraUpdateFactory.zoomTo(13));
		mmap.addMarker(new MarkerOptions().position(latLng));

	}

}

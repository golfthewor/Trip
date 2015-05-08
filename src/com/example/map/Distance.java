package com.example.map;

import java.util.ArrayList;

import org.w3c.dom.Document;

import com.example.trib.Detail;
import com.example.trib.R;
import com.example.trib.Result;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Distance extends FragmentActivity {

	GMapV2Direction md;
	GoogleMap mMap;
	LocationManager lm;
	double lat, lng;
	Intent intentNext;
	Intent intent_no;

	Button btnNext;

	private ArrayList<Double> la = Map.la;
	private ArrayList<Double> ln = Map.ln;

	String mode = "walking";
	LatLng startPosition;
	LatLng endPosition;
	
	int distance_value;
	String distance_text;
	String duration_text;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.distance);
		
		for (int i = 0; i < la.size(); i++) {
			Log.d("Log", "lat: " + la.get(i) + " lng: " + ln.get(i));
			
			startPosition = new LatLng(la.get(0), ln.get(0));
			endPosition = new LatLng(la.get(1), ln.get(1));
		}

		mMap = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map_result)).getMap();

		generate();
		new ProgressTask().execute();

		
		btnNext = (Button) findViewById(R.id.btnNext);
		btnNext.bringToFront();
		btnNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Distance.this);
				alertDialogBuilder.setCancelable(true)
						.setTitle("Select!")
						.setIcon(R.drawable.scooter)
						.setMessage("Would you like to travel by private vehicle or not?")
						.setCancelable(true)
						.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,int id) {
										// Go to vehical page.
										Log.d("Log", "Distance value: "+distance_value);
										Log.d("Log", "Distance text: "+distance_text);
										Log.d("Log", "Duration text: "+duration_text);
										
										intentNext = new Intent(
												getApplicationContext(),
												Detail.class);
										intentNext.putExtra("distance_value", distance_value);
										intentNext.putExtra("distance_text", distance_text);
										intentNext.putExtra("duration_text", duration_text);
										startActivity(intentNext);
										
									}
								})
						.setNegativeButton("No",new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,int id) {
										// Go to result page.
										intent_no = new Intent(
												getApplicationContext(),
												Result.class);
										intent_no.putExtra("distance", distance_text);
										startActivity(intent_no);
									}
								});
				
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();
				
				

			}
		});
	}

	public void generate() {
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		md = new GMapV2Direction();
		mMap = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map_result)).getMap();


		mMap.addMarker(new MarkerOptions().position(startPosition));
		mMap.addMarker(new MarkerOptions().position(endPosition));

		Document doc = md.getDocument(startPosition, endPosition, mode);

		@SuppressWarnings("unchecked")
		ArrayList<LatLng> directionPoint = md.getDirection(doc);
		PolylineOptions rectLine = new PolylineOptions().width(8).color(
				Color.RED);

		for (int i = 0; i < directionPoint.size(); i++) {
			rectLine.add(directionPoint.get(i));
		}

		distance_value = md.getDistanceValue(doc);
		distance_text = md.getDistanceText(doc);
		duration_text = md.getDurationText(doc);
		
		mMap.addPolyline(rectLine);
	}

	LocationListener listener = new LocationListener() {
		public void onLocationChanged(Location loc) {
			LatLng coordinate = new LatLng(loc.getLatitude(),
					loc.getLongitude());
			lat = loc.getLatitude();
			lng = loc.getLongitude();

			mMap.animateCamera(CameraUpdateFactory
					.newLatLngZoom(coordinate, 14));

		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onProviderDisabled(String provider) {
		}
	};

	public void onResume() {
		super.onResume();

		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		boolean isNetwork = lm
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		boolean isGPS = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

		if (isNetwork) {
			lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000,
					10, listener);
			Location loc = lm
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			if (loc != null) {
				lat = loc.getLatitude();
				lng = loc.getLongitude();
			}
		}

		if (isGPS) {
			lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10,
					listener);
			Location loc = lm
					.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (loc != null) {
				lat = loc.getLatitude();
				lng = loc.getLongitude();
			}
		}
	}

	public void onPause() {
		super.onPause();
		lm.removeUpdates(listener);
	}

	// --------Wait------------------------------------

	private class ProgressTask extends AsyncTask<String, Void, Boolean> {
		private ProgressDialog dialog;

		protected void onPreExecute() {
			dialog = ProgressDialog.show(Distance.this, "", "Loading...");
		}

		@Override
		protected void onPostExecute(final Boolean success) {

			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			if (success) {
				Log.d("Log", "Complete wait");
			} else {
			}
		}

		@Override
		protected Boolean doInBackground(final String... args) {

			try {
				Thread.sleep(8000);
			} catch (Exception e) {
				return false;
			}

			return true;

		}

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main,menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int id = item.getItemId();
		if (id == R.id.action_menu) {
			Intent intent = new Intent(getApplicationContext(), com.example.trib.MenuPage.class);
			startActivity(intent);
		}else if (id == R.id.action_plan) {
			Intent intent = new Intent(getApplicationContext(), com.example.map.Map.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onBackPressed() {
		Intent intent_back = new Intent(getApplicationContext(),com.example.map.Map.class);
		startActivity(intent_back);
	
	}
}

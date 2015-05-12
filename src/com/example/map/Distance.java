package com.example.map;

import java.text.DecimalFormat;
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
	
	int distance_value;
	String distance_text;
	String duration_text;
	
	LatLng position1 = null;
	LatLng position2 = null;
	LatLng position3 = null;
	LatLng position4 = null;
	
	Document doc12 = null;
	Document doc13 = null;
	Document doc23 = null;
	Document doc32 = null;
	Document doc14 = null;
	Document doc24 = null;
	Document doc34 = null;
	Document doc42 = null;
	Document doc43 = null;
	
	String pattern = "##0.00";
	DecimalFormat df = new DecimalFormat(pattern);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.distance);
		
		Log.d("Log",la.size()+" "+ln.size());
		
		position1 = new LatLng(la.get(0), ln.get(0));
		Log.d("Log","position1 created");
		position2 = new LatLng(la.get(1), ln.get(1));
		Log.d("Log","position2 created");
		if(la.size() >= 3){
			position3 = new LatLng(la.get(2), ln.get(2));
			Log.d("Log","position3 created");
		}
		if(la.size() >= 4){
			position4 = new LatLng(la.get(3), ln.get(3));
			Log.d("Log","position4 created");
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

	@SuppressWarnings("unchecked")
	public void generate() {
		
		double dt12 = 0;
		double dt13 = 0;
		double dt23 = 0;
		double dt32 = 0;
		double dt14 = 0;
		double dt24 = 0;
		double dt34 = 0;
		double dt42 = 0;
		double dt43 = 0;
		
		int dr12 = 0;
		int dr13 = 0;
		int dr23 = 0;
		int dr32 = 0;
		int dr14 = 0;
		int dr24 = 0;
		int dr34 = 0;
		int dr42 = 0;
		int dr43 = 0;
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		md = new GMapV2Direction();
		mMap = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map_result)).getMap();
		
		//----Add mark-----
				mMap.addMarker(new MarkerOptions().position(position1));
				mMap.addMarker(new MarkerOptions().position(position2));
				if(position3 != null){
					mMap.addMarker(new MarkerOptions().position(position3));
				}
				if(position4 != null){
					mMap.addMarker(new MarkerOptions().position(position4));
				}
				
				//----Gen direction-----
				if(la.size() == 2){
					doc12 = md.getDocument(position1, position2, mode);
					ArrayList<LatLng> directionPoint1 = md.getDirection(doc12);
					PolylineOptions rectLine1 = new PolylineOptions().width(8).color(Color.RED);
					for (int i = 0; i < directionPoint1.size(); i++) {
						rectLine1.add(directionPoint1.get(i));
					}	
					mMap.addPolyline(rectLine1);
					dt12 = md.getDistanceValue(doc12);
					dr12 = md.getDurationValue(doc12);
					
					String dt_text = String.valueOf(df.format(dt12/1000));
					//String dr_text = String.valueOf(df.format(dr12/60));
					
					String startTime = "00:00";
					int minutes = dr12/60;
					int h = minutes / 60 + Integer.valueOf(startTime.substring(0,1));
					int m = minutes % 60 + Integer.valueOf(startTime.substring(3,4));
					String dr_text = h+":"+m;
					
					distance_value = (int) dt12;
					distance_text = dt_text;
					duration_text = dr_text;
					Log.d("Log", "Distance value: "+dt12);
					Log.d("Log", "Distance text: "+dt_text+" km");
					Log.d("Log", "Duration text: "+dr_text+" hr");
					
					Log.d("Log", "Distance: 1-2 ");
					
				}else if(la.size() == 3){
					doc12 = md.getDocument(position1, position2, mode);
					doc13 = md.getDocument(position1, position3, mode);
					doc23 = md.getDocument(position2, position3, mode);
					doc32 = md.getDocument(position3, position2, mode);
					
					dt12 = md.getDistanceValue(doc12);
					dt13 = md.getDistanceValue(doc13);
					dt23 = md.getDistanceValue(doc23);
					dt32 = md.getDistanceValue(doc32);
					
					dr12 = md.getDurationValue(doc12);
					dr13 = md.getDurationValue(doc13);
					dr23 = md.getDurationValue(doc23);
					dr32 = md.getDurationValue(doc32);
					
					if(dt12 <= dt13){
						ArrayList<LatLng> directionPoint1 = md.getDirection(doc12);
						PolylineOptions rectLine1 = new PolylineOptions().width(8).color(Color.RED);
						for (int i = 0; i < directionPoint1.size(); i++) {
							rectLine1.add(directionPoint1.get(i));
						}	
						mMap.addPolyline(rectLine1);
						
						ArrayList<LatLng> directionPoint2 = md.getDirection(doc23);
						PolylineOptions rectLine2 = new PolylineOptions().width(8).color(Color.GREEN);
						for (int i = 0; i < directionPoint2.size(); i++) {
							rectLine2.add(directionPoint2.get(i));
						}	
						mMap.addPolyline(rectLine2);
						
						String dt_text = String.valueOf(df.format((dt12+dt23)/1000));
						//String dr_text = String.valueOf(df.format(((dr12+dr23)/60)/60));
						
						String startTime = "00:00";
						int minutes = (dr12+dr23)/60;
						int h = minutes / 60 + Integer.valueOf(startTime.substring(0,1));
						int m = minutes % 60 + Integer.valueOf(startTime.substring(3,4));
						String dr_text = h+":"+m;
						
						distance_value = (int) (dt12+dt23);
						distance_text = dt_text;
						duration_text = dr_text;
						Log.d("Log", "Distance value: "+dt12+dt23);
						Log.d("Log", "Distance text: "+dt_text+" km");
						Log.d("Log", "Duration text: "+dr_text+" hr");
						
						Log.d("Log", "Distance: 1-2-3 ");
						
						
					}else{
						ArrayList<LatLng> directionPoint1 = md.getDirection(doc13);
						PolylineOptions rectLine1 = new PolylineOptions().width(8).color(Color.RED);
						for (int i = 0; i < directionPoint1.size(); i++) {
							rectLine1.add(directionPoint1.get(i));
						}	
						mMap.addPolyline(rectLine1);
						
						ArrayList<LatLng> directionPoint2 = md.getDirection(doc32);
						PolylineOptions rectLine2 = new PolylineOptions().width(8).color(Color.GREEN);
						for (int i = 0; i < directionPoint2.size(); i++) {
							rectLine2.add(directionPoint2.get(i));
						}	
						mMap.addPolyline(rectLine2);
						
						String dt_text = String.valueOf(df.format((dt13+dt32)/1000));
						//String dr_text = String.valueOf(df.format(((dr13+dr32)/60)/60));
						
						String startTime = "00:00";
						int minutes = (dr13+dr32)/60;
						int h = minutes / 60 + Integer.valueOf(startTime.substring(0,1));
						int m = minutes % 60 + Integer.valueOf(startTime.substring(3,4));
						String dr_text = h+":"+m;
						
						distance_value = (int) (dt13+dt32);
						distance_text = dt_text;
						duration_text = dr_text;
						Log.d("Log", "Distance value: "+dt13+dt32);
						Log.d("Log", "Distance text: "+dt_text+" km");
						Log.d("Log", "Duration text: "+dr_text+" hr");
						
						Log.d("Log", "Distance: 1-3-2 ");
						
					}
					
				}else if(la.size() == 4){
					doc12 = md.getDocument(position1, position2, mode);
					doc13 = md.getDocument(position1, position3, mode);
					doc14 = md.getDocument(position1, position4, mode);
					doc23 = md.getDocument(position2, position3, mode);
					doc24 = md.getDocument(position2, position4, mode);
					doc32 = md.getDocument(position3, position2, mode);
					doc34 = md.getDocument(position3, position4, mode);
					doc42 = md.getDocument(position4, position2, mode);
					doc43 = md.getDocument(position4, position3, mode);
					
					dt12 = md.getDistanceValue(doc12);
					dt13 = md.getDistanceValue(doc13);
					dt14 = md.getDistanceValue(doc14);
					dt23 = md.getDistanceValue(doc23);
					dt24 = md.getDistanceValue(doc24);
					dt32 = md.getDistanceValue(doc32);
					dt34 = md.getDistanceValue(doc34);
					dt42 = md.getDistanceValue(doc42);
					dt43 = md.getDistanceValue(doc43);
					
					dr12 = md.getDurationValue(doc12);
					dr13 = md.getDurationValue(doc13);
					dr14 = md.getDurationValue(doc14);
					dr23 = md.getDurationValue(doc23);
					dr24 = md.getDurationValue(doc24);
					dr32 = md.getDurationValue(doc32);
					dr34 = md.getDurationValue(doc34);
					dr42 = md.getDurationValue(doc42);
					dr43 = md.getDurationValue(doc43);
					
					if(dt12 <= dt13 && dt12 <= dt14){
						ArrayList<LatLng> directionPoint1 = md.getDirection(doc12);
						PolylineOptions rectLine1 = new PolylineOptions().width(8).color(Color.RED);
						for (int i = 0; i < directionPoint1.size(); i++) {
							rectLine1.add(directionPoint1.get(i));
						}	
						mMap.addPolyline(rectLine1);
						
						if(dt23 <= dt24){
							ArrayList<LatLng> directionPoint2 = md.getDirection(doc23);
							PolylineOptions rectLine2 = new PolylineOptions().width(8).color(Color.GREEN);
							for (int i = 0; i < directionPoint2.size(); i++) {
								rectLine2.add(directionPoint2.get(i));
							}	
							mMap.addPolyline(rectLine2);
							
							ArrayList<LatLng> directionPoint3 = md.getDirection(doc34);
							PolylineOptions rectLine3 = new PolylineOptions().width(8).color(Color.BLUE);
							for (int i = 0; i < directionPoint3.size(); i++) {
								rectLine3.add(directionPoint3.get(i));
							}	
							mMap.addPolyline(rectLine3);
							
							String dt_text = String.valueOf(df.format((dt12+dt23+dt34)/1000));
							//String dr_text = String.valueOf(df.format(((dr12+dr23+dr34)/60)/60));
							
							String startTime = "00:00";
							int minutes = (dr12+dr23+dr34)/60;
							int h = minutes / 60 + Integer.valueOf(startTime.substring(0,1));
							int m = minutes % 60 + Integer.valueOf(startTime.substring(3,4));
							String dr_text = h+":"+m;
							
							distance_value = (int) (dt12+dt23+dt34);
							distance_text = dt_text;
							duration_text = dr_text;
							Log.d("Log", "Distance value: "+dt12+dt23+dt34);
							Log.d("Log", "Distance text: "+dt_text+" km");
							Log.d("Log", "Duration text: "+dr_text+" hr");
							
							Log.d("Log", "Distance: 1-2-3-4 ");
							
							
						}else{
							ArrayList<LatLng> directionPoint2 = md.getDirection(doc24);
							PolylineOptions rectLine2 = new PolylineOptions().width(8).color(Color.GREEN);
							for (int i = 0; i < directionPoint2.size(); i++) {
								rectLine2.add(directionPoint2.get(i));
							}	
							mMap.addPolyline(rectLine2);
							
							ArrayList<LatLng> directionPoint3 = md.getDirection(doc43);
							PolylineOptions rectLine3 = new PolylineOptions().width(8).color(Color.BLUE);
							for (int i = 0; i < directionPoint3.size(); i++) {
								rectLine3.add(directionPoint3.get(i));
							}	
							mMap.addPolyline(rectLine3);
							
							String dt_text = String.valueOf(df.format((dt12+dt24+dt43)/1000));
							//String dr_text = String.valueOf(df.format(((dr12+dr24+dr43)/60)/60));
							
							String startTime = "00:00";
							int minutes = (dr12+dr24+dr43)/60;
							int h = minutes / 60 + Integer.valueOf(startTime.substring(0,1));
							int m = minutes % 60 + Integer.valueOf(startTime.substring(3,4));
							String dr_text = h+":"+m;
							
							distance_value = (int) (dt12+dt24+dt43);
							distance_text = dt_text;
							duration_text = dr_text;
							Log.d("Log", "Distance value: "+dt12+dt24+dt43);
							Log.d("Log", "Distance text: "+dt_text+" km");
							Log.d("Log", "Duration text: "+dr_text+" hr");
						
							Log.d("Log", "Distance: 1-2-4-3 ");
							
						
						}
						
					}else if(dt13 < dt12 && dt13 <= dt14){
						ArrayList<LatLng> directionPoint1 = md.getDirection(doc13);
						PolylineOptions rectLine1 = new PolylineOptions().width(8).color(Color.RED);
						for (int i = 0; i < directionPoint1.size(); i++) {
							rectLine1.add(directionPoint1.get(i));
						}	
						mMap.addPolyline(rectLine1);
						
						if(dt32 <= dt34){
							ArrayList<LatLng> directionPoint2 = md.getDirection(doc32);
							PolylineOptions rectLine2 = new PolylineOptions().width(8).color(Color.GREEN);
							for (int i = 0; i < directionPoint2.size(); i++) {
								rectLine2.add(directionPoint2.get(i));
							}	
							mMap.addPolyline(rectLine2);
							
							ArrayList<LatLng> directionPoint3 = md.getDirection(doc24);
							PolylineOptions rectLine3 = new PolylineOptions().width(8).color(Color.BLUE);
							for (int i = 0; i < directionPoint3.size(); i++) {
								rectLine3.add(directionPoint3.get(i));
							}	
							mMap.addPolyline(rectLine3);
							
							String dt_text = String.valueOf(df.format((dt13+dt32+dt24)/1000));
							//String dr_text = String.valueOf(df.format(((dr13+dr32+dr24)/60)/60));
							
							String startTime = "00:00";
							int minutes = (dr13+dr32+dr24)/60;
							int h = minutes / 60 + Integer.valueOf(startTime.substring(0,1));
							int m = minutes % 60 + Integer.valueOf(startTime.substring(3,4));
							String dr_text = h+":"+m;
							
							distance_value = (int) (dt13+dt32+dt24);
							distance_text = dt_text;
							duration_text = dr_text;
							Log.d("Log", "Distance value: "+dt13+dt32+dt24);
							Log.d("Log", "Distance text: "+dt_text+" km");
							Log.d("Log", "Duration text: "+dr_text+" hr");
							
							Log.d("Log", "Distance: 1-3-2-4 ");
							
							
						}else{
							ArrayList<LatLng> directionPoint2 = md.getDirection(doc34);
							PolylineOptions rectLine2 = new PolylineOptions().width(8).color(Color.GREEN);
							for (int i = 0; i < directionPoint2.size(); i++) {
								rectLine2.add(directionPoint2.get(i));
							}	
							mMap.addPolyline(rectLine2);
							
							ArrayList<LatLng> directionPoint3 = md.getDirection(doc42);
							PolylineOptions rectLine3 = new PolylineOptions().width(8).color(Color.BLUE);
							for (int i = 0; i < directionPoint3.size(); i++) {
								rectLine3.add(directionPoint3.get(i));
							}	
							mMap.addPolyline(rectLine3);
						
							String dt_text = String.valueOf(df.format((dt13+dt34+dt42)/1000));
							//String dr_text = String.valueOf(df.format(((dr13+dr34+dr42)/60)));
							
							String startTime = "00:00";
							int minutes = (dr13+dr34+dr42)/60;
							int h = minutes / 60 + Integer.valueOf(startTime.substring(0,1));
							int m = minutes % 60 + Integer.valueOf(startTime.substring(3,4));
							String dr_text = h+":"+m;
							
							distance_value = (int) (dt13+dt34+dt42);
							distance_text = dt_text;
							duration_text = dr_text;
							Log.d("Log", "Distance value: "+dt13+dt34+dt42);
							Log.d("Log", "Distance text: "+dt_text+" km");
							Log.d("Log", "Duration text: "+dr_text+" hr");
							
							Log.d("Log", "Distance: 1-3-4-2 ");
							
						
						}
						
					}else if(dt14 < dt12 && dt14 < dt13){
						ArrayList<LatLng> directionPoint1 = md.getDirection(doc14);
						PolylineOptions rectLine1 = new PolylineOptions().width(8).color(Color.RED);
						for (int i = 0; i < directionPoint1.size(); i++) {
							rectLine1.add(directionPoint1.get(i));
						}	
						mMap.addPolyline(rectLine1);
						
						if(dt42 <= dt43){
							ArrayList<LatLng> directionPoint2 = md.getDirection(doc42);
							PolylineOptions rectLine2 = new PolylineOptions().width(8).color(Color.GREEN);
							for (int i = 0; i < directionPoint2.size(); i++) {
								rectLine2.add(directionPoint2.get(i));
							}	
							mMap.addPolyline(rectLine2);
							
							ArrayList<LatLng> directionPoint3 = md.getDirection(doc23);
							PolylineOptions rectLine3 = new PolylineOptions().width(8).color(Color.BLUE);
							for (int i = 0; i < directionPoint3.size(); i++) {
								rectLine3.add(directionPoint3.get(i));
							}	
							mMap.addPolyline(rectLine3);
							
							String dt_text = String.valueOf(df.format((dt14+dt42+dt23)/1000));
							//String dr_text = String.valueOf(df.format(((dr14+dr42+dr23)/60)/60));
							
							String startTime = "00:00";
							int minutes = (dr14+dr42+dr23)/60;
							int h = minutes / 60 + Integer.valueOf(startTime.substring(0,1));
							int m = minutes % 60 + Integer.valueOf(startTime.substring(3,4));
							String dr_text = h+":"+m;
							
							distance_value = (int) (dt14+dt42+dt23);
							distance_text = dt_text;
							duration_text = dr_text;
							Log.d("Log", "Distance value: "+dt14+dt42+dt23);
							Log.d("Log", "Distance text: "+dt_text+" km");
							Log.d("Log", "Duration text: "+dr_text+" hr");
							
							Log.d("Log", "Distance: 1-4-2-3 ");
							
							
						}else{
							ArrayList<LatLng> directionPoint2 = md.getDirection(doc43);
							PolylineOptions rectLine2 = new PolylineOptions().width(8).color(Color.GREEN);
							for (int i = 0; i < directionPoint2.size(); i++) {
								rectLine2.add(directionPoint2.get(i));
							}	
							mMap.addPolyline(rectLine2);
							
							ArrayList<LatLng> directionPoint3 = md.getDirection(doc32);
							PolylineOptions rectLine3 = new PolylineOptions().width(8).color(Color.BLUE);
							for (int i = 0; i < directionPoint3.size(); i++) {
								rectLine3.add(directionPoint3.get(i));
							}	
							mMap.addPolyline(rectLine3);
							
							String dt_text = String.valueOf(df.format((dt14+dt43+dt32)/1000));
							//String dr_text = String.valueOf(df.format(((dr14+dr43+dr32)/60)/60));
							
							String startTime = "00:00";
							int minutes = (dr14+dr43+dr32)/60;
							int h = minutes / 60 + Integer.valueOf(startTime.substring(0,1));
							int m = minutes % 60 + Integer.valueOf(startTime.substring(3,4));
							String dr_text = h+":"+m;
							
							distance_value = (int) (dt14+dt43+dt32);
							distance_text = dt_text;
							duration_text = dr_text;
							Log.d("Log", "Distance value: "+dt14+dt43+dt32);
							Log.d("Log", "Distance text: "+dt_text+" km");
							Log.d("Log", "Duration text: "+dr_text+" hr");
						
							Log.d("Log", "Distance: 1-4-3-2 ");
							
						
						}
						
					}
					
				}		
		
		
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

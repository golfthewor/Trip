package com.example.trib;

import java.text.DecimalFormat;

import com.example.map.Distance;
import com.example.map.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.Menu;

public class Result extends Activity {

	final Context context = this;
	private Button btnConfirm;
	private Intent intent;
	private String gasPrice = "null";
	private String getVehical = "null";
	private String getEngine = "null";
	private String getGas = "null";
	private double average = 0.00;
	private String getDistance;
	private String getDuration;
	
	TextView vehical;
	TextView gas;
	TextView distance;
	TextView price;
	TextView duration;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
		
		vehical = (TextView) findViewById(R.id.vehicalResult);
		gas = (TextView) findViewById(R.id.gasResult);
		distance = (TextView) findViewById(R.id.distanceResult);
		price = (TextView) findViewById(R.id.priceResult);
		duration = (TextView) findViewById(R.id.duration);
		
		getVehical = getIntent().getStringExtra("vehical");
		getEngine = getIntent().getStringExtra("engine");
		getGas = getIntent().getStringExtra("gas");
		
		gasPrice = getIntent().getStringExtra("price");
		average = getIntent().getDoubleExtra("average", 0.00);
		getDistance = getIntent().getStringExtra("distance");
		getDuration = getIntent().getStringExtra("duration");
		
		setText();
		
		btnConfirm = (Button) findViewById(R.id.btnMap);
		btnConfirm.bringToFront();
		btnConfirm.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub			
				intent = new Intent(getApplicationContext(),Distance.class);
				startActivity(intent);
			}
									
		});
		
	}
	
	public void setText(){
		
		DecimalFormat df = new DecimalFormat("###,##0.00");
		  
		df.format(1.010);
		
		if(getVehical == null || getEngine == null || getGas == null){
			vehical.setText("_");
			gas.setText("_");
			distance.setText(getDistance+" km");
			price.setText("_");
			duration.setText("_");
		}else{
			vehical.setText(getVehical+" "+getEngine+"cc");
			gas.setText(getGas+" "+gasPrice+"b/l");
			distance.setText(getDistance+" km");
			price.setText(df.format(average)+" Bath.");
			duration.setText(getDuration+" hr");
		}
	}
	
	public void onBackPressed() {
		Intent intent_back = new Intent(getApplicationContext(),Map.class);
		startActivity(intent_back);
	
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

}

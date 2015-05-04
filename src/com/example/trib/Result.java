package com.example.trib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Result extends Activity {

	final Context context = this;
	private Button btnConfirm;
	private Intent intent;
	private String gasPrice = "null";
	private String getVehical = "null";
	private String getEngine = "null";
	private String getGas = "null";
	private double average = 0.00;
	private double getDistance;
	
	TextView vehical;
	TextView gas;
	TextView distance;
	TextView price;
	TextView tripName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
		
		vehical = (TextView) findViewById(R.id.vehicalResult);
		gas = (TextView) findViewById(R.id.gasResult);
		distance = (TextView) findViewById(R.id.distanceResult);
		price = (TextView) findViewById(R.id.priceResult);
		
		getVehical = getIntent().getStringExtra("vehical");
		getEngine = getIntent().getStringExtra("engine");
		getGas = getIntent().getStringExtra("gas");
		
		gasPrice = getIntent().getStringExtra("price");
		average = getIntent().getDoubleExtra("average", 0.00);
		getDistance = getIntent().getDoubleExtra("distance", 0.00);
		
		setText();
		
		btnConfirm = (Button) findViewById(R.id.btnMap);
		btnConfirm.bringToFront();
		btnConfirm.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub			
				intent = new Intent(getApplicationContext(),Mapping.class);
				startActivity(intent);
			}
									
		});
		
	}
	
	public void setText(){
		
		if(getVehical == null || getEngine == null || getGas == null){
			vehical.setText("_");
			gas.setText("_");
			distance.setText("200km.");
			price.setText("_");
		}else{
			vehical.setText(getVehical+" "+getEngine+"cc");
			gas.setText(getGas+" "+gasPrice+"฿/l");
			distance.setText(getDistance+"km.");
			price.setText(average+" Bath.");
		}
	}
	
	public void onBackPressed() {
		Intent intent_back = new Intent(getApplicationContext(),Vehical.class);
		startActivity(intent_back);
	
	}

}

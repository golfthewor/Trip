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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);

		/* Result page detail */
		//final TextView tripName = (TextView) findViewById(R.id.tripResult);
		final TextView vehical = (TextView) findViewById(R.id.vehicalResult);
		final TextView gas = (TextView) findViewById(R.id.gasResult);
		final TextView distance = (TextView) findViewById(R.id.distanceResult);
		final TextView price = (TextView) findViewById(R.id.priceResult);
		
		String getVehical =  getIntent().getStringExtra("vehical");
		String getEngine = getIntent().getStringExtra("engine");
		String getGas = getIntent().getStringExtra("gas");
		
		if(getVehical == null || getEngine == null || getGas == null){
			vehical.setText("_");
			gas.setText("_");
			distance.setText("200km.");
			price.setText("_");
		}else{
			vehical.setText(getVehical+" "+getEngine+"cc");
			gas.setText(getGas+" gas price");
			distance.setText("200km.");
			price.setText("500 Bath.");
		}
		
		btnConfirm = (Button) findViewById(R.id.btnMap);
		btnConfirm.bringToFront();
		btnConfirm.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub			
				intent = new Intent(getApplicationContext(),Mapping.class);
				startActivity(intent);
			}
									
		});
		
		/* Dialog for get trip name */
	/*	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);

		final EditText input = new EditText(this);

		alertDialogBuilder.setMessage("Please enter trip name.").setView(input)
				.setCancelable(false)
				.setNeutralButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// if this button is clicked, close
						// current activity

						 set result 
						tripName.setText(input.getText());
						vehical.setText(getIntent().getStringExtra("vehical")
								+ " " + getIntent().getStringExtra("engine"));
						gas.setText(getIntent().getStringExtra("gas")
								+ " price");
						distance.setText("200km.");
						price.setText("500 Bath.");

						dialog.cancel();
					}
				});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();*/
	}

}

package com.example.trib;

import android.view.View.OnClickListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class Vehical extends Activity {

	private Spinner spinner2, spinner3;
	private Button btnConfirm;
	private Intent intent_result;

	private String vehical;
	private String engine;
	private String gas;
	
	String listVehical[]={"Motorcycle","Pickup","Saloon"};
	String listEngineMotorcycle[]={"100","110","125","150"};
	String listEnginePickup[]={"2200","2500","3000","3200"};
	String listEngineSaloon[]={"1500","1800","2000","2400"};
	String listGas[]={"Gasoline 91","Gasoline 95","Gasohal 91","Gasohal 95","Gasohal E20","Gasohal E85","Diesel"};

	String vehicalSelect = "Null";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vehical);
		
		vehical();
	}
	
	private void vehical(){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Vehical.this);
		alertDialogBuilder.setCancelable(true)
				.setTitle("Select vehical type")
				.setIcon(R.drawable.scooter)
				.setCancelable(false)
				.setItems(listVehical, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						vehicalSelect = listVehical[which];
						
						TextView settxt = (TextView) findViewById(R.id.txtVehical);		
						settxt.setText(vehicalSelect);
						addListenerOnButton();
						addListenerOnSpinnerItemSelection();
					}
				});
				
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
		
	}

	private void addListenerOnButton() {
		// TODO Auto-generated method stub
		
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		
		if(vehicalSelect.equals("Motorcycle")){
			ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,R.layout.selected_item, listEngineMotorcycle);
			adapter1.setDropDownViewResource(R.layout.dropdown_item);
			spinner2.setAdapter(adapter1);
		}else if(vehicalSelect.equals("Pickup")){
			ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,R.layout.selected_item, listEnginePickup);
			adapter2.setDropDownViewResource(R.layout.dropdown_item);
			spinner2.setAdapter(adapter2);
		}else if(vehicalSelect.equals("Saloon")){
			ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,R.layout.selected_item, listEngineSaloon);
			adapter3.setDropDownViewResource(R.layout.dropdown_item);
			spinner2.setAdapter(adapter3);
		}
		
		spinner3 = (Spinner) findViewById(R.id.spinner3);
		
		ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this,R.layout.selected_item, listGas);
		adapter4.setDropDownViewResource(R.layout.dropdown_item);
		spinner3.setAdapter(adapter4);

		btnConfirm = (Button) findViewById(R.id.btnConfirm);
		btnConfirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				vehical = vehicalSelect; //String.valueOf(spinner1.getSelectedItem());
				engine = String.valueOf(spinner2.getSelectedItem());
				gas = String.valueOf(spinner3.getSelectedItem());

				intent_result = new Intent(getApplicationContext(),
						Result.class);
				intent_result.putExtra("vehical", vehical);
				intent_result.putExtra("engine", engine);
				intent_result.putExtra("gas", gas);

				startActivity(intent_result);

			}
		});
	}

	private void addListenerOnSpinnerItemSelection() {
		// TODO Auto-generated method stub

		spinner2 = (Spinner) findViewById(R.id.spinner2);
		spinner2.setOnItemSelectedListener(new CustomOnItemSelectedListener());

		spinner3 = (Spinner) findViewById(R.id.spinner3);
		spinner3.setOnItemSelectedListener(new CustomOnItemSelectedListener());

	}
	

}

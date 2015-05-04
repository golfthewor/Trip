package com.example.trib;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class Vehical extends Activity {

	private Spinner spinner2, spinner3;
	private Button btnConfirm;
	private Intent intent_value;

	private String vehical;
	private String engine;
	private String gas;
	
	String listVehical[]={"Motorcycle","Pickup","Saloon"};
	String listEngineMotorcycle[]={"100","110","125","150"};
	String listEnginePickup[]={"2200","2500","3000","3200"};
	String listEngineSaloon[]={"1500","1800","2000","2400"};
	
	String vehicalSelect = "Null";
	String Gasoline95 = "Gasoline 95";
	String Diesel = "Diesel";
	String Gasohol91 = "Gasohol 91";
	String GasoholE20 = "Gasohol E20";
	String NGV = "NGV";
	String Gasohol95 = "Gasohol 95";
	String GasoholE85 = "Gasohol E85";
	String HyForceDiesel = "HyForce Diesel";
	
	String listGas[] = {Gasoline95,Gasohol91,Gasohol95,GasoholE20,GasoholE85,
			Diesel, HyForceDiesel, NGV};

	
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
						SpannableString spanString = new SpannableString(vehicalSelect);
						spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
						spanString.setSpan(new ForegroundColorSpan(Color.parseColor("#FFBF00")), 0, spanString.length(), 0);
						settxt.setTextColor(Color.parseColor("#FFBF00"));
						settxt.setText(spanString);
						settxt.setOnClickListener(new View.OnClickListener() {
						    @Override
						    public void onClick(View v) {
						       vehical();
						    }
						});
						addListenerOnButton();
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
				vehical = vehicalSelect; 
				Log.d("Log","Select "+vehical);
				engine = String.valueOf(spinner2.getSelectedItem());
				Log.d("Log","Select "+engine);
				gas = String.valueOf(spinner3.getSelectedItem());
				Log.d("Log","Select "+gas);
				
				intent_value = new Intent(getApplicationContext(),SetValue.class);
				intent_value.putExtra("vehical", vehical);
				intent_value.putExtra("engine", engine);
				intent_value.putExtra("gas", gas);

				startActivity(intent_value);

			}
		});
	}
	

}

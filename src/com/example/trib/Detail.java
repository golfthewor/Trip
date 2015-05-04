package com.example.trib;

import com.example.gasprice.ConnectPtt;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Detail extends Activity {
	
	private Button btnConfirm, btnChangeVehical, btnChangeEngine, btnChangeGas;
	private Intent intent_value;

	private String gas;
	private String gasPrice;
	
	private String listVehical[]={"Motorcycle","Pickup","Saloon"};
	private String listEngineMotorcycle[]={"100","110","125","150"};
	private String listEnginePickup[]={"2200","2500","3000","3200"};
	private String listEngineSaloon[]={"1500","1800","2000","2400"};
	
	private String vehicalSelect = "Null";
	private String engineSelect = "Null";
	private String gasSelect = "Null";
	
	private String PriceGasoline95 = "0.00";
	private String PriceDiesel = "0.00";
	private String PriceGasohol91 = "0.00";
	private String PriceGasoholE20 = "0.00";
	private String PriceNGV = "0.00";
	private String PriceGasohol95 = "0.00";
	private String PriceGasoholE85 = "0.00";
	private String PriceHyForceDiesel = "0.00";
	
	private String Gasoline95 = "Gasoline 95";
	private String Diesel = "Diesel";
	private String Gasohol91 = "Gasohol 91";
	private String GasoholE20 = "Gasohol E20";
	private String NGV = "NGV";
	private String Gasohol95 = "Gasohol 95";
	private String GasoholE85 = "Gasohol E85";
	private String HyForceDiesel = "HyForce Diesel";

	ConnectPtt sv;
	
	String listGas[] = {Gasoline95,Gasohol91,Gasohol95,GasoholE20,GasoholE85,
			Diesel, HyForceDiesel, NGV};
	
	TextView setVehical;
	TextView setEngine;
	TextView setGas;
	TextView setPrice;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);

		sv = new ConnectPtt();
		detail();
		change();
		
		btnConfirm = (Button) findViewById(R.id.btnConfirm);
		btnConfirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(vehicalSelect != "Null" && engineSelect != "Null" && gasSelect != "Null" ){		
					intent_value = new Intent(getApplicationContext(),SetValue.class);
					intent_value.putExtra("vehical", vehicalSelect);
					intent_value.putExtra("engine", engineSelect);
					intent_value.putExtra("gas", gasSelect);
					intent_value.putExtra("gasPrice", gasPrice);
	
					startActivity(intent_value);
				}else{
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Detail.this);
					alertDialogBuilder.setCancelable(true)
					.setTitle("Warning!")
					.setIcon(R.drawable.scooter)
					.setMessage("Data incomplete.")
					.setPositiveButton("OK",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
											
							}
						
					});
					AlertDialog alertDialog = alertDialogBuilder.create();
					alertDialog.show();
				}
			}
		});
	}
	
	private void detail(){
		Log.d("Log","detail");
		setVehical = (TextView) findViewById(R.id.txtVehical);	
		SpannableString spanVehical = new SpannableString("Select!");
		spanVehical.setSpan(new ForegroundColorSpan(Color.parseColor("#FFBF00")), 0, spanVehical.length(), 0);
		setVehical.setTextColor(Color.parseColor("#FFBF00"));
		setVehical.setText(spanVehical);
		
		setEngine = (TextView) findViewById(R.id.txtEngine);	
		SpannableString spanEngine = new SpannableString("Select!");
		spanEngine.setSpan(new ForegroundColorSpan(Color.parseColor("#FFBF00")), 0, spanEngine.length(), 0);
		setEngine.setTextColor(Color.parseColor("#FFBF00"));
		setEngine.setText(spanEngine);
		
		setGas = (TextView) findViewById(R.id.txtGas);	
		SpannableString spanGas = new SpannableString("Select!");
		spanGas.setSpan(new ForegroundColorSpan(Color.parseColor("#FFBF00")), 0, spanGas.length(), 0);
		setGas.setTextColor(Color.parseColor("#FFBF00"));
		setGas.setText(spanGas);
		
		setPrice = (TextView) findViewById(R.id.txtGasPrice);	
		SpannableString spanPrice = new SpannableString("0.00");
		spanPrice.setSpan(new ForegroundColorSpan(Color.parseColor("#FFBF00")), 0, spanPrice.length(), 0);
		setPrice.setTextColor(Color.parseColor("#FFBF00"));
		setPrice.setText(spanPrice);
		
	}
	
	private void change(){
		Log.d("Log","change");
		/*btnChangeVehical*/
		
		btnChangeVehical = (Button) findViewById(R.id.btnChangeVehical);
		btnChangeVehical.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Detail.this);
				alertDialogBuilder.setCancelable(true)
						.setTitle("Select vehical type")
						.setIcon(R.drawable.scooter)
						.setItems(listVehical, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								vehicalSelect = listVehical[which];
								setVehical.setText(vehicalSelect);
								
							}
						});
						
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
				
			}
		});
		
		/*btnChangeEngine*/
		
		btnChangeEngine = (Button) findViewById(R.id.btnChangeEngine);
		btnChangeEngine.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Detail.this);
				
				if(vehicalSelect.equals("Motorcycle")){
					
					alertDialogBuilder.setCancelable(true)
							.setTitle("Select engine size")
							.setIcon(R.drawable.scooter)
							.setItems(listEngineMotorcycle, new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									engineSelect = listEngineMotorcycle[which];
									setEngine.setText(engineSelect);
									
								}
							});					
					AlertDialog alertDialog = alertDialogBuilder.create();
					alertDialog.show();
					
				}else if(vehicalSelect.equals("Pickup")){
					
					alertDialogBuilder.setCancelable(true)
					.setTitle("Select engine size")
					.setIcon(R.drawable.scooter)
					.setItems(listEnginePickup, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							engineSelect = listEnginePickup[which];
							setEngine.setText(engineSelect);
							
						}
					});	
					AlertDialog alertDialog = alertDialogBuilder.create();
					alertDialog.show();
					
				}else if(vehicalSelect.equals("Saloon")){
					
					alertDialogBuilder.setCancelable(true)
					.setTitle("Select engine size")
					.setIcon(R.drawable.scooter)
					.setItems(listEngineSaloon, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							engineSelect = listEngineSaloon[which];
							setEngine.setText(engineSelect);
							
						}
					});
					AlertDialog alertDialog = alertDialogBuilder.create();
					alertDialog.show();
					
				}else{
					
					alertDialogBuilder.setCancelable(true)
					.setTitle("Select engine size")
					.setIcon(R.drawable.scooter)
					.setMessage("Please select vehical type.")
					.setPositiveButton("OK",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
											
							}
						
					});
					AlertDialog alertDialog = alertDialogBuilder.create();
					alertDialog.show();
					
				}
			}
		});
		
		/*btnChangeGas*/
		
		btnChangeGas = (Button) findViewById(R.id.btnChangeGas);
		btnChangeGas.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Detail.this);
				alertDialogBuilder.setCancelable(true)
						.setTitle("Select gas")
						.setIcon(R.drawable.scooter)
						.setItems(listGas, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								
								gasSelect = listGas[which];
								gas = gasSelect;
								setPrice();
								checkGas();
								Log.d("Log",gasPrice);
								setGas.setText(gasSelect);							
								setPrice.setText(gasPrice+" Baht.");
								
							}
						});
						
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
				
			}
		});
	}
	
	private void setPrice(){
		Log.d("Log","setPrice");
		PriceGasoline95 = sv.getGasoline95();			
		PriceDiesel = sv.getDiesel();		
		PriceGasohol91 = sv.getGasohol91();			
		PriceGasoholE20 = sv.getGasoholE20();			
		PriceNGV = sv.getNGV();		
		PriceGasohol95 = sv.getGasohol95();			
		PriceGasoholE85	= sv.getGasoholE85();		
		PriceHyForceDiesel = sv.getHyForcePremiumDiesel();
			
	}
	
	private void checkGas(){
		
		  if(gas.equals("Gasoline 95")){
			  gasPrice = PriceGasoline95;
	      }else if(gas.equals("Diesel")){
	    	  gasPrice = PriceDiesel;
	      }else if(gas.equals("Gasohol 91")){
	    	  gasPrice = PriceGasohol91;;
	      }else if(gas.equals("Gasohol E20")){
	    	  gasPrice = PriceGasoholE20;
	      }else if(gas.equals("NGV")){
	    	  gasPrice = PriceNGV;
	      }else if(gas.equals("Gasohol 95")){
	    	  gasPrice = PriceGasohol95;
	      }else if(gas.equals("Gasohol E85")){
	    	  gasPrice = PriceGasoholE85;
	      }else if(gas.equals("HyForce Diesel")){
	    	  gasPrice = PriceHyForceDiesel;
	      }
	}
	
}

package com.example.trib;

import com.example.calculate.Calculate;
import com.example.gasprice.SetPrice;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.app.AlertDialog;

public class SetValue extends Activity {
	
	private Intent intent_result;
	
	private String getVehical;
	private String getEngine;
	private String getGas;
	private String price;
	private double average;
	private double distance = 200;
	private SetPrice sp;
	private Calculate cal;
	
	String editText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setvalue);
		
		getVehical = getIntent().getStringExtra("vehical");
		getEngine = getIntent().getStringExtra("engine");
		getGas = getIntent().getStringExtra("gas");
		      
		    getGasPrice();	  		   
			dialog();
		
	}
	
	public void dialog(){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		final EditText input = new EditText(this);
		
		input.setText(price);
		
		alertDialogBuilder.setView(input)
		.setTitle("Gas price.")
		.setIcon(R.drawable.scooter)
		.setCancelable(false)
		.setNeutralButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				
				editText = input.getText().toString();
				
				getCalculate();
				
				intent_result = new Intent(getApplicationContext(),Result.class);
				intent_result.putExtra("vehical", getVehical);
				intent_result.putExtra("engine", getEngine);
				intent_result.putExtra("gas", getGas);
				
				intent_result.putExtra("price", editText);
				intent_result.putExtra("distance", distance);
				intent_result.putExtra("average", average);
				
				startActivity(intent_result);
				
			}
		});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}
	
	public void getGasPrice(){
		
		try{
			sp = new SetPrice();
			sp.FindPrice(getGas);
			price = sp.getPrice();
			
			Log.d("Log","SetValue Price "+price);
		}catch(Exception e){
			e.printStackTrace();
			Log.d("Log","Fail get gas price;");
		}

	}
	
	public void getCalculate(){
		
		try{
			cal = new Calculate();
			cal.setValue(editText, getEngine);
			average = cal.cal(distance);
		}catch(Exception e){
			e.printStackTrace();
			Log.d("Log","Fail calculate;");
		}
	}
	
}

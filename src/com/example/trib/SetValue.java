package com.example.trib;

import com.example.calculate.Calculate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class SetValue extends Activity {

	private Intent intent_result;

	private String getVehical;
	private String getEngine;
	private String getGas;
	private String getPrice;

	private double average;
	private double distance = 200;
	private Calculate cal;

	final Context context = this;

	String editText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setvalue);

		getVehical = getIntent().getStringExtra("vehical");
		getEngine = getIntent().getStringExtra("engine");
		getGas = getIntent().getStringExtra("gas");
		getPrice = getIntent().getStringExtra("gasPrice");

		dialog();

	}

	public void dialog() {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		final EditText input = new EditText(this);

		input.setText(getPrice);
		input.setTextSize(25);
		alertDialogBuilder.setView(input).setTitle("Gas price")
				.setIcon(R.drawable.scooter).setCancelable(false)
				.setNeutralButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

						editText = input.getText().toString();
						
						try {
						   double num = Double.parseDouble(editText);
						   Log.i("Log",num+" is a number");
						   
						   getCalculate();

							intent_result = new Intent(getApplicationContext(),
									Result.class);
							intent_result.putExtra("vehical", getVehical);
							intent_result.putExtra("engine", getEngine);
							intent_result.putExtra("gas", getGas);

							intent_result.putExtra("price", editText);
							intent_result.putExtra("distance", distance);
							intent_result.putExtra("average", average);

							startActivity(intent_result);
							
						} catch (NumberFormatException e) {
						   Log.i("Log",editText+"is not a number");
						   Toast.makeText(context,editText+" is not a number", Toast.LENGTH_SHORT).show();
						   dialog();
						}
						

					}
				});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();

	}

	public void getCalculate() {

		try {
			cal = new Calculate();
			cal.setValue(editText, getEngine);
			average = cal.cal(distance);
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("Log", "Fail calculate;");
		}
	}

}

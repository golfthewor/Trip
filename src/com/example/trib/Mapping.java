package com.example.trib;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Mapping extends Activity {

	private Button btnConfirm;
	private Intent intent_yes;
	private Intent intent_no;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapping);

		btnConfirm = (Button) findViewById(R.id.btn_confirm);
		btnConfirm.bringToFront();
		btnConfirm.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Mapping.this);
				alertDialogBuilder.setCancelable(true)
						.setTitle("Select!")
						.setIcon(R.drawable.scooter)
						.setMessage("Would you like to travel by private vehicle or not?")
						.setCancelable(true)
						.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,int id) {
										// Go to vehical page.
										
										intent_yes = new Intent(
												getApplicationContext(),
												Detail.class);
										startActivity(intent_yes);
									}
								})
						.setNegativeButton("No",new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,int id) {
										// Go to result page.
										intent_no = new Intent(
												getApplicationContext(),
												Result.class);
										//intent_no.putExtra("status", "no");
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
			Intent intent = new Intent(getApplicationContext(), com.example.trib.Mapping.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

}

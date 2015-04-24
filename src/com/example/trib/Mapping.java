package com.example.trib;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
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
												Vehical.class);
										startActivity(intent_yes);
									}
								})
						.setNegativeButton("No",new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,int id) {
										// Go to result page.
										intent_no = new Intent(
												getApplicationContext(),
												Result.class);
										
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

	public void onBackPressed() {
        
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("Exit!");
		dialog.setIcon(R.drawable.scooter);
		dialog.setCancelable(true);
		dialog.setMessage("Do you want to exit?");
		dialog.setPositiveButton("Yes", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});

		dialog.setNegativeButton("No", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});

		dialog.show();
	}

}

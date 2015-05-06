package com.example.trib;

import com.example.gasprice.ConnectPtt;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuPage extends Activity {

	private Intent intent_mapping;
	private Button btn_planning;
	private Button btn_GasPrice;
	ProgressDialog PD;

	private String PriceGasoline95 = "0.00";
	private String PriceDiesel = "0.00";
	private String PriceGasohol91 = "0.00";
	private String PriceGasoholE20 = "0.00";
	private String PriceNGV = "0.00";
	private String PriceGasohol95 = "0.00";
	private String PriceGasoholE85 = "0.00";
	private String PriceHyForceDiesel = "0.00";

	private String listGas[];
	private ConnectPtt sv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);

		 sv = new ConnectPtt();
		
		/* btn_planning */
		btn_planning = (Button) findViewById(R.id.btnPlan);
		intent_mapping = new Intent(getApplicationContext(), Mapping.class);
		btn_planning.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(intent_mapping);
			}
		});

		/* btn_gas price */
		btn_GasPrice = (Button) findViewById(R.id.btnGasPrice);
		btn_GasPrice.setOnClickListener(new OnClickListener() {

			

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//setPrice();
				//alertGasPrice();
				new ProgressTask().execute();
			}
		});
	}

	public void alertGasPrice() {
		Log.d("Log", "Alert");
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				MenuPage.this);
		alertDialogBuilder.setCancelable(true).setTitle("Gas price today")
				.setIcon(R.drawable.scooter)
				.setItems(listGas, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();

	}

	@Override
	public void onBackPressed() {

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle("Exit!");
		alertDialog.setIcon(R.drawable.scooter);
		alertDialog.setCancelable(true);
		alertDialog.setMessage("Do you want to exit?");
		alertDialog.setPositiveButton("YES",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						AppExit();
					}
				});

		alertDialog.setNegativeButton("NO",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// Write your code here to invoke NO event
						dialog.cancel();
					}
				});

		alertDialog.show();
	}

	public void AppExit() {

		this.finish();
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);

	}
	

	private class ProgressTask extends AsyncTask<String, Void, Boolean> {
		private ProgressDialog dialog;
		
		protected void onPreExecute() {
			dialog = ProgressDialog.show(MenuPage.this, "", "Loading...");

		}

		@Override
		protected void onPostExecute(final Boolean success) {
			
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			if (success) {
				Log.d("Log", "Complete");
				//setPrice();
				alertGasPrice();
			} else {
				alertGasPrice();
			}
		}

		@Override
		protected Boolean doInBackground(final String... args) {
			
			try{
				Thread.sleep(1000);						
			}catch(Exception e){	
				return false;
			}
				
					PriceGasoline95 = sv.getGasoline95();
					PriceDiesel = sv.getDiesel();
					PriceGasohol91 = sv.getGasohol91();
					PriceGasoholE20 = sv.getGasoholE20();
					PriceNGV = sv.getNGV();
					PriceGasohol95 = sv.getGasohol95();
					PriceGasoholE85 = sv.getGasoholE85();
					PriceHyForceDiesel = sv.getHyForcePremiumDiesel();
					
					String Gasoline95 =    "Gasoline 95          "+PriceGasoline95+"  Baht.";
					String Diesel =        "Diesel                     "+PriceDiesel+"  Baht.";
					String Gasohol91 =     "Gasohol 91           "+PriceGasohol91+"  Baht.";
					String GasoholE20 =    "Gasohol E20         "+PriceGasoholE20+"  Baht.";
					String NGV =           "NGV                        "+PriceNGV+"  Baht.";
					String Gasohol95 =     "Gasohol 95           "+PriceGasohol95+"  Baht.";
					String GasoholE85 =    "Gasohol E85         "+PriceGasoholE85+"  Baht";
					String HyForceDiesel = "HyForce Diesel     "+PriceHyForceDiesel+"  Baht.";
					
					
					listGas = new String[] { Gasoline95, Gasohol91, Gasohol95, GasoholE20,
							GasoholE85, Diesel, HyForceDiesel, NGV };
					
					Log.d("Log", "setPrice complete");
				
				return true;
		
		}

	}

}

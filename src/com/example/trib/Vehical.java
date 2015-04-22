package com.example.trib;

import android.view.View.OnClickListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class Vehical extends Activity {

	private Spinner spinner1, spinner2, spinner3;
	private Button btnConfirm;
	private Intent intent_result;

	private String vehical;
	private String engine;
	private String gas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vehical);
		
		Toast toast = Toast.makeText ( this, "Please select your vehical detail!", Toast.LENGTH_LONG );	 
		toast.show ( );

		addListenerOnButton();
		addListenerOnSpinnerItemSelection();

	}

	private void addListenerOnButton() {
		// TODO Auto-generated method stub
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		spinner3 = (Spinner) findViewById(R.id.spinner3);

		btnConfirm = (Button) findViewById(R.id.btnConfirm);
		btnConfirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				vehical = String.valueOf(spinner1.getSelectedItem());
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
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());

		spinner2 = (Spinner) findViewById(R.id.spinner2);
		spinner2.setOnItemSelectedListener(new CustomOnItemSelectedListener());

		spinner3 = (Spinner) findViewById(R.id.spinner3);
		spinner3.setOnItemSelectedListener(new CustomOnItemSelectedListener());

	}

}

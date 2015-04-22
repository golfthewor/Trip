package com.example.trib;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends Activity {

	private final int SPLASH_DISPLAY_LENGTH = 3000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				/* Create an Intent that will start the Menu-Activity. */
				Intent mainIntent = new Intent(MainActivity.this, Mapping.class);
				MainActivity.this.startActivity(mainIntent);
				MainActivity.this.finish();
			}
		}, SPLASH_DISPLAY_LENGTH);
	}
}

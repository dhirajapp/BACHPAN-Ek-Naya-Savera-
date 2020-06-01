package com.spring.ngopro;

import com.springs.new_ngo_pro.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.widget.ProgressBar;

public class MainActivity extends Activity {

	Intent in;
	ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);

		boolean b = sp.getBoolean("status", false);

		// come back to home screen
		if (b) {
			in = new Intent(this, HomeActivity.class);
		} else {
			in = new Intent(this, LoginActivity.class);
		}

		getActionBar().hide();

		pd = new ProgressDialog(this);
		pd.setMessage("Loading....");
		pd.show();

		new Mythread().start();

	}

	class Mythread extends Thread {
		@Override
		public void run() {

			try {

				Thread.sleep(5000);

			} catch (Exception e) {
				Log.e("error in thread", e.toString());
			}

			pd.cancel();
			startActivity(in);

		}

	}

}

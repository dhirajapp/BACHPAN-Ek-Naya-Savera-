package com.springs.new_ngo_pro;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.webkit.WebView;

public class PayUPayment extends Activity {
	private WebView wv1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay_upayment);
		
		wv1 = (WebView) findViewById(R.id.webView2);
		wv1.getSettings().setJavaScriptEnabled(true);
		Log.v("url====","==== "+wv1);
//		wv.loadUrl("http://www.google.com");
		wv1.loadUrl("https://www.payumoney.com/paybypayumoney/#/383781");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pay_upayment, menu);
		return true;
	}

}

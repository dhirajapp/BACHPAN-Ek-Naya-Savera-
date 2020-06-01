package com.spring.ngopro;

import com.springs.new_ngo_pro.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class WebViewActivity extends Activity {

	private WebView wv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view);
		
		wv = (WebView) findViewById(R.id.webView1);
		wv.getSettings().setJavaScriptEnabled(true);
		Log.v("url====","==== "+wv);
//		wv.loadUrl("http://www.google.com");
		wv.loadUrl("https://accounts.paytm.com/oauth2/authorize?theme=mp-web&redirect_uri=https%3A%2F%2Fpaytm.com%2Fv1%2Fapi%2Fcode&is_verification_excluded=false&client_id=paytm-web&type=web_server&scope=paytm&response_type=code#/login");
	}


}

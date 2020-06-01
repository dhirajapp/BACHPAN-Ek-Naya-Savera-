package com.spring.ngopro;

import com.springs.new_ngo_pro.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

public class BlogActivity extends Activity
{
	private WebView wv1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.blog);
		
		wv1 = (WebView) findViewById(R.id.webView2);
		wv1.getSettings().setJavaScriptEnabled(true);
		Log.v("url====","==== "+wv1);
//		wv.loadUrl("http://www.google.com");
		wv1.loadUrl("http://e-portal.in/bachapan/public/blog/blog");

	}
}

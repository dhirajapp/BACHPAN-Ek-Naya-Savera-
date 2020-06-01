package com.spring.ngopro;

//import com.example.uploadandsaveimage.UploadActivity;
//import com.example.uploadandsaveimage.UploadActivity;
import com.springs.new_ngo_pro.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends Activity {
	ImageButton ivbtnreport, ivbtntrack, ivbtnlistngo, ivbtndonate,
			ivbtnneedngo, ivbtnblog, ivbtnabut, ivbtnfaq;
	ImageView tvlogout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		ivbtnreport = (ImageButton) findViewById(R.id.imageButton1);
		ivbtntrack = (ImageButton) findViewById(R.id.imageButton2);

		ivbtnlistngo = (ImageButton) findViewById(R.id.imageButton4);
		ivbtndonate = (ImageButton) findViewById(R.id.imageButton5);

		ivbtnneedngo = (ImageButton) findViewById(R.id.imageButton3);
		ivbtnblog = (ImageButton) findViewById(R.id.imageButton6);

		ivbtnabut = (ImageButton) findViewById(R.id.imageButton8);
		ivbtnfaq = (ImageButton) findViewById(R.id.imageButton7);
		
		tvlogout=(ImageView)findViewById(R.id.textView1);

		ivbtnreport.setOnClickListener(new OnClickListener() { // Report

			@Override
			public void onClick(View v) {
					
			//	Toast.makeText(HomeActivity.this, "Report", 2).show();
				
				startActivity(new Intent(HomeActivity.this,com.example.uploadandsaveimage.UploadActivity.class));
			}
		});
		ivbtntrack.setOnClickListener(new OnClickListener() { //Track

			@Override
			public void onClick(View v) {
		//		Toast.makeText(HomeActivity.this, "Track", 2).show();
				startActivity(new Intent(HomeActivity.this,TrackReportActivity.class));
			}
		});
		ivbtnlistngo.setOnClickListener(new OnClickListener() { //List of ngo

			@Override
			public void onClick(View v) {
		//		Toast.makeText(HomeActivity.this, "ListNgo", 2).show();
				startActivity(new Intent(HomeActivity.this,NGO_ListActivity.class));
			}
		});
		ivbtndonate.setOnClickListener(new OnClickListener() {	//DOnate

			@Override
			public void onClick(View v) {
		//		Toast.makeText(HomeActivity.this, "Donate", 2).show();
				startActivity(new Intent(HomeActivity.this,DonateActivity.class));
			}
		});
		ivbtnneedngo.setOnClickListener(new OnClickListener() { //need ngo

			@Override
			public void onClick(View v) {
		//		Toast.makeText(HomeActivity.this, "Need", 2).show();
				startActivity(new Intent(HomeActivity.this,NeedNGOActivity.class));
			}
		});
		ivbtnblog.setOnClickListener(new OnClickListener() { // Blog

			@Override
			public void onClick(View v) {
			//	Toast.makeText(HomeActivity.this, "Blog", 2).show();
				startActivity(new Intent(HomeActivity.this,BlogActivity.class));
			}
		});
		ivbtnabut.setOnClickListener(new OnClickListener() { //About us

			@Override
			public void onClick(View v) {
			//	Toast.makeText(HomeActivity.this, "About Us", 2).show();
				startActivity(new Intent(HomeActivity.this,AboutUsActivity.class));
			}
		});
		ivbtnfaq.setOnClickListener(new OnClickListener() { //FAQ

			@Override
			public void onClick(View v) {
		//		Toast.makeText(HomeActivity.this, "FAQ", 2).show();
				startActivity(new Intent(HomeActivity.this,FAQActivity.class));
			}
		});
		
		//Logout Process
		tvlogout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				     SharedPreferences sp=getSharedPreferences("login", MODE_PRIVATE);
				     Editor ed=sp.edit();
				     ed.putBoolean("status", false);
				     ed.commit();
				     
				     boolean b = sp.getBoolean("status", false);
				 //    Toast.makeText(HomeActivity.this, "Logout", 2).show();
				     Log.e("After Logout Value", b+"");
				     
				     Intent in = new Intent(HomeActivity.this, LoginActivity.class);
				     startActivity(in);
				
			}
		});

	}
	
	// to mange back button click
	@Override
	public void onBackPressed() {
		
		 SharedPreferences sp=getSharedPreferences("login", MODE_PRIVATE);
	     
		 boolean b=sp.getBoolean("status", false);
		 
		 // come back to home screen
		 if(b)
		 {
			 Intent intent = new Intent(Intent.ACTION_MAIN);
			 intent.addCategory(Intent.CATEGORY_HOME);
			 startActivity(intent);
		 }
		
		
	}
	
	
	
	
	
	
}













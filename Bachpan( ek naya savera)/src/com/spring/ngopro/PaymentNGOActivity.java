package com.spring.ngopro;

import com.spring.dto.NGO;
import com.springs.new_ngo_pro.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class PaymentNGOActivity extends Activity {

	TextView edname;
	ImageView imv,imv2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay);
		
		edname=(TextView)findViewById(R.id.Cid);
		imv = (ImageView) findViewById(R.id.imageView1);
		imv2 = (ImageView) findViewById(R.id.imageView2);		
		NGO ngo=(NGO)getIntent().getSerializableExtra("NGO");
		
		edname.setText("Name : "+ngo.getName());
		
		imv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(PaymentNGOActivity.this, WebViewActivity.class);
				startActivity(i);
				
			}
		});
imv2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(PaymentNGOActivity.this, com.springs.new_ngo_pro.PayUPayment.class);
				startActivity(i);
				
			}
		});		
		
	}

}

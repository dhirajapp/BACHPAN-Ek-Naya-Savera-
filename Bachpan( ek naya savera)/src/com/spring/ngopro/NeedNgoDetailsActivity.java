package com.spring.ngopro;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.spring.dto.NGO;
import com.springs.new_ngo_pro.R;

public class NeedNgoDetailsActivity extends Activity {

	TextView edname,eddesc,phone;
	EditText needn,ngmsg;
	Button ngone,ngoc;
	String call = "";
	String uri = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.needngodetails);
		needn =(EditText)findViewById(R.id.needsub);
		ngmsg =(EditText)findViewById(R.id.needmsg);
		
		edname=(TextView)findViewById(R.id.ednid);
		eddesc=(TextView)findViewById(R.id.eddesvid);
		phone = (TextView) findViewById(R.id.phone);
		ngone=(Button)findViewById(R.id.ngoneed);
		ngoc=(Button)findViewById(R.id.ngocall);	
		
		NGO ngo=(NGO)getIntent().getSerializableExtra("NGO");
	
		
		//	
		edname.setText("Name : "+ngo.getName());
		eddesc.setText("Need : "+ngo.getNeed());
		phone.setText("phone : "+ngo.getPhone());
		final String lin=ngo.getLink();
		
		ngone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_VIEW);
				Uri data = Uri.parse("mailto:"+lin+"?subject=" + ngone.getText() + "&body=" + ngmsg.getText());
				intent.setData(data);
				startActivity(intent);
			}
		});
		
//		call = "111-333-222-4";
//		 String uri = "tel:" + posted_by.trim() ;
//		 Intent intent = new Intent(Intent.ACTION_DIAl);
//		 intent.setData(Uri.parse(uri));
//		 startActivity(intent);
		
		call =ngo.getPhone();
		
		Log.v("call ", "==== "+call);
		uri = "tel:" + call;
		
		Log.v("uri==", "==="+uri);
		
		ngoc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			//	Toast.makeText(NeedNgoDetailsActivity.this, "Appointment Booked", 2)
				//.show();

				Intent i = new Intent(Intent.ACTION_DIAL);
				i.setData(Uri.parse(uri));
				startActivity(i);
			}
		});
		
	}

}

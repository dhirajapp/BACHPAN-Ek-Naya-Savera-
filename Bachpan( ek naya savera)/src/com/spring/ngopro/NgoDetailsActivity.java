package com.spring.ngopro;

import com.spring.dto.NGO;
import com.springs.new_ngo_pro.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class NgoDetailsActivity extends Activity {

	TextView edname,eddesc,edadd,edlink;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ngodetails);
		
		edname=(TextView)findViewById(R.id.ednid);
		eddesc=(TextView)findViewById(R.id.eddesvid);
		edadd=(TextView)findViewById(R.id.edaddid);
		edlink=(TextView)findViewById(R.id.edlinkid);
		
		NGO ngo=(NGO)getIntent().getSerializableExtra("NGO");
		
		
		edname.setText("Name : "+ngo.getName());
		eddesc.setText("About : "+ngo.getDesc());
		edadd.setText("Address : "+ngo.getAddress());
		edlink.setText("Knew More : "+ngo.getLink());
		
		
	}

}

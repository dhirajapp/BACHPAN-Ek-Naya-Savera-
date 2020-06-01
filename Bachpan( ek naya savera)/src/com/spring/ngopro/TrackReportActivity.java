package com.spring.ngopro;

import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.spring.dto.NGO;
import com.spring.dto.TrackReport;
import com.spring.ngopro.NGO_ListActivity.GetAllNGOTask;
import com.spring.ngopro.res.WebResources;
import com.springs.new_ngo_pro.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TrackReportActivity extends Activity {
	
	
	TextView cid,rid,ngo,caption,action;
	EditText td;
	ArrayList<TrackReport>Tlist;
	ListView lv;
	ArrayAdapter<TrackReport>adap;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
				
		setContentView(R.layout.track);
		
		Tlist=new ArrayList<TrackReport>();
		
		
		Button btn = (Button) findViewById(R.id.GetOTP);
	    td = (EditText) findViewById(R.id.EtRid);
	    lv=(ListView)findViewById(R.id.listView1);
	    
	    adap=new ArrayAdapter<TrackReport>(this, android.R.layout.simple_list_item_1, Tlist);
		
		
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(TrackReportActivity.this, "Track Report", 2).show();
				
				
				TrackReport tr = (TrackReport)getIntent().getSerializableExtra("TrackReport");
//				NGO ngo=(NGO)getIntent().getSerializableExtra("NGO");
				
				String  trid = td.getText().toString();
				Log.v("Referance ID : ", trid);
				//rid.setText("rid : "+tr.getRid());
				
				if (trid.equals("")) {
					td.setError("Fill out this Field");
				}else{
				
				String url=WebResources.TrackReport;
				String data ="rid="+trid; 
				Log.v("Base URL : ",url+"?"+data);
				GetTrackCaseid getTrack= new GetTrackCaseid();
				getTrack.execute(url+"?"+data);
				
				}
//				cid.setText("cid : "+ngo.getName());
//				eddesc.setText("About : "+ngo.getDesc());
//				edadd.setText("Address : "+ngo.getAddress());
//				edlink.setText("Knew More : "+ngo.getLink());
			}
		});
	}
	
	class GetTrackCaseid extends AsyncTask<String, Void, String> {
		ProgressDialog pd;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pd = new ProgressDialog(TrackReportActivity.this);
			pd.setMessage("Loading");
			pd.show();

		}

		@Override
		protected String doInBackground(String... params) {
			String result = "";

			HttpGet get = new HttpGet(params[0]);
			Log.v("Param :  ",params.toString());
			HttpClient client = new DefaultHttpClient();

			try {

				HttpResponse response = client.execute(get);

				HttpEntity entity = response.getEntity();

				InputStream in = entity.getContent();

				while (true) {
					int x = in.read();

					if (x == -1)
						break;
					else
						result = result + (char) x;

				}

			} catch (Exception e) {
				result = e.toString();
			}

			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			Log.v("  Result :  ", result);

			//[{"id":1,"name":"ayush","desc":"mp nagar","link":"spring.com","need":"b-10,b-11","address":"b-10,b-11"}]
			
			Toast.makeText(TrackReportActivity.this, result, 2).show();
			if(result.equals("error")!=true)
			{
			try {
				JSONArray jobj = new JSONArray(result);
					
				for (int i = 0; i < jobj.length(); i++) {
					JSONObject j = jobj.getJSONObject(i);
					
					Log.v("------", "JSONArray =="+j.toString());
					String caseid = j.getString("cid");
					Log.v("cid ", "cid = "+cid);
					String refid = j.getString("rid");
					Log.v("============rid ", " =====rid ==== "+caseid);
					String tcaption = j.getString("caption");
					Log.v("========caption== ", "======caption = "+tcaption);
					String taction = j.getString("action");
					Log.v("========action ", "========action = "+taction);
					String ngoName = j.getString("ngoName");
					Log.v("=======ngoName ", "====== ngoName = "+ngoName);
					TrackReport tr = new TrackReport(caseid, refid, tcaption, taction, ngoName);
					Tlist.add(tr);
					
				}
					
					Log.e("size list", ""+Tlist.size());
				
					
//					cid.setText("Case id : "+tr.getCid());
//					rid.setText("Reference id : "+tr.getRid());
//					caption.setText("Caption id : "+tr.getCaption());
//					action.setText("Action : "+tr.getAction());
//					ngo.setText("Ngo Name : "+tr.getNgoName());
					
					
					//		NGO ngo=new NGO(id, name, desc, link, need, add);
	               // Tlist.add(tr);

			//	}
				lv.setAdapter(adap);
				adap.notifyDataSetChanged();
			//	lv.setAdapter(adap);
			

			} catch (Exception e) {
				Log.e("Track Report JSON  :" , "jSON Error");
				Toast.makeText(TrackReportActivity.this, e.toString(), 2)
						.show();
			}
			}else
			{
				finish();
				startActivity(getIntent());	
				Toast.makeText(TrackReportActivity.this, "Error in Tracking", 1).show();
			}
			pd.cancel();
		}
	}

}

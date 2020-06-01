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
import com.spring.ngopro.NGO_ListActivity.GetAllNGOTask;
import com.spring.ngopro.res.WebResources;
import com.springs.new_ngo_pro.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class DonateActivity extends Activity{

ListView lv;
ArrayAdapter<NGO>adap;
ArrayList<NGO>nlist;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);

	setContentView(R.layout.ngolist);
	
	nlist=new ArrayList<NGO>();
	
	lv=(ListView)findViewById(R.id.listView1);
	
	adap=new ArrayAdapter<NGO>(this, android.R.layout.simple_list_item_1, nlist);
	
	String url=WebResources.GetAllNgo;
	
	new GetAllNGOTask().execute(url);
	
	lv.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

			NGO ngo=nlist.get(arg2);
			
			Intent i=new Intent(DonateActivity.this, PaymentNGOActivity.class);
			i.putExtra("NGO",ngo);
			startActivity(i);
			//Toast.makeText(NGO_ListActivity.this, ""+list.get(arg2), 2).show();
			
		}
	});
	
	
}


class GetAllNGOTask extends AsyncTask<String, Void, String> {
	ProgressDialog pd;

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		pd = new ProgressDialog(DonateActivity.this);
		pd.setMessage("Loading");
		pd.show();

	}

	@Override
	protected String doInBackground(String... params) {
		String result = "";

		HttpGet get = new HttpGet(params[0]);
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
		pd.cancel();

		//[{"id":1,"name":"ayush","desc":"mp nagar","link":"spring.com","need":"b-10,b-11","address":"b-10,b-11"}]
		
	//	Toast.makeText(DonateActivity.this, result, 2).show();
		try {
			JSONArray jobj = new JSONArray(result);

			for (int i = 0; i < jobj.length(); i++) {
				JSONObject j = jobj.getJSONObject(i);
				int id = j.getInt("id");
				String name = j.getString("name");
				String desc = j.getString("desc");
				String link = j.getString("link");
				String need = j.getString("need");
				String add = j.getString("address");
				String phone = j.getString("phone");
			
				NGO ngo=new NGO(id, name, desc, link, need, add, phone);
                nlist.add(ngo);

			}
		//	adap.notifyDataSetChanged();
			lv.setAdapter(adap);
		

		} catch (Exception e) {
		//	Toast.makeText(DonateActivity.this, e.toString(), 2).show();
		}

	}
}
}

package com.spring.ngopro;

import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.spring.ngopro.res.WebResources;
import com.springs.new_ngo_pro.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	Button btnlogin, btnreg, btnfpw;

	EditText edmobile, edpassword;

	String mobile = "", password = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		btnlogin = (Button) findViewById(R.id.btnloginid);
		btnreg = (Button) findViewById(R.id.btnregid);
		edmobile = (EditText) findViewById(R.id.edmobile);
		edpassword = (EditText) findViewById(R.id.edpassword);
		btnfpw = (Button) findViewById(R.id.btnfrgtpw);
		
		
		
		
		

		btnfpw.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(LoginActivity.this, ForgetpwActivity.class);
				startActivity(i);
				
			}
		});
		btnlogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(LoginActivity.this, "Login", 2).show();

				Log.v("Testin", "...");
				
				mobile = edmobile.getText().toString();
				password = edpassword.getText().toString();
				
				if (mobile.equals("")) {
					edmobile.setError("Fill out this Field");
				}
				
				else if (password.equals("")) {
					edpassword.setError("Fill out this Field");
				}
				
				
				else 
				{
					//startActivity(new Intent(LoginActivity.this, HomeActivity.class));
					String url=WebResources.LOGIN_URL;
					Log.v("URL", url);
					String data="mobile="+mobile+"&password="+password;
					
					LoginTask lt=new LoginTask();
					
				
					lt.execute(url+"?"+data);
				}
			}		
		});
		btnreg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			//	Toast.makeText(LoginActivity.this, "new user", 2).show();
				startActivity(new Intent(LoginActivity.this, SignupActivity.class));

			}
		});
	}
	

	class LoginTask extends AsyncTask<String, Void, String>
	{
		
		ProgressDialog pd;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pd = new ProgressDialog(LoginActivity.this);
			pd.setMessage("login please wait");
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
			super.onPostExecute(result);
			
		//	Log.e("Error : ", result);
			
		//	Toast.makeText(LoginActivity.this, result, 2).show();
			
			//{"id":1,"name":"NGO","email":"ngo@gmail.com","contact":"8871489921","password":"1234567","role":"NGO"}
			if(result.equals("error")!=true)
			{
				String status="";
				try {
					JSONObject j=new JSONObject(result);
					
					int id=j.getInt("id");
					String name = j.getString("name");
					String email = j.getString("email");
					String contact = j.getString("contact");
					String password = j.getString("password");
					String role = j.getString("role");
					status=j.getString("status");
					
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
			//		Log.e("Error in JSON", e.toString());
		//			Toast.makeText(LoginActivity.this, "Error in JSON", 2).show();
				}
				
				
				if(status.equals("done"))
				{
					
			     SharedPreferences sp=getSharedPreferences("login", MODE_PRIVATE);
			     Editor ed=sp.edit();
			     ed.putBoolean("status", true);
			     ed.commit();
			
				  Intent i = new Intent(LoginActivity.this, HomeActivity.class);
				  startActivity(i);
				}
				else
				{
					Toast.makeText(LoginActivity.this, "Invalid Id/Password", 2).show();
				}
			}
			else
			{
				Toast.makeText(LoginActivity.this, "Invalid Id/Password!", 2).show();
			}
			pd.cancel();
		}
		
		
		
	}
	
	
}

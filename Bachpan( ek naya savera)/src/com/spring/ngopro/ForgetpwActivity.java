package com.spring.ngopro;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.spring.ngopro.res.WebResources;
import com.springs.new_ngo_pro.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgetpwActivity extends Activity {

	Button btnfpw;

	EditText edmobile;

	String mobile = "", password = "";
	String pass="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgetpw);

		edmobile = (EditText) findViewById(R.id.editText1);
		btnfpw = (Button) findViewById(R.id.btnsubmit);

		
		btnfpw.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(LoginActivity.this, "Login", 2).show();

				Log.v("forget pass", ".......");
				
				mobile = edmobile.getText().toString();
				
				if (mobile.equals("")) {
					edmobile.setError("Fill out this Field");
				}
				else if (mobile.length()!=10) {
					edmobile.setError("Mobile Number must be 10 digit");
				}
				
				else 
				{
					//startActivity(new Intent(LoginActivity.this, HomeActivity.class));
					String url=WebResources.LOGIN_FORGOT;
					
					Log.v("........", ".......");
					String data="mobile="+mobile;
					
					ForgotTask lt=new ForgotTask();
					
				
					lt.execute(url+"?"+data);
					
					new OtpTask1().execute();
					Toast.makeText(getApplicationContext(), "You should receive password shortly", 1000).show();
					
				}
			}		
		});
		
	}
	

	class ForgotTask extends AsyncTask<String, Void, String>
	{
		
		ProgressDialog pd;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pd = new ProgressDialog(ForgetpwActivity.this);
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
			
			Log.e("Error : ", result);
			
			Toast.makeText(ForgetpwActivity.this, result, 2).show();
			
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
					pass="Please find your password which is setup by you! Password is : "+password;
					Log.v("Password is: ", password);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					Log.e("Error in JSON", e.toString());
					Toast.makeText(ForgetpwActivity.this, "Error in JSON", 2).show();
				}
				
				
				if(status.equals("done"))
				{
Toast.makeText(getApplicationContext(), "Password is sent on mail", 1000).show();
				}
				else
				{
					Toast.makeText(ForgetpwActivity.this, "Error in Forget password", 2).show();
				}
			}
			else
			{
				Toast.makeText(ForgetpwActivity.this, "Error in Login", 2).show();
			}
			pd.cancel();
		}
		
		
		
	}
	class OtpTask1 extends AsyncTask<String, Void, String> {

		ProgressDialog pd;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pd = new ProgressDialog(ForgetpwActivity.this);
			pd.setMessage("please wait....");
			pd.show();

		}

		@Override
		protected String doInBackground(String... params) {

			String result = "";
			postData();
			
			return result;
		}

		@Override
		protected void onPostExecute(String result) {

			super.onPostExecute(result);

			Log.v("error", "======="+result);
			Toast.makeText(ForgetpwActivity.this, "Error "+result, Toast.LENGTH_SHORT).show();

			pd.cancel();


		}
		
		public void postData() {
			HttpClient httpclient = new DefaultHttpClient();
			
		        // specify the URL you want to post to
//			HttpPost httppost = new HttpPost("http://mysms.springstrategies.in/API/WebSMS/Http/v1.0a/index.php?");
			HttpPost httppost = new HttpPost("http://mysms.springstrategies.in/API/WebSMS/Http/v1.0a/index.php?");
			try {
				// create a list to store HTTP variables and their values
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		                // add an HTTP variable and value pair
//				nameValuePairs.add(new BasicNameValuePair("myHttpData", valueIwantToSend));
				nameValuePairs.add(new BasicNameValuePair("username", "spring"));
				nameValuePairs.add(new BasicNameValuePair("password", "bRT5hp"));
				nameValuePairs.add(new BasicNameValuePair("sender", "SPRING"));
				nameValuePairs.add(new BasicNameValuePair("to", edmobile.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("message", pass));
				nameValuePairs.add(new BasicNameValuePair("reqid", "1"));
				nameValuePairs.add(new BasicNameValuePair("format", "text"));
				nameValuePairs.add(new BasicNameValuePair("route_id", "1"));
				nameValuePairs.add(new BasicNameValuePair("callback", "Any+Callback+URL"));
				nameValuePairs.add(new BasicNameValuePair("unique", "0"));
				nameValuePairs.add(new BasicNameValuePair("sendondate", "20-11-2017T11:24:15"));
				
				Log.v("mobile ", "no. = "+edmobile.getText().toString());
				Log.v("Otp ", " ====== "+pass);
				
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		                // send the variable and value, in other words post, to the URL
				HttpResponse response = httpclient.execute(httppost);
				Log.v("=====", "response = "+response);
				
			} catch (ClientProtocolException e) {
				
				Toast.makeText(getApplicationContext(), "error 1", 2).show();
				// process execption
			} catch (IOException e) {
				Toast.makeText(getApplicationContext(), "error 2", 2).show();
				// process execption
			}
		}// postData method

	}
	
	
}

package com.spring.ngopro;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

import com.spring.ngopro.res.WebResources;
import com.springs.new_ngo_pro.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputFilter.LengthFilter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignupActivity extends Activity
{
	EditText edname,edmobile,edpassword,edrepassword,edemail,eotp,edcpw;
	Button btnsignup,getotp;
	
	
	String Sotp="";
	String sootp="";
	int Generatedotp = 0;
	
	String phone = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reg);
		
		edname = (EditText) findViewById(R.id.ednameid);
		edpassword = (EditText) findViewById(R.id.edpasswordid);
		edmobile = (EditText) findViewById(R.id.edmobileid);
		
	//	phone = edmobile.getText().toString();
		
		Log.v("mobile ", "no. = "+edmobile.getText().toString());
		
		edemail=(EditText) findViewById(R.id.EtRid);
		eotp = (EditText) findViewById(R.id.editText2);
		edcpw = (EditText) findViewById(R.id.edcpw);

		getotp = (Button) findViewById(R.id.GetOTP);
		
		getotp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			new OtpTask().execute();
			Toast.makeText(getApplicationContext(), "You should receive OTP SMS Shortly", 1000).show();
				
			}
		});
		
		Generatedotp = GenrOTP();
		
		Sotp = "Thank you for Sign Up at Bachpan App, Find Your Secure Sign up OTP : " +String.valueOf(Generatedotp);
		sootp=String.valueOf(Generatedotp);
		Log.v("SMS", Sotp);
		btnsignup = (Button) findViewById(R.id.btnsignupid);
		btnsignup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(SignupActivity.this, "You are Registered!!", 2).show();
				
				String name=edname.getText().toString();
				String mobile=edmobile.getText().toString();
				String password=edpassword.getText().toString();
				String email=edemail.getText().toString();
				String otp = eotp.getText().toString();
				String cpw = edcpw.getText().toString();
				
				if(name.equals("")){
					edname.setError("Fill out this Field");
					edname.requestFocus();
				}
				else if (mobile.equals("")) {
					edmobile.setError("Fill out this Field");
					edmobile.requestFocus();
				}
				else if (mobile.length()!=10) {
					edmobile.setError("Mobile Number must be 10 digit");
					edmobile.requestFocus();
				}
				else if (password.equals("")) {
					edpassword.setError("Fill out this Field");
					edpassword.requestFocus();
				}
				
				else if (password.length() < 6) {
					edpassword.setError("Password must be more than 6 characters");
					edpassword.requestFocus();
					
				} 
				else if(cpw.equals("")){
					edcpw.setError("Fill out this Field");
					edcpw.requestFocus();
              }
				else if(!password.equals(cpw)){
					edcpw.setError("Password not matched");
					edcpw.requestFocus();
              }
				else if(otp.equals(""))
				{
					eotp.setError("This field is required");
					eotp.requestFocus();
				}
				else if (!otp.equals(sootp))
				{
					eotp.setError("Please enter the correct otp");
					eotp.requestFocus();
				}
				else {
				//http://localhost:8080/UploadWeb/RegistrationServices?name=Kiran&email=contact@kiran.co&mobile=9200101010&password=1234
				

				String data="name="+name+"&mobile="+mobile+"&password="+password+"&email="+email+"&otp="+otp;
				

				RegTask rt = new RegTask();

				rt.execute(WebResources.RegistrationUrl+"?"+data);
				
//				startActivity(new Intent(SignupActivity.this, LoginActivity.class));
				
				}

			}
		});
	
	}

	
	int GenrOTP()
	{
		Random r = new Random();
		return r.nextInt(9999)+1000;
	}

	class RegTask extends AsyncTask<String, Void, String> {

		ProgressDialog pd;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pd = new ProgressDialog(SignupActivity.this);
			pd.setMessage("please wait....");
			pd.show();

		}

		@Override
		protected String doInBackground(String... params) {

			String result = "";

			String url = params[0];

			try {

				HttpGet get = new HttpGet(url);

				HttpClient client = new DefaultHttpClient();

				HttpResponse response = client.execute(get);

				HttpEntity entity = response.getEntity();

				InputStream in = entity.getContent();


				int x = in.read();

				while (x != -1) {
					result = result + (char) x;
					x = in.read();

				}

			} catch (Exception e) {
				result = e.toString();
			}

			return result;
		}

		@Override
		protected void onPostExecute(String result) {

			super.onPostExecute(result);

			pd.cancel();

			if (result.equals("done"))
			{
				Toast.makeText(SignupActivity.this, "Registered Successfully", 2).show();
				startActivity(new Intent(SignupActivity.this, LoginActivity.class));
			}
			else
				Toast.makeText(SignupActivity.this, "Error "+result, 2).show();

		}

	}
	
	class OtpTask extends AsyncTask<String, Void, String> {

		ProgressDialog pd;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pd = new ProgressDialog(SignupActivity.this);
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
			Toast.makeText(SignupActivity.this, "Error "+result, Toast.LENGTH_SHORT).show();

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
				nameValuePairs.add(new BasicNameValuePair("message", Sotp));
				nameValuePairs.add(new BasicNameValuePair("reqid", "1"));
				nameValuePairs.add(new BasicNameValuePair("format", "text"));
				nameValuePairs.add(new BasicNameValuePair("route_id", "1"));
				nameValuePairs.add(new BasicNameValuePair("callback", "Any+Callback+URL"));
				nameValuePairs.add(new BasicNameValuePair("unique", "0"));
				nameValuePairs.add(new BasicNameValuePair("sendondate", "20-11-2017T11:24:15"));
				
				Log.v("mobile ", "no. = "+edmobile.getText().toString());
				Log.v("Otp ", " ====== "+Sotp);
				
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

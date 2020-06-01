package com.example.uploadvideos;
import com.example.uploadandsaveimage.UploadActivity.MyLocationListener;
import com.example.uploadvideos.*;
import com.example.uploadvideos.AndroidMultiPartEntity.ProgressListener;
import com.example.uploadvideos.MainActivity.FileUploadTask;
import com.spring.ngopro.LoginActivity;
import com.spring.ngopro.SignupActivity;
import com.spring.ngopro.res.WebResources;
import com.springs.new_ngo_pro.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
 
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
 
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
 
public class UploadActivity extends Activity {
    // LogCat tag
    private static final String TAG = MainActivity.class.getSimpleName();
 
    private ProgressBar progressBar;
    private String filePath = null;
    private TextView txtPercentage;
    private ImageView imgPreview;
    private VideoView vidPreview;
    private Button btnUpload,tkimg,ngoneed;
    private EditText locid,numtime,caption;
	String results="";

    long totalSize = 0;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload);
        txtPercentage = (TextView) findViewById(R.id.txtPercentage);
        btnUpload = (Button) findViewById(R.id.button2);
        tkimg = (Button) findViewById(R.id.button3);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        imgPreview = (ImageView) findViewById(R.id.imgPreview);
        vidPreview = (VideoView) findViewById(R.id.videoPreview);
        locid=(EditText)findViewById(R.id.locid);
        numtime=(EditText)findViewById(R.id.notime);
        caption=(EditText)findViewById(R.id.EtRid);
        ngoneed= (Button)findViewById(R.id.ngoneed);
      
// progressBar.setVisibility(View);
        // Changing action bar background color
        getActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor(getResources().getString(
                        R.color.action_bar))));
 
        // Receiving the data from previous activity
        Intent i = getIntent();
 
        // image or video path that is captured in previous activity
        filePath = i.getStringExtra("filePath");
 
        // boolean flag to identify the media type, image or video
        boolean isImage = i.getBooleanExtra("isImage", true);
 
        if (filePath != null) {
            // Displaying the image or video on the screen
            previewMedia(isImage);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Sorry, file path is missing!", Toast.LENGTH_LONG).show();
        }
 
        btnUpload.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View v) {
                // uploading the file to server
            	
            	Random rdd=new Random();
            	int cid=rdd.nextInt(10000);
            	String refid=caption.getText().toString()+rdd.nextInt(10000);
            	String data="caseid="+cid+"&refid="+refid+"&caption="+caption.getText().toString()+"&location="+locid.getText().toString()+"&ntime="+numtime.getText().toString();
            Log.v("dt", data);
            	UploadDt upl=new UploadDt();
            	
				upl.execute(WebResources.Upload+"?"+data);
            //    upl.execute(WebResources.Upload);
            }
        });
        

        ngoneed.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View v) {
                // uploading the file to server
            	
Log.v("location s", "......");           	 
                turnGPSOn(); // method to turn on the GPS if its in off state.
               getMyCurrentLocation();
            	
            }
        });
        
        
        tkimg.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View v) {
                // uploading the file to server
            	

              new UploadFileToServer().execute();
            	
			//	upl.execute(WebResources.Upload+"?"+data);
            //    upl.execute(WebResources.Upload);
            }
        });
        }
 
    /**
     * Displaying captured image/video on the screen
     * */
    private void previewMedia(boolean isImage) {
        // Checking whether captured media is image or video
        if (isImage) {
            imgPreview.setVisibility(View.VISIBLE);
            vidPreview.setVisibility(View.GONE);
            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();
 
            // down sizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;
 
            final Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
 
            imgPreview.setImageBitmap(bitmap);
        } else {
            imgPreview.setVisibility(View.GONE);
            vidPreview.setVisibility(View.VISIBLE);
            vidPreview.setVideoPath(filePath);
            // start playing
            vidPreview.start();
        }
    }
 
	/** Method to turn on GPS **/
    public void turnGPSOn(){
        try
        {
       
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

       
        if(!provider.contains("gps")){ //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }
        }
        catch (Exception e) {
           
        }
    }
// Method to turn off the GPS
    public void turnGPSOff(){
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if(provider.contains("gps")){ //if gps is enabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }
    }
   
    // turning off the GPS if its in on state. to avoid the battery drain.
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        turnGPSOff();
    }
   
    /**
     * Check the type of GPS Provider available at that instance and
     * collect the location informations
     *
     * @Output Latitude and Longitude
     */
    void getMyCurrentLocation() {
       
       
        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locListener = new MyLocationListener();
       
       
         try{gps_enabled=locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);}catch(Exception ex){}
           try{network_enabled=locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);}catch(Exception ex){}

            //don't start listeners if no provider is enabled
            //if(!gps_enabled && !network_enabled)
                //return false;

            if(gps_enabled){
                locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
               
            }
           
           
            if(gps_enabled){
                location=locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
               
               
            }
           
 
            if(network_enabled && location==null){
                locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locListener);
               
            }
       
       
            if(network_enabled && location==null)    {
                location=locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER); 
           
            }
       
        if (location != null) {
           
            MyLat = location.getLatitude();
            MyLong = location.getLongitude();

       
        } else {
            Location loc= getLastKnownLocation(this);
            if (loc != null) {
               
                MyLat = loc.getLatitude();
                MyLong = loc.getLongitude();
               

            }
        }
        locManager.removeUpdates(locListener); // removes the periodic updates from location listener to //avoid battery drainage. If you want to get location at the periodic intervals call this method using //pending intent.
       
        try
        {
// Getting address from found locations.
        Geocoder geocoder;
       
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
         addresses = geocoder.getFromLocation(MyLat, MyLong, 1);

        StateName= addresses.get(0).getAdminArea();
        CityName = addresses.get(0).getLocality();
        CountryName = addresses.get(0).getCountryName();
        arean= addresses.get(0).getThoroughfare();
        
        
        
        try {
            List<Address> addressList = geocoder.getFromLocation(
                    MyLat, MyLong, 1);
            if (addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    sb.append(address.getAddressLine(i)).append("\n");
                }
              //  String mm=address.getAdminArea();
                String mm=address.getAddressLine(0);
                Log.v("loc", mm);
                sb.append(mm);
                results = sb.toString();
            }
        } catch (IOException e) {
            Log.e("", "Unable connect to Geocoder", e);
        }
        
        Log.v("Data", results);
        
        
        // you can get more details other than this . like country code, state code, etc.
       
       
        System.out.println(" StateName " + StateName);
        System.out.println(" CityName " + CityName);
        System.out.println(" CountryName " + CountryName);
        System.out.println(" Area " + arean);
        
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
      
       locid.setText(results);
    }
   
    // Location listener class. to get location.
    public class MyLocationListener implements LocationListener {
        public void onLocationChanged(Location location) {
            if (location != null) {
            }
        }

        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
        }

        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub
        }
    }
    private boolean gps_enabled=false;
    private boolean network_enabled=false;
    Location location;
   
    Double MyLat, MyLong;
    String CityName="";
    String StateName="";
    String CountryName="";
    String arean="";
   
// below method to get the last remembered location. because we don't get locations all the times .At some instances we are unable to get the location from GPS. so at that moment it will show us the last stored location. 

    public static Location getLastKnownLocation(Context context)
    {
        Location location = null;
        LocationManager locationmanager = (LocationManager)context.getSystemService("location");
        List list = locationmanager.getAllProviders();
        boolean i = false;
        Iterator iterator = list.iterator();
        do
        {
            //System.out.println("---------------------------------------------------------------------");
            if(!iterator.hasNext())
                break;
            String s = (String)iterator.next();
            //if(i != 0 && !locationmanager.isProviderEnabled(s))
            if(i != false && !locationmanager.isProviderEnabled(s))
                continue;
           // System.out.println("provider ===> "+s);
            Location location1 = locationmanager.getLastKnownLocation(s);
            if(location1 == null)
                continue;
            if(location != null)
            {
                //System.out.println("location ===> "+location);
                //System.out.println("location1 ===> "+location);
                float f = location.getAccuracy();
                float f1 = location1.getAccuracy();
                if(f >= f1)
                {
                    long l = location1.getTime();
                    long l1 = location.getTime();
                    if(l - l1 <= 600000L)
                        continue;
                }
            }
            location = location1;
           // System.out.println("location  out ===> "+location);
            //System.out.println("location1 out===> "+location);
            i = locationmanager.isProviderEnabled(s);
           // System.out.println("---------------------------------------------------------------------");
        } while(true);
        return location;
    }
   
    
    
    
    
    
    
    /**
     * Uploading the file to server
     * */
    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            progressBar.setProgress(0);
            super.onPreExecute();
            
        }
 
        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible
            progressBar.setVisibility(View.VISIBLE);
 
            // updating progress bar value
            progressBar.setProgress(progress[0]);
 
            // updating percentage value
            txtPercentage.setText(String.valueOf(progress[0]) + "%");
        }
 
        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }
 
        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;
 
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Config.FILE_UPLOAD_URL);
 
            try {
               
            	AndroidMultiPartEntity entity =new AndroidMultiPartEntity(new ProgressListener() {
					
					@Override
					public void transferred(long num) {
						// TODO Auto-generated method stub
						
					}
				});
 
                File sourceFile = new File(filePath);
 
                // Adding file data to http body
                entity.addPart("image", new FileBody(sourceFile));
 
                // Extra parameters if you want to pass to server
                entity.addPart("website",
                        new StringBody("www.androidhive.info"));
                entity.addPart("email", new StringBody("abc@gmail.com"));
 
                totalSize = entity.getContentLength();
                httppost.setEntity(entity);
 
                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();
 
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }
 
            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }
 
            return responseString;
 
        }
 
        @Override
        protected void onPostExecute(String result) {
            Log.e(TAG, "Response from server: " + result);
 
            // showing the server response in an alert dialog
            showAlert(result);
 
            super.onPostExecute(result);
        }
 
    }
 
    
    class UploadDt extends AsyncTask<String, Void, String> {

		ProgressDialog pd;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pd = new ProgressDialog(UploadActivity.this);
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
				Toast.makeText(UploadActivity.this, "Data report", 2).show();
				startActivity(new Intent(UploadActivity.this,MainActivity.class));
			}
			else
				Toast.makeText(UploadActivity.this, "Error "+result, 2).show();

		}

	}
	
    
    
    
    
    
    
    /**
     * Method to show alert dialog
     * */
    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message).setTitle("Response from Servers")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // do nothing
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
 
}
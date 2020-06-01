package com.example.uploadandsaveimage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.spring.ngopro.HomeActivity;
import com.spring.ngopro.LoginActivity;
import com.spring.ngopro.res.WebResources;
import com.springs.new_ngo_pro.R;

public class UploadActivity extends Activity {

	ImageView imgView;
	//String ipAddtress = "careersociety.in";
	String ipAddtress = WebResources.IP_ADDRESS+":"+WebResources.PORT;
	Button  buttonUpload,locbut,btnvideo;
	String imageName = "profile" + ".png";
	//ProgressBar pbar;
	EditText edcaption, edloc, notime;
	String caption="", loc="",nomtime="";
	String numtime="";
	String ref_id = "12345";
	String results="";
	File imgFile;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.upload);
		imgView = (ImageView) findViewById(R.id.imageView1);
		//btCamera = (Button) findViewById(R.id.GetOTP);
		buttonUpload = (Button) findViewById(R.id.button2);
		 btnvideo= (Button) findViewById(R.id.button3);
		locbut = (Button) findViewById(R.id.ngoneed);
		//pbar = (ProgressBar) findViewById(R.id.progressBar1);
		//pbar.setVisibility(View.INVISIBLE);
		edloc = (EditText) findViewById(R.id.locid);
		edcaption = (EditText) findViewById(R.id.EtRid);
		notime = (EditText) findViewById(R.id.notime);
Intent intent = getIntent();
		
		String imgPath = intent.getStringExtra("imagepath");
		
		Log.v("imgPath = ", "===="+imgPath);
		
		if(imgPath!=null)
		{
		imgFile = new  File(imgPath);
		

		
		if(imgFile.exists()){

		    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

//		    ImageView myImage = (ImageView) findViewById(R.id.imageviewTest);

		    imgView.setImageBitmap(myBitmap);
			}
		}
		// check if profile image already exist
		// ApplicationInfo appInfo=getApplicationInfo();
		// String appPackageDir=appInfo.dataDir+"/userdir";
		String appPackageDir = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/userdir";
		// Toast.makeText(this, appPackageDir, 5).show();
		final File f = new File(appPackageDir, imageName);
		if (f.exists()) {
			Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
			//imgView.setImageBitmap(bmp);
		}
		buttonUpload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				caption = edcaption.getText().toString();
				loc = edloc.getText().toString();
nomtime=notime.getText().toString();
				ref_id = new Random().nextInt(999) + "";
				String filePath = imgFile.getAbsolutePath();
				FileUploadTask task = new FileUploadTask();
				task.execute(filePath);
				Toast.makeText(UploadActivity.this,
						"hello" + imgFile.getAbsolutePath(), 5).show();
				//pbar.setVisibility(View.VISIBLE);
			}
		});
//		
		locbut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				 
                turnGPSOn(); // method to turn on the GPS if its in off state.
               getMyCurrentLocation();

			}
		});
		
		
		btnvideo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				 
				startActivity(new Intent(UploadActivity.this, com.example.uploadvideos.MainActivity.class));

			}
		});
		
//		btCamera.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				// start camera using implicite intent
//				/*
//				 * Intent in=new Intent();
//				 * in.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
//				 * startActivityForResult(in, 1000);
//				 */
//				Intent in = new Intent(Intent.ACTION_PICK,
//						MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//				startActivityForResult(in, 1000);
//
//			}
//		});

	}// eof oncreate

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
              //  for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                //    sb.append(address.getAddressLine(i)).append("\n");
               // }
               // sb.append(MyLat+" / "+MyLong).append("\n");
               // sb.append(address.getLocality()).append("\n");
               // sb.append(address.getPostalCode()).append("\n");
               // sb.append(address.getCountryName());
                sb.append(address.getAddressLine(0));
                results = sb.toString();
            }
        } catch (IOException e) {
            Log.e("", "Unable connect to Geocoder", e);
        }
        
        
        
        
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
      
       edloc.setText(results);
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
   
	
	
	
	class FileUploadTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String filePath = params[0];
			String str = "";
			try {
				// URL u=new
				// URL("http://192.168.43.152:8080/UploadWeb/UploadServlet");
				URL u = new URL(WebResources.Upload);
				//URL u = new URL("http://" + ipAddtress
				//		+ ":8084/UploadWeb/UploadServlet");
				MultipartUtility mpu = new MultipartUtility(u);
				mpu.addFilePart("file", new File(filePath));
				mpu.addFormField("user_id", "12345");
				mpu.addFormField("caption", caption);
				mpu.addFormField("loc", loc);
				mpu.addFormField("ref_id", ref_id);
				mpu.addFormField("numtime", nomtime);				
				byte[] result = mpu.finish();
				str = new String(result);
			} catch (Exception ex) {
				str = ex.toString();
			}

			return str;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Log.e("result", result);
			Toast.makeText(getApplicationContext(), "Case Reported! Your Case Reference Id is "+ref_id, 15000).show();
			  Intent in = new Intent(UploadActivity.this, HomeActivity.class);
			     startActivity(in);
			
			//pbar.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1000) {
			/*
			 * Bundle bn=data.getExtras(); Bitmap bmp=(Bitmap)bn.get("data");
			 * imgView.setImageBitmap(bmp);
			 */
			Bitmap bmp = null;
			try {
				bmp = MediaStore.Images.Media.getBitmap(getContentResolver(),
						data.getData());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			imgView.setImageBitmap(bmp);

			// save image data in your app folder
			// ApplicationInfo appInfo=getApplicationInfo();
			// String appPackageDir=appInfo.dataDir;

			// create folder in app dir
			// String folderPath=appPackageDir+"/userdir";
			// File userdir=new File(folderPath);

			// create folder in external memory card
			// get external memory card path
			String sdcardPath = Environment.getExternalStorageDirectory()
					.getAbsolutePath();
			File userdir = new File(sdcardPath + "/userdir");

			if (!userdir.exists()) // no dir exist on this path
				userdir.mkdir();

			File f = new File(userdir, imageName);
			try {
				FileOutputStream fout = new FileOutputStream(f);
				// bmp.compress(format, quality, stream);
				boolean result = bmp.compress(Bitmap.CompressFormat.PNG, 100,
						fout);
				if (result == true)
					Toast.makeText(UploadActivity.this, "profile updated", 6)
							.show();

				fout.close();

			} catch (Exception ex) {
				Log.e("error;", ex.toString());
			}

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

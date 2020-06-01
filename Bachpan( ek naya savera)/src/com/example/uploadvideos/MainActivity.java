package com.example.uploadvideos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.spring.ngopro.HomeActivity;
import com.spring.ngopro.TrackReportActivity;
import com.springs.new_ngo_pro.R;

 
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;


@SuppressWarnings("unused")
public class MainActivity extends Activity {
    
   // LogCat tag
   private static final String TAG = MainActivity.class.getSimpleName();
    
 
   // Camera activity request codes
   private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
   private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    
   public static final int MEDIA_TYPE_IMAGE = 1;
   public static final int MEDIA_TYPE_VIDEO = 2;
	String ipAddtress = "192.168.1.7:8084";
	
	  public static String fpath="";
 
   private Uri fileUri; // file url to store image/video
    
   private Button btnCapturePicture, btnRecordVideo;
 
   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
        
       // Changing action bar background color
       // These two lines are not needed
       getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(getResources().getString(R.color.action_bar))));
       
       btnCapturePicture = (Button) findViewById(R.id.btnCapturePicture);
       btnRecordVideo = (Button) findViewById(R.id.btnRecordVideo);
 
       /**
        * Capture image button click event
        */
       btnCapturePicture.setOnClickListener(new View.OnClickListener() {
 
           @Override
           public void onClick(View v) {
               // capture picture
               captureImage();
               
               Log.v("image path", "==== "+fpath);
           }
       });
 
       /**
        * Record video button click event
        */
       btnRecordVideo.setOnClickListener(new View.OnClickListener() {
 
           @Override
           public void onClick(View v) {
               // record video
               recordVideo();
           }
       });
 
       // Checking camera availability
       if (!isDeviceSupportCamera()) {
           Toast.makeText(getApplicationContext(),
                   "Sorry! Your device doesn't support camera",
                   Toast.LENGTH_LONG).show();
           // will close the app if the device does't have camera
           finish();
       }
   }
 
   /**
    * Checking device has camera hardware or not
    * */
   private boolean isDeviceSupportCamera() {
       if (getApplicationContext().getPackageManager().hasSystemFeature(
               PackageManager.FEATURE_CAMERA)) {
           // this device has a camera
           return true;
       } else {
           // no camera on this device
           return false;
       }
   }
   /**
    * Launching camera app to capture image
    */
   private void captureImage() {
       Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
 
       fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
 
       intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
 
       // start the image capture Intent
       startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
//       Intent ii=new Intent(MainActivity.this,UploadActivity.class);
//      ii.putExtra("filePath", fileUri);
//     startActivity(ii);
       
   }
    
   /**
    * Launching camera app to record video
    */
   private void recordVideo() {
       Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
 
       fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
 
       // set video quality
       intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
 
       intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file// name
       
       
       
       // start the video capture Intent
       startActivityForResult(intent, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);
//		startActivity(new Intent(MainActivity.this,UploadActivity.class));
       
       
   }
 
   /**
    * Here we store the file url as it will be null after returning from camera
    * app
    */
   @Override
   protected void onSaveInstanceState(Bundle outState) {
       super.onSaveInstanceState(outState);
 
       // save file url in bundle as it will be null on screen orientation
       // changes
       outState.putParcelable("file_uri", fileUri);
   }
 
   @Override
   protected void onRestoreInstanceState(Bundle savedInstanceState) {
       super.onRestoreInstanceState(savedInstanceState);
 
       // get the file url
       fileUri = savedInstanceState.getParcelable("file_uri");
   }
 
    
 
   /**
    * Receiving activity result method will be called after closing the camera
    * */
   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       // if the result is capturing Image
       if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
           if (resultCode == RESULT_OK) {
                
               // successfully captured the image
               // launching upload activity
               launchUploadActivity(true);
                
                
           } else if (resultCode == RESULT_CANCELED) {
                
               // user cancelled Image capture
               Toast.makeText(getApplicationContext(),
                       "User cancelled image capture", Toast.LENGTH_SHORT)
                       .show();
           } else {
               // failed to capture image
               Toast.makeText(getApplicationContext(),
                       "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                       .show();
           }
        
       } else if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
           if (resultCode == RESULT_OK) {
                
               // video successfully recorded
               // launching upload activity
               launchUploadActivity(false);
            
           } else if (resultCode == RESULT_CANCELED) {
                
               // user cancelled recording
               Toast.makeText(getApplicationContext(),
                       "User cancelled video recording", Toast.LENGTH_SHORT)
                       .show();
            
           } else {
               // failed to record video
               Toast.makeText(getApplicationContext(),
            		   "Sorry! Failed to record video", Toast.LENGTH_SHORT)
                       .show();
           }
       }
   }
    
   private void launchUploadActivity(boolean isImage){
     
	   new FileUploadTask().execute(fpath);
   }
     
   /**
    * ------------ Helper Methods ---------------------- 
    * */
 
   /**
    * Creating file uri to store image/video
    */
   public Uri getOutputMediaFileUri(int type) {
	   
	   Toast.makeText(this, getOutputMediaFile(type).getAbsolutePath(), 2).show();
	   
	   
       return Uri.fromFile(getOutputMediaFile(type));
   }
 
   /**
    * returning image / video*/
   private static File getOutputMediaFile(int type) {
	   
       // External sdcard location
       File mediaStorageDir = new File(
               Environment
                       .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
               Config.IMAGE_DIRECTORY_NAME);
 
       // Create the storage directory if it does not exist
       if (!mediaStorageDir.exists()) {
           if (!mediaStorageDir.mkdirs()) {
               Log.d(TAG, "Oops! Failed create "
                       + Config.IMAGE_DIRECTORY_NAME + " directory");
               return mediaStorageDir;
           }
           return mediaStorageDir;
       }
 
       // Create a media file name
       String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
               Locale.getDefault()).format(new Date());
       File mediaFile;
       if (type == MEDIA_TYPE_IMAGE) {
           mediaFile = new File(mediaStorageDir.getPath() + File.separator
                   + "IMG_" + timeStamp + ".jpg");
       } else if (type == MEDIA_TYPE_VIDEO) {
           mediaFile = new File(mediaStorageDir.getPath() + File.separator
                   + "VID_" + timeStamp + ".mp4");
       } else {
           return null;
       }
 
       fpath=mediaFile.getAbsolutePath();
       
       Log.e("File Path",fpath);
       return mediaFile;
   }

///-------------------------------------------


// atsk
class FileUploadTask extends AsyncTask<String, Void, String> {
	//ProgressBar pbar;
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		String filePath = params[0];
		String str = "";
		try {
			// URL u=new
			// URL("http://192.168.43.152:8080/UploadWeb/UploadServlet");
//			URL u = new URL("http://" + ipAddtress
//					+ "/UploadWeb/UploadServlet");
			//URL u = new URL("http://" + ipAddtress
			//		+ ":8084/UploadWeb/UploadServlet");
//			MultipartUtility mpu = new MultipartUtility(u);
//			mpu.addFilePart("file", new File(filePath));
//		
//			byte[] result = mpu.finish();
//			str = new String(result);
			
			Log.v("=========", "=== fpath = "+fpath);
			 Intent i = new Intent(MainActivity.this, com.example.uploadandsaveimage.UploadActivity.class);
            i.putExtra("imagepath", fpath);
            startActivity(i);
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
		//pbar.setVisibility(View.INVISIBLE);
	}
}


}




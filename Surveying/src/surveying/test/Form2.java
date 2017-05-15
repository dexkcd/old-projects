package surveying.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;
import android.telephony.SmsManager;;
public class Form2 extends Activity{
	private ImageButton preview,main,reset;
	private EditText occupation,address, cellphone;
	private CheckBox relationship[] = new CheckBox[4];
	private RadioButton male, female;
	static String text,marital,sex;
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	     setContentView(R.layout.form2);
	     //Toast.makeText(context, resId, duration);
	     preview = (ImageButton)findViewById(R.id.preview);
	     main = (ImageButton)findViewById(R.id.mainMenu);
	     reset = (ImageButton)findViewById(R.id.send);
	     
	     //text areas
	     occupation = (EditText)findViewById(R.id.occupation);
	     cellphone = (EditText)findViewById(R.id.cellphoneNum);
	     address = (EditText)findViewById(R.id.address);
	     
	     //check boxes
	     relationship[0] = (CheckBox)findViewById(R.id.isSingle);
	     relationship[1] = (CheckBox)findViewById(R.id.isMarried);
	     relationship[2] = (CheckBox)findViewById(R.id.isSeparated);
	     relationship[3] = (CheckBox)findViewById(R.id.isWidowed);
	     
	     //radio buttons
	     male = (RadioButton)findViewById(R.id.male);
	     female = (RadioButton)findViewById(R.id.female);
	     
	     addListener();
	     /* Use the LocationManager class to obtain GPS locations */

	     LocationManager mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

	     LocationListener mlocListener = new MyLocationListener();


	     mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
	 }
	 
	 
	 public void addListener()
	    {
	    	final Context context = this;
	    	//image button listeners
	    	 main.setOnClickListener(new OnClickListener() {
	   		  public void onClick(View v) {
	   			  Intent intent = new Intent(context,SurveyingActivity.class);
	   			  startActivity(intent);
	   		  }
	    
	    	 });
	    	 
	    	 preview.setOnClickListener( new OnClickListener() {
				
				@Override
				public void onClick(View v) {
				     LocationManager mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

				     LocationListener mlocListener = new MyLocationListener();


				     mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
					Bundle extras = getIntent().getExtras();
					if(extras !=null) {
						String value = extras.getString("prev");					
						String details = value+"\n"+sex+"\n"+marital+"\n"+occupation.getText()+"\n"+address.getText()+"\n"+cellphone.getText()+"\n"+text;
						int duration = Toast.LENGTH_SHORT;
		 			    Toast toast = Toast.makeText(context, details, duration);
		 			    toast.show();
		 			    
					}
				}

				
			});
	    	 
	    	 reset.setOnClickListener(new OnClickListener() {
		   		  public void onClick(View v) {
		   			Bundle extras = getIntent().getExtras();
		   			//PendingIntent pi = PendingIntent.getActivity(context, 0, new Intent(context, SurveyingActivity.class), 0);                
		   	        SmsManager sms = SmsManager.getDefault();
		   	        //sms.sendTextMessage(phoneNumber, null, message, pi, null); 
		   			String value = extras.getString("prev");					
					String details = value+"\n"+sex+"\n"+marital+"\n"+occupation.getText()+"\n"+address.getText()+"\n"+cellphone.getText()+"\n"+text;
		   			//SmsManager sms = SmsManager.getDefault();
	 			    sms.sendTextMessage("09153153895", null, details, null, null); 
	 			   // pi.
	 	   			Intent intent = new Intent(context,SurveyingActivity.class);
	 	   			startActivity(intent);
		   		  }
		    
		    	 });
	    	 //end of image button listeners
	    	 
	    	 //check boxes
	    	 relationship[0].setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					relationship[1].setChecked(false);
					relationship[2].setChecked(false);
					relationship[3].setChecked(false);
					marital = "Single";
				}
			});
	    	 
	    	 relationship[1].setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						relationship[0].setChecked(false);
						relationship[2].setChecked(false);
						relationship[3].setChecked(false);
						marital = "Married";
					}
				});
	    	 relationship[2].setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						relationship[1].setChecked(false);
						relationship[0].setChecked(false);
						relationship[3].setChecked(false);
						marital = "Separated";
					}
				});
	    	 relationship[3].setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						relationship[1].setChecked(false);
						relationship[2].setChecked(false);
						relationship[0].setChecked(false);
						marital = "Widowed";
						
					}
				});	    	 
	    	 //end of check boxes
	    	 
	    	 //radio buttons
	    	 male.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					female.setChecked(false);
					sex = "M";
				}
			});
	    	 
	    	 female.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						male.setChecked(false);
						sex = "F";
					}
				});
	    	 
	    	 //end of radio buttons
	    }
	 
	 
	 
	 
	 
	 /* Class My Location Listener */

	 public class MyLocationListener implements LocationListener

	 {

	 public void onLocationChanged(Location loc)

	 {

	 loc.getLatitude();

	 loc.getLongitude();

	 text = "Latitude = " + loc.getLatitude() +

			 "\nLongitude = " + loc.getLongitude();

	 }


	 public void onProviderDisabled(String provider)

	 {

	 Toast.makeText( getApplicationContext(),

	 "Gps Disabled",

	 Toast.LENGTH_SHORT ).show();

	 }


	 public void onProviderEnabled(String provider)

	 {

	 Toast.makeText( getApplicationContext(),

	 "Gps Enabled",

	 Toast.LENGTH_SHORT).show();

	 }


	 public void onStatusChanged(String provider, int status, Bundle extras)

	 {


	 }

	 }/* End of Class MyLocationListener */
}

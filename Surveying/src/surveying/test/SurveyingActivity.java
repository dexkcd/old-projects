package surveying.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class SurveyingActivity extends Activity {
    /** Called when the activity is first created. */
	ImageButton survey, settings, about;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        survey = (ImageButton)findViewById(R.id.surveyButton);
        settings = (ImageButton)findViewById(R.id.settingsButton);
        about  = (ImageButton)findViewById(R.id.aboutButton);
        addListener();
    }
    
    public void addListener()
    {
    	final Context context = this;
    	
    	 survey.setOnClickListener(new OnClickListener() {
   		  public void onClick(View v) {
   			  Intent intent = new Intent(context,Form.class);
   			  startActivity(intent);
   		  }
    
    	 });
    	 
    	 about.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 Intent intent = new Intent(context,About.class);
	   			 startActivity(intent);
			}
		});
    	 
    	
     	 
    }
}

package surveying.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

public class Form extends Activity{
	 private ImageButton next,main,reset;
	 static private DatePicker bday;
	 static private EditText firstName,lastName;
	 static private EditText middle;
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	     setContentView(R.layout.form);
	     middle = (EditText)findViewById(R.id.middleName);
	     bday = (DatePicker)findViewById(R.id.bDay);
	     firstName = (EditText)findViewById(R.id.firstName);
	     lastName = (EditText)findViewById(R.id.lastName);
	     
	     next = (ImageButton)findViewById(R.id.next);
	     main = (ImageButton)findViewById(R.id.mainMenu);
	     reset = (ImageButton)findViewById(R.id.reset);
	     
	     
	     addListener();	     
	 }
	 
	 public void addListener()
    {
    	final Context context = this;
    	
    	 main.setOnClickListener(new OnClickListener() {
   		  public void onClick(View v) {
   			  Intent intent = new Intent(context,SurveyingActivity.class);
   			  startActivity(intent);
   		  }
    
    	 });
    	 
    	 next.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 Intent intent = new Intent(context,Form2.class);
				 String details = firstName.getText().toString()+" "+lastName.getText().toString()+" "+middle.getText().toString()+"\n";
 			     details+= bday.getYear()+"-"+(bday.getMonth()+1)+"-"+bday.getDayOfMonth(); 			    
				 intent.putExtra("prev", details);
	   			 startActivity(intent);
			}
		});
    	 reset.setOnClickListener(new OnClickListener() {
	   		  public void onClick(View v) {
	   			  Intent intent = new Intent(context,Form.class);
	   			  startActivity(intent);
	   		  }
	    
    	 });
    	 
    }
}

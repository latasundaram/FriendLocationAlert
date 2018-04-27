package com.location;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ContactFriend extends Activity {
	
	TextView nameTextView,numberTextView;
	Button callButton,smsButton;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.nearby_list);
	nameTextView=(TextView) findViewById(R.id.n_name);
	numberTextView=(TextView) findViewById(R.id.n_num);
	callButton=(Button) findViewById(R.id.call);
	
	
	smsButton=(Button) findViewById(R.id.message);
	Intent i=getIntent();
	String name=i.getStringExtra("name");
	final String number=i.getStringExtra("number");
	
	
	
	nameTextView.setText(name);
	numberTextView.setText(number);
	callButton.setOnClickListener(new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:"+number));
			startActivity(callIntent);
		}
	});
	smsButton.setOnClickListener(new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent sendIntent = new Intent(Intent.ACTION_VIEW);
			sendIntent.putExtra("sms_body", "default content"); 
			sendIntent.setType("vnd.android-dir/mms-sms");
			startActivity(sendIntent);
		}
	});
	
}
}

package com.location;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.layout;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SecurityQuesActivity extends Activity {
	EditText sEditText,ansEditText;
	Button submitButton;
	String answer,password;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.security);
		Intent i=getIntent();
		String userid=i.getStringExtra("userid");
		System.out.println(userid);
		
		sEditText = (EditText) findViewById(R.id.sques);
		ansEditText = (EditText) findViewById(R.id.sanswer);
		submitButton=(Button) findViewById(R.id.sub);
		String url=ConnectionUtility.url+"FriendServelt?usecase=forgotpassword&userid="+userid+"";
		System.out.println(url);
		String response=ConnectionUtility.send(url);
		System.out.println(response);
		if(!response.trim().equalsIgnoreCase("invalid"))
		  {
			
			try {
				JSONObject object=new JSONObject(response);
				sEditText.setText(object.getString("qname").trim());
				
				
				password=object.getString("password");
				answer=object.getString("answer");
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
			 else
			  {
				  Toast.makeText(SecurityQuesActivity.this, "Invalid answer", 3000).show();
				  
			  }
		

			
		  
		submitButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(answer.equalsIgnoreCase(ansEditText.getText().toString()))
				{
					Dialog d=new Dialog(SecurityQuesActivity.this);
					d.setTitle("Get Password");
					TextView t=new TextView(SecurityQuesActivity.this);
					t.setText(password);
					d.addContentView(t,new LayoutParams(100, 100));
					d.show();
					
				}
				else
				{
					Toast.makeText(SecurityQuesActivity.this, "invalid", 3000).show();
				}
				
			}
		}) ;
		
	}
}

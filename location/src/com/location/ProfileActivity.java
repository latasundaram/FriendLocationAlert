package com.location;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class ProfileActivity extends Activity {
	EditText userEditText,idEditText,passEditText,dEditText,mEditText,aEditText;
	Spinner sqSpinner,radSpinner;
	Button updateButton,dateButton,editButton;
	UserForm form;
	String a,val;
	ArrayList<String> list1 = null;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
	
	userEditText=(EditText)findViewById(R.id.puname);
	idEditText=(EditText)findViewById(R.id.puid);
	passEditText=(EditText)findViewById(R.id.ppwd);
	dEditText=(EditText)findViewById(R.id.pdate);
	mEditText=(EditText)findViewById(R.id.puno);
	sqSpinner=(Spinner)findViewById(R.id.pusq);
	radSpinner=(Spinner)findViewById(R.id.prad);
	dateButton=(Button)findViewById(R.id.pdatepicker);
	updateButton=(Button)findViewById(R.id.update);
	editButton=(Button)findViewById(R.id.edit);
	aEditText=(EditText)findViewById(R.id.pans);
	
	
	userEditText.setEnabled(false);
	idEditText.setEnabled(false);
	passEditText.setEnabled(false);
	dEditText.setEnabled(false);
	radSpinner.setEnabled(false);
	mEditText.setEnabled(false);
	sqSpinner.setEnabled(false);
	aEditText.setEnabled(false);
	
	
	String url=ConnectionUtility.url+"FriendServelt?usecase=getprofile&userid="+LoginActivity.userid;
	System.out.println(url);
	
	
	
	String response=ConnectionUtility.send(url);
		
	System.out.println(response);
	if(!response.equalsIgnoreCase("empty"))
	{
		try {
			JSONObject object=new JSONObject(response);
			userEditText.setText(object.getString("name"));
			idEditText.setText(object.getString("id"));
			passEditText.setText(object.getString("password"));
			dEditText.setText(object.getString("dob"));
			radSpinner.setAdapter(new ArrayAdapter<String>(ProfileActivity.this, android.R.layout.simple_expandable_list_item_1,new String[]{object.getString("radius")}));
			mEditText.setText(object.getString("number"));
			aEditText.setText(object.getString("answer"));
			sqSpinner.setAdapter(new ArrayAdapter<String>(ProfileActivity.this, android.R.layout.simple_expandable_list_item_1,new String[]{object.getString("qname")}));
            
			
			
			
			
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
	}
	
	
	
	
	/*ArrayList<String> name=new ArrayList<String>();
	ArrayList<String> id=new ArrayList<String>();
	ArrayList<String> pwd=new ArrayList<String>();
	ArrayList<String> dob=new ArrayList<String>();
	ArrayList<String> rad=new ArrayList<String>();
	ArrayList<String> mno=new ArrayList<String>();
	ArrayList<String> sq=new ArrayList<String>();
	try {
		JSONArray questionArray=new JSONArray(response);
		for(int i=0;i<questionArray.length();i++)
		{
			name.add(questionArray.getJSONObject(i).getString("name"));
			id.add(questionArray.getJSONObject(i).getString("id"));
		
		}
		
		
		
		
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}*/
	
	
	editButton.setOnClickListener(new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			userEditText.setEnabled(true);
			idEditText.setEnabled(true);
			passEditText.setEnabled(true);
			dEditText.setEnabled(true);
			radSpinner.setEnabled(true);
			//mEditText.setEnabled(false);
			sqSpinner.setEnabled(true);
			aEditText.setEnabled(true);
			String url=ConnectionUtility.url+"FriendServelt?usecase=question";
			System.out.println(url);
			String response=ConnectionUtility.send(url);
			System.out.println(response+"**********************************************");
			
			list1 = new ArrayList<String>();
			list1.add("2");
			list1.add("4");
			list1.add("6");
			list1.add("8");
			list1.add("10");
			System.out.println("llllllllllllllllllllllllllllllllllllllllllllllllllllllllll"+list1);
			ArrayAdapter<String> radAdapter = new ArrayAdapter<String>(ProfileActivity.this,android.R.layout.simple_spinner_item, list1);
			radSpinner.setAdapter(radAdapter);
			radSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					 val=list1.get(arg2);
			//		System.out.println(val);
					
				}

				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			
			final ArrayList<String> qid=new ArrayList<String>();
			ArrayList<String> qn=new ArrayList<String>();
			try {
				JSONArray questionArray=new JSONArray(response);
				for(int i=0;i<questionArray.length();i++)
				{
					qid.add(questionArray.getJSONObject(i).getString("qid"));
					qn.add(questionArray.getJSONObject(i).getString("ques"));
				}
				
				
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ProfileActivity.this,android.R.layout.simple_spinner_item, qn);
			sqSpinner.setAdapter(dataAdapter);
			sqSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					 a=qid.get(arg2);
					
				}

				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			
			
			/*Intent in2=new Intent(ProfileActivity.this,ProfileActivity.class);
			startActivity(in2);*/
			
			
		}
	});
	updateButton.setOnClickListener(new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			form=new UserForm();
			form.setDateOfBirth(dEditText.getText().toString());
			form.setPassword(passEditText.getText().toString());
			form.setUserId(idEditText.getText().toString());
			form.setUserName(userEditText.getText().toString());
			form.setSecurtyAnswer(aEditText.getText().toString());
			form.setRadius(val);
			form.setMobileNumber(mEditText.getText().toString());
			form.setSecurityQuestionId(a);
			
			if(!form.isValidSecurtyAnswer())
			{
				aEditText.setError("please enter valid answer");
			}
			else if(!form.isValidUserId())
			{
				idEditText.setError("please enter valid id");
			}
			else if(!form.isValidUserName())
			{
				userEditText.setError("enter valid name");
			}
			else if(!form.isValidPassword())
			{
				passEditText.setError("please enter valid password");
			}
			
			else if(!form.isValidDob())
			{
				dEditText.setError("enter correct date of birth");
			}
			/*else if(!form.isValidRadius())
			{
				Toast.makeText(ProfileActivity.this, "FAILED", 3000).show();
			}
			else if(!form.isValidSecurityQuestion())
			{
				Toast.makeText(ProfileActivity.this, "FAILED", 3000).show();
			}*/

			else
			{
				String url=ConnectionUtility.url+"FriendServelt?usecase=update&name="+form.getUserName()+"&id="+form.getUserId()+"&pwd="+form.getPassword()+"&dob="+form.getDateOfBirth()+"&number="+form.getMobileNumber()+"&answer="+form.getSecurtyAnswer()+"&qid="+form.getSecurityQuestionId()+"&radius="+form.getRadius();
				  System.out.println(url);
				  String response=ConnectionUtility.send(url);
				  System.out.println(response);
				  if(response.trim().equalsIgnoreCase("success"))
				  {
					  Toast.makeText(ProfileActivity.this, "SUCCESS", 3000).show();
					  /*Intent in4=new Intent(ProfileActivity.this,LoginActivity.class);
					  startActivity(in4);*/
				  }
				  else
				  {
					  Toast.makeText(ProfileActivity.this, "FAIL", 3000).show();
					  
				  }
			}
			
		}
	});
	
	}
}

package com.location;

import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class RegistrationActivity extends Activity {

	EditText userEditText, idEditText, passEditText, dEditText, rEditText,
			mEditText, ansEditText;
	Spinner sqSpinner, radSpinner;
	Button subButton, dateButton;
	String a;
	DatePickerDialog datePickerDialog;
	final static int DATE_DIALOG = 0;
	int day;
	int month;
	int year;
	String url;
	ArrayList<String> list = null;
	ArrayList<String> list1 = null;
	String val;
	UserForm form;
	MysqliteDatabase database;
	private DatePickerDialog.OnDateSetListener datePickerListener = new OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// TODO Auto-generated method stub
			year = year;
			month = monthOfYear;
			day = dayOfMonth;
			dEditText.setText(new StringBuilder().append(month + 1).append("-")
					.append(day).append("-").append(year).append(" "));

		}
	};

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);

		userEditText = (EditText) findViewById(R.id.uname);
		idEditText = (EditText) findViewById(R.id.uid);
		passEditText = (EditText) findViewById(R.id.pwd);
		dEditText = (EditText) findViewById(R.id.date);
		mEditText = (EditText) findViewById(R.id.uno);
		sqSpinner = (Spinner) findViewById(R.id.usq);
		subButton = (Button) findViewById(R.id.submit);
		dateButton = (Button) findViewById(R.id.datepicker);
		radSpinner=(Spinner)findViewById(R.id.rad);
		ansEditText=(EditText)findViewById(R.id.sans);
		database=new MysqliteDatabase(RegistrationActivity.this);
		if(database.getUserId()!=null)
		{
			Toast.makeText(RegistrationActivity.this, "already registRATION", 3000).show();
			startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
		}
			
		url=ConnectionUtility.url+"FriendServelt?usecase=question";
		System.out.println(url);
		String response=ConnectionUtility.send(url);
		System.out.println(response+"**********************************************");
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
		
		
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(RegistrationActivity.this,android.R.layout.simple_spinner_item, qn);
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
		
		
		list1 = new ArrayList<String>();
		list1.add("2");
		list1.add("4");
		list1.add("6");
		list1.add("8");
		list1.add("10");
		System.out.println("llllllllllllllllllllllllllllllllllllllllllllllllllllllllll"+list1);
		ArrayAdapter<String> radAdapter = new ArrayAdapter<String>(RegistrationActivity.this,android.R.layout.simple_spinner_item, list1);
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
		
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		dEditText.setText(new StringBuilder()
		// Month is 0 based, just add 1
		.append(month + 1).append("-").append(day).append("-")
		.append(year).append(" "));
		
		dateButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(DATE_DIALOG);
			}
		});
	
	subButton.setOnClickListener(new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String url=null;
					
			
			form=new UserForm();
			form.setDateOfBirth(dEditText.getText().toString().trim());
			form.setLatitude("0".trim());
			form.setLongitude("0".trim());
			form.setUserId(idEditText.getText().toString().trim());
			form.setUserName(userEditText.getText().toString().trim());
			form.setPassword(passEditText.getText().toString().trim());
			form.setSecurtyAnswer(ansEditText.getText().toString().trim());
			form.setMobileNumber(mEditText.getText().toString().trim());
			form.setRadius(val.trim());
			form.setSecurityQuestionId(a.trim());
			
			if(!form.isValidSecurtyAnswer())
			{
				ansEditText.setError("please enter valid answer");
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
			
			else if(!form.isValidMobileNumber())
			{
				mEditText.setError(" enter valid mobile number");
			}
			else if(!form.isValidDob())
			{
				dEditText.setError("enter correct date of birth");
			}
			else if(!form.isValidLatitude())
			{
				//idEditText.setError("");
			}
			else if(!form.isValidLongitude())
			{
				//idEditText.setError("");
			}
			else if(!form.isValidRadius())
			{
				Toast.makeText(RegistrationActivity.this, "FAILED", 3000).show();
			}
			else if(!form.isValidSecurityQuestion())
			{
				Toast.makeText(RegistrationActivity.this, "FAILED", 3000).show();
			}
			else
			{
				url=ConnectionUtility.url+"FriendServelt?usecase=registration&usern="+form.getUserName()+"&uid="+form.getUserId()+"&pwd="+form.getPassword()+"&dob="+form.getDateOfBirth()+"&mno="+form.getMobileNumber()+"&ans="+form.getSecurtyAnswer()+"&qid="+form.getSecurityQuestionId()+"&radius="+form.getRadius()+"&latitude="+form.getLatitude()+"&longitude="+form.getLongitude();
				  System.out.println(url);
				  String response=ConnectionUtility.send(url);
				  System.out.println(response);
				  if(response.trim().equalsIgnoreCase("success"))
				  {
					  Toast.makeText(RegistrationActivity.this, "SUCCESSS", 3000).show();
					  if(database.insertUserDetails(form.getMobileNumber())>0)
					  {
						  startService(new Intent(RegistrationActivity.this,LocationUpdateService.class));
						  //startService(new Intent(RegistrationActivity.this,FindingNearestfriendsService.class));
						  Intent in4=new Intent(RegistrationActivity.this,LoginActivity.class);
						  startActivity(in4);  
					  }
					  else
					  {
						  Toast.makeText(RegistrationActivity.this, "Sqlite  FAIL", 3000).show();
					  }
				  }
				  else
				  {
					  Toast.makeText(RegistrationActivity.this, "FAIL", 3000).show();
					  
				  }
			}
		
	  
			
			
		}
	});
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		switch (id) {
		case DATE_DIALOG:
			return new DatePickerDialog(this, datePickerListener, year, month,
					day);

		}

		return super.onCreateDialog(id);
	}
}

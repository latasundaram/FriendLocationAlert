package com.location;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ListView;
import android.widget.Toast;

public class FriendlistActivity extends Activity {
	ListView friendListView;
	Context context;
	JSONArray array;
	ArrayList<String> name, number, status,lat,lon;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friendlist);
		context=this;
		
		friendListView=(ListView) findViewById(R.id.friendlist);
		getFriend();
		
		
		/*Cursor cursor = getContentResolver().query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
				null, null);
		while (cursor.moveToNext()) {
			String name = cursor
					.getString(cursor
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
			Toast.makeText(FriendlistActivity.this, name, 3000).show();
			String phoneNumber = cursor
					.getString(cursor
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			Toast.makeText(FriendlistActivity.this, phoneNumber, 3000).show();
		}*/

	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		getFriend();
	}
	
	void getFriend()
	{
		String urlString=ConnectionUtility.url+"FriendServelt?usecase=flist&userid="+LoginActivity.userid;
		System.out.println(urlString);
		String response=ConnectionUtility.send(urlString);
		
		
		name=new ArrayList<String>();
		number=new ArrayList<String>();
		status=new ArrayList<String>();
		lat=new ArrayList<String>();
		lon=new ArrayList<String>();
		if(response.trim().equalsIgnoreCase("empty"))
		{
			Toast.makeText(context, "please add your friends", 3000).show();
		}
		else if(response.trim().equalsIgnoreCase("error"))
		{
			Toast.makeText(context, "error", 3000).show();
		}
		else
		{
			try {
				array=new JSONArray(response);
				for (int i = 0; i < array.length(); i++) {
					name.add(array.getJSONObject(i).getString("name"));
					number.add(array.getJSONObject(i).getString("number"));
					status.add(array.getJSONObject(i).getString("status"));
					lat.add(array.getJSONObject(i).getString("lat"));
					lon.add(array.getJSONObject(i).getString("lon"));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		CustomizedAdapter adapter=new CustomizedAdapter(context, R.layout.customview, name, number, status,lat,lon);
		friendListView.setAdapter(adapter);	
	}
}

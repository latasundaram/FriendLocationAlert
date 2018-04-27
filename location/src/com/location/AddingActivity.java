package com.location;

import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class AddingActivity extends Activity {
	JSONArray array;
	ArrayList<String> name, number, status;
	private ListView friendlistView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friendlist);

		// /
		System.out.println("ddddddddddddddddddd");
		friendlistView = (ListView) findViewById(R.id.friendlist);
		array = new JSONArray();

		Cursor cursor = getContentResolver().query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
				null, null);
		int j=0;
		while (cursor.moveToNext()&&j<10) {
j++;
			JSONObject object = new JSONObject();

			String name = cursor
					.getString(cursor
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
			// Toast.makeText(AddingActivity.this, name, 3000).show();
			String phoneNumber = cursor
					.getString(cursor
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			// Toast.makeText(AddingActivity.this, phoneNumber, 3000).show();

			try {
				object.put("number", phoneNumber);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Toast.makeText(AddingActivity.this, phoneNumber, 3000).show();
			array.put(object);
		}

		String urlString = ConnectionUtility.url
				+ "FriendServelt?usecase=checkfriends&contacts="
				+ URLEncoder.encode(array.toString()) + "&uid="
				+ LoginActivity.userid;
		System.out.println("##########################"+urlString);
		Toast.makeText(AddingActivity.this, "" + urlString, 3000).show();
		String response = ConnectionUtility.send(urlString);
		Toast.makeText(AddingActivity.this, response, 3000).show();
		name = new ArrayList<String>();
		number = new ArrayList<String>();
		status = new ArrayList<String>();
		try {
			array = new JSONArray(response);
			for (int i = 0; i < array.length(); i++) {
				name.add(array.getJSONObject(i).getString("name"));
				number.add(array.getJSONObject(i).getString("number"));

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FriendCustomizedAdapter adapter = new FriendCustomizedAdapter(
				AddingActivity.this, R.layout.friendcustomview, name, number);
		friendlistView.setAdapter(adapter);

	}
}
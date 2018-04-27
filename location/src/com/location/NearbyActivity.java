package com.location;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

public class NearbyActivity extends Activity {
	ListView nearByFriendsListView;
	JSONArray array;
	ArrayList<String> name, number, status, lat, lon;
	LocationManager locationManager;
	Location location;
	double latitude, longitude;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nearby_friends);
		nearByFriendsListView = (ListView) findViewById(R.id.n_friendlist);
		getData();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				NearbyActivity.this, android.R.layout.simple_list_item_1,name);
		nearByFriendsListView.setAdapter(adapter);
		nearByFriendsListView
				.setOnItemLongClickListener(new OnItemLongClickListener() {

					public boolean onItemLongClick(AdapterView<?> arg0,
							View arg1, int arg2, long arg3) {
						// TODO Auto-generated method stub

						Intent i = new Intent(NearbyActivity.this,ContactFriend.class);
						i.putExtra("name", name.get(arg2));
						i.putExtra("number", number.get(arg2));
						startActivity(i);
						return false;
					}
				});

	}

	void getData() {
		String urlString = ConnectionUtility.url
				+ "FriendServelt?usecase=flist&userid=" + LoginActivity.userid;
		System.out.println(urlString);
		String response = ConnectionUtility.send(urlString);
System.out.println("ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
		name = new ArrayList<String>();
		number = new ArrayList<String>();
		status = new ArrayList<String>();
		lat = new ArrayList<String>();
		lon = new ArrayList<String>();
		System.out.println(response+"**************************************************************");
		if (response.trim().equalsIgnoreCase("empty")) {
			Toast
					.makeText(NearbyActivity.this, "please add your friends",
							3000).show();
		} else if (response.trim().equalsIgnoreCase("error")) {
			Toast.makeText(NearbyActivity.this, "error", 3000).show();
		} else {
			try {
				array = new JSONArray(response);
				locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
				if (locationManager
						.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
					location = locationManager
							.getLastKnownLocation(LocationManager.GPS_PROVIDER);
					latitude = location.getLatitude();
					longitude = location.getLongitude();

				} else if (locationManager
						.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

					location = locationManager
							.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
					latitude = location.getLatitude();
					longitude = location.getLongitude();
				}
				if (location != null) {

					for (int i = 0; i < array.length(); i++) {
						System.out.println(distance(Double.parseDouble(array
								.getJSONObject(i).getString("lat")), Double
								.parseDouble(array.getJSONObject(i).getString(
										"lon")), latitude, longitude));

						if (distance(Double.parseDouble(array.getJSONObject(i)
								.getString("lat")), Double.parseDouble(array
								.getJSONObject(i).getString("lon")), latitude,
								longitude) <= 220) {
							System.out.println("#################################################");
							name.add(array.getJSONObject(i).getString("name"));
							number.add(array.getJSONObject(i).getString("number"));
							System.out.println(array);
							System.out.println(name);
							System.out.println(number);
							
							/*
							 * status.add(array.getJSONObject(i).getString("status"
							 * ));
							 * lat.add(array.getJSONObject(i).getString("lat"));
							 * lon.add(array.getJSONObject(i).getString("lon"));
							 */}

					}
				} else {
					Toast.makeText(NearbyActivity.this,
							"location is not avaliable", 3000).show();
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
		public double distance(Double latitude, Double longitude, double e, double f) {
		double d2r = Math.PI / 180;

		double dlong = (longitude - f) * d2r;
		double dlat = (latitude - e) * d2r;
		double a = Math.pow(Math.sin(dlat / 2.0), 2) + Math.cos(e * d2r)
				* Math.cos(latitude * d2r) * Math.pow(Math.sin(dlong / 2.0), 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double d = 6367 * c;
		return d;

	}
		private void addDefaultNotification(){
	        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
	         
	        int icon = R.drawable.ic_launcher;
	        CharSequence text = "Notification Text";
	        CharSequence contentTitle = "Notification Title";
	        CharSequence contentText = "Sample notification text.";
	        long when = System.currentTimeMillis();
	         
	        Intent intent = new Intent(this, NotificationViewer.class);
	        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);
	        Notification notification = new Notification(icon,text,when);
	         
	        long[] vibrate = {0,100,200,300};
	        notification.vibrate = vibrate;
	         
	        notification.ledARGB = Color.RED;
	        notification.ledOffMS = 300;
	        notification.ledOnMS = 300;
	         
	        notification.defaults |= Notification.DEFAULT_LIGHTS;
	        //notification.flags |= Notification.FLAG_SHOW_LIGHTS;
	         
	        notification.setLatestEventInfo(this, contentTitle, contentText, contentIntent);
	         
	        notificationManager.notify(Constants.NOTIFICATION_ID, notification);
	    }

}

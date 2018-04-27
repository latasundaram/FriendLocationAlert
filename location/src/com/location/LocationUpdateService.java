package com.location;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.widget.Toast;

public class LocationUpdateService extends Service {
	Location location;
	JSONArray array;
	ArrayList<String> name, number, status, lat, lon;
	LocationManager locationManager;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		System.out.println("onbinder");
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		System.out.println("oncreate");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub

		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				double latitude = 0.0;
				double longitude = 0.0;

				LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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

					String urlString = ConnectionUtility.url
							+ "FriendServelt?usecase=flist&userid="
							+ LoginActivity.userid;
					System.out.println(urlString);
					String response = ConnectionUtility.send(urlString);

					name = new ArrayList<String>();
					number = new ArrayList<String>();
					status = new ArrayList<String>();
					lat = new ArrayList<String>();
					lon = new ArrayList<String>();
					if (response.trim().equalsIgnoreCase("empty")) {
						/*
						 * Toast .makeText(NearbyActivity.this,
						 * "please add your friends", 3000).show();
						 */
						System.out.println("please add your friends");
					} else if (response.trim().equalsIgnoreCase("error")) {
						// Toast.makeText(NearbyActivity.this, "error",
						// 3000).show();
						System.out.println("error");
					} else {
						try {
							array = new JSONArray(response);
							for (int i = 0; i < array.length(); i++) {
								System.out.println(distance(Double
										.parseDouble(array.getJSONObject(i)
												.getString("lat")), Double
										.parseDouble(array.getJSONObject(i)
												.getString("lon")), latitude,
										longitude));

								if (distance(Double.parseDouble(array
										.getJSONObject(i).getString("lat")),
										Double.parseDouble(array.getJSONObject(
												i).getString("lon")), latitude,
										longitude) <= 220) {
									System.out
											.println("#################################################");

									name.add(array.getJSONObject(i).getString(
											"name"));
									number.add(array.getJSONObject(i)
											.getString("number"));

									addDefaultNotification();

									System.out.println(array);
									System.out.println(name);
									System.out.println(number);

									/*
									 * status.add(array.getJSONObject(i).getString
									 * ("status" ));
									 * lat.add(array.getJSONObject(
									 * i).getString("lat"));
									 * lon.add(array.getJSONObject
									 * (i).getString("lon"));
									 */}

							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						String userid = new MysqliteDatabase(
								LocationUpdateService.this).getUserId();
						if (userid != null)

						{
							String url = ConnectionUtility.url
									+ "FriendServelt?usecase=getLocation&latitude="
									+ latitude + "&longitude=" + longitude
									+ "&mob=" + userid;
							System.out.println(url);
							String response1 = ConnectionUtility.send(url);
							System.out.println(response1);
						} else {
							Toast.makeText(LocationUpdateService.this,
									"user not avaliable", 3000).show();
						}

					}
				} else {
					Toast.makeText(LocationUpdateService.this,
							"location not avaliable", 3000).show();
				}

			}
		}, 1000, 30000);

		return START_NOT_STICKY;
	}

	private void addDefaultNotification() {
		NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		int icon = R.drawable.ic_launcher;
		CharSequence text = "Notification Text";
		CharSequence contentTitle = "Notification Title";
		CharSequence contentText = "Number of friends." + name.size();
		long when = System.currentTimeMillis();

		Intent intent = new Intent(this, NotificationViewer.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				intent, 0);
		Notification notification = new Notification(icon, text, when);

		long[] vibrate = { 0, 100, 200, 300 };
		notification.vibrate = vibrate;

		notification.ledARGB = Color.RED;
		notification.ledOffMS = 300;
		notification.ledOnMS = 300;

		notification.defaults |= Notification.DEFAULT_LIGHTS;
		// notification.flags |= Notification.FLAG_SHOW_LIGHTS;

		notification.setLatestEventInfo(this, contentTitle, contentText,
				contentIntent);

		notificationManager.notify(Constants.NOTIFICATION_ID, notification);
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
}

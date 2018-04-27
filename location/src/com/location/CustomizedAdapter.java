package com.location;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class CustomizedAdapter extends ArrayAdapter<String> {

	ArrayList<String> name, number, status,lat,lon;
	Context context;
	TextView nameTextView;
	TextView numberTextView;
	ToggleButton trackingStatusToggleButton;
	Button mapButton;
	Location location;

	public CustomizedAdapter(Context context, int textViewResourceId,
			ArrayList<String> name, ArrayList<String> number,
			ArrayList<String> status, ArrayList<String> lat, ArrayList<String> lon) {
		super(context, textViewResourceId);

		this.name = name;
		this.context = context;
		this.number = number;
		this.status = status;
		this.lat=lat;
		this.lon=lon;

		// TODO Auto-generated constructor stub

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return name.size();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = convertView;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		v = inflater.inflate(R.layout.customview, null);

		nameTextView = (TextView) v.findViewById(R.id.fname);
		numberTextView = (TextView) v.findViewById(R.id.number);
		trackingStatusToggleButton = (ToggleButton) v.findViewById(R.id.track);
		mapButton=(Button)v.findViewById(R.id.map);
		mapButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				double latitude = 0.0;
				double longitude = 0.0;

				LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
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
				if(location!=null)
				{
					Toast.makeText(context, "map "+lat.get(position)+lon.get(position), 3000).show();
					Intent i=new Intent(Intent.ACTION_VIEW,Uri.parse("http://maps.google.com/maps?daddr="+latitude+","+longitude+"+to:"+lat.get(position)+","+lon.get(position)));
						context.startActivity(i);
				}
				else
				{
					Toast.makeText(context, "map ", 3000).show();
				}
			

			}
		});
		nameTextView.setText(name.get(position));
				numberTextView.setText(number.get(position));
		if (!status.get(position).trim().equalsIgnoreCase("off")) {
			trackingStatusToggleButton.setChecked(true);
			mapButton.setEnabled(true);
		} else {
			trackingStatusToggleButton.setChecked(false);
			mapButton.setEnabled(false);
		}
		trackingStatusToggleButton
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked) {
							String urlString = ConnectionUtility.url
									+ "FriendServelt?usecase=setstatus&fnumber="
									+ number.get(position)
									+ "&status=on&number="
									+ LoginActivity.userid;
							String response = ConnectionUtility.send(urlString);
							if (response.trim().equalsIgnoreCase("success")) {
								Toast.makeText(context, "tracking is on", 3000)
										.show();
							} else {
								Toast.makeText(context, "error", 3000).show();
							}
						} else if (!isChecked) {
							String urlString = ConnectionUtility.url
									+ "FriendServelt?usecase=setstatus&fnumber="
									+ number.get(position)
									+ "&status=off&number="
									+ LoginActivity.userid;
							String response = ConnectionUtility.send(urlString);
							if (response.trim().equalsIgnoreCase("success")) {
								Toast
										.makeText(context, "tracking is off",
												3000).show();
							} else {
								Toast.makeText(context, "error", 3000).show();
							}
						}

					}
				});

		return v;
	}

}

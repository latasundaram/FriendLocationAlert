package com.location;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MenuActivity extends TabActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		Resources resources = getResources();
		TabHost host = getTabHost();
		Intent addIntent = new Intent(MenuActivity.this, AddingActivity.class);
		// 1
		Intent profileIntent = new Intent(MenuActivity.this,
				ProfileActivity.class);
		// 2
		Intent friendIntent = new Intent(MenuActivity.this,
				FriendlistActivity.class);
		// 3

		Intent nearbyIntent = new Intent(MenuActivity.this,
				NearbyActivity.class);

		TabSpec addTabSpec = host.newTabSpec(" ");
		addTabSpec.setIndicator(" ", resources.getDrawable(R.drawable.images))
				.setContent(addIntent);
		// 1
		TabSpec profileTabSpec = host.newTabSpec(" ");
		profileTabSpec.setIndicator("",
				resources.getDrawable(R.drawable.profile)).setContent(
				profileIntent);
		// 2
		TabSpec friendTabSpec = host.newTabSpec(" ");
		friendTabSpec.setIndicator("", resources.getDrawable(R.drawable.flist))
				.setContent(friendIntent);

		// 3
		TabSpec nearbyTabSpec = host.newTabSpec(" ");
		nearbyTabSpec.setIndicator("",
				resources.getDrawable(R.drawable.nearbyfriendslogo))
				.setContent(nearbyIntent);

		host.addTab(addTabSpec);
		// 1
		host.addTab(profileTabSpec);
		// 2
		host.addTab(friendTabSpec);

		// 3
		host.addTab(nearbyTabSpec);

		host.setCurrentTab(1);

	}

}

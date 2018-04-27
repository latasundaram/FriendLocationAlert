package com.location;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class LocationActivity extends Activity {
	/** Called when the activity is first created. */
	ProgressBar homebar;
	ImageView img;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		homebar = (ProgressBar) findViewById(R.id.progressBar1);
		img = (ImageView) findViewById(R.id.imageView1);
		try {
			new Thread() {
				public void run() {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						Intent n = new Intent(LocationActivity.this,
								LoginActivity.class);
						startActivity(n);
					}
				}
			}.start();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
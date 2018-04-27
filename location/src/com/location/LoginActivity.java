package com.location;

import javax.security.auth.PrivateCredentialPermission;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class LoginActivity extends Activity {

	EditText userEditText, passwordEditText;
	Button loginButton, signUpButton;
	CheckBox forgotPasswordCheckBox;
	UserForm form;
	static String userid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_page);

		userEditText = (EditText) findViewById(R.id.eid);
		passwordEditText = (EditText) findViewById(R.id.epwd);
		loginButton = (Button) findViewById(R.id.login);
		signUpButton = (Button) findViewById(R.id.signup);
		forgotPasswordCheckBox = (CheckBox) findViewById(R.id.fpwd);
		signUpButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent in = new Intent(LoginActivity.this,
						RegistrationActivity.class);
				startActivity(in);

			}
		});
		loginButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

				form = new UserForm();
				form.setUserId(userEditText.getText().toString().trim());
				System.out
						.println(passwordEditText.getText().toString().trim());
				form.setPassword(passwordEditText.getText().toString().trim());

				if (!form.isValidUserId()) {
					userEditText.setError("enter valid id");
				} else if (!form.isValidPassword()) {
					passwordEditText.setError("please enter valid password");
				} else {
					String url = ConnectionUtility.url
							+ "FriendServelt?usecase=login&userid="
							+ form.getUserId() + "&" + "password="
							+ form.getPassword();
					String response = ConnectionUtility.send(url);
					if (response.trim().equalsIgnoreCase("success")) {
						userid = form.getUserId();
						Intent in1 = new Intent(LoginActivity.this,
								MenuActivity.class);
						startActivity(in1);
					} else {
						Toast.makeText(LoginActivity.this, "INVALID", 3000)
								.show();
					}
				}

			}
		});

		forgotPasswordCheckBox
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked) {
							form = new UserForm();
							form.setUserId(userEditText.getText().toString()
									.trim());
							if (form.isValidUserId()) {

								Intent in5 = new Intent(LoginActivity.this,
										SecurityQuesActivity.class);
								in5.putExtra("userid", form.getUserId());
								startActivity(in5);
							} else {
								userEditText
										.setError("please enter valid userid");
							}
						}

					}

				});

	}

}

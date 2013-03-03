package com.helias.myapplication;

//import jhu.spring2013.networks.lhz.DBox.DisplayMessageActivity;
//mport jhu.spring2013.networks.lhz.DBox.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	private Button registerButton;
	private Button loginButton;
	private EditText usernameField;
	private EditText passwordField;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initButtons();
		initTextFields();
	}
	
	/**
	 * Initialize the buttons and create listeners
	 */
	private void initButtons(){
		registerButton = (Button)findViewById(R.id.registerButton);
		loginButton = (Button)findViewById(R.id.loginButton);
		registerButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view) {
				//go to register activity
				//startActivity(new Intent("com.helias.myapplication.DisplayMessageActivity"));
				sendMessage(view);
			}
		});
		loginButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view) {
				//verify login credentials
				//if verified, then go to file listing activity
			}
		});
	}
	public void sendMessage(View view) {
	    // Do something in response to button
		Intent intent = new Intent(this, DisplayMessageActivity.class);
		startActivity(intent);
	}

	/**
	 * Initialize the username/password text fields
	 */
	private void initTextFields(){
		usernameField = (EditText)findViewById(R.id.usernameEditText);
		passwordField = (EditText)findViewById(R.id.passwordEditText);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
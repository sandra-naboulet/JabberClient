package etna.pmob.jabberclient.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import etna.pmob.jabberclient.R;
import etna.pmob.jabberclient.network.ConnectionManager;
import etna.pmob.jabberclient.ui.LoginHandler;

public class LoginActivity extends Activity implements LoginHandler {

	RelativeLayout layout = null;
	Button loginButton = null;
	EditText usernameEditText = null;
	EditText passwordEditText = null;
	TextView signupTextView = null;

	ConnectionManager connectionManager;
	private boolean isLogging = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		layout = (RelativeLayout) RelativeLayout.inflate(this,
				R.layout.activity_login, null);

		setContentView(layout);

		usernameEditText = (EditText) layout.findViewById(R.id.login_username);
		passwordEditText = (EditText) layout.findViewById(R.id.login_password);

		loginButton = (Button) layout.findViewById(R.id.login_button);
		loginButton.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();

				if (action == MotionEvent.ACTION_UP) {
					if (!isLogging) {
						connectionManager.login(LoginActivity.this,
								usernameEditText.getText().toString(),
								passwordEditText.getText().toString());
						isLogging = true;
					}

				}
				return true;
			}
		});

		signupTextView = (TextView) layout.findViewById(R.id.signup_link);
		signupTextView.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();

				if (action == MotionEvent.ACTION_UP) {
					Intent intent = new Intent(LoginActivity.this,
							SignupActivity.class);
					startActivity(intent);
				}
				return true;
			}
		});

		connectionManager = ConnectionManager.getInstance();
		connectionManager.start(); // connection to the server

	}

	// UPDATE UI METHODS

	@Override
	public void loading() {
		// TODO Auto-generated method stub

	}

	@Override
	public void isLogged(boolean is) {
		if (is) {
			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			startActivity(intent);
		} else {
			usernameEditText.setText("");
			passwordEditText.setText("");
			// connectionManager.restart();
		}
		isLogging = false;
	}

	@Override
	public Activity getActivity() {
		return this;
	}

	@Override
	public void displayToast(String message) {

		Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_LONG);
		toast.show();
	}

	@Override
	public void isDisconnected(boolean is) {

	}
}

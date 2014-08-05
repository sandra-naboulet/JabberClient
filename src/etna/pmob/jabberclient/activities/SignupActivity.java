package etna.pmob.jabberclient.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import etna.pmob.jabberclient.R;
import etna.pmob.jabberclient.network.ConnectionManager;
import etna.pmob.jabberclient.ui.SignupHandler;

public class SignupActivity extends Activity implements SignupHandler {

	RelativeLayout layout = null;
	Button signupButton = null;
	EditText usernameEditText = null;
	EditText passwordEditText = null;
	EditText passwordConfirmEditText = null;

	ConnectionManager connectionManager;
	private boolean isRegistering = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		layout = (RelativeLayout) RelativeLayout.inflate(this,
				R.layout.activity_signup, null);

		setContentView(layout);

		usernameEditText = (EditText) layout.findViewById(R.id.signup_username);
		passwordEditText = (EditText) layout.findViewById(R.id.signup_password);
		passwordConfirmEditText = (EditText) layout
				.findViewById(R.id.signup_confirm_password);

		signupButton = (Button) layout.findViewById(R.id.signup_button);
		signupButton.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();

				if (action == MotionEvent.ACTION_UP) {
					if (!isRegistering) {
						if (usernameEditText.getText().toString().isEmpty()) {
							displayToast(getResources().getString(
									R.string.signup_username_empty));
							return true;
						} else if (passwordEditText.getText().toString()
								.isEmpty()) {
							displayToast(getResources().getString(
									R.string.signup_password_empty));
							return true;
						} else if (!passwordEditText
								.getText()
								.toString()
								.equals(passwordConfirmEditText.getText()
										.toString())) {
							displayToast(getResources().getString(
									R.string.signup_passwords_dont_match));
							return true;
						}
						connectionManager.register(usernameEditText.getText()
								.toString(), passwordEditText.getText()
								.toString());
						isRegistering = true;
					}

				}
				return true;
			}
		});

		connectionManager = ConnectionManager.getInstance();
		connectionManager.setUiHandler(this);
		connectionManager.start(); // connection to the server

	}

	// UPDATE UI METHODS

	@Override
	public void isRegistered(boolean is) {
		if (is) {
			Intent intent = new Intent(SignupActivity.this, MainActivity.class);
			startActivity(intent);
		} else {
			//usernameEditText.setText("");
			//passwordEditText.setText("");
			//passwordConfirmEditText.setText("");
			// connectionManager.restart();
		}
		isRegistering = false;
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

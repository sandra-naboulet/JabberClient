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

public class MyProfileActivity extends Activity implements SignupHandler {

	RelativeLayout layout = null;
	Button signupButton = null;
	EditText usernameEditText = null;
	EditText passwordEditText = null;
	EditText passwordConfirmEditText = null;

	ConnectionManager connectionManager;

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

				}
				return true;
			}
		});

		connectionManager = ConnectionManager.getInstance();
		connectionManager.start(); // connection to the server

	}

	// UPDATE UI METHODS

	@Override
	public void isRegistered(boolean is) {
		if (is) {
			Intent intent = new Intent(MyProfileActivity.this,
					MainActivity.class);
			startActivity(intent);
		} else {
			// usernameEditText.setText("");
			// passwordEditText.setText("");
			// passwordConfirmEditText.setText("");
			// connectionManager.restart();
		}

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

package etna.pmob.jabberclient.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import etna.pmob.jabberclient.R;
import etna.pmob.jabberclient.datas.Contact;
import etna.pmob.jabberclient.network.ConnectionManager;
import etna.pmob.jabberclient.ui.ActivityHandler;

public class ProfileActivity extends Activity implements ActivityHandler {

	LinearLayout layout = null;
	TextView headerNameTextView = null;
	TextView nameTextView = null;
	TextView emailIdTextView = null;

	ConnectionManager connectionManager;

	Contact contact;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		layout = (LinearLayout) LinearLayout.inflate(this,
				R.layout.activity_contact_profile, null);

		setContentView(layout);

		// Parameters
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			contact = new Contact(extras.getString("contactEmailId"),
					extras.getString("contactName"));
		}

		headerNameTextView = (TextView) layout
				.findViewById(R.id.profile_header_name);
		headerNameTextView.setText(contact.getName() + "'s profile");
		
		nameTextView = (TextView) layout.findViewById(R.id.profile_name);
		nameTextView.setText(contact.getName());
		
		emailIdTextView = (TextView) layout.findViewById(R.id.profile_email);
		emailIdTextView.setText(contact.getEmailId());

		connectionManager = ConnectionManager.getInstance();
		connectionManager.start(); // connection to the server

	}

	// UPDATE UI METHODS

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

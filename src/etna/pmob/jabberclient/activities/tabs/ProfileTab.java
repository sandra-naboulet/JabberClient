package etna.pmob.jabberclient.activities.tabs;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import etna.pmob.jabberclient.R;
import etna.pmob.jabberclient.network.ConnectionManager;
import etna.pmob.jabberclient.util.Session;

public class ProfileTab extends Fragment {

	LinearLayout layout = null;
	TextView nameTextView = null;
	TextView emailIdTextView = null;

	Session session;
	ConnectionManager connectionManager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_profile_tab,
				container, false);

		session = Session.getInstance();

		nameTextView = (TextView) rootView.findViewById(R.id.my_profile_name);
		
		emailIdTextView = (TextView) rootView
				.findViewById(R.id.my_profile_email);
		emailIdTextView.setText(session.getEmailId());
		

		//connectionManager = ConnectionManager.getInstance();
		//connectionManager.start(); // connection to the server
		
		

		return rootView;
	}
	
	
}

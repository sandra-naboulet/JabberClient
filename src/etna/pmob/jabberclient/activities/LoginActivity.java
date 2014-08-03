package etna.pmob.jabberclient.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import etna.pmob.jabberclient.R;
import etna.pmob.jabberclient.network.ConnectionManager;
import etna.pmob.jabberclient.ui.UIHandler;

public class LoginActivity extends Activity implements UIHandler {

	RelativeLayout layout = null;
	Button loginButton = null;

	ConnectionManager connectionManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		layout = (RelativeLayout) RelativeLayout.inflate(this,
				R.layout.activity_login, null);

		setContentView(layout);

		connectionManager = new ConnectionManager();

		loginButton = (Button) layout.findViewById(R.id.login_button);
		loginButton.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();

				if (action == MotionEvent.ACTION_UP) {
					connectionManager.start();
				}
				return true;
			}
		});

		connectionManager = ConnectionManager.getInstance();
		connectionManager.setUiHandler(this);

	}

	// UPDATE UI METHODS

	@Override
	public void loading() {
		// TODO Auto-generated method stub

	}

	@Override
	public void noInternet() {
		// TODO Auto-generated method stub

	}

	@Override
	public void connected(boolean connected) {
		// TODO Auto-generated method stub

	}

	@Override
	public Activity getActivity() {
		return this;
	}
}

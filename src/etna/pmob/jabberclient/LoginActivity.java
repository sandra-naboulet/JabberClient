package etna.pmob.jabberclient;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

public class LoginActivity extends Activity {

	RelativeLayout layout = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		layout = (RelativeLayout) RelativeLayout.inflate(this,
				R.layout.activity_login, null);

		setContentView(layout);

	}

}

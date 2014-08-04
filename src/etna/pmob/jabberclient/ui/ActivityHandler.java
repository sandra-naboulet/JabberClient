package etna.pmob.jabberclient.ui;

import android.app.Activity;

public interface ActivityHandler {
	Activity getActivity();

	void isDisconnected(boolean is);

	void displayToast(String message);
}

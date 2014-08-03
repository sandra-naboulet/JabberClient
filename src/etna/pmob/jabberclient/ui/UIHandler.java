package etna.pmob.jabberclient.ui;

import android.app.Activity;

public interface UIHandler {

	void loading();

	void noInternet();

	void connected(boolean connected);

	Activity getActivity();
}

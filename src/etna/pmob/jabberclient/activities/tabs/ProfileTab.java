package etna.pmob.jabberclient.activities.tabs;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import etna.pmob.jabberclient.R;

public class ProfileTab extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_profile_tab,
				container, false);
		return rootView;
	}
}

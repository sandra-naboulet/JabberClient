package etna.pmob.jabberclient.util;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Fragment;
import android.app.FragmentTransaction;
import etna.pmob.jabberclient.R;

public class TabListener implements ActionBar.TabListener {

	Fragment currentTab;

	public TabListener(Fragment fragment) {
		this.currentTab = fragment;
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		ft.replace(R.id.tabs_container, currentTab);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		ft.remove(currentTab);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}
}

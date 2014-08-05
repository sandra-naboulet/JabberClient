package etna.pmob.jabberclient.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;
import etna.pmob.jabberclient.R;
import etna.pmob.jabberclient.activities.tabs.ContactsTab;
import etna.pmob.jabberclient.activities.tabs.HistoryTab;
import etna.pmob.jabberclient.activities.tabs.ProfileTab;
import etna.pmob.jabberclient.ui.MainHandler;
import etna.pmob.jabberclient.util.TabListener;

public class MainActivity extends Activity implements MainHandler {

	ActionBar.Tab HistoryTab, ContactsTab, ProfileTab;
	Fragment historyTabFragment = new HistoryTab();
	Fragment contactsTabFragment = new ContactsTab();
	Fragment profileTabFragment = new ProfileTab();

	FrameLayout layout = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		layout = (FrameLayout) FrameLayout.inflate(this,
				R.layout.activity_main, null);

		setContentView(layout);

		// Init tabs
		ActionBar actionBar = getActionBar();

		// Hide Actionbar Icon
		actionBar.setDisplayShowHomeEnabled(false);

		// Hide Actionbar Title
		actionBar.setDisplayShowTitleEnabled(false);

		// Create Actionbar Tabs
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Set Tab Icon and Titles
		// HistoryTab = actionBar.newTab().setIcon(R.drawable.tab1);
		HistoryTab = actionBar.newTab().setText("History");
		ContactsTab = actionBar.newTab().setText("Contacts");
		ProfileTab = actionBar.newTab().setText("Profile");

		// Set Tab Listeners
		HistoryTab.setTabListener(new TabListener(historyTabFragment));
		ContactsTab.setTabListener(new TabListener(contactsTabFragment));
		ProfileTab.setTabListener(new TabListener(profileTabFragment));

		// Add tabs to actionbar
		actionBar.addTab(HistoryTab);
		actionBar.addTab(ContactsTab);
		actionBar.addTab(ProfileTab);

	}

	@Override
	public Activity getActivity() {
		return this;
	}

	@Override
	public void isDisconnected(boolean is) {

	}

	@Override
	public void displayToast(String message) {
		Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_LONG);
		toast.show();
	}

}

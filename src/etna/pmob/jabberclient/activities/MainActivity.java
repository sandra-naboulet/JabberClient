package etna.pmob.jabberclient.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;
import etna.pmob.jabberclient.R;
import etna.pmob.jabberclient.activities.tabs.ContactsTab;
import etna.pmob.jabberclient.activities.tabs.HistoryTab;
import etna.pmob.jabberclient.activities.tabs.ProfileTab;
import etna.pmob.jabberclient.network.Contact;
import etna.pmob.jabberclient.ui.MainHandler;
import etna.pmob.jabberclient.util.TabListener;

public class MainActivity extends Activity implements MainHandler {

	ActionBar.Tab HistoryTab, ContactsTab, ProfileTab;
	Fragment historyTabFragment = new HistoryTab();
	Fragment contactsTabFragment = new ContactsTab();
	Fragment profileTabFragment = new ProfileTab();

	FrameLayout layout = null;

	Contact selectedContact;

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

		registerForContextMenu(layout);

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

	public void openContactMenu(Contact contact) {
		selectedContact = contact;
		openContextMenu(layout);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle(selectedContact.getName());
		menu.add(0, v.getId(), 0, getString(R.string.contact_menu_chat));
		menu.add(0, v.getId(), 0, getString(R.string.contact_menu_profile));
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getTitle() == getString(R.string.contact_menu_chat)) {
			// Intent intent = new Intent(MainActivity.this,
			// PsControllerActivity.class);
			// startActivity(intent);
		} else if (item.getTitle() == getString(R.string.contact_menu_profile)) {

		} else {
			return false;
		}
		return true;
	}

}

package etna.pmob.jabberclient.activities.tabs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import etna.pmob.jabberclient.R;
import etna.pmob.jabberclient.activities.MainActivity;
import etna.pmob.jabberclient.datas.Contact;
import etna.pmob.jabberclient.network.ConnectionManager;
import etna.pmob.jabberclient.ui.ContactsHandler;

public class ContactsTab extends ListFragment implements ContactsHandler {

	List<Contact> contactsList = null;
	ListView listview = null;

	final String EMAIL_KEY = "email";
	final String NAME_KEY = "name";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_contacts_tab,
				container, false);

		listview = (ListView) rootView.findViewById(R.id.contactsListView);

		ConnectionManager.getInstance().displayContactsList(this);

		return rootView;

	}

	public class ArrayAdapter extends BaseAdapter {

		private Activity activity;
		private ArrayList<HashMap<String, String>> data;
		private LayoutInflater inflater = null;

		public ArrayAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
			activity = a;
			data = d;
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public int getCount() {
			return data.size();
		}

		public Contact getItem(int position) {
			return new Contact(data.get(position).get(EMAIL_KEY), data.get(
					position).get(NAME_KEY));
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View vi = convertView;
			if (convertView == null)
				vi = inflater.inflate(R.layout.activity_contact_item, null);

			TextView name = (TextView) vi.findViewById(R.id.contact_name); // name

			HashMap<String, String> contact = new HashMap<String, String>();
			contact = data.get(position);

			if (contact.get(NAME_KEY) == null
					|| contact.get(NAME_KEY).isEmpty()) {
				name.setText(contact.get(EMAIL_KEY));
			} else {
				name.setText(contact.get(NAME_KEY));
			}

			return vi;
		}

	}

	@Override
	public void isDisconnected(boolean is) {
		// TODO Auto-generated method stub

	}

	@Override
	public void displayToast(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateContacts(List<Contact> contacts) {
		this.contactsList = contacts;

		ArrayList<HashMap<String, String>> contactsItemList = new ArrayList<HashMap<String, String>>();

		for (Contact contact : contactsList) {

			HashMap<String, String> map = new HashMap<String, String>();
			map.put(EMAIL_KEY, contact.getEmailId());
			map.put(NAME_KEY, contact.getName());
			map.put("thumbnail", "...");

			contactsItemList.add(map);
		}
		ArrayAdapter adapter = new ArrayAdapter(this.getActivity(),
				contactsItemList);

		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {

				Contact selectedContact = (Contact) parent
						.getItemAtPosition(position);

				Activity actionBarActivity = getActivity();

				if (actionBarActivity instanceof MainActivity) {
					((MainActivity) actionBarActivity)
							.openContactMenu(selectedContact);
				}

			}

		});

	}
	
	@Override
	public void onStop(){
		super.onStop();
		ConnectionManager.getInstance().onActivityStop();
	}

}

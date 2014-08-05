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
import etna.pmob.jabberclient.network.ConnectionManager;
import etna.pmob.jabberclient.network.Contact;
import etna.pmob.jabberclient.ui.ContactsHandler;

public class ContactsTab extends ListFragment implements ContactsHandler {

	List<Contact> contactsList = null;
	ListView listview = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_contacts_tab,
				container, false);

		listview = (ListView) rootView.findViewById(R.id.contactsListView);

		// final StableArrayAdapter adapter = new StableArrayAdapter(
		// getActivity(), R.layout.activity_contact_item,
		// // android.R.layout.simple_list_item_1,
		// contactsList);

		ConnectionManager.getInstance().setUiHandler(this);
		ConnectionManager.getInstance().displayContactsList();

		return rootView;
	}

	// private class StableArrayAdapter extends ArrayAdapter<Contact> {
	//
	// HashMap<Contact, Integer> mIdMap = new HashMap<Contact, Integer>();
	//
	// public StableArrayAdapter(Context context, int textViewResourceId,
	// List<Contact> objects) {
	//
	// super(context, textViewResourceId, objects);
	// for (int i = 0; i < objects.size(); ++i) {
	// mIdMap.put(objects.get(i), i);
	// }
	// }
	//
	// @Override
	// public long getItemId(int position) {
	// Contact item = getItem(position);
	// return mIdMap.get(item);
	// }
	//
	// @Override
	// public boolean hasStableIds() {
	// return true;
	// }
	//
	// }

	public class ArrayAdapter extends BaseAdapter {

		private Activity activity;
		private ArrayList<HashMap<String, String>> data;
		private LayoutInflater inflater = null;

		// public ImageLoader imageLoader;

		public ArrayAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
			activity = a;
			data = d;
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// imageLoader = new ImageLoader(activity.getApplicationContext());
		}

		public int getCount() {
			return data.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View vi = convertView;
			if (convertView == null)
				vi = inflater.inflate(R.layout.activity_contact_item, null);

			TextView email = (TextView) vi.findViewById(R.id.contact_emailId); // email
			TextView name = (TextView) vi.findViewById(R.id.contact_name); // name

			// ImageView thumb_image = (ImageView) vi
			// .findViewById(R.id.contact_thumbnail); // thumb image

			HashMap<String, String> contact = new HashMap<String, String>();
			contact = data.get(position);

			// Setting all values in listview
			email.setText(contact.get("email"));
			name.setText(contact.get("name"));
			// imageLoader.DisplayImage(contact.get("thumbnail"), thumb_image);
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

		// looping through all song nodes &lt;song&gt;
		for (Contact contact : contactsList) {
			
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("email", contact.getEmailId());
			map.put("name", contact.getFirstname() + " " + contact.getLastname());
			map.put("thumbnail", "...");

			// adding HashList to ArrayList
			contactsItemList.add(map);
		}
		ArrayAdapter adapter = new ArrayAdapter(this.getActivity(),
				contactsItemList);

		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				// final String item = (String)
				// parent.getItemAtPosition(position);
				// view.animate().setDuration(2000).alpha(0)
				// .withEndAction(new Runnable() {
				// @Override
				// public void run() {
				// contactsList.remove(item);
				// // adapter.notifyDataSetChanged();
				// view.setAlpha(1);
				// }
				// });
			}

		});

	}

}

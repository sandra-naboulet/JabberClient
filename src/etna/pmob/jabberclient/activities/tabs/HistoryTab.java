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
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import etna.pmob.jabberclient.R;
import etna.pmob.jabberclient.datas.Contact;
import etna.pmob.jabberclient.datas.History;
import etna.pmob.jabberclient.datas.Message;

public class HistoryTab extends ListFragment {

	List<Contact> historyList = null;
	ListView listview = null;

	final String EMAIL_KEY = "email";
	final String NAME_KEY = "name";
	final String MSG_KEY = "content";
	final String DATE_KEY = "date";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_history_tab,
				container, false);

		listview = (ListView) rootView.findViewById(R.id.historyListView);

		// populate history list

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

		public History getItem(int position) {
			Contact contact = new Contact(data.get(position).get(EMAIL_KEY),
					data.get(position).get(NAME_KEY));
			Message msg = new Message(data.get(position).get(EMAIL_KEY), data
					.get(position).get(MSG_KEY), data.get(position).get(
					DATE_KEY));
			return new History(contact, msg);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View vi = convertView;
			if (convertView == null)
				vi = inflater.inflate(R.layout.activity_history_item, null);

			TextView name = (TextView) vi.findViewById(R.id.histo_name); // name
			TextView content = (TextView) vi.findViewById(R.id.histo_content); // message
																				// content

			HashMap<String, String> history = new HashMap<String, String>();
			history = data.get(position);

			if (history.get(NAME_KEY) == null
					|| history.get(NAME_KEY).isEmpty()) {
				name.setText(history.get(EMAIL_KEY));
			} else {
				name.setText(history.get(NAME_KEY));
			}

			content.setText(history.get(MSG_KEY));

			return vi;
		}

	}

	public void loadHistory() {

	}

}

package etna.pmob.jabberclient.network;

import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import android.os.AsyncTask;
import etna.pmob.jabberclient.ui.ContactsHandler;

class ContactsListTask extends AsyncTask<String, String, List<Contact>> {

	private XMPPConnection connection;
	private ContactsHandler handler;

	public ContactsListTask(XMPPConnection connection, ContactsHandler handler) {
		super();
		this.connection = connection;
		this.handler = handler;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected List<Contact> doInBackground(String... params) {
		List<Contact> contacts = null;
		try {
			connection.connect();

			if (connection.isConnected()) {

				Roster roster = connection.getRoster();
				contacts = new ArrayList<Contact>();

				for (RosterEntry entry : roster.getEntries()) {
					contacts.add(new Contact(entry.getUser(), entry.getName(),
							entry.getName()));
				}
			}

		} catch (XMPPException e) {
			return null;
		} catch (Exception e) {
			return null;
		}

		return contacts;

	}

	@Override
	protected void onPostExecute(List<Contact> contacts) {
		super.onPostExecute(contacts);
		handler.updateContacts(contacts);
	}
}

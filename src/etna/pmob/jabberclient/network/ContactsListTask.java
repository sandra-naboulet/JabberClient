package etna.pmob.jabberclient.network;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smackx.provider.VCardProvider;

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
		} catch (XMPPException e) {
			return null;
		} catch (Exception e) {
			return null;
		}
		if (connection.isConnected()) {

			Roster roster = connection.getRoster();
			contacts = new ArrayList<Contact>();

			ProviderManager.getInstance().addIQProvider("vCard", "vcard-temp",
					new VCardProvider());
			Presence presencek;

			for (RosterEntry entry : roster.getEntries()) {

				presencek = roster.getPresence(entry.getUser());
				contacts.add(new Contact(entry.getUser().toString(), entry
						.getName()));

			}

			Collections.sort(contacts, new Comparator<Contact>() {

				@Override
				public int compare(Contact lhs, Contact rhs) {
					String name1, name2;
					if (lhs.name == null || lhs.name.isEmpty()) {
						name1 = lhs.getEmailId();
					} else {
						name1 = lhs.getName();
					}

					if (rhs.name == null || rhs.name.isEmpty()) {
						name2 = rhs.getEmailId();
					} else {
						name2 = rhs.getName();
					}
					return name1.compareTo(name2);
				}
			});

		}

		return contacts;

	}

	@Override
	protected void onPostExecute(List<Contact> contacts) {
		super.onPostExecute(contacts);
		handler.updateContacts(contacts);
	}
}

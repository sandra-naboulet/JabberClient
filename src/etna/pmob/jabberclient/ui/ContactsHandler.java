package etna.pmob.jabberclient.ui;

import java.util.List;

import etna.pmob.jabberclient.network.Contact;

public interface ContactsHandler extends ActivityHandler {

	void updateContacts(List<Contact> contacts);
}

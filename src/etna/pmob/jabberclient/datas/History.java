package etna.pmob.jabberclient.datas;

public class History {

	Contact contact;
	Message message;

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public History(Contact contact, Message message) {
		super();
		this.contact = contact;
		this.message = message;
	}

}

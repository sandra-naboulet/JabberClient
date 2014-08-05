package etna.pmob.jabberclient.network;

public class Contact {

	String emailId;
	String name;

	public Contact(String emailId, String name) {
		super();
		this.emailId = emailId;
		this.name = name;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Contact [emailId=" + emailId + ", firstname=" + name + "]";
	}

}

package etna.pmob.jabberclient.network;

public class Contact {

	String emailId;
	String firstname;
	String lastname;

	public Contact(String emailId, String firstname, String lastname) {
		super();
		this.emailId = emailId;
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Override
	public String toString() {
		return "Contact [emailId=" + emailId + ", firstname=" + firstname
				+ ", lastname=" + lastname + "]";
	}

}

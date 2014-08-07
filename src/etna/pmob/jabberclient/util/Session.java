package etna.pmob.jabberclient.util;

public class Session {
	String emailId;
	String password;

	private static class Holder {
		private final static Session INSTANCE = new Session();
	}

	public static Session getInstance() {
		return Holder.INSTANCE;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

package etna.pmob.jabberclient.network;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import android.os.AsyncTask;
import android.os.StrictMode;
import etna.pmob.jabberclient.R;
import etna.pmob.jabberclient.ui.ChatHandler;
import etna.pmob.jabberclient.ui.ContactsHandler;
import etna.pmob.jabberclient.ui.LoginHandler;
import etna.pmob.jabberclient.ui.SignupHandler;
import etna.pmob.jabberclient.util.Session;

public class ConnectionManager {

	// public static final String HOST = "talk.l.google.com";
	public static final String HOST = "talk.google.com";
	public static final int PORT = 5222;
	public static final String SERVICE = "gmail.com";

	private Session session;

	private XMPPConnection connection;
	ConnectionConfiguration config;

	private static class Holder {
		private final static ConnectionManager INSTANCE = new ConnectionManager();
	}

	public static ConnectionManager getInstance() {
		return Holder.INSTANCE;
	}

	public void setSession(Session newSession) {
		this.session = Session.getInstance();
		session.setEmailId(newSession.getEmailId());
		session.setPassword(newSession.getPassword());

	}

	public ConnectionManager() {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
	}

	public Session getSession() {
		return this.session;
	}

	public void start() {
		new ConnectionTask().execute();
	}

	public void restart() {
		this.start();
	}

	public void startMessagesListener(ChatHandler handler) {
		new MessageReceiveListener(connection, handler).start();
	}

	public void login(final LoginHandler handler, String username,
			String password) {

		try {
			Session session = Session.getInstance();

			connection.connect();

			if (username.contains("@gmail.com")) {
				connection.login(username, password);
				session.setEmailId(username);
			} else {
				connection.login(username + "@gmail.com", password);
				session.setEmailId(username + "@gmail.com");
			}
			session.setPassword(password);
			// session.setName(connection.getAccountManager().get);

			AccountManager m = new AccountManager(connection);

			handler.displayToast(handler.getActivity().getResources()
					.getString(R.string.login_succeed));
			handler.isLogged(true);

		} catch (XMPPException e) {
			handler.displayToast(handler.getActivity().getResources()
					.getString(R.string.login_failed));
			handler.isLogged(false);
		}

		connection.disconnect();

	}

	public void register(SignupHandler handler, String username, String password) {
		new RegisterTask(connection, (SignupHandler) handler).execute(username,
				password);
	}

	public void send(final ChatHandler handler, String to, String message) {
		new SendMessageTask(connection, handler).execute(to, message);
	}

	public void displayContactsList(ContactsHandler handler) {
		new ContactsTask(connection, handler).execute();
	}

	public void disconnect() {
		// new DisconnectTask(connection, handler).execute();
	}

	/** Run in background **/
	private class ConnectionTask extends AsyncTask<Void, Integer, Boolean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Boolean doInBackground(Void... arg0) {

			// try {

			// WifiManager wifiManager = (WifiManager) handler.getActivity()
			// .getSystemService(Context.WIFI_SERVICE);
			// MulticastLock multicastLock = wifiManager
			// .createMulticastLock("multicastLock");
			// multicastLock.setReferenceCounted(true);
			// multicastLock.acquire();
			// ConnectivityManager conMgr = (ConnectivityManager) handler
			// .getActivity().getSystemService(
			// Context.CONNECTIVITY_SERVICE);
			// if (conMgr.getActiveNetworkInfo() != null
			// && conMgr.getActiveNetworkInfo().isAvailable()
			// && conMgr.getActiveNetworkInfo().isConnected()) {

			connection = new XMPPConnection(SERVICE);
			// new MessageReceiveListener(connection).start();

			return Boolean.TRUE;
			//
			// } else {
			// return Boolean.FALSE;
			// }
			//
			// } catch (Exception e) {
			// return Boolean.FALSE;
			// }
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
		}
	}

	public void onActivityStop() {
		connection.disconnect();
	}

}
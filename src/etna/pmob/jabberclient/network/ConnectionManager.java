package etna.pmob.jabberclient.network;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.sasl.SASLDigestMD5Mechanism;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.MulticastLock;
import android.os.AsyncTask;
import android.util.Log;
import etna.pmob.jabberclient.BuildConfig;
import etna.pmob.jabberclient.ui.ActivityHandler;
import etna.pmob.jabberclient.ui.LoginHandler;

public class ConnectionManager {

	public static final String HOST = "jabberd.org";
	public static final int PORT = 5222;
	public static final String SERVICE = "gmail.com";

	private XMPPConnection connection;
	ConnectionConfiguration config;
	private ActivityHandler handler = null;

	private static class Holder {
		private final static ConnectionManager INSTANCE = new ConnectionManager();
	}

	public static ConnectionManager getInstance() {
		return Holder.INSTANCE;
	}

	public void start() {
		new ConnectionTask().execute();
	}

	public void restart() {
		this.start();
	}

	public void setUiHandler(ActivityHandler handler) {
		this.handler = handler;
	}

	public void login(String username, String password) {
		new LoginTask(connection, (LoginHandler) handler).execute(username,
				password);
	}

	public void register(String username, String password) {
		new RegisterTask(connection, handler).execute(username, password);
	}

	public void disconnect() {
		new DisconnectTask(connection, handler).execute();
	}
	
	public boolean isConnected(){
		return connection.isConnected();
	}

	public ActivityHandler getActivityHandler() {
		return handler;
	}

	/** Run in background **/
	private class ConnectionTask extends AsyncTask<Void, Integer, Boolean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// if (BuildConfig.DEBUG) {
			// Log.i("PIXL", "Searching for XBMC");
			// }
			// handler.loading();
		}

		@Override
		protected Boolean doInBackground(Void... arg0) {

			try {
				// if (BuildConfig.DEBUG) {
				// Log.i("PIXL", "Testing internet connection...");
				// }
				WifiManager wifiManager = (WifiManager) handler.getActivity()
						.getSystemService(Context.WIFI_SERVICE);
				MulticastLock multicastLock = wifiManager
						.createMulticastLock("multicastLock");
				multicastLock.setReferenceCounted(true);
				multicastLock.acquire();
				ConnectivityManager conMgr = (ConnectivityManager) handler
						.getActivity().getSystemService(
								Context.CONNECTIVITY_SERVICE);
				if (conMgr.getActiveNetworkInfo() != null
						&& conMgr.getActiveNetworkInfo().isAvailable()
						&& conMgr.getActiveNetworkInfo().isConnected()) {

					if (BuildConfig.DEBUG) {
						Log.i("Jabber", "Connected to internet");
					}

					SASLAuthentication.registerSASLMechanism("DIGEST-MD5",
							SASLDigestMD5Mechanism.class);
					SASLAuthentication.supportSASLMechanism("DIGEST-MD5", 1);

					System.setProperty("smack.debugEnabled", "true");
					XMPPConnection.DEBUG_ENABLED = true;
					SmackConfiguration.setPacketReplyTimeout(6000);

					config = new ConnectionConfiguration(HOST, PORT);
					config.setSASLAuthenticationEnabled(true);
					config.setRosterLoadedAtLogin(false);
					config.setCompressionEnabled(true);

					connection = new XMPPConnection(config);

					SASLAuthentication.supportSASLMechanism("PLAIN");
					config.setSASLAuthenticationEnabled(true);
					connection.connect();
					Roster.setDefaultSubscriptionMode(Roster.SubscriptionMode.manual);

					return Boolean.TRUE;

				} else {
					if (BuildConfig.DEBUG) {
						Log.i("PIXL", "No internet connection.");
					}
					return Boolean.FALSE;
				}

			} catch (Exception e) {
				// if (BuildConfig.DEBUG) {
				// Log.e("PIXL", e.getMessage());
				// }
				// handler.loading();
				return Boolean.FALSE;
			}
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
		}
	}

}
package etna.pmob.jabberclient.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.MulticastLock;
import android.os.AsyncTask;
import android.util.Log;
import etna.pmob.jabberclient.BuildConfig;
import etna.pmob.jabberclient.api.XMPPConnection;
import etna.pmob.jabberclient.ui.UIHandler;

public class ConnectionManager {

	private XMPPConnection xmpp;
	private UIHandler handler = null;

	private static class Holder {
		private final static ConnectionManager INSTANCE = new ConnectionManager();
	}

	public static ConnectionManager getInstance() {
		return Holder.INSTANCE;
	}

	public void start() {
		new ConnectionTask().execute();
	}

	public void setUiHandler(UIHandler handler) {
		this.handler = handler;
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

					xmpp = new XMPPConnection();
					xmpp.connect();

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
			// if (BuildConfig.DEBUG) {
			// Log.i("PIXL", "Connected to XBMC : " + result.toString());
			// }
			// handler.connected(result);
		}
	}

}
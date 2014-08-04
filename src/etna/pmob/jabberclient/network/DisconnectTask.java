package etna.pmob.jabberclient.network;

import org.jivesoftware.smack.XMPPConnection;

import android.os.AsyncTask;
import etna.pmob.jabberclient.R;
import etna.pmob.jabberclient.ui.ActivityHandler;

class DisconnectTask extends AsyncTask<String, String, Result> {

	private XMPPConnection connection;
	private ActivityHandler handler;

	public DisconnectTask(XMPPConnection connection, ActivityHandler handler) {
		super();
		this.connection = connection;
		this.handler = handler;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected Result doInBackground(String... params) {
		try {

			connection.disconnect();

			return new Result(Result.Status.SUCCESS, handler.getActivity()
					.getApplicationContext().getResources()
					.getString(R.string.logout_succeed));

		} catch (Exception e) {
			return new Result(Result.Status.ERROR, handler.getActivity()
					.getApplicationContext().getResources()
					.getString(R.string.logout_failed)
					+ e.getMessage());
		}

	}

	@Override
	protected void onPostExecute(Result result) {
		super.onPostExecute(result);
		handler.displayToast(result.getMessage());
		handler.isDisconnected(result.getStatus().equals(Result.Status.SUCCESS));
	}
}

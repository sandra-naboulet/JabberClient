package etna.pmob.jabberclient.network;

import org.jivesoftware.smack.XMPPConnection;

import android.os.AsyncTask;
import etna.pmob.jabberclient.ui.ActivityHandler;

class RegisterTask extends AsyncTask<String, String, Result> {

	private XMPPConnection connection;
	private ActivityHandler handler;

	public RegisterTask(XMPPConnection connection, ActivityHandler handler) {
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
		// connection.connect();
		Result res = new Result(Result.Status.SUCCESS, "hello");
		return res;
	}

	@Override
	protected void onPostExecute(Result result) {
		super.onPostExecute(result);
		handler.displayToast(result.getMessage());
	}
}

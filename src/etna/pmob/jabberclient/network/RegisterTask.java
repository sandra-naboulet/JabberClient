package etna.pmob.jabberclient.network;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import android.os.AsyncTask;
import etna.pmob.jabberclient.R;
import etna.pmob.jabberclient.ui.SignupHandler;

class RegisterTask extends AsyncTask<String, String, Result> {

	private XMPPConnection connection;
	private SignupHandler handler;

	public RegisterTask(XMPPConnection connection, SignupHandler handler) {
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
			// String username = params[0];
			// String password = params[1];

			connection.connect();

			// TODO if it is possible on gmail

			return new Result(Result.Status.SUCCESS, handler.getActivity()
					.getApplicationContext().getResources()
					.getString(R.string.signup_succeed));

		} catch (XMPPException e) {
			return new Result(Result.Status.ERROR, handler.getActivity()
					.getApplicationContext().getResources()
					.getString(R.string.signup_failed));
		} catch (Exception e) {
			return new Result(Result.Status.ERROR, e.getMessage());
		}

	}

	@Override
	protected void onPostExecute(Result result) {
		super.onPostExecute(result);
		handler.displayToast(result.getMessage());
		handler.isRegistered(result.getStatus().equals(Result.Status.SUCCESS));
	}
}

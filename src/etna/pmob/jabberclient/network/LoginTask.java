package etna.pmob.jabberclient.network;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import android.os.AsyncTask;
import etna.pmob.jabberclient.R;
import etna.pmob.jabberclient.ui.LoginHandler;

class LoginTask extends AsyncTask<String, String, Result> {

	private XMPPConnection connection;
	private LoginHandler handler;

	public LoginTask(XMPPConnection connection, LoginHandler handler) {
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

			connection.connect();
			//connection.login(params[0], params[1]);
			connection.login("s.naboulet@gmail.com", "imediosl1");

		} catch (XMPPException e) {
			return new Result(Result.Status.ERROR, handler.getActivity()
					.getApplicationContext().getResources()
					.getString(R.string.login_failed));
		} catch (Exception e) {
			return new Result(Result.Status.ERROR, handler.getActivity()
					.getApplicationContext().getResources()
					.getString(R.string.login_failed));
		}

		return new Result(Result.Status.SUCCESS, handler.getActivity()
				.getApplicationContext().getResources()
				.getString(R.string.login_succeed));

	}

	@Override
	protected void onPostExecute(Result result) {
		super.onPostExecute(result);
		handler.displayToast(result.getMessage());
		handler.isLogged(result.getStatus().equals(Result.Status.SUCCESS));
	}
}

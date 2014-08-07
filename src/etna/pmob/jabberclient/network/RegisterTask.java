package etna.pmob.jabberclient.network;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Registration;

import android.os.AsyncTask;
import android.util.Log;
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

		String username = params[0];
		String password = params[1];

		// connection.connect();
		XMPPConnection conn;
		ConnectionConfiguration connectionConfiguration = null;
		connectionConfiguration = new ConnectionConfiguration(
				"jabberd.org", 5222);
		// connectionConfiguration.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
		conn = new XMPPConnection(connectionConfiguration);

		try {

			conn.connect();
			boolean b = new AccountManager(conn).supportsAccountCreation();

			System.out.println("support = " + b);

			if (new AccountManager(conn).supportsAccountCreation()) {
				return new Result(Result.Status.ERROR, handler.getActivity()
						.getApplicationContext().getResources()
						.getString(R.string.signup_failed));
			}

			Log.v("test", "attribute : username : " + username + " pw : "
					+ password);
			Registration reg = new Registration();
			reg.setType(IQ.Type.SET);
			reg.setTo(conn.getServiceName());

			// for(String s : attributes.keySet()){
			//
			// reg.addAttribute(s, attributes.get(s));
			// Log.v("test", reg.getAttributes().get(s));
			// }
			reg.setUsername(username);
			reg.setPassword(password);

			PacketFilter filter = new AndFilter(new PacketIDFilter(
					reg.getPacketID()), new PacketTypeFilter(IQ.class));
			PacketCollector collector = conn.createPacketCollector(filter);
			conn.sendPacket(reg);
			IQ result = (IQ) collector.nextResult(SmackConfiguration
					.getPacketReplyTimeout());
			// Stop queuing results
			collector.cancel();
			if (result == null) {
				return new Result(Result.Status.ERROR, handler.getActivity()
						.getApplicationContext().getResources()
						.getString(R.string.signup_failed));
			} else if (result.getType() == IQ.Type.ERROR) {
				return new Result(Result.Status.ERROR, handler.getActivity()
						.getApplicationContext().getResources()
						.getString(R.string.signup_failed));
			}
			conn.disconnect();

			// TODO if it is possible on gmail

			return new Result(Result.Status.SUCCESS, handler.getActivity()
					.getApplicationContext().getResources()
					.getString(R.string.signup_succeed));

		} catch (Exception e) {
			conn.disconnect();
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

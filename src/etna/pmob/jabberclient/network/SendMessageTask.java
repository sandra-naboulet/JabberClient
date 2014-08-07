package etna.pmob.jabberclient.network;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import android.os.AsyncTask;
import etna.pmob.jabberclient.ui.ChatHandler;
import etna.pmob.jabberclient.util.Session;

class SendMessageTask extends AsyncTask<String, String, Boolean> {

	private XMPPConnection connection;
	private ChatHandler handler;

	public SendMessageTask(XMPPConnection connection, ChatHandler handler) {
		super();
		this.connection = connection;
		this.handler = handler;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected Boolean doInBackground(String... params) {

		try {
			Session session = Session.getInstance();
			connection.connect();
			connection.login(session.getEmailId(), session.getPassword());

			Chat chat = connection.getChatManager().createChat(params[0],
					new MessageListener() {

						public void processMessage(Chat chat, Message message) {

							System.out.println("Received message: " + message);
							// handler.displayToast(message.getBody());
						}
					});
			chat.sendMessage(params[1]);
			connection.disconnect();
			
			return Boolean.TRUE;

		} catch (XMPPException e) {
			return Boolean.FALSE;
		} catch (Exception e) {
			return Boolean.FALSE;
		}
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		handler.messageIsSent(true);
		if (!result) {
			handler.messageIsSent(false);
			handler.displayToast("Erreur envoi");
		}
	}
}

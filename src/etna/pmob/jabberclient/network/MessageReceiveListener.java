package etna.pmob.jabberclient.network;

import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

import etna.pmob.jabberclient.activities.ChatActivity;
import etna.pmob.jabberclient.ui.ChatHandler;
import etna.pmob.jabberclient.util.Session;

public class MessageReceiveListener extends Thread implements Runnable {

	XMPPConnection connection;
	ChatHandler handler;

	public MessageReceiveListener(XMPPConnection connection, ChatHandler handler) {
		super();
		this.connection = connection;
		this.handler = handler;

	}

	public void run() {
		Session session = Session.getInstance();
		try {
			XMPPConnection newConn = new XMPPConnection("gmail.com");
			newConn.connect();
			newConn.login(session.getEmailId(), session.getPassword());

			PacketFilter filter = new AndFilter(new PacketTypeFilter(
					Message.class));
			PacketCollector collector = newConn.createPacketCollector(filter);

			while (true) {
				Packet packet = collector.nextResult();
				if (packet instanceof Message) {
					final Message message = (Message) packet;
					if (message != null && message.getBody() != null) {

						ChatActivity.runOnUI(new Runnable() {
							public void run() {
								handler.onMessageReceived(message);
							}
						});
					}
				}
			}
		} catch (XMPPException e) {
			e.printStackTrace();
		}

	}
}

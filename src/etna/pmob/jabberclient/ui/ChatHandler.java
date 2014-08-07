package etna.pmob.jabberclient.ui;

import org.jivesoftware.smack.packet.Message;

public interface ChatHandler extends ActivityHandler {

	void messageIsSent(boolean is);
	
	void onMessageReceived(Message message);
}

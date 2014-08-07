package etna.pmob.jabberclient.activities;

import org.jivesoftware.smack.packet.Message;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.Toast;
import etna.pmob.jabberclient.R;
import etna.pmob.jabberclient.datas.Contact;
import etna.pmob.jabberclient.network.ConnectionManager;
import etna.pmob.jabberclient.ui.ChatHandler;

public class ActiveChatsActivity extends Activity implements ChatHandler {

	RelativeLayout layout = null;
	LayoutInflater inflater;

	ConnectionManager connectionManager;

	Contact contact;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		layout = (RelativeLayout) RelativeLayout.inflate(this,
				R.layout.fragment_active_chats, null);

		setContentView(layout);

		inflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		connectionManager = ConnectionManager.getInstance();
		connectionManager.startMessagesListener(this);

	}

	// UPDATE UI METHODS

	@Override
	public Activity getActivity() {
		return this;
	}

	@Override
	public void displayToast(String message) {

		Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_LONG);
		toast.show();
	}

	@Override
	public void isDisconnected(boolean is) {

	}

	@Override
	public void messageIsSent(boolean is) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMessageReceived(Message message) {
		RelativeLayout chatItemView = (RelativeLayout) inflater.inflate(
				R.layout.activity_active_chats_item, null);
		layout.addView(chatItemView);
	}
}

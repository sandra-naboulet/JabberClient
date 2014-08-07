package etna.pmob.jabberclient.activities;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import etna.pmob.jabberclient.R;
import etna.pmob.jabberclient.datas.Contact;
import etna.pmob.jabberclient.datas.Message;
import etna.pmob.jabberclient.network.ConnectionManager;
import etna.pmob.jabberclient.ui.ChatHandler;

public class ChatActivity extends Activity implements ChatHandler {

	final String FROM_KEY = "from";
	final String MESSAGE_KEY = "message";
	final String DATE_KEY = "date";

	RelativeLayout layout = null;
	TextView contactTextView = null;
	EditText messageEditText = null;
	ListView messagesListView = null;
	Button sendButton = null;

	ArrayAdapter adapter;
	ArrayList<HashMap<String, String>> msgItemList;

	ConnectionManager connectionManager;
	Contact contact;

	boolean isSending = false;

	public static Handler UIHandler;

	static {
		UIHandler = new Handler(Looper.getMainLooper());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		layout = (RelativeLayout) RelativeLayout.inflate(this,
				R.layout.activity_chat, null);

		setContentView(layout);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			contact = new Contact(extras.getString("contactEmailId"),
					extras.getString("contactName"));
		}

		messageEditText = (EditText) layout
				.findViewById(R.id.message_edit_text);

		sendButton = (Button) layout.findViewById(R.id.sendButton);
		sendButton.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();

				if (action == MotionEvent.ACTION_UP) {
					if (!isSending && contact != null) {

						connectionManager.send(ChatActivity.this, contact
								.getEmailId(), messageEditText.getText()
								.toString());
						isSending = false;
					}
				}
				return true;
			}
		});

		contactTextView = (TextView) layout
				.findViewById(R.id.chat_contact_name);
		contactTextView.setText(contact.getName());

		messagesListView = (ListView) layout
				.findViewById(R.id.messages_list_view);

		// connection
		connectionManager = ConnectionManager.getInstance();
		// connectionManager.start(); // connection to the server

		connectionManager.startMessagesListener(this);

		// init messages list
		msgItemList = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(MESSAGE_KEY, "lalald lkdl ufo uofdiu odfugiodu ogidf");
		HashMap<String, String> map2 = new HashMap<String, String>();
		map2.put(MESSAGE_KEY,
				"SAlut  sdif n je suis fatigueee vivement ce weekend !");
		msgItemList.add(map);
		msgItemList.add(map2);

		adapter = new ArrayAdapter(this.getActivity(), msgItemList);

		messagesListView.setAdapter(adapter);

	}

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

	public class ArrayAdapter extends BaseAdapter {

		private Activity activity;
		private ArrayList<HashMap<String, String>> data;
		private LayoutInflater inflater = null;

		public ArrayAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
			activity = a;
			data = d;
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public int getCount() {
			return data.size();
		}

		public Message getItem(int position) {
			return new Message(data.get(position).get(FROM_KEY), data.get(
					position).get(MESSAGE_KEY));
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View view, ViewGroup parent) {
			View vi = view;
			if (view == null)
				vi = inflater.inflate(R.layout.activity_message_item, null);

			TextView content = (TextView) vi
					.findViewById(R.id.chat_message_content);

			HashMap<String, String> message = new HashMap<String, String>();
			message = data.get(position);

			content.setText(message.get(MESSAGE_KEY));

			return vi;
		}

	}

	@Override
	public void messageIsSent(boolean is) {
		isSending = false;
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(MESSAGE_KEY, messageEditText.getText().toString());
		msgItemList.add(map);
		adapter.notifyDataSetChanged();
		messagesListView.setAdapter(adapter);

		messageEditText.setText("");

	}

	@Override
	public void onMessageReceived(org.jivesoftware.smack.packet.Message message) {

		HashMap<String, String> map = new HashMap<String, String>();
		map.put(MESSAGE_KEY, message.getBody());
		msgItemList.add(map);
		adapter.notifyDataSetChanged();
		messagesListView.setAdapter(adapter);

	}

	public static void runOnUI(Runnable runnable) {
		UIHandler.post(runnable);
	}

}

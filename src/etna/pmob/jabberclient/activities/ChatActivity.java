package etna.pmob.jabberclient.activities;

import java.util.ArrayList;
import java.util.Date;
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
import etna.pmob.jabberclient.util.Session;

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
						isSending = false;
						connectionManager.send(ChatActivity.this, contact
								.getEmailId(), messageEditText.getText()
								.toString());

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

		connectionManager = ConnectionManager.getInstance();
		connectionManager.startMessagesListener(this);

		// init messages list
		msgItemList = new ArrayList<HashMap<String, String>>();

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
					position).get(MESSAGE_KEY), new Date().toString());
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
			RelativeLayout container = (RelativeLayout) vi
					.findViewById(R.id.chat_message_container);

			HashMap<String, String> message = new HashMap<String, String>();
			message = data.get(position);

			content.setText(message.get(MESSAGE_KEY));

			if (message.get(FROM_KEY)
					.equals(Session.getInstance().getEmailId())) {
				container.setBackgroundColor(getResources().getColor(
						R.color.primary));
				content.setTextColor(getResources().getColor(R.color.header_bg));
			} else {

				container.setBackgroundColor(getResources().getColor(
						R.color.header_bg));
				content.setTextColor(getResources().getColor(R.color.white));
			}

			return vi;
		}
	}

	@Override
	public void messageIsSent(boolean is) {
		isSending = false;
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(MESSAGE_KEY, "me : " + messageEditText.getText().toString());
		map.put(FROM_KEY, Session.getInstance().getEmailId());
		msgItemList.add(map);
		adapter.notifyDataSetChanged();
		messagesListView.setAdapter(adapter);
		messageEditText.setText("");

	}

	@Override
	public void onMessageReceived(org.jivesoftware.smack.packet.Message message) {

		HashMap<String, String> map = new HashMap<String, String>();
		map.put(MESSAGE_KEY, message.getBody());
		map.put(FROM_KEY, message.getFrom());
		msgItemList.add(map);
		adapter.notifyDataSetChanged();
		messagesListView.setAdapter(adapter);

	}

	public static void runOnUI(Runnable runnable) {
		UIHandler.post(runnable);
	}

}

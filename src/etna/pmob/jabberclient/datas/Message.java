package etna.pmob.jabberclient.datas;

import java.util.Date;

public class Message {

	String fromId;
	String content;
	Date date;

	public Message(String fromId, String content) {
		super();
		this.fromId = fromId;
		this.content = content;
		this.date = null;
	}

	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Message [fromId=" + fromId + ", content=" + content + ", date="
				+ date + "]";
	}

}

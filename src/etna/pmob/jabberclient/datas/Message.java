package etna.pmob.jabberclient.datas;

public class Message {

	String fromId;
	String content;
	String date;

	public Message(String fromId, String content, String date) {
		super();
		this.fromId = fromId;
		this.content = content;
		this.date = date;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Message [fromId=" + fromId + ", content=" + content + ", date="
				+ date + "]";
	}

}

package etna.pmob.jabberclient.api;

public class Response {

	public enum Type {
		RESULT, ERROR
	}

	private Type status;
	private String message;

	public Response(Type status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public Type getStatus() {
		return status;
	}

	public void setStatus(Type status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Jabber response : [status=" + status + ", message=" + message
				+ "]";
	}

}

package etna.pmob.jabberclient.network;

public class Result {

	public enum Status {
		SUCCESS, ERROR
	}

	private Status status;
	private String message;

	public Result(Status status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public Result() {

	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
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
		return "Server response : [status=" + status + ", message=" + message
				+ "]";
	}

}

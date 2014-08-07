package etna.pmob.jabberclient.ui;

public interface LoginHandler extends ActivityHandler {

	public void loading();

	void isLogged(boolean connected);
}

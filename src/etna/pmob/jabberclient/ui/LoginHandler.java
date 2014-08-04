package etna.pmob.jabberclient.ui;

public interface LoginHandler extends ActivityHandler {

	void loading();

	void noInternet();

	void isLogged(boolean connected);
}

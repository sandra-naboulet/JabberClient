package etna.pmob.jabberclient.api;

import etna.pmob.jabberclient.api.Response.Type;

public class Jabber {

	public static Response login() {
		return new Response(Type.RESULT, "logged");
		
	}

}

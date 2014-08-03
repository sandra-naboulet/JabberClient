package etna.pmob.jabberclient.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import etna.pmob.jabberclient.api.Response.Type;

public class XMPPConnection {

	private final String SERVER = "jabber.fr";
	private final Integer PORT = 5222;
	private Socket socket = null;
	private PrintWriter out = null;

	private BufferedReader in = null;

	private static class Holder {
		private final static XMPPConnection INSTANCE = new XMPPConnection();
	}

	public static XMPPConnection getInstance() {
		return Holder.INSTANCE;
	}

	public Response connect() {

		try {
			socket = new Socket(SERVER, PORT);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));

		} catch (Exception e) {
			return new Response(Type.ERROR, e.getMessage());
		}

		try {
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().newDocument();

			// create root: <stream:stream>
			Element stream = doc.createElement("stream:stream");
			doc.appendChild(stream);
			stream.setAttribute("to", SERVER);
			stream.setAttribute("xmlns", "jabber:client");
			stream.setAttribute("xmlns:stream",
					"http://etherx.jabber.org/streams");
			stream.setAttribute("version", "1.0");

			// create Transformer object
			Transformer transformer = TransformerFactory.newInstance()
					.newTransformer();
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			transformer.transform(new DOMSource(doc), result);

			out.print(writer.toString());
			out.flush();

			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				System.out.println("Server: " + inputLine);
				out.println(inputLine);
			}

			// close();

			return new Response(Type.RESULT, writer.toString());

		} catch (ParserConfigurationException e) {
			return new Response(Type.ERROR, e.getMessage());
		} catch (TransformerConfigurationException e) {
			return new Response(Type.ERROR, e.getMessage());
		} catch (TransformerFactoryConfigurationError e) {
			return new Response(Type.ERROR, e.getMessage());
		} catch (TransformerException e) {
			return new Response(Type.ERROR, e.getMessage());
		} catch (IOException e) {
			return new Response(Type.ERROR, e.getMessage());
		}

	}

	private void close() {
		try {
			out.close();
			in.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

package etna.pmob.jabberclient.util;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLRequestGenerator {

	private static Document doc;
	private static Transformer transformer;

	// <?xml version='1.0'?> <stream:stream to='jabber.fr' xmlns='jabber:client'
	// xmlns:stream='http://etherx.jabber.org/streams' version='1.0'>
	//
	// <iq type='get' id='reg1'><query xmlns='jabber:iq:register'/></iq>
	//
	// <iq type='set' id='reg2'><query
	// xmlns='jabber:iq:register'><username>skdjfhksdjhfksdjfhjfdhkds</username><password>skgkfdjghdsjhgkjdsf</password></query></iq>

	public static String openDialog(String servername) {

		/*
		 * Element stream; try { doc =
		 * DocumentBuilderFactory.newInstance().newDocumentBuilder()
		 * .newDocument();
		 * 
		 * stream = doc.createElement("stream:stream"); doc.appendChild(stream);
		 * stream.setAttribute("to", servername); stream.setAttribute("xmlns",
		 * "jabber:client"); stream.setAttribute("xmlns:stream",
		 * "http://etherx.jabber.org/streams"); stream.setAttribute("version",
		 * "1.0");
		 * 
		 * // create Transformer object transformer =
		 * TransformerFactory.newInstance().newTransformer(); StringWriter
		 * writer = new StringWriter(); StreamResult result = new
		 * StreamResult(writer); transformer.transform(new DOMSource(doc),
		 * result);
		 * 
		 * return writer.toString(); } catch (Exception e) {
		 * e.printStackTrace(); return ""; }
		 */

		return "<?xml version=\"1.0\"?><stream:stream xmlns=\"jabber:client\" xmlns:stream=\"http://etherx.jabber.org/streams\" to=\""
				+ servername + "\" version=\"1.0\">";

	}

	public static String closeDialog() {
		return "</stream:stream>";
	}

	// Methods for creating account
	// <iq type='get' id='reg1'><query xmlns='jabber:iq:register'/></iq>
	public static String requiredInformations() {
		Element iqElement, queryElement;

		try {

			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.newDocument();
			iqElement = doc.createElement("iq");
			doc.appendChild(iqElement);
			iqElement.setAttribute("type", "get");
			iqElement.setAttribute("id", "reg1");

			queryElement = doc.createElement("query");
			queryElement.setAttribute("xmlns", "jabber:iq:register");
			iqElement.appendChild(queryElement);

			// create Transformer object
			transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
					"yes");
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			transformer.transform(new DOMSource(doc), result);
			// <iq type="get" id="reg1"><query xmlns="jabber:iq:register"/></iq>
			return writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String register(String username, String password) {

		Element iqElement, queryElement, usernameElement, passwordElement;

		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.newDocument();

			iqElement = doc.createElement("iq");
			doc.appendChild(iqElement);
			iqElement.setAttribute("type", "set");
			iqElement.setAttribute("id", "reg2");

			queryElement = doc.createElement("query");
			queryElement.setAttribute("xmlns", "jabber:iq:register");
			iqElement.appendChild(queryElement);

			usernameElement = doc.createElement("username");
			usernameElement.setTextContent(username);
			queryElement.appendChild(usernameElement);

			passwordElement = doc.createElement("password");
			passwordElement.setTextContent(password);
			queryElement.appendChild(passwordElement);

			transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
					"yes");
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			transformer.transform(new DOMSource(doc), result);

			return writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String login(String username, String password) {
		return "";
	}
}

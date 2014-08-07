package etna.pmob.jabberclient.util;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import etna.pmob.jabberclient.datas.History;

public class XMLHistory {

	private static Document doc;
	private static Transformer transformer;
	private final static String filepath = "history.xml";

	public static String addHistory(History history) {

		try {
			// String filepath = "c:\\file.xml";
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);

			// Get the root element
			Node historyNode = doc.getFirstChild(); // <history>

			// Get the staff element , it may not working if tag has spaces, or
			// whatever weird characters in front...it's better to use
			// getElementsByTagName() to get it directly.
			// Node staff = company.getFirstChild();

			Element contact = doc.createElement("contact");
			contact.setAttribute("email", history.getContact().getEmailId());
			contact.setAttribute("name", history.getContact().getName());

			// message element
			Element message = doc.createElement("message");
			message.appendChild(doc.createTextNode(history.getMessage()
					.getContent()));

			// date element
			Element date = doc.createElement("date");
			date.appendChild(doc.createTextNode(history.getMessage().getDate()));

			contact.appendChild(date);
			contact.appendChild(message);

			// Add in history list
			historyNode.appendChild(contact);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filepath));
			transformer.transform(source, result);

			return transformer.toString();

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
			return "";
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
			return "";
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return "";
		} catch (SAXException sae) {
			sae.printStackTrace();
			return "";
		}

	}

}

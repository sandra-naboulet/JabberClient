package etna.pmob.jabberclient.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import etna.pmob.jabberclient.network.Result;
import etna.pmob.jabberclient.network.Result.Status;

public class Response {

	// XML node keys
	static final String KEY_IQ = "iq"; // parent node
	static final String KEY_QUERY = "query";
	static final String KEY_ERROR = "error";

	// XML node attributes
	static final String ATTR_TYPE = "type";

	public static Result getResult(String response) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document doc;

		try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(new InputSource(new ByteArrayInputStream(
					response.getBytes("utf-8"))));

			doc.getDocumentElement().normalize();

			// NodeList nodesList = doc.getElementsByTagName(KEY_IQ);
			// Node nNode = nodesList.item(0);
			Node nNode = doc.getDocumentElement();
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;
				String value = eElement.getAttribute(ATTR_TYPE);
				if (value.equals("result")) {
					return new Result(Status.SUCCESS, "");
				} else {
					String errorMessage = doc.getElementById(KEY_ERROR)
							.getTextContent();
					return new Result(Status.ERROR, errorMessage);
				}
			}

			return new Result(Status.ERROR, "error parsing");

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return new Result(Status.ERROR, "lala");
		} catch (SAXException e) {
			e.printStackTrace();
			return new Result(Status.ERROR, "lala");
		} catch (IOException e) {
			e.printStackTrace();
			return new Result(Status.ERROR, "lala");
		}

	}

}

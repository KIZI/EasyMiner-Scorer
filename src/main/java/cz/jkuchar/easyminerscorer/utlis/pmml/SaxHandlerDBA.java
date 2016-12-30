package cz.jkuchar.easyminerscorer.utlis.pmml;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 
 * @author Jaroslav Kuchar <https://github.com/jaroslav-kuchar>
 *
 */
public class SaxHandlerDBA extends DefaultHandler {

	XMLReader reader;
	SaxHandlerRoot parent;
	String content = "";
	String id;

	private Map<String, String> tmp = new HashMap<String, String>();

	public SaxHandlerDBA(XMLReader reader, SaxHandlerRoot parent, String id) {
		super();
		this.reader = reader;
		this.parent = parent;
		this.id = id;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		content += String.copyValueOf(ch, start, length).trim();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		content = "";
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (qName.equals("BARef")) {
			tmp.put(content, content);
		} else if (qName.equals("DBA")) {
			parent.allMap.put(id, tmp);
			reader.setContentHandler(parent);
		}
	}

}

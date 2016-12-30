package cz.jkuchar.easyminerscorer.utlis.pmml;

import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import cz.jkuchar.easyminerscorer.utlis.Tuple;

/**
 * 
 * @author Jaroslav Kuchar <https://github.com/jaroslav-kuchar>
 *
 */
public class SaxHandlerBBA extends DefaultHandler {

	XMLReader reader;
	SaxHandlerRoot parent;
	String content = "";

	String id;
	String field;
	String cat;

	public SaxHandlerBBA(XMLReader reader, SaxHandlerRoot parent, String id) {
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
		if (qName.equals("FieldRef")) {
			field = new String(content);
		}
		if (qName.equals("CatRef")) {
			cat = new String(content);
		}
		if (qName.equals("BBA")) {
			parent.allMap.put(id, new HashMap<String, String>() {
				{
					put(field, cat);
				}
			});
			reader.setContentHandler(parent);
		}
	}

}

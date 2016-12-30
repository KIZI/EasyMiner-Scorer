package cz.jkuchar.easyminerscorer.utlis.pmml;

import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 
 * @author Jaroslav Kuchar <https://github.com/jaroslav-kuchar>
 *
 */
public class SaxHandlerItem extends DefaultHandler {

	XMLReader reader;
	SaxHandlerRoot parent;

	String id;
	String field;
	String cat;

	public SaxHandlerItem(XMLReader reader, SaxHandlerRoot parent, String id) {
		super();
		this.reader = reader;
		this.parent = parent;
		this.id = id;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (qName.equals("Extension")
				&& "field".equals(attributes.getValue("name"))) {

			field = new String(attributes.getValue("value"));
		}
		if (qName.equals("Extension")
				&& "value".equals(attributes.getValue("name"))) {

			cat = new String(attributes.getValue("value"));
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (qName.equals("Item")) {
			parent.allMap.put(id, new HashMap<String, String>() {
				{
					put(field, cat);
				}
			});
			reader.setContentHandler(parent);
		}
	}

}

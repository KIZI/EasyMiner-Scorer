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
public class SaxHandlerItemset extends DefaultHandler {

	XMLReader reader;
	SaxHandlerRoot parent;

	String id;
	Map<String, String> tmp;

	public SaxHandlerItemset(XMLReader reader, SaxHandlerRoot parent, String id) {
		super();
		this.reader = reader;
		this.parent = parent;
		this.id = id;
		this.tmp = new HashMap<String, String>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (qName.equals("ItemRef")) {
			tmp.put(attributes.getValue("itemRef"), attributes.getValue("itemRef"));
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (qName.equals("Itemset")) {
			parent.allMap.put(id, tmp);
			reader.setContentHandler(parent);
		}
	}

}

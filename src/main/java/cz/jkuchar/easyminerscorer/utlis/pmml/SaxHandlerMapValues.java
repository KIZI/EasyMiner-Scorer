package cz.jkuchar.easyminerscorer.utlis.pmml;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import cz.jkuchar.easyminerscorer.utlis.transformation.TransformationBuilder;

/**
 * Pmml MapValues handler
 * @author Jaroslav Kuchar <https://github.com/jaroslav-kuchar>
 *
 */
public class SaxHandlerMapValues extends DefaultHandler {

	private static Log logger = LogFactory.getLog(SaxHandlerMapValues.class);

	XMLReader reader;
	SaxHandlerRoot parent;
	String content = "";

	String newName = "";
	String fromName = "";
	String mapToName = "";
	String mapFromName = "";
	Map<String, String> mapper;

	String column = "";
	String field = "";

	public SaxHandlerMapValues(XMLReader reader, SaxHandlerRoot parent,
			String newName, String mapToName) {
		super();
		this.reader = reader;
		this.parent = parent;
		this.newName = newName;
		this.mapToName = mapToName;
		mapper = new HashMap<String, String>();
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		content += String.copyValueOf(ch, start, length).trim();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (qName.equals("FieldColumnPair")) {
			fromName = attributes.getValue("field");
			mapFromName = attributes.getValue(mapFromName);
		}
		content = "";
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (qName.equals("MapValues")) {
			parent.transformations.add(TransformationBuilder.mapValues(
					fromName, newName, mapper));
			reader.setContentHandler(parent);
		}
		if (qName.equals("column")) {
			column = content;
		}
		if (qName.equals(mapToName)) {
			field = content;
		}
		if (qName.equals("row")) {
			mapper.put(column, field);
		}
	}

}

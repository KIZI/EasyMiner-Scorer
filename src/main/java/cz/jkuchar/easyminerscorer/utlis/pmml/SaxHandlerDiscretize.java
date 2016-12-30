package cz.jkuchar.easyminerscorer.utlis.pmml;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import cz.jkuchar.easyminerscorer.utlis.transformation.DiscretizeBin;
import cz.jkuchar.easyminerscorer.utlis.transformation.TransformationBuilder;

/**
 * Pmml Discretize handler
 * @author Jaroslav Kuchar <https://github.com/jaroslav-kuchar>
 *
 */
public class SaxHandlerDiscretize extends DefaultHandler {

	XMLReader reader;
	SaxHandlerRoot parent;
	String content = "";
	
	String fromName;
	String newName;
	String binValue;
	String closure;
	String leftMargin;
	String rightMargin;
	List<Function<String, String>> mapper;

	public SaxHandlerDiscretize(XMLReader reader, SaxHandlerRoot parent, String newName, String fromName) {
		super();
		this.reader = reader;
		this.parent = parent;
		this.fromName = fromName;
		this.newName = newName;
		this.mapper = new LinkedList<Function<String, String>>();
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		content += String.copyValueOf(ch, start, length).trim();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (qName.equals("DiscretizeBin")) {
			binValue = attributes.getValue("binValue");
		}
		if (qName.equals("Interval")) {
			closure = new String(attributes.getValue("closure"));
			leftMargin = attributes.getValue("leftMargin");
			rightMargin = attributes.getValue("rightMargin");
		}
		content = "";
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (qName.equals("DiscretizeBin")) {
			mapper.add(new DiscretizeBin(closure, leftMargin, rightMargin, binValue));			
		}
		if (qName.equals("Discretize")) {
			parent.transformations.add(TransformationBuilder.discretize(fromName, newName, mapper));
			reader.setContentHandler(parent);
		}
	}

}

package cz.jkuchar.easyminerscorer.utlis.pmml;

import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import cz.jkuchar.easyminerscorer.rules.BadRuleFormatException;
import cz.jkuchar.easyminerscorer.rules.Rule;
import cz.jkuchar.easyminerscorer.rules.RuleBuilder;

/**
 * Pmml Rule handler
 * 
 * @author Jaroslav Kuchar <https://github.com/jaroslav-kuchar>
 *
 */
public class SaxHandlerAssociationRule extends DefaultHandler {
	XMLReader reader;
	SaxHandlerRoot parent;
	String content = "";
	boolean isConfidence = false;
	boolean isSupport = false;

	String id;
	String antecedent;
	String consequent;
	double confidence;
	double support;
	String text;

	public SaxHandlerAssociationRule(XMLReader reader, SaxHandlerRoot parent,
			String id, String antecedent, String consequent) {
		super();
		this.reader = reader;
		this.parent = parent;
		this.id = id;
		this.antecedent = antecedent;
		this.consequent = consequent;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		content += String.copyValueOf(ch, start, length).trim();
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (qName.equals("AssociationRule")) {
			RuleBuilder rb = Rule.builder().id(this.id)
					.confidence(this.confidence).support(this.support);
			if (antecedent != null) {
				Map<String, String> cedent = SaxHandlerRoot.iterateOverMap(
						antecedent, parent.allMap);
				for (String key : cedent.keySet()) {
					rb.putAntecedent(key, cedent.get(key));
				}
			}
			if (consequent != null) {
				Map<String, String> cedent = SaxHandlerRoot.iterateOverMap(
						consequent, parent.allMap);
				if (cedent.keySet().size() != 1) {
					throw new BadRuleFormatException(
							"Consequent with only one filed is allowed: "
									+ cedent);
				}
				for (String key : cedent.keySet()) {
					rb.putConsequent(key, cedent.get(key));
				}
			}

			parent.rules.add(rb.build());
			reader.setContentHandler(parent);
		} else if (qName.equals("Text")) {
			this.text = content;
		}
		if (qName.equals("IMValue")) {
			if (isConfidence) {
				this.confidence = Double.valueOf(content);
				isConfidence = false;
			}
			if (isSupport) {
				this.support = Double.valueOf(content);
				isSupport = false;
			}

		}
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (qName.equals("IMValue") && attributes.getValue(0).equals("Conf")) {
			isConfidence = true;
		}
		if (qName.equals("IMValue") && attributes.getValue(0).equals("Supp")) {
			isSupport = true;
		}
		if (qName.equals("FourFtTable")) {
			double a = Double.valueOf(attributes.getValue("a"));
			double b = Double.valueOf(attributes.getValue("b"));
			double c = Double.valueOf(attributes.getValue("c"));
			double d = Double.valueOf(attributes.getValue("d"));
			this.support = a / (a + b + c + d);
			this.confidence = a / (a + b);
		}
		content = "";
	}

}

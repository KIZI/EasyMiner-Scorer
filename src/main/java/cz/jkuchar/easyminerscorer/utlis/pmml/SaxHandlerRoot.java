package cz.jkuchar.easyminerscorer.utlis.pmml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import cz.jkuchar.easyminerscorer.rules.BadRuleFormatException;
import cz.jkuchar.easyminerscorer.rules.Item;
import cz.jkuchar.easyminerscorer.rules.Rule;
import cz.jkuchar.easyminerscorer.rules.RuleBuilder;
import cz.jkuchar.easyminerscorer.utlis.Tuple;

/**
 * Pmml Root handler
 * 
 * @author Jaroslav Kuchar <https://github.com/jaroslav-kuchar>
 *
 */
public class SaxHandlerRoot extends DefaultHandler {

	XMLReader reader;
	List<Rule> rules = new ArrayList<Rule>();
	List<Function<Item, Item>> transformations = new LinkedList<Function<Item, Item>>();

	Map<String, Map<String, String>> allMap = new HashMap<String, Map<String, String>>();

	Map<String, Tuple<String>> bba = new HashMap<String, Tuple<String>>();
	Map<String, Map<String, String>> dba = new HashMap<String, Map<String, String>>();
	Map<String, String> tmp = new HashMap<String, String>();

	private boolean isGuha = true;

	private String newName = "";

	public SaxHandlerRoot(XMLReader reader) {
		super();
		this.reader = reader;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		if (localName.equals("AssociationModel") && uri.indexOf("GUHA") > -1) {
			isGuha = true;
		} else if (localName.equals("AssociationModel")
				&& uri.indexOf("GUHA") < 0) {
			isGuha = false;
		} else if (qName.equals("DerivedField")) {
			newName = attributes.getValue("name");
		} else if (qName.equals("MapValues")) {
			reader.setContentHandler(new SaxHandlerMapValues(reader, this,
					newName, attributes.getValue("outputColumn")));
		} else if (qName.equals("Discretize")) {
			reader.setContentHandler(new SaxHandlerDiscretize(reader, this,
					newName, attributes.getValue("field")));
		}

		if (isGuha) {

			switch (qName) {
			case "AssociationRule": {
				reader.setContentHandler(new SaxHandlerAssociationRule(reader,
						this, attributes.getValue("id"), attributes
								.getValue("antecedent"), attributes
								.getValue("consequent")));
				break;
			}
			case "BBA": {
				reader.setContentHandler(new SaxHandlerBBA(reader, this,
						attributes.getValue("id")));
				break;
			}
			case "DBA": {
				if (!"Conjunction".equals(attributes.getValue("connective"))) {
					throw new BadRuleFormatException(
							"Only Conjunction in rule cedents is supported (DBA: "
									+ attributes.getValue("id") + ")");
				} else {
					reader.setContentHandler(new SaxHandlerDBA(reader, this,
							attributes.getValue("id")));
				}
				break;
			}

			default: {
				break;
			}
			}
		} else {
			switch (qName) {
			case "Item": {
				reader.setContentHandler(new SaxHandlerItem(reader, this,
						attributes.getValue("id")));
				break;
			}
			case "Itemset": {
				reader.setContentHandler(new SaxHandlerItemset(reader, this,
						attributes.getValue("id")));
				break;
			}
			case "AssociationRule": {
				RuleBuilder rb = Rule
						.builder()
						.confidence(
								Double.parseDouble(attributes
										.getValue("confidence")))
						.support(
								Double.parseDouble(attributes
										.getValue("support")));
				if (attributes.getValue("id") != null) {
					rb.id(attributes.getValue("id"));
				}
				if (attributes.getValue("antecedent") != null) {
					Map<String, String> cedent = iterateOverMap(
							attributes.getValue("antecedent"), allMap);
					for (String key : cedent.keySet()) {
						rb.putAntecedent(key, cedent.get(key));
					}
				}
				if (attributes.getValue("consequent") != null) {
					Map<String, String> cedent = iterateOverMap(
							attributes.getValue("consequent"), allMap);
					if (cedent.keySet().size() != 1) {
						throw new BadRuleFormatException(
								"Consequent with only one filed is allowed: "
										+ cedent);
					}
					for (String key : cedent.keySet()) {
						rb.putConsequent(key, cedent.get(key));
					}
				}
				this.rules.add(rb.build());
				break;
			}
			default: {
				break;
			}
			}
		}
	}

	protected static Map<String, String> iterateOverMap(String key,
			Map<String, Map<String, String>> allMap) {
		Map<String, String> result = new HashMap<String, String>();
		for (String tmpKey : allMap.get(key).keySet()) {
			if (allMap.keySet().contains(tmpKey)) {
				result.putAll(iterateOverMap(tmpKey, allMap));
			} else {
				return allMap.get(key);
			}
		}
		return result;

	}
}

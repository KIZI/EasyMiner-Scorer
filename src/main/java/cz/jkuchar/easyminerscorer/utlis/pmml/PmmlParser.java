package cz.jkuchar.easyminerscorer.utlis.pmml;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import cz.jkuchar.easyminerscorer.rules.BadRuleFormatException;
import cz.jkuchar.easyminerscorer.rules.Item;
import cz.jkuchar.easyminerscorer.rules.Rule;

/**
 * PMML parser
 * 
 * @author Jaroslav Kuchar <https://github.com/jaroslav-kuchar>
 *
 */
@Component
@Scope("prototype")
public class PmmlParser {

	List<Rule> rules = new LinkedList<Rule>();
	List<Function<Item, Item>> transformations = new LinkedList<Function<Item, Item>>();

	public void parse(InputStream source) {
		try {
			SAXParserFactory parserFactor = SAXParserFactory.newInstance();
			parserFactor.setNamespaceAware(true);
			SAXParser parser = parserFactor.newSAXParser();
			XMLReader xmlReader = parser.getXMLReader();
			SaxHandlerRoot handler = new SaxHandlerRoot(xmlReader);
			xmlReader.setContentHandler(handler);
			xmlReader.parse(new InputSource(source));
			this.rules = handler.rules;
			this.transformations = handler.transformations;

		} catch (Exception e) {
			e.printStackTrace();
			throw new BadRuleFormatException(e.toString());
		}
	}

	public List<Rule> getRules() {
		return this.rules;
	}

	public List<Function<Item, Item>> getTransformations() {
		return transformations;
	}

}

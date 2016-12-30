package cz.jkuchar.easyminerscorer.utils;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cz.jkuchar.easyminerscorer.TestConfiguration;
import cz.jkuchar.easyminerscorer.rules.Item;
import cz.jkuchar.easyminerscorer.rules.Rule;
import cz.jkuchar.easyminerscorer.utlis.IO;
import cz.jkuchar.easyminerscorer.utlis.pmml.PmmlParser;
import cz.jkuchar.easyminerscorer.utlis.transformation.TransformationBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public class GuhaPmmlParserTest {

	// private static Log logger = LogFactory.getLog(PmmlParserTest.class);

	InputStream source;

	@Autowired
	PmmlParser pmmlParser;

	@Before
	public void load() throws IOException {
		this.source = getClass().getResourceAsStream("/guha-pmml.xml");
	}

	@Test
	public void testNumberRules() {
		pmmlParser.parse(source);
		Assert.assertEquals(257, pmmlParser.getRules().size());
	}

	@Test
	public void testFirstRule() {
		pmmlParser.parse(source);
		Rule rule = pmmlParser.getRules().get(0);
		Assert.assertEquals(0.01, rule.getSupport(), 0.001);
		Assert.assertEquals(1.0, rule.getConfidence(), 0.001);
		Assert.assertEquals("Liberec", rule.getAnt().get("District").iterator()
				.next());
		Assert.assertEquals("bad", rule.getCons().get("stav").iterator().next());
	}

	@Test
	public void testNumberTransformations() {
		pmmlParser.parse(source);
		Assert.assertEquals(6, pmmlParser.getTransformations().size());
	}

	@Test
	public void testTransformations() {
		pmmlParser.parse(source);
		Item item = new Item();
		item.put("amount", "10000");
		item.put("District", "Pisek");
		TransformationBuilder.transform(item, pmmlParser.getTransformations());
		Assert.assertEquals("[4980;122148)", item.get("amount2"));
		Assert.assertEquals("Pisek", item.get("District"));
	}

	@Test
	public void testTransformations2() {
		pmmlParser.parse(source);
		Item item = new Item();
		item.put("status", "C");
		TransformationBuilder.transform(item, pmmlParser.getTransformations());
		Assert.assertEquals("C", item.get("status"));
		Assert.assertEquals("bad", item.get("stav"));
	}

	@Test
	public void testTransformations3() {
		pmmlParser.parse(source);
		Item item = new Item();
		item.put("amount", "356484");
		TransformationBuilder.transform(item, pmmlParser.getTransformations());
		Assert.assertEquals("[356484;473652)", item.get("amount2"));
	}

}

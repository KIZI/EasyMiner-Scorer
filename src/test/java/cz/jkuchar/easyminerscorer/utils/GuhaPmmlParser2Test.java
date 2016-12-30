package cz.jkuchar.easyminerscorer.utils;

import cz.jkuchar.easyminerscorer.TestConfiguration;
import cz.jkuchar.easyminerscorer.rules.Item;
import cz.jkuchar.easyminerscorer.rules.Rule;
import cz.jkuchar.easyminerscorer.utlis.pmml.PmmlParser;
import cz.jkuchar.easyminerscorer.utlis.transformation.TransformationBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.InputStream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public class GuhaPmmlParser2Test {

	InputStream source;

	@Autowired
	PmmlParser pmmlParser;

	@Before
	public void load() throws IOException {
		this.source = getClass().getResourceAsStream("/guha-pmml2.xml");
	}

	@Test
	public void testNumberRules() {
		pmmlParser.parse(source);
		Assert.assertEquals(77, pmmlParser.getRules().size());
	}

	@Test
	public void testFirstRule() {
		pmmlParser.parse(source);
		Rule rule = pmmlParser.getRules().get(0);
		Assert.assertEquals(0.0819936, rule.getSupport(), 0.0000001);
		Assert.assertEquals(1.0, rule.getConfidence(), 0.001);
		Assert.assertEquals("-inf_to_0.0", rule.getAnt().get("x5").iterator()
				.next());
		Assert.assertEquals("-1.0", rule.getCons().get("y").iterator().next());
	}

	@Test
	public void testNumberTransformations() {
		pmmlParser.parse(source);
		Assert.assertEquals(15, pmmlParser.getTransformations().size());
	}

	@Test
	public void testTransformations() {
		pmmlParser.parse(source);
		Item item = new Item();
		item.put("Y", "1");
		item.put("X5", "-inf_to_0.0");
		TransformationBuilder.transform(item, pmmlParser.getTransformations());
		Assert.assertEquals("-inf_to_0.0", item.get("x5"));
		Assert.assertEquals("1", item.get("y"));
	}

}

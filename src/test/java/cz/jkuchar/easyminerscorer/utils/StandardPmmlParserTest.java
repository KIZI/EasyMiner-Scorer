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
import cz.jkuchar.easyminerscorer.rules.Rule;
import cz.jkuchar.easyminerscorer.utlis.IO;
import cz.jkuchar.easyminerscorer.utlis.pmml.PmmlParser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public class StandardPmmlParserTest {

	InputStream source;

	@Autowired
	PmmlParser pmmlParser;

	@Before
	public void load() throws IOException {
		this.source = getClass().getResourceAsStream("/standard-pmml.xml");
	}

	@Test
	public void testNumberRules() {
		pmmlParser.parse(source);
		Assert.assertEquals(123, pmmlParser.getRules().size());
	}

	@Test
	public void testFirstRule() {
		pmmlParser.parse(source);
		Rule rule = pmmlParser.getRules().get(0);
		Assert.assertEquals(0.00526, rule.getSupport(), 0.0001);
		Assert.assertEquals(0.762, rule.getConfidence(), 0.001);
		Assert.assertEquals("Dan_Brown", rule.getAnt().get("author").iterator()
				.next());
		Assert.assertEquals("0", rule.getCons().get("rating").iterator().next());
	}

	@Test
	public void testNumberTransformations() {
		pmmlParser.parse(source);
		Assert.assertEquals(0, pmmlParser.getTransformations().size());
	}

}

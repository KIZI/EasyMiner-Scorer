package cz.jkuchar.easyminerscorer.utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cz.jkuchar.easyminerscorer.TestConfiguration;
import cz.jkuchar.easyminerscorer.rules.Item;
import cz.jkuchar.easyminerscorer.utlis.transformation.TransformationBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public class TransformationTest {

//	private static Log logger = LogFactory.getLog(TransformationTest.class);

	@Test
	public void mapToSame() {
		String source = "a";
		String target = "a";
		Map<String, String> mapper = new HashMap<String, String>() {
			{
				put("v1", "v2");
			}
		};
		Item item = new Item();
		item.put("a", "v1");

		Item it = TransformationBuilder.mapValues(source, target, mapper)
				.apply(item);
		Assert.assertEquals(it.get("a"), "v2");
	}

	@Test
	public void mapToDifferent() {
		String source = "a";
		String target = "b";
		Map<String, String> mapper = new HashMap<String, String>() {
			{
				put("v1", "v2");
			}
		};
		Item item = new Item();
		item.put("a", "v1");

		Item it = TransformationBuilder.mapValues(source, target, mapper)
				.apply(item);
		Assert.assertEquals(it.get("b"), "v2");
	}

	@Test
	public void mapToNonExist() {
		String source = "c";
		String target = "b";
		Map<String, String> mapper = new HashMap<String, String>() {
			{
				put("v1", "v2");
			}
		};
		Item item = new Item();
		item.put("a", "v1");

		Item it = TransformationBuilder.mapValues(source, target, mapper)
				.apply(item);
		Assert.assertFalse(it.containsKey("b"));
		Assert.assertFalse(it.containsKey("c"));
	}

	@Test
	public void discretize() {
		String source = "a";
		String target = "a";
		List<Function<String, String>> mapper = new LinkedList<Function<String, String>>();
		mapper.add(value -> {
			if (Double.valueOf(value) >= 0 && Double.valueOf(value) <= 100) {
				return "[0;100]";
			}
			return value;
		});
		Item item = new Item();
		item.put("a", "5");

		Item it = TransformationBuilder.discretize(source, target, mapper)
				.apply(item);
		Assert.assertEquals(it.get("a"), "[0;100]");
	}

}

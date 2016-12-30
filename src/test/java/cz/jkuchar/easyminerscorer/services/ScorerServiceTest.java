package cz.jkuchar.easyminerscorer.services;

import cz.jkuchar.easyminerscorer.TestConfiguration;
import cz.jkuchar.easyminerscorer.rules.Item;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public class ScorerServiceTest {


	@Test
	public void test() throws IOException {
		ScorerService scorerService = new ScorerService();
		List<Item> out = scorerService.processJson("{\"a\":1.0}");
		Assert.assertEquals(out.size(),1);
		Assert.assertEquals(out.get(0).containsKey("a"),true);
		Assert.assertEquals(out.get(0).get("a"),"1.0");

		out = scorerService.processJson("{\"a\":1}");
		Assert.assertEquals(out.size(),1);
		Assert.assertEquals(out.get(0).containsKey("a"),true);
		Assert.assertEquals(out.get(0).get("a"),"1");


		out = scorerService.processJson("{\"a\":\"1.0\"}");
		Assert.assertEquals(out.size(),1);
		Assert.assertEquals(out.get(0).containsKey("a"),true);
		Assert.assertEquals(out.get(0).get("a"),"1.0");

		out = scorerService.processJson("{\"a\":\"1\"}");
		Assert.assertEquals(out.size(),1);
		Assert.assertEquals(out.get(0).containsKey("a"),true);
		Assert.assertEquals(out.get(0).get("a"),"1");
	}


}

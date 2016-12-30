package cz.jkuchar.easyminerscorer.services;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cz.jkuchar.easyminerscorer.TestConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public class ConfusionMatrixTest {

	@Test(expected = IllegalArgumentException.class)
	public void testNull() {
		ConfusionMatrix.build(null, null);
	}

	@Test
	public void test() {
		List<Object> truth = Arrays.asList("a", "b", "a");
		List<Object> prediction = Arrays.asList("a", "b", "a");
		ConfusionMatrix cm = ConfusionMatrix.build(truth, prediction);
		Assert.assertEquals(cm.getLabels().size(), 2);
		Assert.assertEquals(cm.getMatrix()[0][0], 2);
		Assert.assertEquals(cm.getMatrix()[1][1], 1);
	}

	@Test
	public void test2() {
		List<Object> truth = Arrays.asList("a", "b", "c");
		List<Object> prediction = Arrays.asList("a", "b", "c");
		ConfusionMatrix cm = ConfusionMatrix.build(truth, prediction);
		Assert.assertEquals(cm.getLabels().size(), 3);
		Assert.assertEquals(cm.getMatrix()[0][0], 1);
		Assert.assertEquals(cm.getMatrix()[1][1], 1);
		Assert.assertEquals(cm.getMatrix()[2][2], 1);
	}

	@Test
	public void test3() {
		List<Object> truth = Arrays.asList("a", "b");
		List<Object> prediction = Arrays.asList("a", "d");
		ConfusionMatrix cm = ConfusionMatrix.build(truth, prediction);
		Assert.assertEquals(3, cm.getLabels().size());
		Assert.assertEquals(cm.getMatrix()[0][0], 1);
		Assert.assertEquals(cm.getMatrix()[1][2], 1);
	}
}

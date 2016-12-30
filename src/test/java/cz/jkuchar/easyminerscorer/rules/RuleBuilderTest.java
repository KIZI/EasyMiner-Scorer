package cz.jkuchar.easyminerscorer.rules;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cz.jkuchar.easyminerscorer.TestConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestConfiguration.class})
public class RuleBuilderTest {
	
	@Test
	public void emptyRule(){
		Rule rule = Rule.builder().build();
		Assert.assertEquals(rule.getConfidence(), 0, 0.01);
		Assert.assertEquals(rule.getSupport(), 0, 0.01);
		Assert.assertEquals(rule.getAnt().size(), 0);
		Assert.assertEquals(rule.getCons().size(), 0);		
	}
	
	@Test
	public void confidenceRule(){
		Rule rule = Rule.builder().confidence(0.1).build();
		Assert.assertEquals(rule.getConfidence(), 0.1, 0.01);
		Assert.assertEquals(rule.getSupport(), 0, 0.01);
		Assert.assertEquals(rule.getAnt().size(), 0);
		Assert.assertEquals(rule.getCons().size(), 0);		
	}
	
	@Test
	public void supportRule(){
		Rule rule = Rule.builder().support(0.1).build();
		Assert.assertEquals(rule.getConfidence(), 0, 0.01);
		Assert.assertEquals(rule.getSupport(), 0.1, 0.01);
		Assert.assertEquals(rule.getAnt().size(), 0);
		Assert.assertEquals(rule.getCons().size(), 0);		
	}
	
	@Test
	public void support2Rule(){
		Rule rule = Rule.builder().support(0.1).support(0.2).build();
		Assert.assertEquals(rule.getSupport(), 0.2, 0.01);		
	}
	
	@Test
	public void supportAndConfidenceRule(){
		Rule rule = Rule.builder().support(0.1).confidence(0.2).build();
		Assert.assertEquals(rule.getSupport(), 0.1, 0.01);
		Assert.assertEquals(rule.getConfidence(), 0.2, 0.01);
	}
	
	@Test
	public void antecedentRule(){
		Rule rule = Rule.builder().putAntecedent("a","b").build();
		Assert.assertEquals(rule.getAnt().size(), 1);
		Assert.assertEquals(rule.getCons().size(), 0);		
	}
	
	@Test
	public void antecedentSameRule(){
		Rule rule = Rule.builder().putAntecedent("a","b").putAntecedent("a","c").build();
		Assert.assertEquals(rule.getAnt().size(), 1);
		Assert.assertEquals(rule.getCons().size(), 0);		
	}
	
	@Test
	public void antecedent2Rule(){
		Rule rule = Rule.builder().putAntecedent("a","b").putAntecedent("d","c").build();
		Assert.assertEquals(rule.getAnt().size(), 2);
		Assert.assertEquals(rule.getCons().size(), 0);		
	}
	
	@Test
	public void idRule(){
		Rule rule = Rule.builder().id("id").build();
		Assert.assertEquals(rule.getId(), "id");		
	}
	
	@Test
	public void textRule(){
		Rule rule = Rule.builder().fromPmmlText("District2(big) & amount2([239316;356484)) → status(C)").build();
		Assert.assertEquals(rule.getAnt().size(), 2);
		Assert.assertEquals(rule.getCons().size(), 1);		
	}

}

package cz.jkuchar.easyminerscorer.rules;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Rule
 * @author Jaroslav Kuchar <https://github.com/jaroslav-kuchar>
 *
 */
@Component
@Scope("prototype")
public class Rule implements Comparable<Rule> {

	protected String id;
	// textual representation of rule
	protected String text;
	// confidence + support
	protected double confidence;
	protected double support;

	protected Map<String, Set<String>> antecendent = new HashMap<String, Set<String>>();
	protected Map<String, Set<String>> consequent = new HashMap<String, Set<String>>();

	protected Rule() {
		super();
	}

	public String getText() {
		return new String(text);
	}

	public double getConfidence() {
		return confidence;
	}

	public double getSupport() {
		return support;
	}
	
	public String getId() {
		return id;
	}

	public Map<String, Set<String>> getAnt() {
		return antecendent;
	}

	public Map<String, Set<String>> getCons() {
		return consequent;
	}

	public static RuleBuilder builder(){
		return new RuleBuilder();
	}

	@Override
	public String toString() {
		return "{ ---id=" + id + "\n---text="+ text + "\n---support=" + support + "\n---confidence="
				+ confidence + "\n---ant=" + antecendent + "\n---cons="
				+ consequent + "}" + "\n";
	}

	public int compareTo(Rule o) {
		int result = Double.compare(o.confidence, this.confidence);
		if (result != 0) {
			return result;
		}
		result = Double.compare(o.support, this.support);
		if (result != 0) {
			return result;
		}
		result = this.antecendent.size() - o.antecendent.size();
		return result;
	}	

}

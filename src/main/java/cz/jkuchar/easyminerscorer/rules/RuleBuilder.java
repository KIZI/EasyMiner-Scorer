package cz.jkuchar.easyminerscorer.rules;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Creator of rules
 * @author Jaroslav Kuchar <https://github.com/jaroslav-kuchar>
 *
 */
public class RuleBuilder {

	private Rule rule;

	public RuleBuilder() {
		this.rule = new Rule();
	}

	public Rule build() {
		return rule;
	}

	public RuleBuilder id(String id) {
		rule.id = id;
		return this;
	}

	public RuleBuilder confidence(double confidence) {
		rule.confidence = confidence;
		return this;
	}

	public RuleBuilder support(double support) {
		rule.support = support;
		return this;
	}

	public RuleBuilder putAntecedent(String key, String value) {
		if (this.rule.antecendent == null) {
			this.rule.antecendent = new HashMap<String, Set<String>>();
		}
		this.rule.antecendent.put(key, new HashSet<String>() {
			{
				add(value);
			}
		});
		return this;
	}

	public RuleBuilder putConsequent(String key, String value) {
		if (this.rule.consequent == null) {
			this.rule.consequent = new HashMap<String, Set<String>>();
		}
		this.rule.consequent.put(key, new HashSet<String>() {
			{
				add(value);
			}
		});
		return this;
	}

	public RuleBuilder fromPmmlText(String text) {
		this.rule.text = text;
		parsePmml();
		return this;
	}

	private void parsePmml() {
		if (this.rule.text != null && this.rule.text.length() > 0
				&& this.rule.text.matches("(.*?)\\s*(>:<|→)\\s*(.+?)")) {
			String[] parts = this.rule.text.trim().split("\\s*(>:<|→)\\s*");
			if (parts.length != 2) {
				throw new BadRuleFormatException("Wrong formattting of: "
						+ this.rule.text);
			}
			this.rule.antecendent = parsePart(parts[0]);
			this.rule.consequent = parsePart(parts[1]);

		} else {
			throw new BadRuleFormatException("Wrong formattting of: "
					+ this.rule.text);
		}
	}

	private Map<String, Set<String>> parsePart(String part) {
		Map<String, Set<String>> out = new HashMap<String, Set<String>>();
		String[] attrs = part.split("\\s*&\\s*");
		for (String attr : attrs) {
			Pattern pattern = Pattern.compile("(.*)\\((.*)\\)");
			Matcher matcher = pattern.matcher(attr);
			if (matcher.matches()) {
				Set<String> p = new HashSet<String>();
				for (String pp : matcher.group(2).split(",")) {
					p.add(pp);
				}
				out.put(matcher.group(1), p);
			} else if (attr != null && attr.length() > 0
					&& !"*".equals(attr.trim())) {
				throw new BadRuleFormatException(
						"Wrong formattting of rule items " + attr + "(rule: "
								+ this.rule.text + ")");
			}
		}
		return out;
	}

}

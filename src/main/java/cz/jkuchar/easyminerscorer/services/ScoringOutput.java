package cz.jkuchar.easyminerscorer.services;

import java.util.HashMap;
import java.util.List;

/**
 * Object with scoring output
 * @author Jaroslav Kuchar <https://github.com/jaroslav-kuchar>
 *
 */
public class ScoringOutput {
	public ConfusionMatrix cm;
	public List<HashMap<String, Object>> scores;
	public List<HashMap<String, Object>> rules;
	
	public ScoringOutput(ConfusionMatrix cm, List<HashMap<String, Object>> scores, List<HashMap<String, Object>> rules) {
		super();
		this.cm = cm;
		this.scores = scores;
		this.rules = rules;
	}
	public ConfusionMatrix getCm() {
		return cm;
	}
	public List<HashMap<String, Object>> getScores() {
		return scores;
	}
	public List<HashMap<String, Object>> getRules() {
		return rules;
	}
	

}

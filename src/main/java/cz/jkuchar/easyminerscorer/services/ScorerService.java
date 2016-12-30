package cz.jkuchar.easyminerscorer.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import cz.jkuchar.easyminerscorer.rules.Item;
import cz.jkuchar.easyminerscorer.rules.Rule;
import cz.jkuchar.easyminerscorer.rules.RuleEngine;
import cz.jkuchar.easyminerscorer.utlis.IO;
import cz.jkuchar.easyminerscorer.utlis.pmml.PmmlParser;
import cz.jkuchar.easyminerscorer.utlis.transformation.TransformationBuilder;

/**
 * Scoring service with cache
 * 
 * @author Jaroslav Kuchar <https://github.com/jaroslav-kuchar>
 *
 */
@Component
@Scope("prototype")
public class ScorerService {

	private static Log logger = LogFactory.getLog(ScorerService.class);

	@Autowired
	RuleEngine ruleEngine;

	private PmmlParser pmmlParser;
	private List<Rule> rules;

	@Cacheable(value = "scorers", key = "#id")
	public ScorerService createScorer(String id, String model) throws IOException {
		if (model == null) {
			throw new IllegalArgumentException("Model input is null.");
		}
		pmmlParser = new PmmlParser();
		pmmlParser.parse(new ByteArrayInputStream(model.getBytes()));
		rules = pmmlParser.getRules();
		if (rules.size() <= 0) {
			throw new IllegalArgumentException("Number of rules is 0.");
		}
		logger.info("Rules: " + rules.size());

		ruleEngine.clear();
		ruleEngine.addRules(rules);
		return this;

	}

	public ScorerService createScorer(String id, MultipartFile model) throws IOException {
		return createScorer(id, IO.streamToString(model.getInputStream()));
	}

	public ScoringOutput score(MultipartFile data) throws JsonProcessingException, IOException {
		return score(IO.streamToString(data.getInputStream()));
	}

	public ScoringOutput score(String data) throws JsonProcessingException, IOException {
		if (data == null) {
			throw new IllegalArgumentException("Data input is null.");
		}
		List<HashMap<String, Object>> output = new LinkedList<HashMap<String, Object>>();
		List<HashMap<String, Object>> usedRules = new LinkedList<HashMap<String, Object>>();
		List<Item> input = processJson(data);
		if (input.size() <= 0) {
			throw new IllegalArgumentException("Number of data items is 0.");
		}
		logger.info("Input size: " + input.size());
		String className = rules.get(0).getCons().keySet().iterator().next();

		logger.info("ClassName: " + className);

		List<Object> truth = new LinkedList<Object>();
		List<Object> predictions = new LinkedList<Object>();

		for (Item inputItem : input) {

			inputItem = TransformationBuilder.transform(inputItem, pmmlParser.getTransformations());

			Rule top = ruleEngine.getTopMatch(inputItem);
			truth.add(inputItem.get(className));
			if (top != null) {
				String value = top.getCons().get(className).iterator().next();
				predictions.add(value);
				output.add(new HashMap<String, Object>() {
					{
						put(className, value);
					}
				});
				usedRules.add(new HashMap<String, Object>() {
					{
						put("id", top.getId());
					}
				});
			} else {
				output.add(null);
				predictions.add(null);
				usedRules.add(null);
			}
		}

		ScoringOutput so = new ScoringOutput(ConfusionMatrix.build(truth, predictions), output, usedRules);
		return so;
	}

	protected List<Item> processJson(String data) throws JsonProcessingException, IOException {
		List<Item> input = new LinkedList<Item>();

		ObjectMapper mapper = new ObjectMapper();
		JsonNode inputData = mapper.readTree(data);

		if (inputData.isArray()) {
			for (JsonNode i : inputData) {
				Item item = new Item();
				Iterator<String> it = i.fieldNames();
				while (it.hasNext()) {
					String field = it.next();
					item.put(field, i.get(field).asText());
				}
				input.add(item);
			}
		} else {
			Item item = new Item();
			Iterator<String> it = inputData.fieldNames();
			while (it.hasNext()) {
				String field = it.next();
				item.put(field, inputData.get(field).asText());
			}
			input.add(item);
		}

		return input;

	}

}

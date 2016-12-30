package cz.jkuchar.easyminerscorer.rest;

import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import cz.jkuchar.easyminerscorer.services.ConfusionMatrix;
import cz.jkuchar.easyminerscorer.services.ScoringOutput;

/**
 * Ok response object
 * @author Jaroslav Kuchar <https://github.com/jaroslav-kuchar>
 *
 */
@Component
public class OkScoringResponse implements Response{
	
	@ApiModelProperty(value="Status code", required = true)
	public int code;
	@ApiModelProperty(value="Status message", required = true)
	public String description;
	@ApiModelProperty(value="Identifier of the scorer", required = true)
    public String id;
	@ApiModelProperty(value="Results of scoring for each provided item in the input data file. Return key=value (classAttribute=value)", required = true)
    public List<HashMap<String, Object>> score;
	@ApiModelProperty(value="List of rules used for scoring (id=ruleId)", required = true)
    public List<HashMap<String, Object>> rule;
	@ApiModelProperty(value="Confusion matrix of scores (null label for all NA values)", required = true)
    public ConfusionMatrix confusionMatrix;
    
    public OkScoringResponse(){
    	super();
    }

	public OkScoringResponse(HttpStatus code, String message, String id, ScoringOutput scoring) {
		this();
		this.code = code.value();
		this.description = message;
		this.id = id;
		this.score = scoring.getScores();
		this.rule = scoring.getRules();
		this.confusionMatrix = scoring.getCm();
	}    

}

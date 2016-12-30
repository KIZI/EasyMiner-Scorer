package cz.jkuchar.easyminerscorer.rest;

import io.swagger.annotations.ApiModelProperty;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * Ok response object
 * 
 * @author Jaroslav Kuchar <https://github.com/jaroslav-kuchar>
 *
 */
@Component
public class OkScorerResponse implements Response {

	@ApiModelProperty(value="Status code", required = true)
	public int code;
	@ApiModelProperty(value="Status message", required = true)
	public String description;
	@ApiModelProperty(value="Identifier of created scorer", required = true)
	public String id;

	public OkScorerResponse() {
		super();
	}

	public OkScorerResponse(HttpStatus code, String message, String id) {
		this();
		this.code = code.value();
		this.description = message;
		this.id = id;
	}

}

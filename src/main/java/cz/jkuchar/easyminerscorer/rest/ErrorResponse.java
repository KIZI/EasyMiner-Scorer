package cz.jkuchar.easyminerscorer.rest;

import io.swagger.annotations.ApiModelProperty;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * Error response object
 * @author Jaroslav Kuchar <https://github.com/jaroslav-kuchar>
 *
 */
@Component
public class ErrorResponse implements Response{
	
	@ApiModelProperty(value="Status code", required = true)
	public int code;
	@ApiModelProperty(value="Status message", required = true)
	public String description;

    public ErrorResponse(){
    	super();
    }

	public ErrorResponse(HttpStatus code, String message) {
		this();
        this.code = code.value();
        this.description = message;
    }

}

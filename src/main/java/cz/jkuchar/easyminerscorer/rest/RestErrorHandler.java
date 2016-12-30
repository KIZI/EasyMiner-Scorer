package cz.jkuchar.easyminerscorer.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Rest Error handler 
 * @author Jaroslav Kuchar <https://github.com/jaroslav-kuchar>
 *
 */
@ControllerAdvice
public class RestErrorHandler {
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(Exception.class)
	@ResponseBody Response handleBadRequest(HttpServletRequest req, Exception ex) {
	    return new ErrorResponse(HttpStatus.BAD_REQUEST, req.getRequestURL().toString() + ": "+ex.getLocalizedMessage());
	}
	
}

package cz.jkuchar.easyminerscorer.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import cz.jkuchar.easyminerscorer.services.ScorerService;
import cz.jkuchar.easyminerscorer.services.ScoringOutput;

/**
 * REST controller
 * @author Jaroslav Kuchar <https://github.com/jaroslav-kuchar>
 *
 */
@Controller
@ResponseBody
@Api(value="API v0.2")
@RequestMapping({ "v0.2" })
public class RestControllerV02 {
	
	@Autowired
	ScorerService scoring;
	
	/**
	 * Create scorer
	 * @param model PMML file
	 * @param data JSON file
	 * @param request Request object
	 * @return response object
	 */
	@RequestMapping(value = "/scorer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Submit a PMML model and create a new scorer", response = Response.class)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Scorer created", response = OkScorerResponse.class),
			@ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class) })
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody ResponseEntity<Response> scorer(
			@ApiParam(value = "pmml file", required=true) @RequestParam("model") MultipartFile model,
			HttpServletRequest request) {

		if (model.isEmpty()) {
			return ResponseEntity.badRequest().body(
					new ErrorResponse(HttpStatus.BAD_REQUEST,
							"Input for Model is empty."));
		}		
		try {
			String id = System.currentTimeMillis() + "-"
					+ (int) (Math.random() * 1000000);
			scoring.createScorer(id, model);
			return ResponseEntity
					.created(URI.create(request.getRequestURL() + "/" + id))
					.body(new OkScorerResponse(HttpStatus.CREATED,
							"Scorer has been succesfully created.", id));

		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(
					new ErrorResponse(HttpStatus.BAD_REQUEST, ex
							.getLocalizedMessage()));
		}
	}
	
	
	/**
	 * Read scorer
	 * @param id scorer id
	 * @return response object
	 */
	@RequestMapping(value = "/scorer/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Submit data for scoring", response = Response.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Scoring created", response = OkScoringResponse.class),
			@ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class) })
	public @ResponseBody ResponseEntity<Response> getScorer(
			@ApiParam(value = "scorer id", required=true) @PathVariable String id,
			@ApiParam(value = "json file", required=true) @RequestParam("data") MultipartFile data) {
		
		if (data.isEmpty()) {
			return ResponseEntity.badRequest().body(
					new ErrorResponse(HttpStatus.BAD_REQUEST,
							"Input for Data is empty."));
		}		
		try {
			ScorerService scorer = scoring.createScorer(id, "");
			ScoringOutput output = scorer.score(data);
			return ResponseEntity.ok().body(
					new OkScoringResponse(HttpStatus.OK,
							"Scorer exists and is returned.", id, output));

		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(
					new ErrorResponse(HttpStatus.BAD_REQUEST, ex
							.getLocalizedMessage()));
		}
	}
	
	
	

}

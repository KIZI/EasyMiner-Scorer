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
import org.springframework.web.multipart.MultipartFile;

import cz.jkuchar.easyminerscorer.services.ScoringOutput;
import cz.jkuchar.easyminerscorer.services.ScoringService;


/**
 * REST controller
 * @author Jaroslav Kuchar <https://github.com/jaroslav-kuchar>
 *
 */
@Controller
@ResponseBody
@Api(value="API v0.1")
@RequestMapping({ "v0.1"})
public class RestController {

	@Autowired
	ScoringService scoring;

	
	/**
	 * Create scorer
	 * @param model PMML file
	 * @param data JSON file
	 * @param request Request object
	 * @return response object
	 */
	@RequestMapping(value = "/scorer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Submit model and data for scoring", response = Response.class)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Scorer created", response = OkScoringResponse.class),
			@ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class) })
	public @ResponseBody ResponseEntity<Response> scorer(
			@ApiParam(value = "pmml file") @RequestParam("model") MultipartFile model,
			@ApiParam(value = "json file") @RequestParam("data") MultipartFile data,
			HttpServletRequest request) {

		if (model.isEmpty()) {
			return ResponseEntity.badRequest().body(
					new ErrorResponse(HttpStatus.BAD_REQUEST,
							"Input for Model is empty."));
		}
		if (data.isEmpty()) {
			return ResponseEntity.badRequest().body(
					new ErrorResponse(HttpStatus.BAD_REQUEST,
							"Input for Data is empty."));
		}
		try {
			String id = System.currentTimeMillis() + "-"
					+ (int) (Math.random() * 1000000);
			ScoringOutput output = scoring.createScorer(id, model, data);
			return ResponseEntity
					.created(URI.create(request.getRequestURL() + "/" + id))
					.body(new OkScoringResponse(HttpStatus.CREATED,
							"Scorer has been succesfully created.", id, output));

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
	@RequestMapping(value = "/scorer/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get scorer from cache", response = Response.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Scorer exists and is returned", response = OkScoringResponse.class),
			@ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class) })
	public @ResponseBody ResponseEntity<Response> getScorer(
			@ApiParam(value = "scorer id") @PathVariable String id) {
		try {
			ScoringOutput output = scoring.createScorer(id, null, null);
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

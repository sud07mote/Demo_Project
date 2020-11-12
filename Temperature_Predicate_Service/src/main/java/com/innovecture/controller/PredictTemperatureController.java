package com.innovecture.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.validation.ValidationErrors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innovecture.service.PredictTemperatureService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("${PREDICT_TEMP_ROOT}")
@Api("Predict temperature controller")
public class PredictTemperatureController {

	@Autowired
	PredictTemperatureService predictTemperatureService;

	/**
	 * @author: Sudarshan Mote
	 * @description: "Predict coolest hour of the day"
	 * @return: String
	 * @URI: localhost:8087/api/v1/weather/search?zipCode=90201
	 **/

	@GetMapping("${FETCH_TEMP_BY_ZIP_CODE}")
	@ApiOperation(value = "Predict coolest hour of the day", notes = "Predict coolest hour of the day", response = ResponseEntity.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "SUCCESS", response = ResponseEntity.class),
			@ApiResponse(code = 400, message = "Bad Request", response = ValidationErrors.class) })
	public String fetchTempByZipCode(@RequestParam("zipCode") String zipCode) {
		return predictTemperatureService.fetchCoolestHourTemperature(zipCode);
	}

}

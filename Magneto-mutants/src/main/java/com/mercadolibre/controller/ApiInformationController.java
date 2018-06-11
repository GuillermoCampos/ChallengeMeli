package com.mercadolibre.controller;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author Guille Campos
 */

@Controller
public class ApiInformationController {
		
	@ResponseBody
	@RequestMapping(value = "/healthCheck", method = GET)
	public ResponseEntity<Date> healthCheck() {
		Date serverTimestamp = new Date();
		return new ResponseEntity<>(serverTimestamp, OK);
	}
}

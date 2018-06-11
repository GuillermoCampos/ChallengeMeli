package com.mercadolibre.controller;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author Guille Campos
 */
public class ApiInformationControllerTest {	
	@Test
	public void canGeTiFORMATIONFromApplicationOk() {
		 ApiInformationController sut = new  ApiInformationController();
		ResponseEntity<Date> result = sut.healthCheck();
		assertNotNull(result);
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}
}

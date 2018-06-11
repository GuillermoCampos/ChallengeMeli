package com.mercadolibre.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mercadolibre.exception.ApiException;
import com.mercadolibre.exception.InvalidDnaExeption;
import com.mercadolibre.exception.ServiceException;
import com.mercadolibre.model.PersonStats;
import com.mercadolibre.service.MutantsService;
/**
 * @author Guille Campos
 */
public class MutantControllerTest {

	private MutantController mc;
	
    @Mock
	private MutantsService analysisService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mc = new MutantController(analysisService);
	}
	
	@Test
	public void whenHasAMutantDnaThenReturnHttpStatus200() throws InvalidDnaExeption, ServiceException {
		
		Mockito.when(analysisService.isMutant(Mockito.any())).thenReturn(Boolean.TRUE);
		// Exercise
		ResponseEntity<String> result = mc.analizeMutantCandidate(MutantFixtureData.getValidMutantDna());
		assertEquals(HttpStatus.OK, result.getStatusCode());
		
	}
	
	@Test
	public void whenHasAHummanDnaThenReturnHttpStatusForbidden() throws InvalidDnaExeption, ServiceException {
		
		Mockito.when(analysisService.isMutant(Mockito.any())).thenReturn(Boolean.FALSE);
		// Exercise
		ResponseEntity<String> result = mc.analizeMutantCandidate(MutantFixtureData.getHummanDna());
		assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
		
	}
	@SuppressWarnings("unchecked")
	@Test
	public void whenHasIncorrectMutantDnaThenReturStatusBadRequest() throws InvalidDnaExeption, ServiceException {
		
		Mockito.when(analysisService.isMutant(Mockito.any())).thenThrow(InvalidDnaExeption.class);
		// Exercise
		ResponseEntity<String> result = mc.analizeMutantCandidate(MutantFixtureData.getInvalidDna());
		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void whenHasServerErrorThenReturSInternalServerError() throws InvalidDnaExeption, ServiceException {		
		Mockito.when(analysisService.isMutant(Mockito.any())).thenThrow(ServiceException.class);
		// Exercise
		ResponseEntity<String> result = mc.analizeMutantCandidate(MutantFixtureData.getInvalidDna());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
	}
	

		
	@Test
	public void whenHasAGetStatsThenResultTheSamePersonStats() throws  ServiceException, ApiException {
		
		PersonStats person = new PersonStats(1,1);		
		Mockito.when(analysisService.getStats()).thenReturn(person);
		// Exercise
		PersonStats result = mc.stats();
		assertEquals(person, result);
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Test(expected = ApiException.class)
	public void whenHasExceprionServiceThenResultApieException() throws  ServiceException, ApiException {
	 
	Mockito.when(analysisService.getStats()).thenThrow(ServiceException.class);
		// Exercise
	  PersonStats stats = mc.stats();	
	}
	
	
}

package com.mercadolibre.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.mercadolibre.controller.MutantFixtureData;
import com.mercadolibre.exception.InvalidDnaExeption;
import com.mercadolibre.exception.ServiceException;
import com.mercadolibre.service.Impl.MutantDetectorImpl;
/**
 * @author Guille Campos
 */
public class MutantDetectorTest {

	private MutantDetector mutantDetector;
	 

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mutantDetector = new MutantDetectorImpl();
	}
	
	
	@Test
	public void whenHasAMutantDnaThenReResultOK() throws InvalidDnaExeption, ServiceException {
		
		assertEquals(mutantDetector.isMutant(MutantFixtureData.dnaMutant), true);
		
	}
	
	@Test
	public void whenHasAHummanDnaThenResultFalse() throws InvalidDnaExeption, ServiceException {
		
		assertEquals(mutantDetector.isMutant(MutantFixtureData.dnaHuman), false);	
	}
	
	@Test(expected = InvalidDnaExeption.class)
	public void whenHasAMutantDnaThenReHStatusForbidden() throws  InvalidDnaExeption {
		mutantDetector.isMutant(MutantFixtureData.invalidDNA);
	
	}

}

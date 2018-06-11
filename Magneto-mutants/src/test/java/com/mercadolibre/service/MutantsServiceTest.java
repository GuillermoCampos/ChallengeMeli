package com.mercadolibre.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.mercadolibre.controller.MutantFixtureData;
import com.mercadolibre.exception.InvalidDnaExeption;
import com.mercadolibre.exception.ServiceException;
import com.mercadolibre.model.Adn;
import com.mercadolibre.model.PersonStats;
import com.mercadolibre.model.StatsResult;
import com.mercadolibre.repository.DNABaseRepository;
import com.mercadolibre.service.Impl.MutantsServiceImpl;

/**
 * @author Guille Campos
 */
public class MutantsServiceTest {

	private MutantsService mutantsServiceImpl;
 
	@Mock
	 private DNABaseRepository dnaRepo;
	@Mock
	 private MutantDetector mutantDetector;
   

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mutantsServiceImpl = new MutantsServiceImpl(dnaRepo,mutantDetector);
	}
	
	
	@Test
	public void whenHasAMutantDnaThenResultOK() throws InvalidDnaExeption, ServiceException {
		
		Mockito.when(mutantDetector.isMutant(Mockito.any())).thenReturn(true);
		Adn adn = new Adn();				
		Mockito.when(dnaRepo.findByDna(Mockito.any())).thenReturn(adn);
		boolean mutant = mutantsServiceImpl.isMutant(MutantFixtureData.dnaHuman);				
		assertEquals(mutant, true);
		
	}
	
	
	@SuppressWarnings("deprecation")
	@Test
	public void whenHasGetSstatsThenResultTheCorrectStatsPerson() throws InvalidDnaExeption, ServiceException {		
		 List<StatsResult> resultlistLocal = new ArrayList<StatsResult>(){};		 
		 StatsResult statusHumman= new StatsResult();
		 statusHumman.set_id(true);
		 statusHumman.setTotal("1");		 
		 StatsResult statusMutant= new StatsResult();
		 statusMutant.set_id(false);
		 statusMutant.setTotal("1");
		 resultlistLocal.add(statusHumman);
		 resultlistLocal.add(statusMutant);		 
		 Mockito.when(dnaRepo.getStats()).thenReturn(resultlistLocal);
		 PersonStats personStats = mutantsServiceImpl.getStats();		
		 assertEquals(personStats.getCount_human_dna(), Double.parseDouble(statusHumman.getTotal()),0);
		
	}
}

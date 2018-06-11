package com.mercadolibre.service;

import com.mercadolibre.exception.ServiceException;
import com.mercadolibre.exception.InvalidDnaExeption;
import com.mercadolibre.model.PersonStats;
/**
 * @author Guille Campos
 */
public interface MutantsService {
		
	boolean isMutant(String[] dna) throws InvalidDnaExeption, ServiceException;

	 PersonStats getStats() throws ServiceException;
	
	
}

package com.mercadolibre.repository;

import java.util.List;

import com.mercadolibre.exception.ServiceException;
import com.mercadolibre.model.Adn;
import com.mercadolibre.model.StatsResult;
/**
 * @author Guille Campos
 */
public interface DNABaseRepository {
	
	List<StatsResult> getStats() throws ServiceException;        
    Adn findByDna(String[] dna)  throws ServiceException;
	void save(Adn adn);
    
    
}

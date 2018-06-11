package com.mercadolibre.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercadolibre.exception.InvalidDnaExeption;
import com.mercadolibre.exception.ServiceException;
import com.mercadolibre.model.Adn;
import com.mercadolibre.model.PersonStats;
import com.mercadolibre.model.StatsResult;
import com.mercadolibre.repository.DNABaseRepository;
import com.mercadolibre.service.MutantDetector;
import com.mercadolibre.service.MutantsService;

/**
 * @author Guille Campos
 */
@Service
public class MutantsServiceImpl implements MutantsService {
		
	 private DNABaseRepository dnaRepo;
	 private MutantDetector mutantDetector;

	    @Autowired
	    public MutantsServiceImpl(final DNABaseRepository dnaRepository,final MutantDetector mutantDetector){
	        this.dnaRepo = dnaRepository;
	        this.mutantDetector=mutantDetector;
	    }

		@Override
		public boolean isMutant(String[] dna) throws InvalidDnaExeption, ServiceException {			
			return saveAction(dna, mutantDetector.isMutant(dna));
		}

		private boolean saveAction(String[] dna, boolean isMutant) throws ServiceException 
		{								
			
			Adn adnbd = dnaRepo.findByDna(dna);				
			if(adnbd==null){
				Adn dnaTosave = new Adn();
				dnaTosave.setDna(dna);
				dnaTosave.setMutant(isMutant);
			    dnaRepo.save(dnaTosave);
			}
		    return isMutant;	
		}
		
		@Override
		public PersonStats getStats() throws ServiceException {
		 List<StatsResult> resultlist = dnaRepo.getStats();			 
		 double  humman= 0;
		 double mutants = 0;
		 for (StatsResult countResult : resultlist) {			
			 if(countResult.is_id()){
				 mutants=Double.parseDouble(countResult.getTotal());
			 }else
			 {
				 humman=Double.parseDouble(countResult.getTotal());
			 }				
		  }			 							    
		  return new PersonStats(humman, mutants);
		}
		

}

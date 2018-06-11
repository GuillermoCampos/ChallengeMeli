package com.mercadolibre.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolibre.exception.ApiException;
import com.mercadolibre.exception.InvalidDnaExeption;
import com.mercadolibre.exception.ServiceException;
import com.mercadolibre.model.DNASequence;
import com.mercadolibre.model.PersonStats;
import com.mercadolibre.service.MutantsService;


/**
 * @author Guille Campos
 */
@RestController
public class MutantController {
	

	private final MutantsService mutantsService;
	private static Logger logger =  Logger.getGlobal();
	
	@Autowired
	public MutantController(final MutantsService mutantsService) {
		this.mutantsService = mutantsService;
	}
		
	@RequestMapping(value="/mutant", method=RequestMethod.POST)
	public  ResponseEntity<String> analizeMutantCandidate(@RequestBody DNASequence dna)  {
		
	   try{									
		   if(!mutantsService.isMutant(dna.getDna())) {
		 	 return new ResponseEntity<>("Is a simple human DNA",HttpStatus.FORBIDDEN);			
		   }else{ 				
			 return new ResponseEntity<>("Is a mutant DNA!",HttpStatus.OK);
		   }	 
		} catch (ServiceException ex) {
			logger.log(Level.ALL, ex.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);	
		} catch (InvalidDnaExeption ex) {
			logger.log(Level.ALL, ex.getMessage());
			return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
		}
	
	}
	
	@RequestMapping(value="/stats", method=RequestMethod.GET, produces="application/json; charset=UTF-8")    	
	   PersonStats stats() throws ApiException {
		PersonStats stats = new PersonStats();
		try{
			stats = mutantsService.getStats();
		}catch (ServiceException e) {
			logger.log(Level.ALL, e.getMessage());
			throw new ApiException();
	    }	
		return stats;
    }

}




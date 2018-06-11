package com.mercadolibre.service;

import com.mercadolibre.exception.InvalidDnaExeption;
/**
 * @author Guille Campos
 */
public interface MutantDetector {
	boolean isMutant(String[] dna) throws InvalidDnaExeption;
}

package com.mercadolibre.controller;

import com.mercadolibre.model.DNASequence;
/**
 * @author Guille Campos
 */
public abstract class MutantFixtureData {

	public static String[] dnaMutant =  new String[] {  "ATGCGA", 
			  "CAGTGC", 
			  "TTATGG", 
			  "AGAAGG", 
			  "CCCGTA", 
			  "TCGCTG"};

	public static String[] dnaHuman =  new String[] {   "ATGCCA", 
			  "CAGTGC", 
			  "TTCTGG", 
			  "AGAAGG", 
			  "CCCGTA", 
			  "TCGCTG"};

	public static String[] invalidDNA =  new String[] { "ATGCCA", 
			  "CAGTGC", 
			  "TTCTGG", 
			  "AGAAGG", 
			  "TCGCTG"};

	public static DNASequence getValidMutantDna() {
		DNASequence result = new DNASequence();
		result.setDna(dnaMutant);
		return result;
	}
	public static DNASequence getHummanDna() {
		DNASequence result = new DNASequence();
		result.setDna(dnaHuman);
		return result;
	}
	
	public static DNASequence getInvalidDna() {
		DNASequence result = new DNASequence();
		result.setDna(invalidDNA);
		return result;
	}

}

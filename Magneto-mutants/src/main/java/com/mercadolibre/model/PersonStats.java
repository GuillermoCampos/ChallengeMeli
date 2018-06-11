package com.mercadolibre.model;
/**
 * @author Guille Campos
 */
public class PersonStats {
	private double count_mutant_dna;
	private double count_human_dna;
	private double ratio;
	

	public PersonStats(){}
	
	public PersonStats(double count_human_dna, double count_mutant_dna) {
		super();
		this.count_mutant_dna = count_mutant_dna;
		this.count_human_dna = count_human_dna;
		this.ratio = ((count_human_dna != 0) ? (this.count_mutant_dna / this.count_human_dna) : 0);
	}
	

	public double getCount_mutant_dna() {
		return count_mutant_dna;
	}

	public void setCount_mutant_dna(double count_mutant_dna) {
		this.count_mutant_dna = count_mutant_dna;
	}


	public double getCount_human_dna() {
		return count_human_dna;
	}

	public void setCount_human_dna(double count_human_dna) {
		this.count_human_dna = count_human_dna;
	}


	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

}

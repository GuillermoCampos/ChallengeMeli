package com.mercadolibre.service.Impl;

import static java.util.Arrays.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.mercadolibre.exception.InvalidDnaExeption;
import com.mercadolibre.service.MutantDetector;

/**
 * @author Guille Campos
 */
@Component
public class MutantDetectorImpl implements MutantDetector {
	
	private static final String G_PATTERN = "GGGG";
	private static final String C_PATTERN = "CCCC";
	private static final String T_PATTERN = "TTTT";
	private static final String A_PATTERN = "AAAA";
	private static final int MUTANT_PATTERN_SIZE = 4;
	private static final int MIN_MUTANT_PATTERN_QUANTITY = 2;

	@Override
	public boolean isMutant(String[] dna) throws InvalidDnaExeption {
		validateCorrectDna(dna);
		return isMutantDetector(dna);
	}
	
	private boolean isMutantDetector(String[] dna) {
		int mutantFlag = 0;
		String[][] dnaMatix = createMatrix(dna);
		mutantFlag += searchPattern(getRows(dnaMatix));
		if (mutantFlag < MIN_MUTANT_PATTERN_QUANTITY) {
			mutantFlag += searchPattern(getColumns(dnaMatix));
		}
		if (mutantFlag < MIN_MUTANT_PATTERN_QUANTITY) {
			mutantFlag += searchPattern(getDiagonals(MUTANT_PATTERN_SIZE,dnaMatix));
		}
		return (mutantFlag>=MIN_MUTANT_PATTERN_QUANTITY)?true:false;
	}

	private String[][] createMatrix(String[] dna) {
		String[][] dnaMatix = new String[dna.length][dna.length];					
		int row = 0;
		for (String dnaSecuence : dna) {
			for (int i = 0; i < dnaSecuence.length(); i++) {
				dnaMatix[row][i] = String.valueOf(dnaSecuence.charAt(i));
			}
			row++;
		}
		return dnaMatix;
	}
	
	public List<String> getDiagonals(final int minSize,String[][] dnaMatix) {
		List<String> diagonals = new ArrayList<>();
		int matrixSize = dnaMatix.length;
		// LEFT TO RIGHT - Column moving (/)
		for (int s = 0; s < matrixSize; s++) {
			if (s >= ((matrixSize - minSize) + 1)) {
				StringBuilder sb = new StringBuilder();
				for (int i = s; i > -1; i--) {
					sb.append(dnaMatix[i][s - i]);
				}
				diagonals.add(sb.toString());
			}
		}
		// LEFT TO RIGHT - Row moving (/)
		for (int s = 1; s < matrixSize; s++) {
			if (s < ((matrixSize - minSize) + 1)) {
				StringBuilder sb = new StringBuilder();
				for (int i = matrixSize - 1; i >= s; i--) {
					sb.append(dnaMatix[i][(s + matrixSize) - 1 - i]);
				}
				diagonals.add(sb.toString());
			}
		}
		// RIGHT TO LEFT - Column moving (\)
		for (int i = 0; i < matrixSize; i++) {
			if ((matrixSize - i) < minSize) {
				StringBuilder sb = new StringBuilder();
				for (int s = matrixSize - 1; s >= (matrixSize - 1 - i); s--) {
					sb.append(dnaMatix[i - (matrixSize - 1 - s)][s]);
				}
				diagonals.add(sb.toString());
			}
		}
		// RIGHT TO LEFT - Row moving (\)
		for (int s = 0; s < (matrixSize - 1); s++) {
			if (s > (matrixSize - minSize)) {
				StringBuilder sb = new StringBuilder();
				for (int i = matrixSize - 1; i >= (matrixSize - s - 1); i--) {
					sb.append(dnaMatix[i][s - (matrixSize - 1 - i)]);
				}
				diagonals.add(sb.toString());
			}
		}
		return diagonals;
	}
	
	public List<String> getRows(String[][] dnaMatix) {
		List<String> rows = new ArrayList<>();
		for (int i = 0; i < dnaMatix.length; i++) {
			rows.add(stream(dnaMatix[i]).map(String::valueOf).collect(Collectors.joining()));
		}
		return rows;
	}
	
	public List<String> getColumns(String[][] dnaMatix) {
		List<String> columns = new ArrayList<>();
		for (int i = 0; i < dnaMatix.length; i++) {
			final int col = i;
			columns.add(stream(dnaMatix).map(o -> o[col]).collect(Collectors.joining()));
		}
		return columns;
	}
	
	
	private int searchPattern(final List<String> nitrogenBases) {
	  int mutantPatternOccurencies = 0;
		for (String base : nitrogenBases) {					
		 if(mutantPatternOccurencies < MIN_MUTANT_PATTERN_QUANTITY){
			if (isPattern(base)) {
				mutantPatternOccurencies++;
			}				
		 }
		}												
	  return mutantPatternOccurencies;
	}
	
	private boolean isPattern(final String nitrogenBasesAsString) {
		return (nitrogenBasesAsString.contains(A_PATTERN) || nitrogenBasesAsString.contains(T_PATTERN)
				|| nitrogenBasesAsString.contains(C_PATTERN) || nitrogenBasesAsString.contains(G_PATTERN));
	} 
	
	private void validateCorrectDna(String[] dna) throws InvalidDnaExeption {
		int nRow = dna.length;
		if (nRow >= MUTANT_PATTERN_SIZE) {
			for (int i = 0; i < nRow; i++) {
				validateCharacterDna(dna[i]);					
				dna[i] = dna[i].toUpperCase();
				validateStructureDna(dna[i], nRow);
			}
		} else {
			throw new InvalidDnaExeption("Ilegal Structure in DNA. N:"+nRow);
		}
	}
	
	private void validateCharacterDna(String dnaX) throws InvalidDnaExeption {
		Pattern p = Pattern.compile("[ATGC]+", Pattern.CASE_INSENSITIVE);		
		if (!p.matcher(dnaX).matches()) {
			throw new InvalidDnaExeption("Invalid characters in DNA");
		}
	}
	
	private void validateStructureDna(String dnaX, int l) throws InvalidDnaExeption {
		if (dnaX.length() != l) {			
			throw new InvalidDnaExeption("Ilegal Structure in DNA");
		}
	}
}

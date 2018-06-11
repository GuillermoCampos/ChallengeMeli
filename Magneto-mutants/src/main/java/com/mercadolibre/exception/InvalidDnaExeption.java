package com.mercadolibre.exception;

/**
 * @author Guille Campos
 */
public class InvalidDnaExeption extends Exception {

	private static final long serialVersionUID = 1L;
	private String message;
	
	public  InvalidDnaExeption(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
 }
}

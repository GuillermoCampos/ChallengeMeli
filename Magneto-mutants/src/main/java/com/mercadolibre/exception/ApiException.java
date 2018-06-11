package com.mercadolibre.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * @author Guille Campos
 */
@ResponseStatus(value=HttpStatus.FORBIDDEN, reason="error occurs please contact the API administrator.")
public class ApiException extends Exception{

	private static final long serialVersionUID = 5810651289023858018L;

}

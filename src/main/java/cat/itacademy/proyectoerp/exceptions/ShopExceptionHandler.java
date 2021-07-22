package cat.itacademy.proyectoerp.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * Exception handler for Shops. It avoids printing the entire stack trace
 * when an error occurs.
 */

@RestControllerAdvice
public class ShopExceptionHandler {
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ApiShopError> handleExcpetion(MethodArgumentTypeMismatchException e) {
		return new ResponseEntity<ApiShopError>(
				new ApiShopError(LocalDateTime.now(), HttpStatus.BAD_REQUEST, e.getMessage()),
				HttpStatus.BAD_REQUEST);
	}
	
}

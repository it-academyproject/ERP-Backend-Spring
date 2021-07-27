package cat.itacademy.proyectoerp.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * Global exception handler. It avoids printing the entire stack trace when
 * an error occurs.
 */

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<CustomErrorResponse> handleExcpetion(MethodArgumentTypeMismatchException e) {
		return new ResponseEntity<CustomErrorResponse>(
			new CustomErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Resource not found due to method argument type mismatch"),
			HttpStatus.BAD_REQUEST);
	}
	
}

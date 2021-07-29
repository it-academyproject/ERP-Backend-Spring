package cat.itacademy.proyectoerp.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * Global exception handler. It catches exceptions thrown in the controller
 * layer. This avoids printing the stack trace and possibly giving away too
 * much information.
 */

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public CustomErrorResponse handleException(MethodArgumentTypeMismatchException e) {
		return new CustomErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Method argument type mismatch");
	}
	
	@ExceptionHandler(MissingPathVariableException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public CustomErrorResponse handleException(MissingPathVariableException e) {
		return new CustomErrorResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR, "Missing path variable");
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.OK)
	public CustomErrorResponse handleException(Exception e) {
		return new CustomErrorResponse(LocalDateTime.now(), HttpStatus.OK, "Unknown exception");
	}
	
	// Custom exceptions:
	
	@ExceptionHandler(ArgumentNotFoundException.class)
	@ResponseStatus(HttpStatus.OK)
	public CustomErrorResponse handleException(ArgumentNotFoundException e) {
		return new CustomErrorResponse(LocalDateTime.now(), HttpStatus.OK, e.getMessage());
	}
	
	@ExceptionHandler(ArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public CustomErrorResponse hanldeException(ArgumentNotValidException e) {
		return new CustomErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST, e.getMessage());
	}
	
}

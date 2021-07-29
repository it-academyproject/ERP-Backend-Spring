package cat.itacademy.proyectoerp.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityExistsException;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * Global exception handler. It catches exceptions thrown in the controller
 * layer. This avoids printing the stack trace and possibly giving away too
 * much information.
 * 
 * @author Rubén Rodríguez
 * @author Cristina Grau
 */

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
	public CustomErrorResponse handleException(MethodArgumentNotValidException e) {
		Map<String, String> detail = new HashMap<>();
		
		for (FieldError err : e.getFieldErrors())
			detail.put(err.getField(), err.getDefaultMessage());
		
		return new CustomErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Method Argument Not Valid", detail);
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
	public CustomErrorResponse handleException(MethodArgumentTypeMismatchException e) {
		return new CustomErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Method Argument Type Mismatch");
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
	public CustomErrorResponse handleException(HttpMessageNotReadableException e) {
		return new CustomErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Http Message Not Readable");
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED) // 401
	public CustomErrorResponse handleException(AccessDeniedException e) {
		return new CustomErrorResponse(LocalDateTime.now(), HttpStatus.UNAUTHORIZED, "Access Denied");
	}
	
	@ExceptionHandler(EntityExistsException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
	public CustomErrorResponse handleException(EntityExistsException e) {
		return new CustomErrorResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR, "Entity Exists Exception");
	}
	
	@ExceptionHandler(MissingPathVariableException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
	public CustomErrorResponse handleException(MissingPathVariableException e) {
		return new CustomErrorResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR, "Missing Path Variable");
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.OK)
	public CustomErrorResponse handleException(Exception e) {
		return new CustomErrorResponse(LocalDateTime.now(), HttpStatus.OK, null, "Unknown exception");
	}
	
	// Custom exceptions:
	
	@ExceptionHandler(ArgumentNotFoundException.class)
	@ResponseStatus(HttpStatus.OK) // 200
	public CustomErrorResponse handleException(ArgumentNotFoundException e) {
		return new CustomErrorResponse(LocalDateTime.now(), HttpStatus.OK, "Argument Not Found", e.getMessage());
	}
	
	@ExceptionHandler(ArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
	public CustomErrorResponse hanldeException(ArgumentNotValidException e) {
		return new CustomErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Argument Not Valid", e.getMessage());
	}
	
}

package cat.itacademy.proyectoerp.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class for handler exception in API.
 * For the moment it will handle the exception "MethodArgumentNotValidException".
 * 
 * @author Rubén Rodríguez
 *
 */
@RestControllerAdvice
public class UserGlobalExceptionHandler   {
	
	/**
	 * Save in a map a list of details of errors with validation constraints.
	 * We need clear map. Otherwise it will keep old values.
	 */
	@JsonProperty
	private Map<String,String> fieldErrors = new HashMap<String, String>();
	
	/**
	 * 
	 * @param error. Collection of validation constraints errors.
	 * @param request
	 * @return ApiUserError object with list of errors in JSON.
	 */
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity <ApiUserError>handleMethodArgumentNotValidException(MethodArgumentNotValidException error, WebRequest request)  {
	    
		        fieldErrors.clear();
		        
		        for(FieldError e : error.getFieldErrors()){
					fieldErrors.put(e.getField(), e.getDefaultMessage());
				}
		        
		        //We construct ApiUserError object.
		        ApiUserError err = new ApiUserError(
		            LocalDateTime.now(),
		            HttpStatus.BAD_REQUEST, 
		            "Request invalid. " ,
		            fieldErrors);
		        
		        return new ResponseEntity<ApiUserError>(err, HttpStatus.BAD_REQUEST);				   
	    }
	
	@ExceptionHandler(value = HttpMessageNotReadableException.class)
	public ResponseEntity <ApiUserError>HttpMessageNotReadableException(HttpMessageNotReadableException error, WebRequest request)  {
		fieldErrors.clear();
        
      
        //We construct ApiUserError object.
        ApiUserError err = new ApiUserError(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST, 
            "Request invalid. ");
        
        return new ResponseEntity<ApiUserError>(err, HttpStatus.BAD_REQUEST);				   
	}
	
	@ExceptionHandler(value = AccessDeniedException.class)
	public ResponseEntity <ApiUserError>AccessDeniedException(AccessDeniedException error, WebRequest request)  {
		fieldErrors.clear();
        
      
        //We construct ApiUserError object.
        ApiUserError err = new ApiUserError(
            LocalDateTime.now(),
            HttpStatus.UNAUTHORIZED, 
            "Access Denied");
        
        return new ResponseEntity<ApiUserError>(err, HttpStatus.UNAUTHORIZED);				   
	} 
		
	
}
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


@RestControllerAdvice
public class ProductExceptionHandler {
	

		
		/**
		 * 
		 * @param error. Collection of validation constraints errors.
		 * @param request
		 * @return ApiError object with list of errors in JSON.
		 */
		@ExceptionHandler(value = ArgumentNotFoundException.class)
		public ResponseEntity <ApiProductError>handleMethodArgumentNotValidException(ArgumentNotFoundException error, WebRequest request)  {
		    
			        
			        
			        //We construct ApiError object.
			        ApiProductError err = new ApiProductError(
			            LocalDateTime.now(),
			            HttpStatus.NOT_FOUND, 			            
			            error.getMessage());
			        
			        return new ResponseEntity<ApiProductError>(err, HttpStatus.NOT_FOUND);				   
		    }
		
		@ExceptionHandler(value = ArgumentNotValidException.class)
		public ResponseEntity <ApiProductError>HttpMessageNotReadableException(ArgumentNotValidException error, WebRequest request)  {
			
	        
	      
		        //We construct ApiError object.
			   ApiProductError err = new ApiProductError(
		            LocalDateTime.now(),
		            HttpStatus.BAD_REQUEST, 
		            error.getMessage());
	        
	        return new ResponseEntity<ApiProductError>(err, HttpStatus.BAD_REQUEST);				   
		}
		
	
		
	
	
	
	
	
	
}

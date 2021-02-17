package cat.itacademy.proyectoerp.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ProductExceptionHandler {

	/**
	 * 
	 * @param error.  Collection of validation constraints errors.
	 * @param request
	 * @return ApiError object with list of errors in JSON.
	 */
	@ExceptionHandler(value = ArgumentNotFoundException.class)
	public ResponseEntity<ApiProductError> handleMethodArgumentNotValidException(ArgumentNotFoundException error,
			WebRequest request) {

		// We construct ApiError object.
		ApiProductError err = new ApiProductError(LocalDateTime.now(), HttpStatus.NOT_FOUND, error.getMessage());

		return new ResponseEntity<ApiProductError>(err, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = ArgumentNotValidException.class)
	public ResponseEntity<ApiProductError> HttpMessageNotReadableException(ArgumentNotValidException error,
			WebRequest request) {

		// We construct ApiError object.
		ApiProductError err = new ApiProductError(LocalDateTime.now(), HttpStatus.BAD_REQUEST, error.getMessage());

		return new ResponseEntity<ApiProductError>(err, HttpStatus.BAD_REQUEST);
	}

}

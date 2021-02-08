package cat.itacademy.proyectoerp.exceptions;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Class for construct Error Object of API. This error is send to Front.
 * @author ITacademy
 *
 */

public class ApiUserError {
	/**
	 * LocalDateTime. Datetime of error.
	 * Http status.
	 * global message of error.
	 * Map errors: list of errors with details.
	 */
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private HttpStatus status;
    private String message;
    private Map errors;
    
	public ApiUserError(LocalDateTime timestamp, HttpStatus status, String message, Map errors) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.message = message;
		this.errors = errors;
	}
	
    
    public ApiUserError(LocalDateTime timestamp, HttpStatus status, String message) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.message = message;
	}
    //SETTERS AND GETTERS
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Map getErrors() {
		return errors;
	}
	public void setErrors(Map errors) {
		this.errors = errors;
	}

	
}
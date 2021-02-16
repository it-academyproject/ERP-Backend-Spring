package cat.itacademy.proyectoerp.exceptions;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

public class ApiProductError {

	/**
	 * LocalDateTime. Datetime of error. Http status. global message of error.
	 * Error: Custom product exception.
	 */

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private HttpStatus status;
	private String exceptionMessage;

	public ApiProductError(LocalDateTime timestamp, HttpStatus status, String exceptionMessage) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.exceptionMessage = exceptionMessage;
	}

	// SETTERS AND GETTERS
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

	public String getErrors() {
		return exceptionMessage;
	}

	public void setErrors(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

}

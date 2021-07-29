package cat.itacademy.proyectoerp.exceptions;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Custom error response.
 */

public class CustomErrorResponse {
	
	private boolean success;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime timestamp;
	private HttpStatus status;
	private String error;
	private String message;
	private Map<String, String> detail;

	public CustomErrorResponse(LocalDateTime timestamp, HttpStatus status, String error) {
		success = false;
		
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
	}
	
	public CustomErrorResponse(LocalDateTime timestamp, HttpStatus status, String error, String message) {
		success = false;
		
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.message = message;
	}
	

	public CustomErrorResponse(LocalDateTime timestamp, HttpStatus status, String error, Map<String, String> detail) {
		success = false;
		
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.detail = detail;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	
	public void setError(String error) {
		this.error = error;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setDetail(Map<String, String> detail) {
		this.detail = detail;
	}
	
	public boolean getSuccess() {
		return success;
	}
	
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	
	public HttpStatus getStatus() {
		return status;
	}
	
	public String getError() {
		return error;
	}
	
	public String getMessage() {
		return message;
	}
	
	public Map<String, String> getDetail() {
		return detail;
	}

}

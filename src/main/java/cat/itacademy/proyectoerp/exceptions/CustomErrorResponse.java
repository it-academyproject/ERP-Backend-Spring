package cat.itacademy.proyectoerp.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Custom error response for Shops.
 */

public class CustomErrorResponse {
	
	private boolean success;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private HttpStatus status;
	private String message;
	
	public CustomErrorResponse(LocalDateTime timestamp, HttpStatus status, String message) {
		success = false;
		
		this.timestamp = timestamp;
		this.status = status;
		this.message = message;
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
	
	public void setMessage(String message) {
		this.message = message;
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
	
	public String getMessage() {
		return message;
	}

}

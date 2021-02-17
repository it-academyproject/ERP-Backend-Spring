package cat.itacademy.proyectoerp.dto;

/**
 * DTO class for send messages to client without data.
 * @author 
 *
 */
public class MessageDTO {
	
	private String success;
	private String message;
	
	
	
	public MessageDTO(String success, String message) {
		super();
		this.success = success;
		this.message = message;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}

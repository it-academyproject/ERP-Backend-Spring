package cat.itacademy.proyectoerp.dto;

/**
 * DTO class for send messages to client without data.
 * @author 
 *
 */
public class MessageDTO {
	
	private String success;
	private String message;
	private Object object;


	public MessageDTO(String success, String message) {
		super();
		this.success = success;
		this.message = message;
	}

	// constructor with an object as 3rd param
	public MessageDTO(String success, String message, Object object) {
		super();
		this.success = success;
		this.message = message;
		this.object = object;
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

	public Object getObject() {
		return object;
	}
	
	public void setObject(Object object) {
		this.object = object;
	}
}

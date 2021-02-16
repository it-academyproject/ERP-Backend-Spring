package cat.itacademy.proyectoerp.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;


import cat.itacademy.proyectoerp.domain.UserType;

/**
 * DTO Class for Users
 * @author Rubén Rodríguez
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Class DTO have a two parameters for information with front: Success and message.
	 * Success = True or False.  If transaction is OK, success = True.
	 * Message = Information message.  
	 */
	private String success;
	private String message;
    
	@NotBlank(message = "Username is mandatory")
    @Size(min = 6, max = 50, message ="username not valid. min 6 characters and max 50")
	private String username;
	private Long id;
	
	UserType user_type;

	
	//SETERRS AND GETTERS


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

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public UserType getUserType() {
		return user_type;
	}
	public void setUserType(UserType user_type) {
		this.user_type = user_type;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	

}

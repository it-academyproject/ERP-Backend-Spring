package cat.itacademy.proyectoerp.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
    @Size(min = 3, max = 12, message ="username not valid. min 3 characters and max 12")
	private String username;
	
	@Size(min = 8, max = 16, message= "Password not valid. min 8 characters and max 16 ")
	private String password;
	
	//@NotNull
	UserType user_type=UserType.CLIENT;

	
	//SETERRS AND GETTERS
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public UserType getUser_type() {
		return user_type;
	}
	public void setUser_type(UserType user_type) {
		this.user_type = user_type;
	}

}

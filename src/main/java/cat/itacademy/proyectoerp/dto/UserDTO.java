package cat.itacademy.proyectoerp.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;


import cat.itacademy.proyectoerp.domain.UserType;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * DTO Class for Users
 * @author 
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserDTO implements Serializable{

	@Override
	public String toString() {
		return "UserDTO [success=" + success + ", message=" + message + ", username=" + username + ", id=" + id +  ", registration: " + registrationDate + ", type: " + user_type + " ]";
	}
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
	
	@JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
	private LocalDateTime registrationDate;

	
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
	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}
	
}

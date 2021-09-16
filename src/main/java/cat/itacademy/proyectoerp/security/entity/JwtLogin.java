package cat.itacademy.proyectoerp.security.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class JwtLogin implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7133599358114658780L;

	@NotBlank(message = "Username is mandatory")
	@Email(message = "Email incorrect")
	@Size(min = 6, max = 50)
	private String username;
	@NotBlank(message = "Password is mandatory")
	private String password;


	
	// default constructor 
	public JwtLogin()
	{
		
	}

	public JwtLogin(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
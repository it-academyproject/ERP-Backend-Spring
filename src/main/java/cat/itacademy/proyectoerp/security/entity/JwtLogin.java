package cat.itacademy.proyectoerp.security.entity;

import java.io.Serializable;

public class JwtLogin implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7133599358114658780L;
	
	private String username;
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
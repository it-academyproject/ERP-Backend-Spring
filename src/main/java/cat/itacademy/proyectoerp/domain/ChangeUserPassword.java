package cat.itacademy.proyectoerp.domain;

import javax.validation.constraints.Pattern;

public class ChangeUserPassword {
	
	private User user;

	private String new_password;

	public User getUser() {
		return user;
	}
	
	public String getNew_password() {
		return new_password;
	}

	

}

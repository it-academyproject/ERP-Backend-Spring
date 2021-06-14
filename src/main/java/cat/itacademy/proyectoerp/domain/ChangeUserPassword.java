package cat.itacademy.proyectoerp.domain;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ChangeUserPassword {

	private User user;

    @Pattern(regexp = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#_$%?&+=]).*$", message = "New password invalid. Minim 8 characters with 1 upercase, 1 lowercase, 1 special symbol, 1 number)")
	@Size(min = 8, max = 16)
    private String new_password;

	public ChangeUserPassword(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}
	
	
	public String getNew_password() {
		return new_password;
	}

	

}

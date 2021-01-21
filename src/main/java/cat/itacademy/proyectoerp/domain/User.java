package cat.itacademy.proyectoerp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;


/**
 * User class. 
 * <br>
 * Attributes: <br>
 * - Id : Auto <br>
 * - username : max length = 12 <br>
 * - password: max length = 16<br>
 * - user_type: Enum type for role type.<br> 
 * 
 * @author Rubén Rodríguez
 *
 */
@Entity
//@Inheritance(strategy = InheritanceType.JOINED)
public class User   {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=12)
	@Size(min = 3, max = 12)
	private String username;
	
	@Column(length=16)
	@Size(min = 8, max = 16)
	private String password;

	@Column(length=16)
    @Enumerated(EnumType.STRING)
	UserType user_type;
	
	
	/**
	 * Constructor. Create a User object with parameters.	
	 * @param username
	 * @param password
	 * @param user_type
	 */
	public User(String username, String password, UserType user_type) {
		this.username = username;
		this.password = password;
		this.user_type = user_type;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public UserType getUser_type() {
		return user_type;
	}


	public void setUser_type(UserType user_type) {
		this.user_type = user_type;
	}


	public Long getId() {
		return id;
	}

	


}

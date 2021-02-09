package cat.itacademy.proyectoerp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * User class. 
 * <br>
 * Attributes: <br>
 * - Id : Auto <br>
 * - username : max length = 12 <br>
 * - password: max length = 16<br>
 * - userType: Enum type for role type.<br> 
 * 
 * @author IAcademy
 *
 */
//@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public class User   {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Email(message="email incorrect")
    @Column(length=50)
	@Size(min = 6, max = 50)
	private String username;
																			
    @Pattern(regexp="^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%?&+=]).*$",message="Password invalid. Minim 8 characters with 1 upercase, 1 lowercase, 1 special symbol, 1 number)")  
	@Column(length=106)
	private String password;

    @JsonProperty("user_type")
	@Column(length=16)
    @Enumerated(EnumType.STRING)
	UserType userType; 
	
	
	
	public User() {
		super();
	}

	/**
	 * Constructor. Create a User object with parameters. By default, userType is CLIENT. 	
	 * @param username
	 * @param password
	 */
	public User(@Size(min = 6, max = 50) String username, @Size(min = 8, max = 16) String password) {
		super();
		this.username = username;
		this.password = password;
		userType = UserType.CLIENT;
	}


	/**
	 * Constructor. Create a User object with parameters.	
	 * @param username
	 * @param password
	 * @param userType
	 */
	public User(String username, String password, UserType user_type) {
		this.username = username;
		this.password = password;
		this.userType = user_type;
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


	public UserType getUserType() {
		return userType;
	}


	public void setUserType(UserType user_type) {
		this.userType = user_type;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", userType=" + userType + "]";
	}


	public Long getId() {
		return id;
	}

	


}

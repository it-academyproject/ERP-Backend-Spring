	package cat.itacademy.proyectoerp.domain;


import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;


/**
 * User class.
 * <br>
 * Attributes: <br>
 * - Id : Auto <br>
 * - username : max length = 12 <br>
 * - password: max length = 16<br>
 * - lastSession: LocalDateTime<br>
 * - userType: Enum type for role type.<br>
 *
 * @author IAcademy
 */


@Entity
@Table(name = "users")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Email(message = "email incorrect")
  @Column(length = 50, unique = true)
  @Size(min = 6, max = 50)
  @NotBlank(message = "Username is mandatory")
  private String username;

  @Pattern(regexp = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[_!@#$%?&+=]).*$", message = "Password invalid. Minim 8 characters with 1 upercase, 1 lowercase, 1 special symbol, 1 number)")
  @Column(length = 106)
  @NotBlank(message = "Password is mandatory")
  private String password;
  
  @Column
  private LocalDateTime lastSession;
  
  @Column
  private LocalDateTime registrationDate;

  @JsonProperty("user_type")
  @Column(length = 16)
  @Enumerated(EnumType.STRING)
  UserType userType;
  
  @Column(nullable=false, columnDefinition = "boolean default true")
  private Boolean active = true;

  @Column(nullable = false, columnDefinition = "boolean default false")
	private Boolean accountLocked;
  
  @Column(columnDefinition = "integer default 0")
	private Integer failedLoginAttempts;
  
  @Column
	private LocalDateTime lockTime;

  @OneToOne(mappedBy = "user")
  private Client client;

  @OneToOne(mappedBy = "user")
  private Employee employee;


  public User() {
    super();
  }

  /**
   * Constructor. Create a User object with parameters. By default, userType is CLIENT.
   *
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
   *
   * @param username
   * @param password
   * @param user_type
   */
  public User(String username, String password, UserType user_type) {
    this.username = username;
    this.password = password;
    this.userType = user_type;
  }
  
  public User (String username) {
	  this.username = username;
  }


  public String getUserName() {
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


  public LocalDateTime getLastSession() {
	return lastSession;
  }

  public void setLastSession(LocalDateTime lastSession) {
	this.lastSession = lastSession;
  }

  public UserType getUserType() {
    return userType;
  }


  public void setUserType(UserType user_type) {
    this.userType = user_type;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }  

  public Boolean getActive() {
	return active;
  }

  public void setActive(Boolean active) {
	this.active = active;
  }

  public LocalDateTime getRegistrationDate() {
	  return registrationDate;
  }

  public void setRegistrationDate(LocalDateTime registrationDate) {
	  this.registrationDate = registrationDate;
  }
	
  public Boolean isAccountLocked() {
		return accountLocked;
	}

  public void setAccountLocked(Boolean accountLocked) {
		this.accountLocked = accountLocked;
	}

  public Integer getFailedLoginAttempts() {
		return failedLoginAttempts;
	}

  public void setFailedLoginAttempts(Integer failedAttempts) {
		this.failedLoginAttempts = failedAttempts;
	}

  public LocalDateTime getLockTime() {
		return lockTime;
	}

  public void setLockTime(LocalDateTime lockTime) {
		this.lockTime = lockTime;
	}
  
  @Override
  public String toString() {
	return "User [id=" + id + ", user_name=" + username + ", password=" + password + ", last_session=" + lastSession
			+ ", user_type=" + userType + ", registration_date= " + registrationDate + " ]";
  }




}

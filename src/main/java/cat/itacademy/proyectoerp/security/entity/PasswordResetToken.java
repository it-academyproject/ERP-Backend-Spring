package cat.itacademy.proyectoerp.security.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import cat.itacademy.proyectoerp.domain.User;

@Entity
public class PasswordResetToken {

	// PasswordResetToken entity attributes

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String token;

	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "user_id")
	private User user;

	private Date expiryDate;

	// Constructors

	/**
	 * Constructor without parameters
	 */
	public PasswordResetToken() {
	}

	/**
	 * Constructor with parameters
	 * 
	 * @param token
	 * @param user
	 */
	public PasswordResetToken(String token, User user) {
		super();
		this.token = token;
		this.user = user;
	}

	// Getters and Setters

	/**
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id to set id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token to set token
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user to set user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return expiry date
	 */
	public Date getExpiryDate() {
		return expiryDate;
	}

	/**
	 * @param expiryDate to set expiry date
	 */
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

}

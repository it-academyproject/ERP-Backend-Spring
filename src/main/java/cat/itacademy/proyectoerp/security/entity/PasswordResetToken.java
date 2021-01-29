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
 
    private static final int EXPIRATION = 60 * 24;
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
 
    private String token;
 
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
 
    private Date expiryDate;

	public PasswordResetToken(String token, User user) {
		super();
		this.token = token;
		this.user = user;
	}
}
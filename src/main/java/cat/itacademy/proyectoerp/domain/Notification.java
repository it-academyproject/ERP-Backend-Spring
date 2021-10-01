package cat.itacademy.proyectoerp.domain;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

/**
 * This class is used to create notification entities. Notifications are used to
 * send a message to a user when an event occurs, represented by a
 * NotificationType element.
 * 
 * @author daniel
 *
 */
@Entity
@Table(name = "notifications")
public class Notification {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Type(type = "uuid-char")
	private UUID id;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "You have to assign notification type")
	private NotificationType type;

	@NotNull(message = "You have to write a notification message")
	private String message;

	@Column(name = "created_At")
	private Date createdAt;
	
	private boolean read;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	public Notification() {
	}

	public Notification(NotificationType type, String message) {
		this.type = type;
		this.message = message;
		this.createdAt = new Date();
		read = false;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public NotificationType getType() {
		return type;
	}

	public void setType(NotificationType type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}

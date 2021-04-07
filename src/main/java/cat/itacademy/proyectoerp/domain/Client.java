package cat.itacademy.proyectoerp.domain;


//import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Anton Lage & Rita Casiello
 *
 */
@Entity
@Table(name = "clients")
public class Client {

	// Client Attributes

	@Id
	@Column(name = "id", columnDefinition = "BINARY(16)")
	private UUID id = UUID.randomUUID();
	private String address;
	private String dni;
	private String image;
	private String nameAndSurname;
	//private List<Order> orders;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;


	public Client() {

	}

	public Client(String address, String dni, String image, String name) {
		this.address = address;
		this.dni = dni; 
		this.image = image;
		this.nameAndSurname = name;
		//this.orders = orders;
	}


	public Client(String address, String dni, String image, String name, User user) {
		//id = UUID.randomUUID();
		this.nameAndSurname = name;
		this.address = address;
		this.dni = dni;
		this.image = image;
		this.user = user;
	}


	public UUID getid() {
		return id;
	}

	public void setid(UUID id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
		 
	public String getNameAndSurname() {
		return nameAndSurname;
	}

	public void setNameAndSurname(String nameAndSurname) {
		this.nameAndSurname = nameAndSurname;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Client [id=" + id + ", address=" + address + ", dni=" + dni + ", image=" + image + ", user=" + user
				+ "]";
	}


}

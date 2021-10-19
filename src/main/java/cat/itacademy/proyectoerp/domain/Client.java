package cat.itacademy.proyectoerp.domain;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sun.istack.Nullable;

/**
 * @author Anton Lage & Rita Casiello
 *
 */

@Entity
@Table(name = "clients")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Client {

	// Client Attributes

	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id")
	@Type(type="uuid-char")
	private UUID id;

	@NotBlank(message = "DNI is mandatory")
	@Column(unique = true)
	private String dni;

	private String image;

	@Column(name="name_surname")
	@NotBlank(message = "Name and surname is mandatory")
	private String nameAndSurname;

	//address_id se corresponde con billing_address_id, de la clase Order.java
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", referencedColumnName = "id", unique = true)
	@NotNull(message = "You have to assign this client to an address")
	@Valid
	private Address address;

	//se corresponde con shipping_address_id, de la clase Order.java
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "shipping_address_id", referencedColumnName = "id")
	@Nullable
	@Valid
	private Address shippingAddress;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "id", unique = true, nullable = false)
	@NotNull(message = "You have to assign this client to an user")
	@Valid
	private User user;

	public Client() {

	}

	public Client(String dni, String image, String nameAndSurname, Address address, User user) {
		this.dni = dni;
		this.image = image;
		this.nameAndSurname = nameAndSurname;
		this.address = address;
		this.user = user;
	}

	public Client(String dni, String image, String nameAndSurname, Address address, Address shippingAddress, User user) {
		this.dni = dni;
		this.image = image;
		this.nameAndSurname = nameAndSurname;
		this.address = address;
		this.shippingAddress = shippingAddress;
		this.user = user;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
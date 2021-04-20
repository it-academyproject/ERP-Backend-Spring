package cat.itacademy.proyectoerp.domain;

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
	private String name_and_surname;
	//private List<Order> orders;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	// Client empty constructor

	public Client() {

	}

	/**
	 * Constructor with parameters
	 * 
	 * @param id             client id
	 * @param address        client address
	 * @param dni            client dni
	 * @param image          client image
	 * @param name_and_surname client name
	 * @param orders         client orders
	 */

	public Client(String address, String dni, String image, String name) {
		this.address = address;
		this.dni = dni; 
		this.image = image;
		this.name_and_surname = name;
		//this.orders = orders;
	}

	/**
	 * Constructor with parameters
	 * 
	 * @param address client address
	 * @param dni     client dni
	 * @param image   client image
	 * @param user    client user
	 */
	public Client(String address, String dni, String image, String name, User user) {
		//id = UUID.randomUUID();
		this.name_and_surname = name;
		this.address = address;
		this.dni = dni;
		this.image = image;
		this.user = user;
	}

	// Getters and setters

	/**
	 * @return client id
	 */

	public UUID getid() {
		return id;
	}

	/**
	 * @param id to set client id
	 */

	public void setid(UUID id) {
		this.id = id;
	}

		

	/**
	 * @return client address
	 */

	public String getAddress() {
		return address;
	}

	

	/**
	 * @param address to set client address
	 */

	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return client dni
	 */

	public String getDni() {
		return dni;
	}

	/**
	 * @param dni to set client dni
	 */

	public void setDni(String dni) {
		this.dni = dni;
	}

	/**
	 * @return client image
	 */

	public String getImage() {
		return image;
	}

	/**
	 * @param image to set client image
	 */

	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * @return client orders
	 */
	
//		 public List<Order> getOrders() {
//	 return orders;
//	 }
/*************************************************************************************/
	/**
	 * @param nameAndSurname  to set client Name
	 */
		 
/*	public String getNameAndSurname() {
		return getName_and_surname();
	}*/

	/**
	 * @param name_and_surname  to set client Name
	 */
		 
	public String getName_and_surname() {
		return name_and_surname;
	}
	
	/**
	 * @return client name
	 */
	
	public void setNameAndSurname(String nameAndSurname) {
		setName_and_surname(nameAndSurname);
	}

	/**
	 * @return client name
	 */

	public void setName_and_surname(String nameAndSurname) {
		this.name_and_surname = nameAndSurname;
	}

	/**
	 * @param orders to set client orders
	 */

//	 public void setOrders(List<Order> orders) {
//	 this.orders = orders;
//	 }

	/**
	 * @return client user
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

	@Override
	public String toString() {
		return "Client [id=" + id + ", address=" + address + ", dni=" + dni + ", image=" + image + ", user=" + user
				+ "]";
	}

	// Console data printing method

}

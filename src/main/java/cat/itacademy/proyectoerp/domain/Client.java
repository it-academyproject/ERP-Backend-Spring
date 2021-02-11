package cat.itacademy.proyectoerp.domain;

import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * @author Anton Lage & Rita Casiello
 *
 */
@Entity
@Table(name="clients")
public class Client extends User {
	
	//Client Attributes
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private UUID id;
	private String address;
	private String dni;
	private String image;
	private List<Product> orders;
	
	//Client empty constructor
	
	public Client() {
		super();

	}
	
	
	/**
	 * Constructor only with super parameters
	 * 
	 * 
	 * @param name              client name
	 * @param stock             client password

	 */

	public Client(@Size(min = 6, max = 50) String username, @Size(min = 8, max = 16) String password) {
		super(username, password);

	}


	/**
	 * Constructor with parameters
	 * 
	 * @param id                client id
	 * @param name              client name
	 * @param password          client password
	 * @param address           client address
	 * @param dni               client dni
	 * @param image             client image
	 * @param orders            client orders
	 */

	public Client(String name, String password, UUID client, String address, String dni, String image, List<Product> orders) {
		super(name, password);
		this.id = client;
		this.address = address;
		this.dni = dni;
		this.image = image;
		this.orders = orders;
	}


	//Getters and setters
	
	/**
	 * @return client id
	 */

	public UUID getClient() {
		return id;
	}


	/**
	 * @param id to set client id
	 */

	public void setClient(UUID client) {
		this.id = client;
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

	public List<Product> getOrders() {
		return orders;
	}


	/**
	 * @param orders to set client orders
	 */

	public void setOrders(List<Product> orders) {
		this.orders = orders;
	}

	// Console data printing method
	
	@Override
	public String toString() {
		return "Client [id=" + id + ", address=" + address + ", dni=" + dni + ", image=" + image + ", orders=" + orders + "]";
	}
	
	
	
	
	

}

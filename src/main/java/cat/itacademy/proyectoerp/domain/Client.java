package cat.itacademy.proyectoerp.domain;

//import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author Anton Lage & Rita Casiello
 *
 */
@Entity
@Table(name="clients")
public class Client {
	
	//Client Attributes
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "client_id")
	private UUID clientId;
	private String address;
	private String dni;
	private String image;
	//private List<Order> orders;
	
	//Client empty constructor
	
	public Client() {

	}
	
	
	/**
	 * Constructor only with super parameters
	 * 
	 * 
	 * @param name              client name
	 * @param stock             client password

	 */


	/**
	 * Constructor with parameters
	 * 
	 * @param clientId                client id
	 * @param address           client address
	 * @param dni               client dni
	 * @param image             client image
	 * @param orders            client orders
	 */

	public Client(String address, String dni, String image/*, List<Order> orders*/) {
		this.address = address;
		this.dni = dni;
		this.image = image;
		//this.orders = orders;
	}
	

	//Getters and setters
	
	/**
	 * @return client id
	 */

	public UUID getClientId() {
		return clientId;
	}


	/**
	 * @param id to set client id
	 */

	public void setClientId(UUID clientId) {
		this.clientId = clientId;
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

	//public List<Product> getOrders() {
		//return orders;
	//}


	/**
	 * @param orders to set client orders
	 */

	//public void setOrders(List<Product> orders) {
		//this.orders = orders;
	//}

	// Console data printing method
	
	@Override
	public String toString() {
		return "Client [ClientId=" + clientId + ", address=" + address + ", dni=" + dni + ", image=" + image+ "]";
	}
	

}

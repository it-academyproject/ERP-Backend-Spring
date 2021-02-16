package cat.itacademy.proyectoerp.domain;

import java.util.ArrayList;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="clients")
public class Client extends User {
	
	//Client Attributes
	/*@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private UUID client;*/
	private String address;
	private String dni;
	private String image;
	private ArrayList<Product> orders;
	
	
	
	public Client() {
		super();
		// TODO Auto-generated constructor stub
	}




	public Client(@Size(min = 6, max = 50) String username, @Size(min = 8, max = 16) String password) {
		super(username, password);
		// TODO Auto-generated constructor stub
	}



	public Client(String name, String password, String address, String dni, String image, ArrayList<Product> orders) {
		super(name, password);
		this.address = address;
		this.dni = dni;
		this.image = image;
		this.orders = orders;
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




	public ArrayList<Product> getOrders() {
		return orders;
	}




	public void setOrders(ArrayList<Product> orders) {
		this.orders = orders;
	}
	
	
	
	
	

}

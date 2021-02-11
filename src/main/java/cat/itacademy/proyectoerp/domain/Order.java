package cat.itacademy.proyectoerp.domain;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
@Entity
@Table(name = "order")
public class Order{
	
	// Order entity attributes

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String employeeId;
	@Column
	private String clientId;
	@Column
	private Date date;
	@Column
	private String status;
	@ManyToMany
	private List<Product> products;


	
	// Constructors
	
	
	//Constructor without parameters.
	
	public Order() {
	}

	/**
	 * Constructor with all the parameters.
	 * 
	 * @param employeeId       employee responsible
	 * @param clientId         client id
	 * @param date			   date of the emitted order
	 * @param status		   order status
	 * @param products		   products included(TODO=
	 */
	
	
	public Order(String employeeId, String clientId, Date date, String status, List<Product> products) {
		this.employeeId = employeeId;
		this.clientId = clientId;
		this.date = date;
		this.status = status;
//		this.products = products;
	}

	//Getters and setters

	/**
	 * @return employee id
	 */
	public String getEmployeeId() {
		return employeeId;
	}
	/**
	 * @return client id
	 */
	public String getClientId() {
		return clientId;
	}
	/**
	 * @return order date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @return order status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @return order products
	 */
//	public List<Product> getProducts() {
//		return products;
//	}
	/**
	 * @param employeeId to set responsible employee id
	 */
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	/**
	 * @param clientId to set client id
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	/**
	 * @param date to set order date
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * @param status to set order status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @param products to set order products
	 */
//	public void setProducts(List<Product> products) {
//		this.products = products;
//	}

	@Override
	public String toString() {
		return "Order id=" + id +" [employeeId=" + employeeId + ", clientId=" + clientId + ", date=" + date + ", status=" + status
				+ "]";
		// ", products=" + products + 
	}
	
	
	
}

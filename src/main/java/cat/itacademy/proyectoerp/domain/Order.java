package cat.itacademy.proyectoerp.domain;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "orders")
public class Order implements Serializable{
	
	// Order entity attributes

	/**
	 * 
	 */
	@Id
	private static final long serialVersionUID = 7415327125952515489L;
	private String employeeId;
	private String clientId;
	private Date date;
	private String status;
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
	 * @param products		   products included
	 */
	
	
	public Order(String employeeId, String clientId, Date date, String status, List<Product> products) {
		this.employeeId = employeeId;
		this.clientId = clientId;
		this.date = date;
		this.status = status;
		this.products = products;
	}

	//Getters and setters
	/**
	 * @return order id
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
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
	public List<Product> getProducts() {
		return products;
	}
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
	public void setProducts(List<Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "Order id=" + serialVersionUID +" [employeeId=" + employeeId + ", clientId=" + clientId + ", date=" + date + ", status=" + status
				+ ", products=" + products + "]";
	}
	
	
	
}

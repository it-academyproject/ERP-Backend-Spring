package cat.itacademy.proyectoerp.domain;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cat.itacademy.proyectoerp.util.StringToListConverter;
@Entity
@Table(name = "orders")
public class Order{
	
	// Order entity attributes

	@Id
	private UUID id;
	@Column
	private String employeeId;
	@Column
	private String clientId;
	@Column
	private Date date;
	@Column
	private String status;
	@Convert(converter = StringToListConverter.class)
	private List<String> productsId;


	
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
	 * @param productsId	   products id included in order
	 */
	
	
	public Order(UUID id, String employeeId, String clientId, Date date, String status, List<String> productsId) {
		this.id = id;
		this.employeeId = employeeId;
		this.clientId = clientId;
		this.date = date;
		this.status = status;
		this.productsId = productsId;
	}

	//Getters and setters
	/**
	 * @return order id
	 */
	public UUID getId() {
		return id;
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
	 * @return order products by id
	 */
	public List<String> getProducts() {
		return productsId;
	}
	/**
	 * @param id to set order id
	 */
	public void setId(UUID id) {
		this.id = id;
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
	 * @param products to set order products id
	 */
	public void setProducts(List<String> productsId) {
		this.productsId = productsId;
	}

	@Override
	public String toString() {
		return "Order id=" + id +" [employeeId=" + employeeId + ", clientId=" + clientId + ", date=" + date + ", status=" + status
				+ ", products=" + productsId 
				+ "]";
		
	}
	
	
	
}

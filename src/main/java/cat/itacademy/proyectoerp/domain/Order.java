package cat.itacademy.proyectoerp.domain;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import cat.itacademy.proyectoerp.util.StringToListConverter;

@Entity
@Table(name = "orders")
public class Order {

	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	@Column
	private String employeeId;
	@Column
	private String clientId;
	@Column
	private Date date;
	@Column
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	@Convert(converter = StringToListConverter.class)
	private List<String> productsId;

	public Order() {
	}

	/**
	 * Constructor with all the parameters.
	 * 
	 * @param employeeId employee responsible
	 * @param clientId   client id
	 * @param date       date of the emitted order
	 * @param status     order status
	 * @param productsId products id included in order
	 */
	
	
	public Order(String employeeId, String clientId, Date date, OrderStatus status, List<String> productsId) {
		this.employeeId = employeeId;
		this.clientId = clientId;
		this.date = date;
		this.status = status;
		this.productsId = productsId;
	}
	
	/**
	 * Constructor with all the parameters.
	 * 
	 * @param id         order id
	 * @param employeeId employee responsible
	 * @param clientId   client id
	 * @param date       date of the emitted order
	 * @param status     order status
	 * @param productsId products id included in order
	 */


	public Order(UUID id, String employeeId, String clientId, Date date, OrderStatus status, List<String> productsId) {
		super();
		this.id = id;
		this.employeeId = employeeId;
		this.clientId = clientId;
		this.date = date;
		this.status = status;
		this.productsId = productsId;
	}
	
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
	public OrderStatus getStatus() {
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
	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	/**
	 * @param productsId to set order products id
	 */
	public void setProducts(List<String> productsId) {
		this.productsId = productsId;
	}

	@Override
	public String toString() {
		return "Order id=" + id + " [employeeId=" + employeeId + ", clientId=" + clientId + ", date=" + date
				+ ", status=" + status + ", products=" + productsId + "]";
	}

}

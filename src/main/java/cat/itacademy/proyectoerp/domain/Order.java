package cat.itacademy.proyectoerp.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.Valid;
import org.hibernate.annotations.GenericGenerator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;



@Entity
@Table(name = "orders")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class,property="orderDetails")
public class Order {

	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
	@Column(name = "id", columnDefinition = "BINARY(16)")
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
	//@Convert(converter = StringToListConverter.class)
	//private List<String> productsId;

	/** @ManyToOne
	@JsonIgnore
	@JoinColumn(name="employee_id")
	private Employee employee; */
	
	
	    @OneToMany(mappedBy = "pk.order")
	    @Valid
	    private List<OrderDetail> orderDetails = new ArrayList<>();


	/**
	 * Constructor with all the parameters.
	 * 
	 * @param employeeId employee responsible
	 * @param clientId   client id
	 * @param date       date of the emitted order
	 * @param status     order status
	 * @param productsId products id included in order
	 */
	
	
	/*
	 * public Order(String employeeId, String clientId, Date date, OrderStatus
	 * status, List<String> productsId) { this.employeeId = employeeId;
	 * this.clientId = clientId; this.date = date; this.status = status;
	 * this.productsId = productsId; }
	 */
	
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

	/*
	 * public Order(UUID id, String employeeId, String clientId, Date date,
	 * OrderStatus status, List<String> productsId) { super(); this.id = id;
	 * this.employeeId = employeeId; this.clientId = clientId; this.date = date;
	 * this.status = status; this.productsId = productsId; }
	 */
	
	/**
	 * @return order id
	 */
	
	 @Transient
	    public Double getTotalOrderPrice() {
	        double sum = 0.0;
	        List<OrderDetail> orderDetails = getOrderDetails();
	        for (OrderDetail od : orderDetails) {
	            sum += od.getTotalPrice();
	        }
	        return sum;
	    }
	
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
	/*
	 * public List<String> getProducts() { return productsId; }
	 */

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

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetail(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}
	
	@Transient
	public int getNumberOfProducts() {
		return this.orderDetails.size();
	}

	/**
	 * @param productsId to set order products id
	 */
	/*
	 * public void setProducts(List<String> productsId) { this.productsId =
	 * productsId; }
	 */

	

}

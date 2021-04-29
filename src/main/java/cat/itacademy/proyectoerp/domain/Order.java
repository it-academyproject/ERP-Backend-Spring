package cat.itacademy.proyectoerp.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sun.istack.Nullable;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name = "orders")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
//@JsonIgnoreProperties("orderDetails")
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "order_id", columnDefinition = "BINARY(16)")
	@JsonProperty("id")
	private UUID id;
	
	@Column(name = "employee_id")
	private UUID employeeId;
	
	@Column(name = "client_id")
	private UUID clientId;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name="date_created")
    private LocalDateTime dateCreated;
	
	@Column
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	@Column
	@Enumerated(EnumType.STRING)
	private PaymentMethod paymentMethod;
	
	@OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "shipping_address_id", referencedColumnName = "id")
	@Nullable
	private Address shippingAddress;
	
	@OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "billing_address_id", referencedColumnName = "id")
	private Address billingAddress;
	
	private double total;
	
	@OneToMany(mappedBy = "order", cascade = {CascadeType.MERGE, CascadeType.REFRESH})
	//@JsonManagedReference (gives 415 Unsupported Media Exception with Post order)
	private Set<OrderDetail> orderDetails = new HashSet<>();
		
	public Order() {
		
	}	
	
	public Order(UUID employeeId, UUID clientId, LocalDateTime dateCreated, OrderStatus status,
			PaymentMethod paymentMethod, Address shippingAddress, Address billingAddress, double total) {
		this.employeeId = employeeId;
		this.clientId = clientId;
		this.dateCreated = LocalDateTime.now();
		this.status = status;
		this.paymentMethod = paymentMethod;
		this.shippingAddress = shippingAddress;
		this.billingAddress = billingAddress;
		this.setTotal(total);
	}
	
	@PrePersist
	public void prePersist() {
		dateCreated = LocalDateTime.now();
	}
	
	//Getters & Setters
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getEmployeeId() {
		return employeeId;
	}

	public void setEmployee_id(UUID employeeId) {
		this.employeeId = employeeId;
	}

	public UUID getClientId() {
		return clientId;
	}

	public void setClient_id(UUID clientId) {
		this.clientId = clientId;
	}
	
	public LocalDateTime getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}	

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public Address getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}

	public Set<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(Set<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}
	
	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	
	//Method to add orderDetails to order
	public void addOrderDetail(OrderDetail orderDetail) {
		this.orderDetails.add(orderDetail);
	}
	
}

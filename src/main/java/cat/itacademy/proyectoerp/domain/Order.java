package cat.itacademy.proyectoerp.domain;

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
public class Order  {  
	
	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "order_id", columnDefinition = "BINARY(16)")
	@JsonProperty("id")
	private UUID id;
	
	@Column(name = "employee_id")
	private UUID employeeId;
	

	//@ManyToOne(fetch = FetchType.LAZY)
	//@JoinColumn(name = "client_id", referencedColumnName = "id", nullable=true)
	//private Client client;  //	private UUID client;
	private UUID clientId;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")

	@Column(name="dateCreated")
    private LocalDateTime dateCreated;
	
	@Column
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	@Column
	@Enumerated(EnumType.STRING)
	private PaymentMethod paymentMethod;
	
	

	@OneToOne(cascade = CascadeType.ALL) //{CascadeType.MERGE, CascadeType.REFRESH}
	@JoinColumn(name = "shipping_address_id", referencedColumnName = "id")
	@Nullable
	private Address shippingAddress;
	
	@OneToOne(cascade = CascadeType.ALL)   //{CascadeType.MERGE, CascadeType.REFRESH}
	@JoinColumn(name = "billing_address_id", referencedColumnName = "id")
	//@NotNull
	private Address billingAddress;
	
	private Double total;
	
	@OneToMany(mappedBy = "order", cascade = {CascadeType.ALL})
	private Set<OrderDetail> orderDetails = new HashSet<>();
		

	public Order() {
		
	}	
	
	public Order(UUID clientId, PaymentMethod paymentMethod, Address billingAddress, Address shippingAddress) {
		this.clientId = clientId;
		this.paymentMethod = paymentMethod;
		this.billingAddress = billingAddress;
		this.shippingAddress = shippingAddress;
	}
	
	public Order(UUID employeeId, UUID clientId, LocalDateTime date_created, OrderStatus status,
			PaymentMethod payment_method, Address shipping_address, Address billing_address, Double total) {
		this.employeeId = employeeId;
		this.clientId = clientId;
		this.dateCreated = LocalDateTime.now();
		this.status = status;
		this.paymentMethod = payment_method;
		this.shippingAddress = shipping_address;
		this.billingAddress = billing_address;
		this.setTotal(total);
	}
	
	@PrePersist
	public void prePersist() {
		this.dateCreated = LocalDateTime.now();
		this.status = OrderStatus.UNASSIGNED;
	}
	
	//Getters & Setters
	public UUID getId() {
		return id;
	}

	public UUID getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(UUID employeeId) {
		this.employeeId = employeeId;
	}

	public UUID getClientId() {
			return clientId;
	}


	public void setclientId(UUID clientId) {
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
	
	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
	
	//Method to add orderDetails to order
	public void addOrderDetail(OrderDetail orderDetails) {
		this.orderDetails.add(orderDetails);
	}
	
}
package cat.itacademy.proyectoerp.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
	
	@Column(name = "client_id")
	private UUID clientId;
	
	//@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name="date_created")
    private LocalDateTime date_created;
	
	@Column
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	@Column
	@Enumerated(EnumType.STRING)
	private PaymentMethod payment_method;
	
	@OneToOne(cascade = CascadeType.ALL) //{CascadeType.MERGE, CascadeType.REFRESH}
	@JoinColumn(name = "shipping_address_id", referencedColumnName = "id")
	@Nullable
	private Address shipping_address;
	
	@OneToOne(cascade = CascadeType.ALL)   //{CascadeType.MERGE, CascadeType.REFRESH}
	@JoinColumn(name = "billing_address_id", referencedColumnName = "id")
	//@NotNull
	private Address billing_address;
	
	private Double total;
	
	@OneToMany(mappedBy = "order", cascade = {CascadeType.MERGE, CascadeType.REFRESH})
	//@JsonManagedReference (gives 415 Unsupported Media Exception with Post order)
	private Set<OrderDetail> order_details = new HashSet<>();
		
	public Order() {
		
	}	
	
	public Order(UUID employeeId, UUID clientId, LocalDateTime date_created, OrderStatus status,
			PaymentMethod payment_method, Address shipping_address, Address billing_address, Double total) {
		this.employeeId = employeeId;
		this.clientId = clientId;
		this.date_created = LocalDateTime.now();
		this.status = status;
		this.payment_method = payment_method;
		this.shipping_address = shipping_address;
		this.billing_address = billing_address;
		this.setTotal(total);
	}
	
	@PrePersist
	public void prePersist() {
		date_created = LocalDateTime.now();
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

	public void setclientId(UUID clientId) {
		this.clientId = clientId;
	}
	
	public LocalDateTime getDateCreated() {
		return date_created;
	}
	
	public void setDateCreated(LocalDateTime date_created) {
		this.date_created = date_created;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}	

	public PaymentMethod getPaymentMethod() {
		return payment_method;
	}

	public void setPaymentMethod(PaymentMethod payment_method) {
		this.payment_method = payment_method;
	}

	public Address getShippingAddress() {
		return shipping_address;
	}

	public void setShippingAddress(Address shipping_address) {
		this.shipping_address = shipping_address;
	}

	public Address getBillingAddress() {
		return billing_address;
	}

	public void setBillingAddress(Address billing_address) {
		this.billing_address = billing_address;
	}

	public Set<OrderDetail> getOrderDetails() {
		return order_details;
	}

	public void setOrderDetails(Set<OrderDetail> order_details) {
		this.order_details = order_details;
	}
	
	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
	
	//Method to add orderDetails to order
	public void addOrderDetail(OrderDetail order_details) {
		this.order_details.add(order_details);
	}
	
}
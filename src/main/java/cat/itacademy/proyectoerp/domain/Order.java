package cat.itacademy.proyectoerp.domain;

//import java.io.Serializable;
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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "orders")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Order {

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
	@Column(name = "id", columnDefinition = "BINARY(16)")
	private UUID id;
	
	@Column(name = "employee_id")
	private String employeeId;
	
	@Column(name = "client_id")
	private String clientId;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name="date_created")
    private LocalDateTime dateCreated;
	
	@Column
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	@Column
	@Enumerated(EnumType.STRING)
	private PaymentMethod paymentMethod;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "shipping_address_id", referencedColumnName = "id")
	@Nullable
	private Address shippingAddress;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "billing_address_id", referencedColumnName = "id")
	private Address billingAddress;
		
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable (name = "order_products", joinColumns = { @JoinColumn(name = "order_id")},
			inverseJoinColumns = { @JoinColumn(name = "product_id")})
	@JsonIgnoreProperties("orders")
	private Set<Product> orderProducts = new HashSet<>();
		
	public Order() {
		
	}	
	
	public Order(UUID id, String employeeId, String clientId, LocalDateTime dateCreated, OrderStatus status,
			PaymentMethod paymentMethod, Address shippingAddress, Address billingAddress, Set<Product> orderProducts) {
		super();
		this.id = id;
		this.employeeId = employeeId;
		this.clientId = clientId;
		this.dateCreated = LocalDateTime.now();
		this.status = status;
		this.paymentMethod = paymentMethod;
		this.shippingAddress = shippingAddress;
		this.billingAddress = billingAddress;
		this.orderProducts = orderProducts;
	}

	
	public Order(String employeeId, String clientId, LocalDateTime dateCreated, OrderStatus status,
			PaymentMethod paymentMethod, Address shippingAddress, Address billingAddress, Set<Product> orderProducts) {
		super();
		this.employeeId = employeeId;
		this.clientId = clientId;
		this.dateCreated = LocalDateTime.now();
		this.status = status;
		this.paymentMethod = paymentMethod;
		this.shippingAddress = shippingAddress;
		this.billingAddress = billingAddress;
		this.orderProducts = orderProducts;
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

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployee_id(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClient_id(String clientId) {
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

	public Set<Product> getOrderProducts() {
		return orderProducts;
	}

	public void setOrderProducts(Set<Product> orderProducts) {
		this.orderProducts = orderProducts;
	}
	
}

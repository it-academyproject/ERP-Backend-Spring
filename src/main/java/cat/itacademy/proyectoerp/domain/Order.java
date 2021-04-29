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
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name = "orders")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Order {

	//private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "order_id", columnDefinition = "BINARY(16)")
	private UUID id;
	
	@Column(name = "employee_id")
	private String employeeId;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "client_id", referencedColumnName = "id", nullable=false)
	private Client client;  //	private UUID client;
		
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name="date_created")
    private LocalDateTime dateCreated;
	
	@Column
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	@Column
	@Enumerated(EnumType.STRING)
	private PaymentMethod paymentMethod;
	
	public Order() {
	 System.out.println(); //Check dapser75
	}
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "shipping_address_id", referencedColumnName = "id")
	@Nullable
	private Address shippingAddress;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "billing_address_id", referencedColumnName = "id")
	private Address billingAddress;
	
	private double total;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	@JsonManagedReference
	private Set<OrderDetail> orderDetails = new HashSet<>();
		
	//Version anterior mezcla B-49 y B48
	//public Order(UUID id, String employeeId, UUID client,LocalDateTime dateCreated, OrderStatus status, Set<Product> orderProducts) {  //@Dapser75
	/*	public Order(UUID id, String employeeId, Client client,LocalDateTime dateCreated, OrderStatus status, Set<Product> orderProducts) { 
			super();
			this.id = id;
			this.employeeId = employeeId;
			this.client= client;
			this.dateCreated = dateCreated;
			this.status = status;
			this.orderProducts = orderProducts;		*/
	
	
	//public Order(String employeeId, UUID client, OrderStatus status, Set<Product> orderProducts) { //@Dapser75
	//	public Order(String employeeId, Client client, OrderStatus status, Set<Product> orderProducts) { 
	public Order(String employeeId, Client client, LocalDateTime dateCreated, OrderStatus status,
			PaymentMethod paymentMethod, Address shippingAddress, Address billingAddress, double total) {
		//super();
		this.employeeId = employeeId;
		this.client = client;
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

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployee_id(String employeeId) {
		this.employeeId = employeeId;
	}

	public Client getClient() {
			return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

/*	public void setClient_id(UUID client) {
		this.client = clientId;
	}*/

	
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

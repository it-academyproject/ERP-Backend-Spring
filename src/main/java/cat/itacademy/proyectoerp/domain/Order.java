package cat.itacademy.proyectoerp.domain;

import java.io.Serializable;

import java.time.LocalDateTime;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Table(name = "orders")
public class Order implements Serializable{

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
	
	
	@ManyToMany
	//@JsonBackReference
	@JoinTable(name="orders_products",
	joinColumns = @JoinColumn(name="order_id", referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name="product_id", referencedColumnName= "id"))
	private Set<Product> products = new HashSet<Product>();

	
	public Order() {
		
	}
	
	public Order(UUID id, String employeeId, String clientId,LocalDateTime dateCreated, OrderStatus status, Set<Product> products) {
		super();
		this.id = id;
		this.employeeId = employeeId;
		this.clientId= clientId;
		this.dateCreated = dateCreated;
		this.status = status;
		this.products = products;
		
	}
	
	public Order(String employeeId, String clientId, LocalDateTime dateCreated, OrderStatus status, Set<Product> products) {
		super();
		this.employeeId = employeeId;
		this.clientId = clientId;
		this.dateCreated = dateCreated;
		this.status = status;
		this.products = products;
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

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}
	
	
	public void addProduct(Product product) {
		this.products.add(product);
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", employeeId=" + employeeId + ", clientId=" + clientId + ", dateCreated="
				+ dateCreated + ", status=" + status + ", products=" + products + "]";
	}



}

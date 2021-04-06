package cat.itacademy.proyectoerp.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import com.fasterxml.jackson.annotation.JsonBackReference;


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
	@Column
	private String employeeId;
	@Column
	private String clientId;
	@Column
	@Temporal(TemporalType.DATE)
	private Date date;
	@Column
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	
	@ManyToMany(cascade = CascadeType.ALL)
	//@JsonBackReference
	@JoinTable(name="orders_products",
	joinColumns = @JoinColumn(name="order_id", referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name="product_id", referencedColumnName= "id"))
	private Set<Product> products = new HashSet<Product>();



	public Order() {
		
	}
	
	public Order(UUID id, String employeeId, String clientId, Date date, OrderStatus status, Set<Product> products) {
		super();
		this.id = id;
		this.employeeId = employeeId;
		this.clientId = clientId;
		this.date = date;
		this.status = status;
		this.products = products;
	}
	
	public Order(String employeeId, String clientId, Date date, OrderStatus status, Set<Product> products) {
		super();
		this.employeeId = employeeId;
		this.clientId = clientId;
		this.date = date;
		this.status = status;
		this.products = products;
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

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	@PrePersist
	public void prePersist() {
		date = new Date();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
		return "Order [id=" + id + ", employeeId=" + employeeId + ", clientId=" + clientId + ", date=" + date
				+ ", status=" + status + ", products=" + products + "]";
	}

}

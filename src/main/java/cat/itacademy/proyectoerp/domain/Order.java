package cat.itacademy.proyectoerp.domain;

//import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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
	
	//@OneToOne(cascade = CascadeType.ALL)
	//@JoinColumn(name = "user_id", referencedColumnName = "id")
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "client_id", referencedColumnName = "id", nullable=false)
	private Client client;  //	private UUID client;
		
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name="date_created")
    private LocalDateTime dateCreated;
	
	@Column
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable (name = "order_products", joinColumns = { @JoinColumn(name = "order_id")},
			inverseJoinColumns = { @JoinColumn(name = "product_id")})

//	@JsonManagedReference -> Gives Exception for ManyToMany unable to map
//	@JsonIgnore -> It works, but does not give us the Json part of products when we GET the order by id
	@JsonIgnoreProperties("orders")
	private Set<Product> orderProducts = new HashSet<>();
	  
	
	public Order() {
	 System.out.println(); //Check dapser75
	}
	
	//public Order(UUID id, String employeeId, UUID client,LocalDateTime dateCreated, OrderStatus status, Set<Product> orderProducts) {  //@Dapser75
	public Order(UUID id, String employeeId, Client client,LocalDateTime dateCreated, OrderStatus status, Set<Product> orderProducts) { 
		super();
		this.id = id;
		this.employeeId = employeeId;
		this.client= client;
		this.dateCreated = dateCreated;
		this.status = status;
		this.orderProducts = orderProducts;		
		
	}
	
	//public Order(String employeeId, UUID client, OrderStatus status, Set<Product> orderProducts) { //@Dapser75
	public Order(String employeeId, Client client, OrderStatus status, Set<Product> orderProducts) { 
		super();
		this.employeeId = employeeId;
		this.client = client;
		this.dateCreated = LocalDateTime.now();
		this.status = status;
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

	//public UUID getClientId() { //julia
/*	public Client getClientId() {
		return getClient();
	}
*/
	//public UUID getClientId() { //julia
	public Client getClient() {
			return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	//	public void setClient_id(UUID client) { //julia
	/*public void setClient_id(Client clientId) {
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

	public Set<Product> getOrderProducts() {
		return orderProducts;
	}

	public void setOrderProducts(Set<Product> orderProducts) {
		this.orderProducts = orderProducts;
	}
	
}

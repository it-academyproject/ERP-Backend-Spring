package cat.itacademy.proyectoerp.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table (name = "order_details")
@JsonIgnoreProperties("order")
public class OrderDetail {
	
	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", columnDefinition = "BINARY(16)")
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	@JsonBackReference (value="order_details-product") //Without value -> 415 in Post Order
	private Product product;
	
	@ManyToOne
	@JoinColumn(name = "order_id")
	@JsonBackReference (value="order_details-order") //Without value -> 415 in Post Order
	private Order order;
	
	private int quantity;
	private double subtotal;
	
	public OrderDetail() {
		
	}
		
	public OrderDetail(Product product, Order order, int quantity, double subtotal) {
		this.product = product;
		this.order = order;
		this.quantity = quantity;
		this.subtotal = subtotal;
	}
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}	
	
}

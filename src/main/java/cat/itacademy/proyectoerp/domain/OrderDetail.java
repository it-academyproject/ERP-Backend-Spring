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
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "order_details")
@JsonIgnoreProperties("order")
public class OrderDetail {
	
	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Type(type="uuid-char")
	@Column(name = "id")
	private UUID id;
	
	@Column(name = "quantity")
	private int quantity;
	@Column(name = "subtotal")
	private double subtotal;
	
	@ManyToOne
	@JoinColumn(name = "product_id", referencedColumnName = "product_id")
	//@JsonBackReference(value="order_details-product") //Without value -> 415 in Post Order
	private Product product;
	
	@ManyToOne
	@JoinColumn(name = "order_id", referencedColumnName = "order_id")
	//@JsonBackReference(value="order_details-order") //Without value -> 415 in Post Order
	private Order order;
	
	public OrderDetail() {
	}
	
	public OrderDetail(int quantity, double subtotal, Product product, Order order) {
		this.quantity = quantity;
		this.subtotal = subtotal;
		this.product = product;
		this.order = order;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public void setOrder(Order order) {
		this.order = order;
	}
	
	public UUID getId() {
		return id;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public double getSubtotal() {
		return subtotal;
	}
	
	public Product getProduct() {
		return product;
	}
	
	public Order getOrder() {
		return order;
	}
	
}
package cat.itacademy.proyectoerp.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "products")
public class Product implements Serializable {

	// Products entity attributes

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	@JsonIgnore
	private int id;
	@Column(unique=true)
	private String name;
	private int stock;
	private String image;
	private String family;
	private double price;
	private double vat;
	private double wholesale_price;
	private int wholesale_quantity;
	
	//@JsonIgnoreProperties("products")
	@OneToMany (mappedBy = "product")
	// @JsonManagedReference (gives 415 Unsupported Media Exception with Post order)
	private Set <OrderDetail> order_details = new HashSet<>();
	
	public Product() {
		
	}
	
	public Product(String name, int stock, String image, String family, double price, double vat,
			double wholesale_price, int wholesale_quantity) {
		this.name = name;
		this.stock = stock;
		this.image = image;
		this.family = family;
		this.price = price;
		this.vat = vat;
		this.wholesale_price = wholesale_price;
		this.wholesale_quantity = wholesale_quantity;
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getVat() {
		return vat;
	}

	public void setVat(double vat) {
		this.vat = vat;
	}

	public double getWholesale_price() {
		return wholesale_price;
	}

	
	public void setWholesale_price(double wholesale_price) {
		this.wholesale_price = wholesale_price;
	}

	public int getWholesale_quantity() {
		return wholesale_quantity;
	}


	public void setWholesale_quantity(int wholesale_quantity) {
		this.wholesale_quantity = wholesale_quantity;
	}
		
	public Set<OrderDetail> getOrderDetails() {
		return order_details;
	}
	
//	@Transient
	public void setOrderDetails(Set<OrderDetail> order_details) {
		this.order_details = order_details;
	}

	// Console data printing method
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", stock=" + stock + ", image=" + image + ", family=" + family
				+ ", price=" + price + ", vat=" + vat + ", wholesale_price=" + wholesale_price + ", wholesale_quantity="
				+ wholesale_quantity + "]";
	}
	
}
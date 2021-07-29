package cat.itacademy.proyectoerp.domain;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;

@Entity
@Table(name = "products")
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private int id;
	@Column(unique=true)
	private String name;
	private int stock;
	private String image;
	private String family;
	private double price;
	private double vat;
	private double wholesalePrice;
	private int wholesaleQuantity;
	private long created;
	private long modified;
	
	@ManyToMany
	private Set<Category> categories;
	
	@OneToMany (mappedBy = "product")
	// @JsonManagedReference (gives 415 Unsupported Media Exception with Post order)
	private Set <OrderDetail> order_details = new HashSet<>();

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "shop_id", referencedColumnName = "shop_id")
	//TODO In the future this field should be mandatory
	//@NotNull(message = "You have to assign shop to this product")
	@Valid
	private Shop shop;

	public Product() {

	}
	
	public Product(String name, int stock, String image, String family, double price, double vat,
			double wholesalePrice, int wholesaleQuantity, long created, long modified, Shop shop) {
		this.name = name;
		this.stock = stock;
		this.image = image;
		this.family = family;
		this.price = price;
		this.vat = vat;
		this.wholesalePrice = wholesalePrice;
		this.wholesaleQuantity = wholesaleQuantity;
		this.created = created;
		this.modified = modified;
		this.shop = shop;
		
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

	public double getWholesalePrice() {
		return wholesalePrice;
	}

	
	public void setWholesalePrice(double wholesalePrice) {
		this.wholesalePrice = wholesalePrice;
	}

	public int getWholesaleQuantity() {
		return wholesaleQuantity;
	}


	public void setWholesaleQuantity(int wholesaleQuantity) {
		this.wholesaleQuantity = wholesaleQuantity;
	}
		
	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public Set<OrderDetail> getOrderDetails() {
		return order_details;
	}
	
	public void setOrderDetails(Set<OrderDetail> orderDetails) {
		this.order_details = orderDetails;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", stock=" + stock + ", image=" + image + ", family=" + family
				+ ", price=" + price + ", vat=" + vat + ", wholesale_price=" + wholesalePrice + ", wholesale_quantity="
				+ wholesaleQuantity + ", created=" + created + ", modified=" + modified + "]";
	}
	
	public long getCreated() {
		return created;
	}
	
	public void setCreated(long created) {
		this.created = created;
	}
	
	public long getModified() {
		return modified;
	}
	
	public void setModified(long modified) {
		this.modified = modified;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}
	
}
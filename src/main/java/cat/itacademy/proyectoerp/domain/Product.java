package cat.itacademy.proyectoerp.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
	
	@Column(name = "name", unique = true)
	private String name;
	@Column(name = "stock")
	private int stock;
	@Column(name = "image")
	private String image;
	@Column(name = "price")
	private double price;
	@Column(name = "vat")
	private double vat;
	@Column(name = "wholesale_price")
	private double wholesalePrice;
	@Column(name = "wholesale_quantity")
	private int wholesaleQuantity;
	@Column(name = "created")
	private long created;
	@Column(name = "modified")
	private long modified;
	
	@ManyToOne
	@JoinColumn(name = "offer_id", referencedColumnName = "offer_id")
	private Offer offer;
	
	@OneToOne
	@JoinColumn(name = "category_id", referencedColumnName = "category_id")
	private Category category;
	
	@OneToMany(mappedBy = "product")
	private List<OrderDetail> orderDetails;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "shop_id", referencedColumnName = "shop_id")
	// TODO In the future this field should be mandatory
	// @NotNull(message = "You have to assign shop to this product")
	@Valid
	private Shop shop;
	
	public Product() {
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setStock(int stock) {
		this.stock = stock;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public void setVat(double vat) {
		this.vat = vat;
	}
	
	public void setWholesalePrice(double wholesalePrice) {
		this.wholesalePrice = wholesalePrice;
	}
	
	public void setWholesaleQuantity(int wholesaleQuantity) {
		this.wholesaleQuantity = wholesaleQuantity;
	}
	
	public void setCreated(long created) {
		this.created = created;
	}
	
	public void setModified(long modified) {
		this.modified = modified;
	}
	
	public void setOffer(Offer offer) {
		this.offer = offer;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
	
	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}
	
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public int getStock() {
		return stock;
	}
	
	public String getImage() {
		return image;
	}
	
	public double getPrice() {
		return price;
	}
	
	public double getVat() {
		return vat;
	}
	
	public double getWholesalePrice() {
		return wholesalePrice;
	}
	
	public int getWholesaleQuantity() {
		return wholesaleQuantity;
	}
	
	public long getCreated() {
		return created;
	}
	
	public long getModified() {
		return modified;
	}
	
	public Offer getOffer() {
		return offer;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}
	
	public Shop getShop() {
		return shop;
	}
	
}

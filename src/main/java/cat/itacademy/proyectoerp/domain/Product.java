package cat.itacademy.proyectoerp.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

	// Products entity attributes

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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


	// Constructors

	/**
	 * Constructor without parameters
	 */
	public Product() {
	}

	/**
	 * Constructor with parameters
	 * 
	 * @param id                product id
	 * @param name              product name
	 * @param stock             product stock
	 * @param image             product image url
	 * @param family            product family
	 * @param price             product price
	 * @param vat               product vat
	 * @param wholesalePrice    product wholesale price
	 * @param wholesaleQuantity product wholesale quantity
	 */
	public Product(int id, String name, int stock, String image, String family, double price, double vat,
			double wholesale_price, int wholesale_quantity) {

		this.id = id;
		this.name = name;
		this.stock = stock;
		this.image = image;
		this.family = family;
		this.price = price;
		this.vat = vat;
		this.wholesale_price = wholesale_price;
		this.wholesale_quantity = wholesale_quantity;
	}

	// Getters and Setters

	/**
	 * @return product id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id to set product id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return product name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name to set product name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return product stock
	 */
	public int getStock() {
		return stock;
	}

	/**
	 * @param stock to set product stock
	 */
	public void setStock(int stock) {
		this.stock = stock;
	}

	/**
	 * @return product image url
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image to set product image url
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * @return product family
	 */
	public String getFamily() {
		return family;
	}

	/**
	 * @param family to set product family
	 */
	public void setFamily(String family) {
		this.family = family;
	}

	/**
	 * @return product price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price to set product price
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return product vat
	 */
	public double getVat() {
		return vat;
	}

	/**
	 * @param vat to set product vat
	 */
	public void setVat(double vat) {
		this.vat = vat;
	}

	/**
	 * @return product wholesale price
	 */
	public double getWholesale_price() {
		return wholesale_price;
	}

	/**
	 * @param wholesale_price to set product wholesale price
	 */
	public void setWholesale_price(double wholesale_price) {
		this.wholesale_price = wholesale_price;
	}

	/**
	 * @return product wholesale quantity
	 */
	public int getWholesale_quantity() {
		return wholesale_quantity;
	}

	/**
	 * @param wholesale_quantity to set product wholesale quantity
	 */
	public void setWholesale_quantity(int wholesale_quantity) {
		this.wholesale_quantity = wholesale_quantity;
	}

	// Console data printing method
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", stock=" + stock + ", image=" + image + ", family=" + family
				+ ", price=" + price + ", vat=" + vat + ", wholesale_price=" + wholesale_price + ", wholesale_quantity="
				+ wholesale_quantity + "]";
	}
}

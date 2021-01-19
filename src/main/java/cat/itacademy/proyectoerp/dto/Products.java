package cat.itacademy.proyectoerp.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Products {

	// Products entity attributes

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private int stock;
	private String image;
	private String family;
	private double price;
	private double vat;
	@Column(name = "wholesale_price") // column name is different in DDBB
	private double wholesalePrice;
	@Column(name = "wholesale_quantity") // column name is different in DDBB
	private int wholesaleQuantity;

	// Constructors

	/**
	 * Constructor without parameters
	 */
	public Products() {
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
	public Products(int id, String name, int stock, String image, String family, double price, double vat,
			double wholesalePrice, int wholesaleQuantity) {

		this.id = id;
		this.name = name;
		this.stock = stock;
		this.image = image;
		this.family = family;
		this.price = price;
		this.vat = vat;
		this.wholesalePrice = wholesalePrice;
		this.wholesaleQuantity = wholesaleQuantity;
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
	public double getWholesalePrice() {
		return wholesalePrice;
	}

	/**
	 * @param wholesalePrice to set product wholesale price
	 */
	public void setWholesalePrice(double wholesalePrice) {
		this.wholesalePrice = wholesalePrice;
	}

	/**
	 * @return product wholesale quantity
	 */
	public int getWholesaleQuantity() {
		return wholesaleQuantity;
	}

	/**
	 * @param wholesaleQuantity to set product wholesale quantity
	 */
	public void setWholesaleQuantity(int wholesaleQuantity) {
		this.wholesaleQuantity = wholesaleQuantity;
	}

	// Console data printing method
	@Override
	public String toString() {
		return "Products [id=" + id + ", name=" + name + ", stock=" + stock + ", image=" + image + ", family=" + family
				+ ", price=" + price + ", vat=" + vat + ", wholesalePrice=" + wholesalePrice + ", wholesaleQuantity="
				+ wholesaleQuantity + "]";
	}
}

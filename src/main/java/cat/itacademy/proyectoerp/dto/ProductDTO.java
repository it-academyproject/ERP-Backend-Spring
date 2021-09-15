package cat.itacademy.proyectoerp.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProductDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private int stock;
	private String image;
	private double price;
	private double vat;
	private double wholesalePrice;
	private int wholesaleQuantity;
	private long created;
	private long modified;
	private OfferDTO offerDto;
	private CategoryDTO categoryDto;
	private ShopDTO shopDto;
	
	public ProductDTO() {
	}
	
	public ProductDTO(String name, int stock, String image, double price, double vat, double wholesalePrice, int wholesaleQuantity,
			OfferDTO offerDto, CategoryDTO categoryDto, ShopDTO shopDto) {
		this.name = name;
		this.stock = stock;
		this.image = image;
		this.price = price;
		this.vat = vat;
		this.wholesalePrice = wholesalePrice;
		this.wholesaleQuantity = wholesaleQuantity;
		this.offerDto = offerDto;
		this.categoryDto = categoryDto;
		this.shopDto = shopDto;
	}
	
	public ProductDTO(int id, String name, int stock, String image, double price, double vat, double wholesalePrice, int wholesaleQuantity,
			long created, long modified, OfferDTO offerDto, CategoryDTO categoryDto, ShopDTO shopDto) {
		this.id = id;
		this.name = name;
		this.stock = stock;
		this.image = image;
		this.price = price;
		this.vat = vat;
		this.wholesalePrice = wholesalePrice;
		this.wholesaleQuantity = wholesaleQuantity;
		this.created = created;
		this.modified = modified;
		this.offerDto = offerDto;
		this.categoryDto = categoryDto;
		this.shopDto = shopDto;
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
	
	public void setOfferDto(OfferDTO offerDto) {
		this.offerDto = offerDto;
	}
	
	public void setCategoryDto(CategoryDTO categoryDto) {
		this.categoryDto = categoryDto;
	}
	
	public void setShopDto(ShopDTO shopDto) {
		this.shopDto = shopDto;
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
	
	public OfferDTO getOfferDto() {
		return offerDto;
	}
	
	public CategoryDTO getCategoryDto() {
		return categoryDto;
	}
	
	public ShopDTO getShopDto() {
		return shopDto;
	}
	
}

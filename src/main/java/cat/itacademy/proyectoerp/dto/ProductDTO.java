package cat.itacademy.proyectoerp.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProductDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id, stock, wholesaleQuantity;
	private String name, image;
	private double price, vat, wholesalePrice;
	private long created, modified;
	private CategoryDTO categoryDto;
	private OfferDTO offerDto;
	
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
	
}

package cat.itacademy.proyectoerp.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProductStatsDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private String image;
	private double price;
	
	
	
	
	public ProductStatsDTO() {
	}


	public ProductStatsDTO(String name, String image, double price) {
		super();
		this.name = name;
		this.image = image;
		this.price = price;
	}


	public ProductStatsDTO(int id, String name, String image, double price) {
		super();
		this.id = id;
		this.name = name;
		this.image = image;
		this.price = price;
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


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}
	
	
}

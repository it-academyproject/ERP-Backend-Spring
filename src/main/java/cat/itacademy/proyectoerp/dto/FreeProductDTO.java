package cat.itacademy.proyectoerp.dto;

import cat.itacademy.proyectoerp.domain.FreeProducts;
import cat.itacademy.proyectoerp.domain.Offer;

public class FreeProductDTO {
	private int id;
	
	private int products_to_buy;
	private int products_to_pay;
	
	public FreeProductDTO(FreeProducts freeproduct) {
		
		this.id = freeproduct.getId();
		this.products_to_buy = freeproduct.getProducts_to_buy();
		this.products_to_pay = freeproduct.getProducts_to_pay();
	
	}

	
	//GETTER & SETTERS
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProducts_to_buy() {
		return products_to_buy;
	}

	public void setProducts_to_buy(int products_to_buy) {
		this.products_to_buy = products_to_buy;
	}

	public int getProducts_to_pay() {
		return products_to_pay;
	}

	public void setProducts_to_pay(int products_to_pay) {
		this.products_to_pay = products_to_pay;
	}
	
}

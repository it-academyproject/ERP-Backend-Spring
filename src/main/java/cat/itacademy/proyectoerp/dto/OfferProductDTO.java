package cat.itacademy.proyectoerp.dto;

import java.util.List;
import java.util.UUID;


public class OfferProductDTO {
	private UUID offer_id;
	private List<Integer> productList;

	public OfferProductDTO() {
		
	}
	
	public OfferProductDTO(UUID offer_id,List<Integer> productList) {
		this.offer_id = offer_id;
		this.productList = productList;
	}
	
	//GETTERS & SETTERS
	public UUID getOffer_id() {
		return offer_id;
	}

	public void setOffer_id(UUID offer_id) {
		this.offer_id = offer_id;
	}

	public List<Integer> getProductList() {
		return productList;
	}

	public void setProductList(List<Integer> productList) {
		this.productList = productList;
	}
	
	
	
}

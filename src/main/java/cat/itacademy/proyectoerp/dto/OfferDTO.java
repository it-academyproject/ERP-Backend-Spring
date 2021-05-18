package cat.itacademy.proyectoerp.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OfferDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UUID id;
	private String description;
	private LocalDateTime start_date;
	private LocalDateTime end_date;
	private String offer_type;
	private String applied;
	private int  free_products_id;
	private int direct_discount_id;
	
	
	
	//GETTER & SETTERS

	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOffer_type() {
		return offer_type;
	}
	
	public void setOffer_type(String offer_type) {
		this.offer_type = offer_type;
	}
	public LocalDateTime getStart_date() {
		return start_date;
	}
	
	public void setStart_date(LocalDateTime start_date) {
		this.start_date = start_date;
	}
	
	public LocalDateTime getEnd_date() {
		return end_date;
	}
	public void setEnd_date(LocalDateTime end_date) {
		this.end_date = end_date;
	}
	public String getApplied() {
		return applied;
	}
	public void setApplied(String applied) {
		this.applied = applied;
	}
	
	public int getFree_products_id() {
		return free_products_id;
	}
	public void setFree_products_id(int free_products_id) {
		this.free_products_id = free_products_id;
	}
	
	public int getDirect_discount_id() {
		return direct_discount_id;
	}
	
	public void setDirect_discount_id(int direct_discount_id) {
		this.direct_discount_id = direct_discount_id;
	}

	
}

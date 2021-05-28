package cat.itacademy.proyectoerp.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import cat.itacademy.proyectoerp.domain.Offer;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OfferFullDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private UUID id;
	private String description;
	@JsonFormat(pattern = "dd-MM-yyyy :: HH:mm:ss")
	private LocalDateTime start_date;
	@JsonFormat(pattern = "dd-MM-yyyy :: HH:mm:ss")
	private LocalDateTime end_date;
	private String offer_type;
	private String applied;
	private int  free_products_id;
	private double discount;
	private int direct_discount_id;
	private int products_to_buy;
	private int products_to_pay;
	
	
	public OfferFullDTO(UUID id, String description, LocalDateTime start_date, LocalDateTime end_date, String offer_type,
			String applied, int free_products_id, double discount, int direct_discount_id, int products_to_buy,
			int products_to_pay) {
		super();
		this.id = id;
		this.description = description;
		this.start_date = start_date;
		this.end_date = end_date;
		this.offer_type = offer_type;
		this.applied = applied;
		this.free_products_id = free_products_id;
		this.discount = discount;
		this.direct_discount_id = direct_discount_id;
		this.products_to_buy = products_to_buy;
		this.products_to_pay = products_to_pay;
	}

	
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

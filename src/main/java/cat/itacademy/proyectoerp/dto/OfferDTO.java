package cat.itacademy.proyectoerp.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OfferDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private UUID id;
	private String name;
	private double discount;
	private LocalDateTime startsOn;
	private LocalDateTime endsOn;
	private int paidQuantity;
	private int freeQuantity;
	
	public OfferDTO() {
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	
	public void setStartDate(LocalDateTime startsOn) {
		this.startsOn = startsOn;
	}
	
	public void setEndDate(LocalDateTime endsOn) {
		this.endsOn = endsOn;
	}
	
	public void setPaidQuantity(int paidQuantity) {
		this.paidQuantity = paidQuantity;
	}
	
	public void setFreeQuantity(int freeQuantity) {
		this.freeQuantity = freeQuantity;
	}
	
	public UUID getId() {
		return id;
	}
	
	public String getDescription() {
		return name;
	}
	
	public double getDiscount() {
		return discount;
	}
	
	public LocalDateTime getStartDate() {
		return startsOn;
	}
	
	public LocalDateTime getEndDate() {
		return endsOn;
	}
	
	public int getPaidQuantity() {
		return paidQuantity;
	}
	
	public int getFreeQuantity() {
		return freeQuantity;
	}
	
}

package cat.itacademy.proyectoerp.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class OfferDTO implements Serializable {	
	
	private UUID id;	
	private String description;	
	private double discount;	
	private LocalDate startDate;		
	private LocalDate endDate;	

	public OfferDTO() {
		
	}

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

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;	}

	
	private static final long serialVersionUID = 1L;

	
}

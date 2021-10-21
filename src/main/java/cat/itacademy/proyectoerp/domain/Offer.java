package cat.itacademy.proyectoerp.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@Entity
@Table(name = "offers")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Offer implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Type(type="uuid-char")
	@Column(name = "offer_id")
	private UUID id;
	
	@Column(name = "name")
	@NotNull(message = "name is mandatory")
	private String name;
	@Column(name = "discount")
	private double discount;
	@JsonProperty("starts_on")
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@Column(name="starts_on")
	private LocalDateTime startsOn;
	@JsonProperty("ends_on")
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@Column(name="ends_on")
	private LocalDateTime endsOn;
	@JsonProperty("paid_quantity")
	@Column(name = "paid_quantity")
	private int paidQuantity;
	@JsonProperty("free_quantity")
	@Column(name = "free_quantity")
	private int freeQuantity;
	
	public Offer() {
	}
	
	
	
	public Offer(UUID id, @NotNull(message = "name is mandatory") String name, double discount, LocalDateTime startsOn,
			LocalDateTime endsOn, int paidQuantity, int freeQuantity) {
		this.id = id;
		this.name = name;
		this.discount = discount;
		this.startsOn = startsOn;
		this.endsOn = endsOn;
		this.paidQuantity = paidQuantity;
		this.freeQuantity = freeQuantity;
	}
	
	public Offer(String name, double discount, LocalDateTime startsOn,
			LocalDateTime endsOn, int paidQuantity, int freeQuantity) {
		
		this.name = name;
		this.discount = discount;
		this.startsOn = startsOn;
		this.endsOn = endsOn;
		this.paidQuantity = paidQuantity;
		this.freeQuantity = freeQuantity;
	}



	//Getters & Setters
	public void setId(UUID id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	
	public void setStartsOn(LocalDateTime startsOn) {
		this.startsOn = startsOn;
	}
	
	public void setEndsOn(LocalDateTime endsOn) {
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
	
	public String getName() {
		return name;
	}
	
	public double getDiscount() {
		return discount;
	}
	
	public LocalDateTime getStartsOn() {
		return startsOn;
	}
	
	public LocalDateTime getEndsOn() {
		return endsOn;
	}
	
	public int getPaidQuantity() {
		return paidQuantity;
	}
	
	public int getFreeQuantity() {
		return freeQuantity;
	}
	
}

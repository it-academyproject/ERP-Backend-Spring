package cat.itacademy.proyectoerp.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "offers")
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
	@NotNull(message = "discount is mandatory")
	private double discount;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@Column(name="starts_on")
	private LocalDateTime startsOn;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@Column(name="ends_on")
	private LocalDateTime endsOn;
	@Column(name = "paid_quantity")
	private int paidQuantity;
	@Column(name = "free_quantity")
	private int freeQuantity;
	
	@OneToMany(mappedBy = "offer")
	private List<Product> products;
	
	@OneToOne(mappedBy = "offer")
	private Category category;
	
	public Offer() {
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
	
	public void setStartsOn(LocalDateTime startsOn) {
		this.startsOn = startsOn;
	}
	
	public void setEndsOn(LocalDateTime endsOn) {
		this.endsOn = endsOn;
	}
	
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	public void setPaidQuantity(int paidQuantity) {
		this.paidQuantity = paidQuantity;
	}
	
	public void setFreeQuantity(int freeQuantity) {
		this.freeQuantity = freeQuantity;
	}
	
	public void setCategory(Category category) {
		this.category = category;
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
	
	public List<Product> getProducts() {
		return products;
	}
	
	public Category getCategory() {
		return category;
	}
	
}

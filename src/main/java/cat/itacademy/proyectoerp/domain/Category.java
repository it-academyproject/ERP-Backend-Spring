package cat.itacademy.proyectoerp.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "categories")
public class Category {
	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "category_id")
	@Type(type = "uuid-char")
	private UUID id;
	
	@Column(name = "name", unique = true)
	//@NotBlank(message = "Name cannot be null or whitespace")
	private String name;
	@Column(name = "description", unique = true)
	//@NotBlank(message = "Description cannot be null or whitespace")
	//@Size(min = 10, max = 200, message = "Description must be between 10 and 200 characters")
	private String description;
	
	@OneToOne
	@JoinColumn(name = "offer_id", referencedColumnName = "offer_id")
	private Offer offer;
	
	@OneToOne(mappedBy = "category")
	private Product product;

	public Category() {
	}
	
	public Category(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setOffer(Offer offer) {
		this.offer = offer;
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public UUID getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Offer getOffer() {
		return offer;
	}
	
	public Product getProduct() {
		return product;
	}
	
}

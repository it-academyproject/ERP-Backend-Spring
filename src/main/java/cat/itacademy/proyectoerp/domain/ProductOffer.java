package cat.itacademy.proyectoerp.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

@Entity
//@Table (name = "product_offer",uniqueConstraints= {@UniqueConstraint(columnNames= {"offer","product"})})
@Table (name = "product_offer")
public class ProductOffer {
	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", columnDefinition = "BINARY(16)")
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
//	private int product;
	
	@ManyToOne
	@JoinColumn(name = "offer_id")
	private Offer offer;

	public ProductOffer(Product product, Offer offer) {
		super();
		this.product = product;
		this.offer = offer;
	}

	
	//to review in future
 
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}
	
	
	

}

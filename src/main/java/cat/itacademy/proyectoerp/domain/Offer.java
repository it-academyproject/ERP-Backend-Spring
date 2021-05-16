package cat.itacademy.proyectoerp.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.Nullable;

@Entity
@Table(name = "offers")
public class Offer {

	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
	@Column(name = "id", columnDefinition = "BINARY(16)")
	private UUID id;
	
	@Column (name = "description", length = 150, nullable = true)
	private String description;
	
	@Column
	@Enumerated(EnumType.STRING)
	private OfferType offertype;
	
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name="start_date")
    private LocalDateTime startDate;
	
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name="end_date")
    private LocalDateTime endDate;
	
	@Column
	@Enumerated(EnumType.STRING)
	private OfferApplied applied;
	
	@ManyToOne
	@JoinColumn(name = "freeproducts_id", referencedColumnName = "id")
	@Nullable
	private FreeProducts freeproducts;
	
	
	@ManyToOne
	@JoinColumn(name = "directdiscount_id", referencedColumnName = "id")
	@Nullable
	private DirectDiscount directdiscount;

	//Constructors
	public Offer() {
		
	}
	
	public Offer(String description, OfferType offertype, LocalDateTime startDate, LocalDateTime endDate, 
			OfferApplied applied,FreeProducts freeproducts) {
		this.description = description;
		this.offertype = offertype;
		this.startDate = startDate;
		this.endDate = endDate;
		this.applied = applied;
		this.freeproducts = freeproducts;
		this.directdiscount = null;
	}
	
	public Offer(String description, OfferType offertype, LocalDateTime startDate, LocalDateTime endDate, 
			OfferApplied applied,DirectDiscount directdiscount) {
		this.description = description;
		this.offertype = offertype;
		this.startDate = startDate;
		this.endDate = endDate;
		this.applied = applied;
		this.directdiscount = directdiscount;
		this.freeproducts = null;
	}
	
	
	//Getters & Setters
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public OfferType getOffertype() {
		return offertype;
	}

	public void setOffertype(OfferType offertype) {
		this.offertype = offertype;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public OfferApplied getApplied() {
		return applied;
	}

	public void setApplied(OfferApplied applied) {
		this.applied = applied;
	}

	public FreeProducts getFreeproducts() {
		return freeproducts;
	}

	public void setFreeproducts(FreeProducts freeproducts) {
		this.freeproducts = freeproducts;
	}

	public DirectDiscount getDirectdiscount() {
		return directdiscount;
	}

	public void setDirectdiscount(DirectDiscount directdiscount) {
		this.directdiscount = directdiscount;
	}

	public UUID getId() {
		return id;
	}

	 
}

package cat.itacademy.proyectoerp.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "offers")
public class Offer implements Serializable {
	

	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id")
	@Type(type="uuid-char")
	private UUID id;
	
	@NotNull(message = "description is mandatory")
	private String description;	
	
	@NotNull(message = "discount is mandatory")	
	private double discount;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name="start_date")
	@NotNull(message = "startDate is mandatory")
	private LocalDate startDate;
	
	@NotNull(message = "endDate is mandatory")
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name="end_date")
	private LocalDate endDate;
	
	@OneToMany(mappedBy = "offer", cascade = {CascadeType.ALL})
	private List<Product> productos = new ArrayList<Product>();
	
	public Offer() {
		
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
		this.endDate = endDate;
	}	

	public List<Product> getProductos() {
		return productos;
	}

	public void setProductos(List<Product> productos) {
		this.productos = productos;
	}

	private static final long serialVersionUID = 1L;

}

package cat.itacademy.proyectoerp.domain;

import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "shops")
public class Shop {

	@JsonProperty("id")
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "shop_id", columnDefinition = "BINARY(16)")
	private UUID id;

	@JsonProperty("brand_name")
	@Column(length = 50, nullable = false)
	@NotBlank(message = "Brand name is mandatory")
	@Size(max = 50)
	private String brandName;

	@JsonProperty("company_name")
	@Column(length = 50, nullable = false)
	@Size(max = 50)
	@NotBlank(message = "Company name is mandatory")
	private String companyName;

	@Column(length = 50, nullable = false)
	@Size(max = 50)
	@NotBlank(message = "NIF is mandatory")
	private String nif;
	
	@Column(length = 11, nullable = false)
	@NotNull(message = "Phone is mandatory")
	private Integer phone;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", referencedColumnName = "id", unique = true)
	@NotNull(message = "You have to assign this shop to an address")
	@Valid
	private Address address;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@CreationTimestamp
	@Column(name="creation_date",  columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date creationDate ;
	
	public Shop() {
	}
	
	public Shop(UUID id, String brandName, String companyName, String nif, Integer phone, Address address) {
		this.id = id;
		this.brandName = brandName;
		this.companyName = companyName;
		this.nif = nif;
		this.phone = phone;
		this.address = address;
	}

	public Shop(String brandName, String companyName,  String nif, Integer phone, Address address) {
		this.brandName = brandName;
		this.companyName = companyName;
		this.nif = nif;
		this.phone = phone;
		this.address = address;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public Integer getPhone() {
		return phone;
	}

	public void setPhone(Integer phone) {
		this.phone = phone;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}	
}

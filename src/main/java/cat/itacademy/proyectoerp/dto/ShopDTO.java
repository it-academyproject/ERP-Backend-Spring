package cat.itacademy.proyectoerp.dto;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import cat.itacademy.proyectoerp.domain.Address;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "brand_name", "company_name", "nif", "phone", "creation_date", "address","web_address" })
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ShopDTO {

	private UUID id;

	@JsonProperty("brand_name")
	private String brandName;

	@JsonProperty("company_name")
	private String companyName;

	private String nif;
	
	private Integer phone;

	@JsonProperty("creation_date")
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date creationDate ;
	
	private Address address;
	
	@JsonProperty("web_address")
	private String webAddress;
		
	public ShopDTO() {
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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getWebAddress() {
		return webAddress;
	}

	public void setWebAddress(String webAddress) {
		this.webAddress = webAddress;
	}

	@Override
	public String toString() {
		return "ShopDTO [brandName=" + brandName + ", companyName=" + companyName + ", phone=" + phone + ", address="
				+ address + ", webAddress=" + webAddress + "]";
	}
	
	
}

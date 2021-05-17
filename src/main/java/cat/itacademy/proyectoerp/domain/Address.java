package cat.itacademy.proyectoerp.domain;

import java.util.UUID;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.GenericGenerator;

import com.sun.istack.Nullable;

@Entity
@Table(name = "addresses")
public class Address {

	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", columnDefinition = "BINARY(16)")
	private UUID id;

	@NotBlank(message = "Street name is mandatory")
	private String street;

	@NotBlank(message = "Floor number is mandatory")
	private String number;

	@NotBlank(message = "City is mandatory")
	private String city;

	@NotBlank(message = "Country is mandatory")
	private String country;

	@NotBlank(message = "Zipcode is mandatory")
	@Pattern(regexp = "^\\d{5}([-]|\\s*)?(\\d{4})?$", message = "Zipcode format is not valid. Valid formats: 08018, 08018 1234, 08018-1234, 080181234")
	@Column(name = "zip_code")
	private String zipcode;

	@OneToOne(mappedBy = "address")
	private Client client;


	public Address() {
	}


	public Address(String street, String number, String city, String country, String zipcode) {
		this.street = street;
		this.number = number;
		this.city = city;
		this.country = country;
		this.zipcode = zipcode;
	}

	public Address(UUID id, String street, String number, String city, String country, String zipcode, Client client) {
		this.id = id;
		this.street = street;
		this.number = number;
		this.city = city;
		this.country = country;
		this.zipcode = zipcode;
		this.client = client;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}


	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
}


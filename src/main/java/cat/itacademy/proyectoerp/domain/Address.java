package cat.itacademy.proyectoerp.domain;

import java.util.UUID;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.sun.istack.Nullable;

@Entity
@Table(name = "addresses")

public class Address {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
	@Type(type="uuid-char")
    private UUID id;

    @NotBlank(message = "Street is mandatory")
    private String street;

    @NotBlank(message = "Number is mandatory")
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

    public Address(Address address) {
        this.street = address.getStreet();
        this.number = address.getNumber();
        this.city = address.getCity();
        this.country = address.getCountry();
        this.zipcode = address.getZipcode();
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

    // Fix recursive loop when requesting
    @JsonIgnore
    @JsonProperty(value = "client")
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}


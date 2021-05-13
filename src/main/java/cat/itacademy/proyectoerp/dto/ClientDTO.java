package cat.itacademy.proyectoerp.dto;

import java.io.Serializable;
import java.util.UUID;

import cat.itacademy.proyectoerp.domain.Address;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sun.istack.Nullable;

/**
 * DTO Class for Clients. This class return id, adress, dni, image and username
 * params.
 * 
 * 
 * @author
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ClientDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID id;
	private String image;
	private String username;
	private String nameAndSurname;

	private AddressDTO address;
	private AddressDTO shippingAddress;
	private UserDTO user;
	private MessageDTO message;


	// SETERRS AND GETTERS

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNameAndSurname() {
		return nameAndSurname;
	}

	public void setNameAndSurname(String nameAndSurname) {
		this.nameAndSurname = nameAndSurname;
	}

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	public AddressDTO getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(AddressDTO shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public MessageDTO getMessage() {
		return message;
	}

	public void setMessage(MessageDTO message) {
		this.message = message;
	}

}
package cat.itacademy.proyectoerp.dto;

import java.io.Serializable;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * DTO Class for Clients. This class return id, adress, dni, image and username
 * params.
 * 
 * 
 * @author
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID id;
	private String address;
	private String dni;
	private String image;

	private String username;
	// private Long id;

	// SETERRS AND GETTERS

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
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

}

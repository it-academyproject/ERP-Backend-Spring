package cat.itacademy.proyectoerp.domain;

/**
 * @author Piero Reppucci
 *
 */

public class StandarRegistration {
	/**
	 * Attributes.
	 */
	
	private String username;
	private String password;
	private String address;
	private String dni;
	private String image;
	private String name_surname;
	
	/**
	 * Constructor.
	 */
	
	private StandarRegistration() {}
	
	/**
	 * Getters and setters.
	 */
	
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getAddress() {
		return address;
	}
	public String getDni() {
		return dni;
	}
	public String getImage() {
		return image;
	}
	public String getNameAndSurname() {
		return name_surname;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public void setNameAndSurname(String nameAndSurname) {
		this.name_surname = nameAndSurname;
	}


	@Override
	public String toString() {
		return "StandarRegistration [username=" + username + ", password=" + password + ", address=" + address
				+ ", dni=" + dni + ", image=" + image + ", nameAndSurname=" + name_surname + "]";
	}

	
}

package cat.itacademy.proyectoerp.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sun.istack.Nullable;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * @author Piero Reppucci
 *
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class StandardRegistration {
    /**
     * Attributes.
     */

    @Email(message = "Email incorrect")
    @Size(min = 6, max = 50)
    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[_!@#$%?&+=]).*$", message = "Password invalid. Minim 8 characters with 1 upercase, 1 lowercase, 1 special symbol, 1 number)")
    private String password;

    @NotBlank(message = "DNI is mandatory")
    private String dni;

    private String image;

    @NotBlank(message = "Name and surname is mandatory")
    private String nameAndSurname;

    @NotNull(message = "You have to assign this client to an address")
    @Valid
    private Address address;

    @Nullable
    @Valid
    private Address shippingAddress;

    /**
     * Constructor.
     */

    private StandardRegistration() {}

    /**
     * Getters and setters.
     */
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getDni() {
        return dni;
    }
    public String getImage() {
        return image;
    }
    public String getNameAndSurname() {
        return nameAndSurname;
    }
    public Address getAddress() {
        return address;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public void setNameAndSurname(String nameAndSurname) {
        this.nameAndSurname = nameAndSurname;
    }
    public void setAddress(Address address) {
        this.address = address;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
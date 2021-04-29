package cat.itacademy.proyectoerp.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author Piero Reppucci
 *
 */

public class StandardRegistration {
    /**
     * Attributes.
     */

    @Email(message = "email incorrect")
    @Size(min = 6, max = 50)
    private String username;
    @Pattern(regexp = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%?&+=]).*$", message = "Password invalid. Minim 8 characters with 1 upercase, 1 lowercase, 1 special symbol, 1 number)")
    private String password;
    private Address address;
    private String dni;
    private String image;
    private String name_surname;

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
    public Address getAddress() {
        return address;
    }
    public String getDni() {
        return dni;
    }
    public String getImage() {
        return image;
    }
    public String getName_surname() {
        return name_surname;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public void setName_surname(String nameAndSurname) {
        this.name_surname = nameAndSurname;
    }


    @Override
    public String toString() {
        return "StandardRegistration [username=" + username + ", password=" + password + ", address=" + address
                + ", dni=" + dni + ", image=" + image + ", name_surname=" + name_surname + "]";
    }


}
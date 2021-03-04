package cat.itacademy.proyectoerp.domain;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class Employee {

  @Id
  @Column(name = "id", columnDefinition = "BINARY(16)")
  private UUID id = UUID.randomUUID();

  private double salary;
  private String email;
  private String dni;
  private int phone;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;


  public Employee(){};

  public Employee(double salary, String email, String dni, int phone, List<Order> orders, User user) {
    this.id = UUID.randomUUID();
    this.salary = salary;
    this.email = email;
    this.dni = dni;
    this.phone = phone;
    this.user = user;
  }

  public Employee(double salary, String email, String dni, int phone, User user) {
    this.salary = salary;
    this.email = email;
    this.dni = dni;
    this.phone = phone;
    this.user = user;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public double getSalary() {
    return salary;
  }

  public void setSalary(double salary) {
    this.salary = salary;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getDni() {
    return dni;
  }

  public void setDni(String dni) {
    this.dni = dni;
  }

  public int getPhone() {
    return phone;
  }

  public void setPhone(int phone) {
    this.phone = phone;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}

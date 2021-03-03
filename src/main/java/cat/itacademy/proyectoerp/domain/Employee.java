package cat.itacademy.proyectoerp.domain;

import cat.itacademy.proyectoerp.util.StringToListConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.aspectj.weaver.ast.Or;

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
  private int phone;

  @JsonIgnore
  @OneToMany(mappedBy = "employee", cascade = {
          CascadeType.ALL
  })
  private List<Order> orders;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;


  public Employee(){};

  public Employee(double salary, String email, int phone, List<Order> orders, User user) {
    this.id = UUID.randomUUID();
    this.salary = salary;
    this.email = email;
    this.phone = phone;
    this.orders = orders;
    this.user = user;
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

  public int getPhone() {
    return phone;
  }

  public void setPhone(int phone) {
    this.phone = phone;
  }

  public List<Order> getOrders() {
    return orders;
  }

  public void setOrders(List<Order> orders) {
    this.orders = orders;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}

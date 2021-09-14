package cat.itacademy.proyectoerp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "employees")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Employee {
	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id")
	@Type(type="uuid-char")
	private UUID id;
	
	@NotNull(message = "Name is mandatory")
	private String name;
	@NotNull(message = "Surname is mandatory")
	private String surname;
	@NotNull(message = "Salary is mandatory")
	private Double salary;
	@NotBlank(message = "DNI is mandatory")
	private String dni;
	@NotNull(message = "Phone is mandatory")
	private Integer phone;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@NotNull(message = "in_date is mandatory")
	@Column(name="in_date")
	private LocalDate inDate;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name="out_date")
	private LocalDate outDate;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "id", unique = true, nullable = false)
	@NotNull(message = "You have to assign this employee to a user")
	@Valid
	private User user;
	
	public Employee(){};
	
	public Employee(UUID id, String name, String surname, Double salary, String dni, Integer phone,
			LocalDate inDate, LocalDate outDate, List<Order> orders, User user) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.salary = salary;
		this.dni = dni;
		this.phone = phone;
		this.inDate = inDate;
		this.outDate = outDate;
		this.user = user;
	}
	
	public Employee(String name, String surname, Double salary, String dni, Integer phone,
			LocalDate inDate, LocalDate outDate, User user) {
		this.name = name;
		this.surname = surname;
		this.salary = salary;
		this.dni = dni;
		this.phone = phone;
		this.inDate = inDate;
		this.outDate = outDate;
		this.user = user;
	}
	
	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public Double getSalary() {
		return salary;
	}
	
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	
	public String getDni() {
		return dni;
	}
	
	public void setDni(String dni) {
		this.dni = dni;
	}
	
	public Integer getPhone() {
		return phone;
	}
	
	public void setPhone(Integer phone) {
		this.phone = phone;
	}
	
	public LocalDate getInDate() {
		return inDate;
	}
	
	public void setInDate(LocalDate inDate) {
		this.inDate = inDate;
	}
	
	public LocalDate getOutDate() {
		return outDate;
	}
	
	public void setOutDate(LocalDate outDate) {
		this.outDate = outDate;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
}

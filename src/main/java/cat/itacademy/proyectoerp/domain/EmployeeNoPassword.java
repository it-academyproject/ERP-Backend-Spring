package cat.itacademy.proyectoerp.domain;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

//this class is was specifically made for responses when we don't want to give password
public class EmployeeNoPassword {
	  private UUID id ;

	  private Double salary;
	  private String dni;
	  private Integer phone;
	  @JsonFormat(pattern = "dd-MM-yyyy")
	  private LocalDate inDate;
	  @JsonFormat(pattern = "dd-MM-yyyy") 
	  private LocalDate outDate;
	  private UserNoPassword user;

	public EmployeeNoPassword(Employee employee) {
		this.id = employee.getId();
		this.salary = employee.getSalary();
		this.dni = employee.getDni();
		this.inDate = employee.getInDate();
		this.outDate = employee.getOutDate();
		this.user = new UserNoPassword(employee.getUser());
		
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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

	public UserNoPassword getUser() {
		return user;
	}

	public void setUser(UserNoPassword user) {
		this.user = user;
	}

	public LocalDate getOutDate() {
		return outDate;
	}

	public void setOutDate(LocalDate outDate) {
		this.outDate = outDate;
	}
	
	

}

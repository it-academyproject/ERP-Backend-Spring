package cat.itacademy.proyectoerp.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import cat.itacademy.proyectoerp.domain.*;
import cat.itacademy.proyectoerp.service.OrderServiceImpl;

//this class is was specifically made for responses when we don't want to give password
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class EmployeeNoPasswordDTO {
	
	  @Autowired
	  OrderServiceImpl orderService;
	  
	  
	  private UUID id ;

	  private Double salary;
	  private String dni;
	  private Integer phone;
	  @JsonFormat(pattern = "dd-MM-yyyy")
	  private LocalDate inDate;
	  @JsonFormat(pattern = "dd-MM-yyyy") 
	  private LocalDate outDate;
	  private UserNoPasswordDTO user;
	  private String name;
	  private int totalOrders;
	  private double totalSold;
	public EmployeeNoPasswordDTO(Employee employee) {
		this.id = employee.getId();
		this.salary = employee.getSalary();
		this.dni = employee.getDni();
		this.inDate = employee.getInDate();
		this.outDate = employee.getOutDate();
		this.user = new UserNoPasswordDTO(employee.getUser());
		this.phone = employee.getPhone();
		this.name = employee.getUser().getUsername();
		/*
        List<Order> ordersOfEmployeeList = orderService.findOrdersByEmployeeId(this.id);
        this.totalOrders = ordersOfEmployeeList.size();
        this.totalSold = 0.0;
        for (Order order: ordersOfEmployeeList) {
        	this.totalSold = order.getTotal();
        }*/
	}
	public void calculateTotalOrdersAndTotalSold(List<Order> ordersOfEmployeeList) {
		this.totalOrders = ordersOfEmployeeList.size();
        this.totalSold = 0.0;
        for (Order order: ordersOfEmployeeList) {
        	this.totalSold = order.getTotal();
        }
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

	public UserNoPasswordDTO getUser() {
		return user;
	}

	public void setUser(UserNoPasswordDTO user) {
		this.user = user;
	}

	public LocalDate getOutDate() {
		return outDate;
	}

	public void setOutDate(LocalDate outDate) {
		this.outDate = outDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTotalOrders() {
		return totalOrders;
	}
	public void setTotalOrders(int totalOrders) {
		this.totalOrders = totalOrders;
	}
	public double getTotalSold() {
		return totalSold;
	}
	public void setTotalSold(double totalSold) {
		this.totalSold = totalSold;
	}
	
	

}

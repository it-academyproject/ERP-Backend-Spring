package cat.itacademy.proyectoerp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class EmployeeDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private UUID id;
    private Double salary;
    private String dni;
    private Integer phone;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate inDate;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate outDate;

    private UserDTO user;
    private MessageDTO message;
    
    //b-79. added by Joan
    private Double totalSales;
    private int totalOrdersAttended;
    
    public Double getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(Double totalSales) {
		this.totalSales = totalSales;
	}

	public int getTotalOrdersAttended() {
		return totalOrdersAttended;
	}

	public void setTotalOrdersAttended(int totalOrdersAttended) {
		this.totalOrdersAttended = totalOrdersAttended;
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

	
    
}

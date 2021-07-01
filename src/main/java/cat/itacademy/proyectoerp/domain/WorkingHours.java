package cat.itacademy.proyectoerp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@IdClass(WorkingHoursId.class)
@Table (name = "working_hours")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkingHours {

	@Id
	@Column(name="date")
	private LocalDate date;
  
	@JsonFormat(pattern = "HH:mm:ss")
	@Column(name="check_in")
	private LocalTime checkIn;
	
	@JsonFormat(pattern = "HH:mm:ss")
	@Column(name="check_out")
	private LocalTime checkOut;

	@Id
	@NotNull(message = "You have to assign an employee id")
	@Column(name="employee_id")
	private UUID employeeId;

	public WorkingHours(){
	  
	};

	public WorkingHours(LocalDate date, LocalTime checkIn, LocalTime checkOut, UUID employeeId) {
		this.date = date;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.employeeId = employeeId;
	}
  
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public LocalTime getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(LocalTime checkIn) {
		this.checkIn = checkIn;
	}
	
	public LocalTime getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(LocalTime checkOut) {
		this.checkOut = checkOut;
	}

	public UUID getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(UUID employeeId) {
		this.employeeId = employeeId;
	}
}

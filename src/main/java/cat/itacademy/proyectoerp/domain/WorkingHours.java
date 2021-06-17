package cat.itacademy.proyectoerp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table (name = "working_hours")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkingHours {
	
	@Id
	@GeneratedValue(generator = "UUID")
    @GenericGenerator( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", columnDefinition = "BINARY(16)")
	@JsonProperty("id")
	private UUID id;

	@Column(name="day")
	private LocalDate day;
  
	@NotNull(message = "check_in is mandatory")
	@JsonFormat(pattern = "HH:mm:ss")
	@Column(name="check_in")
	private LocalTime checkIn;
	
	@NotNull(message = "check_out is mandatory")
	@JsonFormat(pattern = "HH:mm:ss")
	@Column(name="check_out")
	private LocalTime checkOut;

	@NotNull(message = "You have to assign an employee id to the working hours")
	private UUID employeeId;

	public WorkingHours(){
	  
	};

	public WorkingHours(LocalDate day, LocalTime checkIn, LocalTime checkOut, UUID employeeId) {
		this.day = day;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.employeeId = employeeId;
	}

	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
  
	public LocalDate getDay() {
		return day;
	}
	public void setDayOfTheWeek(LocalDate day) {
		this.day = day;
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

package cat.itacademy.proyectoerp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table (name = "working_hours")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkingHours {
	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", columnDefinition = "BINARY(16)")
	private UUID id;

  @JsonFormat(pattern = "HH:mm:ss")
  @NotNull(message = "check_in is mandatory")
  @Column(name="check_in")
  private LocalDateTime checkIn;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "employee_id", referencedColumnName = "id", unique = true, nullable = false)
  @NotNull(message = "You have to assign this checkIn to an employee")
  @Valid
  private Employee employee;

  public WorkingHours(){
	  
  };

  public WorkingHours(LocalDateTime checkIn, Employee employee) {
    this.checkIn = checkIn;
    this.employee = employee;
  }

  public UUID getId() {
		return id;
  }
  public void setId(UUID id) {
 	this.id = id;
  }
	
  public LocalDateTime getCheckIn() {
    return checkIn;
  }

  public void setCheckIn(LocalDateTime checkIn) {
    this.checkIn = checkIn;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }
}

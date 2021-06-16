package cat.itacademy.proyectoerp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkingHours {
	
  @Id
  @Column(name = "id", columnDefinition = "BINARY(16)")
  private UUID id = UUID.randomUUID();

  @JsonFormat(pattern = "HH:mm:ss")
  @NotNull(message = "in_date is mandatory")
  @Column(name="in_date")
  private LocalDateTime checkIn;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "employee_id", referencedColumnName = "id", unique = true, nullable = false)
  @NotNull(message = "You have to assign this checkIn to an employee")
  @Valid
  private Employee employee;

  public WorkingHours(){};

  public WorkingHours(UUID id, LocalDateTime checkIn, Employee employee) {
    this.id = id;
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

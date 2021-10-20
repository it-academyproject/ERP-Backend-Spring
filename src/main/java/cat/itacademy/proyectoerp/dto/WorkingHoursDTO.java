package cat.itacademy.proyectoerp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class WorkingHoursDTO implements Serializable {
	
	

	private static final long serialVersionUID = 1L;

	private LocalDate date;
		
	private LocalTime checkIn;
	
    private LocalTime checkOut;
     
    private UUID employeeId;

    
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
	

	
	public static class WorkingHoursDTOBuilder {

		private LocalDate date;

		private LocalTime checkIn;

		private LocalTime checkOut;

		private UUID employeeId;

		public WorkingHoursDTOBuilder withDate(LocalDate date) {
			this.date = date;
			return this;
		}

		public WorkingHoursDTOBuilder withCheckIn(LocalTime checkIn) {
			this.checkIn = checkIn;
			return this;
		}

		public WorkingHoursDTOBuilder withCheckOut(LocalTime checkOut) {
			this.checkOut = checkOut;
			return this;
		}

		public WorkingHoursDTOBuilder withEmployeeId(UUID employeeId) {
			this.employeeId = employeeId;
			return this;
		}

		public WorkingHoursDTO build() {
			WorkingHoursDTO workingHoursDTO = new WorkingHoursDTO();
			workingHoursDTO.checkIn = this.checkIn;
			workingHoursDTO.checkOut = this.checkOut;
			workingHoursDTO.date = this.date;
			workingHoursDTO.employeeId = this.employeeId;

			return workingHoursDTO;
		}
	}
    

}

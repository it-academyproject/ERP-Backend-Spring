package cat.itacademy.proyectoerp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.io.Serializable;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class WorkingHoursToStringDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String date;

	private String checkIn;

	private String checkOut;

	private UUID employeeId;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(String checkIn) {
		this.checkIn = checkIn;
	}

	public String getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(String checkOut) {
		this.checkOut = checkOut;
	}

	public UUID getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(UUID employeeId) {
		this.employeeId = employeeId;
	}

	public static class WorkingHoursToStringDTOBuilder {

		private String date;

		private String checkIn;

		private String checkOut;

		private UUID employeeId;

		public WorkingHoursToStringDTOBuilder withDate(String date) {
			this.date = date;
			return this;
		}

		public WorkingHoursToStringDTOBuilder withCheckIn(String checkIn) {
			this.checkIn = checkIn;
			return this;
		}

		public WorkingHoursToStringDTOBuilder withCheckOut(String checkOut) {
			this.checkOut = checkOut;
			return this;
		}

		public WorkingHoursToStringDTOBuilder withEmployeeId(UUID employeeId) {
			this.employeeId = employeeId;
			return this;
		}

		public WorkingHoursToStringDTO build() {
			WorkingHoursToStringDTO workingHoursToStringDTO = new WorkingHoursToStringDTO();
			workingHoursToStringDTO.checkIn = this.checkIn;
			workingHoursToStringDTO.checkOut = this.checkOut;
			workingHoursToStringDTO.date = this.date;
			workingHoursToStringDTO.employeeId = this.employeeId;

			return workingHoursToStringDTO;
		}
	}

}

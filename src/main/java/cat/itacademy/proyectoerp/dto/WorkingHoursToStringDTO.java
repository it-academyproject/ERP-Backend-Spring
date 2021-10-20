package cat.itacademy.proyectoerp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import cat.itacademy.proyectoerp.dto.WorkingHoursDTO.WorkingHoursDTOBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

	/**
	 * @param LocalDate
	 * @return String
	 */
	/*
	public static String localDateToString(LocalDate localDate) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String strDate = localDate.format(formatter);

		return strDate;
	}
	*/

	/**
	 * @param LocalTime
	 * @return String
	 */
	/*
	 * public static String localTimeToString(LocalTime localTime) {
	 * 
	 * DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss"); String
	 * strDate = localTime.format(formatter);
	 * 
	 * return strDate; }
	 */

	/*
	 * @Override public String toString() { return "WorkingHoursDTO [date=" +
	 * localDateToString() + ", checkIn=" + localTimeToString(checkIn) +
	 * ", checkOut=" + localTimeToString(checkOut) + ", employeeId=" + employeeId +
	 * "]"; }
	 */
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
			;
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

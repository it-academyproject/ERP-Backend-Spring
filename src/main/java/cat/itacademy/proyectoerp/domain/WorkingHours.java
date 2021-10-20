package cat.itacademy.proyectoerp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
	//private String date;
  
	@JsonFormat(pattern = "HH:mm:ss")
	@Column(name="check_in")
	private LocalTime checkIn;
	//private String checkIn;
	@JsonFormat(pattern = "HH:mm:ss")
	@Column(name="check_out")
	private LocalTime checkOut;
	//private String checkOut;
	@Id
	@NotNull(message = "You have to assign an employee id")
	@Type(type="org.hibernate.type.UUIDCharType")
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
	/*
	public WorkingHours(LocalDate date, LocalTime checkIn, LocalTime checkOut, UUID employeeId) {
		this.date = localDateToString(date);
		this.checkIn = localTimeToString(checkIn);
		this.checkOut = localTimeToString(checkOut);
		this.employeeId = employeeId;
	}
	public WorkingHours(String date, String checkIn, String checkOut, UUID employeeId) {
		this.date = date;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.employeeId = employeeId;
	}
	*/
  /*
	public String getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = localDateToString(date);
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(LocalTime checkIn) {
		this.checkIn = localTimeToString(checkIn);
	}
	public void setCheckIn(String checkIn) {
		this.checkIn = checkIn;
	}
	
	public String getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(LocalTime checkOut) {
		this.checkOut = localTimeToString(checkOut);
	}
	public void setCheckOut(String checkOut) {
		this.checkOut = checkOut;
	}
	*/
	
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

	 /**
		 * @param LocalDate
		 * @return String
		 */
		public String localDateToString() {

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String strDate = this.date.format(formatter);

			return strDate;
		}

		/**
		 * @param LocalTime
		 * @return String
		 */
		public String localTimeToString(LocalTime localTime) {

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
			String strDate = localTime.format(formatter);

			return strDate;
		}
		/**
		 * @param LocalTime
		 * @return String
		 */
		public String checkOutToString() {

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
			String strDate = this.checkOut.format(formatter);

			return strDate;
		}
		
		/**
		 * @param LocalTime
		 * @return String
		 */
		public String checkInToString() {

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
			String strDate = this.checkIn.format(formatter);

			return strDate;
		}


	@Override
	public String toString() {
		return "WorkingHoursDTO [date=" + localDateToString() + ", checkIn=" + localTimeToString(checkIn) + ", checkOut=" + localTimeToString(checkOut) + ", employeeId="
				+ employeeId + "]";
	}
	
}

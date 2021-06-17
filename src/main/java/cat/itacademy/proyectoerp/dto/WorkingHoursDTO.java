package cat.itacademy.proyectoerp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class WorkingHoursDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private int id;
	private String dayOfTheWeek;
	private LocalTime checkIn;
    private LocalTime checkOut;
    private UUID employeeId;
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

    public String getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public void setDayOfTheWeek(String dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;
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

    public void setEmployee(UUID employeeId) {
        this.employeeId = employeeId;
    }

}

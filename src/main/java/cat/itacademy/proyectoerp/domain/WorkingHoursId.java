package cat.itacademy.proyectoerp.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class WorkingHoursId implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private UUID employeeId;
	
	private LocalDate date;
	
	// default constructor
	
	public WorkingHoursId() {
	}
	
	public WorkingHoursId(UUID employeeId, LocalDate date) {
		this.employeeId = employeeId;
		this.date = date;
	}
	
	// equals() and hashCode()

}

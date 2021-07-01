package cat.itacademy.proyectoerp.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
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
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkingHoursId accountId = (WorkingHoursId) o;
        return employeeId.equals(accountId.employeeId) &&
        		date.equals(accountId.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, date);
    }

}

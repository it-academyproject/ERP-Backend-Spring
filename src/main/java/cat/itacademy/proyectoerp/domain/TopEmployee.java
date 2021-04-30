package cat.itacademy.proyectoerp.domain;

import java.util.UUID;

public class TopEmployee {
	private UUID employee_id;
	private Double total;

	public TopEmployee(UUID employee_id, Double total) {
		this.employee_id = employee_id;
		this.total = total;
	}

	public UUID getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(UUID employee_id) {
		this.employee_id = employee_id;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	
}

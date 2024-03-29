package cat.itacademy.proyectoerp.dto;

import java.util.UUID;

public class TopEmployeeDTO {

	
	private UUID id;
	private Double total;
	private String dni;
	

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public TopEmployeeDTO(UUID id, Double total) {
		this.id = id;
		this.total = total;
	}
	
	public TopEmployeeDTO() {
		super();
	}
	
	
	//GETTERS & SETTERS
	
	public UUID getId() {
		return id;
	}
	
	
	public void setId(UUID id) {
		this.id = id;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	

	

}